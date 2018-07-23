package com.yang.wallpapers.utils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

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
//        hierarchy.setPlaceholderImage(R.drawable.image_def);
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
}
