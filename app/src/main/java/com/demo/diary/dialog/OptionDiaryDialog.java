package com.demo.diary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.demo.diary.MainActivity;
import com.demo.diary.R;
import com.demo.diary.adapter.DiaryAdapter;
import com.demo.diary.bean.Diary;
import com.demo.diary.utils.T;

import org.litepal.crud.DataSupport;

/**
 * Created by GuangSIR on 2017/12/1.
 * Description :
 */

public class OptionDiaryDialog extends Dialog {
    private Context mContext;
    private DiaryAdapter mAdapter;
    private Diary mDiary;
    private int mPosition;


    public OptionDiaryDialog(@NonNull Context context, DiaryAdapter adapter, Diary diary, int potion) {
        super(context);
        mContext = context;
        mAdapter = adapter;
        mDiary = diary;
        mPosition = potion;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_option_diary, null);
        setContentView(view);

        Button deleteBtn = view.findViewById(R.id.btn_delete);
        Button editBtn = view.findViewById(R.id.btn_edit);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataSupport.delete(Diary.class, mDiary.getId()) != 0) {
                    mAdapter.removeItem(mPosition);
                    T.showShort(mContext, "删除成功！");
                    OptionDiaryDialog.this.dismiss();
                } else {
                    T.showShort(mContext, "Oops,删除失败！");
                }
            }
        });
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionDiaryDialog.this.dismiss();
                new EditDiaryDialog((MainActivity) mContext, mAdapter,mDiary, mPosition).show();
            }
        });
    }
}
