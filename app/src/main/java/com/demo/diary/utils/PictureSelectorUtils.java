package com.demo.diary.utils;

import android.app.Activity;
import android.content.Intent;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by GuangSIR on 2017/11/30.
 * Description :
 */

public class PictureSelectorUtils {
    // 日记图片选择
    public static void selectDiaryPic(Activity activity) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage()) // 图片.ofImage()
                .selectionMode(PictureConfig.SINGLE) // 单选 PictureConfig.SINGLE
                .enableCrop(true) // 是否裁剪 true or false
                .withAspectRatio(3, 2) // int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .compress(true) // 是否压缩 true or false
                .hideBottomControls(false) // 是否隐藏uCrop工具栏
                .showCropGrid(false)// 是否显示裁剪矩形网格
                .cropCompressQuality(50)// 裁剪压缩质量 默认90 int
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code;
    }

    // 图片选择结果回调
    public static String imgSelectCallBack(int requestCode, int resultCode, Intent data) {
        String imgPath = null;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> mediaList = PictureSelector.obtainMultipleResult(data);
                    // LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    LocalMedia media = mediaList.get(0);
                    if(media.isCompressed()){
                        imgPath = media.getCompressPath();
                        L.d("imgSelectCallBack","---> media.isCompressed()");
                    } else if (media.isCut()) {
                        imgPath = media.getCutPath();
                        L.d("imgSelectCallBack","---> media.isCut()");
                    } else {
                        imgPath = media.getPath();
                        L.d("imgSelectCallBack","---> media");
                    }
            }
        }
        return imgPath;
    }
}
