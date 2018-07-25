package com.yang.wallpapers.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableBitmap;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;

/**
 * Created by
 * yangshuang on 2018/7/23.
 */

public class ImageUtil {
    /**
     * 加载图片
     */
    public static void setImage(SimpleDraweeView imageView, String url) {
        if (StringUtil.isEmpty(url)) return;
        setImage(imageView, url, null);
    }

    public static void setImage(SimpleDraweeView imageView, String url, ControllerListener controllerListener) {
        setImage(imageView, url, null, controllerListener);
    }

    public static void setImage(SimpleDraweeView imageView, String url, ScalingUtils.ScaleType type, ControllerListener controllerListener) {
        GenericDraweeHierarchy hierarchy = imageView.getHierarchy();
        if (type != null) {
            hierarchy.setActualImageScaleType(type);
        }
        PipelineDraweeControllerBuilder builder = Fresco.newDraweeControllerBuilder();
        builder.setUri(url);
        if (controllerListener != null) {
            builder.setControllerListener(controllerListener);
        }
        DraweeController controller = builder.build();
        imageView.setController(controller);
    }

    public static void getImageFromInternet(Context context, String url, final ImageLoadListener loadListener) {
        if (url == null || url.equals("")) return;
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).setProgressiveRenderingEnabled(true).build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, context);
        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            public void onNewResultImpl(@Nullable Bitmap bitmap) {
                loadListener.onResult(bitmap);
            }

            @Override
            public void onFailureImpl(DataSource dataSource) {
            }
        }, CallerThreadExecutor.getInstance());
    }

    /**
     * 从缓存中获取图片
     */
    public static Bitmap getBitmapFromCache(String url,Context context) {
        //获取缓存key
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).build();
        CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(imageRequest, context);
        //从内存缓存获取
        CloseableReference<CloseableImage> reference = ImagePipelineFactory.getInstance().getBitmapMemoryCache().get(cacheKey);
        try {
            if (reference != null) {
                CloseableImage image = reference.get();
                if (image != null && image instanceof CloseableBitmap) {
                    Bitmap bitmap = ((CloseableBitmap) image).getUnderlyingBitmap();
                    if (bitmap != null) {
                        return bitmap;
                    }
                }
            }
        } finally {
            CloseableReference.closeSafely(reference);
        }
        //从磁盘缓存获取
        BinaryResource resource = ImagePipelineFactory.getInstance().getMainFileCache().getResource(cacheKey);
        if (resource != null && resource instanceof FileBinaryResource) {
            final File file = ((FileBinaryResource) resource).getFile();
            return BitmapFactory.decodeFile(file.getPath());
        }
        return null;
    }

    /**
     * 清除指定的图片缓存
     *
     * @param url
     */
    public static void removeFromCache(String url) {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.evictFromCache(Uri.parse(url));
    }

    /**
     * 清除所有图片缓存
     */
    public static void clearCaches() {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearCaches();
    }

    public interface ImageLoadListener {
        void onResult(Bitmap bitmap);
    }
}
