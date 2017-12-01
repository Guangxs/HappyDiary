package com.demo.diary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.demo.diary.adapter.DiaryAdapter;
import com.demo.diary.bean.Diary;
import com.demo.diary.dialog.AddDiaryDialog;
import com.demo.diary.dialog.EditDiaryDialog;
import com.demo.diary.utils.PictureSelectorUtils;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mDiaryRv;
    private AddDiaryDialog mAddDiaryDialog;
    private EditDiaryDialog mEditDiaryDialog;
    private DiaryAdapter mAdapter;
    private RelativeLayout mMainRt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainRt = (RelativeLayout) findViewById(R.id.rt_main);
        mDiaryRv = (RecyclerView) findViewById(R.id.rv_diary);
        FloatingActionButton writeFab = (FloatingActionButton) findViewById(R.id.fab_write);

        writeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddDiaryDialog = new AddDiaryDialog(MainActivity.this, mAdapter);
                mAddDiaryDialog.show();
            }
        });

        // 构建数据库
        Connector.getDatabase();
        // 加载数据
        initData();
        // 初始化RecyclerView
        initRecyclerView();
    }

    private void initData() {
        List<Diary> diaryLists = DataSupport.order("id desc").find(Diary.class);
        mAdapter = new DiaryAdapter(diaryLists);
        mAdapter.setBackLayout(mMainRt);
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mDiaryRv.setLayoutManager(layoutManager);
        mDiaryRv.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String diaryPicPath = PictureSelectorUtils.imgSelectCallBack(requestCode, resultCode, data);
        if (diaryPicPath != null) {
            if (mAddDiaryDialog != null) {
                mAddDiaryDialog.setDiaryPic(diaryPicPath);
                mAddDiaryDialog = null;
            } else if (mEditDiaryDialog != null) {
                mEditDiaryDialog.setDiaryPic(diaryPicPath);
                mAddDiaryDialog = null;
            }
        }
    }

    public void setEditDiaryDialog(EditDiaryDialog editDiaryDialog) {
        mEditDiaryDialog = editDiaryDialog;
    }
}
