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

import com.demo.diary.MainActivity;
import com.demo.diary.R;
import com.demo.diary.adapter.DiaryAdapter;
import com.demo.diary.bean.Diary;
import com.demo.diary.utils.L;
import com.demo.diary.utils.PictureSelectorUtils;
import com.demo.diary.utils.T;

/**
 * Created by GuangSIR on 2017/11/30.
 * Description :
 */

public class EditDiaryDialog extends Dialog {
    private Context mContext;
    private DiaryAdapter mAdapter;
    private Diary mDiary;
    private int mPotion;

    private ImageView mDiaryPicIv;
    private String mDiaryPicPath;

    public EditDiaryDialog(@NonNull MainActivity context, DiaryAdapter adapter, Diary diary, int position) {
        super(context, R.style.Dialog_No_Title);
        mContext = context;
        mAdapter = adapter;
        mPotion = position;

        mDiary = diary;
        context.setEditDiaryDialog(this);
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

        String imgPath = mDiary.getPicPath();
        if (imgPath != null && imgPath.length() != 0) {
            mDiaryPicIv.setImageURI(Uri.parse(imgPath));
        }

        contentEt.setText(mDiary.getContent());

        mDiaryPicIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelectorUtils.selectDiaryPic((Activity) mContext);
                L.d("AddDiaryDialog", "---> choose diary pic");
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDiaryDialog.this.dismiss();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDiary.setContent(contentEt.getText().toString());
                mDiary.setPicPath(mDiaryPicPath);

                if (mDiary.update(mDiary.getId()) != 0) {
                    mAdapter.updateItem(mPotion, mDiary);
                    T.showShort(mContext, "保存成功！");
                    EditDiaryDialog.this.dismiss();
                } else {
                    T.showShort(mContext, "Oops！保存失败了");
                }

                L.d("Edit old diary", "---> " + mDiary);
            }
        });

    }

    // 用于MainActivity调用更改日记图片
    public void setDiaryPic(String imgPath) {
        mDiaryPicPath = imgPath;
        mDiaryPicIv.setImageURI(Uri.parse(imgPath));
    }
}
