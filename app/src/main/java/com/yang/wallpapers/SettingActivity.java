package com.yang.wallpapers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.hawk.Hawk;
import com.yang.wallpapers.utils.AppConfigConst;
import com.yang.wallpapers.utils.ImageUtil;

public class SettingActivity extends AppCompatActivity {

    private TextView cacheTv, countTv;
    private String cacheHint = "此操作会清除所有图片缓存  所有缓存：";
    private String countHint = "当前每个分类加载图片数量:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        cacheTv = findViewById(R.id.setting_cache_tv);
        countTv = findViewById(R.id.setting_count_tv);

        countTv.setText(countHint + Hawk.get(AppConfigConst.Key.CATEGORY_IMAGE_COUNT, "20"));

        cacheTv.setText(cacheHint + (Fresco.getImagePipelineFactory().getMainFileCache().getSize() / 1000) + " KB");
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_back_iv:
                finish();
                break;
            case R.id.setting_category_tv:
                startActivity(new Intent(this, CategoryActivity.class));
                break;
            case R.id.setting_count_tv:
                saveLoadCount();
                break;
            case R.id.setting_clear_tv:
                ImageUtil.clearCaches();
                cacheTv.setText(cacheHint + (Fresco.getImagePipelineFactory().getMainFileCache().getSize() / 1000) + " KB");
                break;
        }
    }

    private void saveLoadCount() {
        new MaterialDialog.Builder(this)
                .content("每天会获取偏好设置中选择分类的最热图片，此处设置平均每个分类要获取的数量")
                .inputType(InputType.TYPE_CLASS_NUMBER)
                .input("建议10到30", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        countTv.setText(countHint + input);
                        Hawk.put(AppConfigConst.Key.CATEGORY_IMAGE_COUNT, input.toString());
                    }
                }).show();
    }
}
