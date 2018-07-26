package com.yang.wallpapers;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yang.wallpapers.entity.ADeskImageResponse;
import com.yang.wallpapers.utils.AppConfigConst;
import com.yang.wallpapers.utils.ImageUtil;
import com.yang.wallpapers.utils.ToastUtils;
import com.yang.wallpapers.utils.WallPaperUtils;

public class ImageActivity extends AppCompatActivity {

    private SimpleDraweeView image;
    private TextView setBtn;
    private ADeskImageResponse.ResBean.VerticalBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        initView();

        bean = (ADeskImageResponse.ResBean.VerticalBean) getIntent().getSerializableExtra(AppConfigConst.Intent.IMAGE_DATA);
        if (bean == null || bean.getUrl() == null) return;
        ImageUtil.setImage(image, bean.getImg());
    }


    private void initView() {
        image = findViewById(R.id.image_sdv);
        setBtn = findViewById(R.id.image_set_tv);
    }

    public void onClick(View view) {
        if (ImageUtil.getBitmapFromCache(bean.getImg(), this) != null) {
            WallPaperUtils.setWallPaper(ImageUtil.getBitmapFromCache(bean.getImg(),this),this);
            WallPaperUtils.setLockWallPaper(ImageUtil.getBitmapFromCache(bean.getImg(),this),this);
        } else {
            ToastUtils.showShort("稍等一下");
        }
    }
}
