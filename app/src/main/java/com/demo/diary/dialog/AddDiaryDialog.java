package com.demo.diary.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.demo.diary.R;
import com.demo.diary.adapter.DiaryAdapter;
import com.demo.diary.bean.Diary;
import com.demo.diary.utils.L;
import com.demo.diary.utils.PictureSelectorUtils;
import com.demo.diary.utils.T;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by GuangSIR on 2017/11/30.
 * Description :
 */

public class AddDiaryDialog extends Dialog {
    private Context mContext;
    private DiaryAdapter mAdapter;
    private ImageView mDiaryPicIv;
    private String mDiaryPicPath;

    public AddDiaryDialog(@NonNull Context context, DiaryAdapter adapter) {
        super(context);
        mContext = context;
        mAdapter = adapter;
        // 点击空白处dialog不会消失
        this.setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_add_diary, null);
        setContentView(view);

        mDiaryPicIv = view.findViewById(R.id.iv_diary_pic);
        final EditText contentEt = view.findViewById(R.id.et_content);
        Button cancelBtn = view.findViewById(R.id.btn_cancel);
        Button saveBtn = view.findViewById(R.id.btn_save);

        mDiaryPicIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelectorUtils.selectDiaryPic((Activity) mContext);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDiaryDialog.this.dismiss();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = new SimpleDateFormat("yyyy年MM月dd日 EEEE HH:mm aa", Locale.CHINA).format(new Date());
                Diary diary = new Diary(mDiaryPicPath, date, contentEt.getText().toString());
                if (diary.save()) {
                    mAdapter.addItem(diary);
                    T.showShort(mContext, "保存成功！");
                    AddDiaryDialog.this.dismiss();
                } else {
                    T.showShort(mContext, "pic_oops！保存失败了");
                }
                L.d("Add new diary", "---> " + diary);
            }
        });

    }

    // 用于MainActivity调用更改日记图片
    public void setDiaryPic(String imgPath) {
        mDiaryPicPath = imgPath;
        mDiaryPicIv.setImageURI(Uri.parse(imgPath));
    }
}
