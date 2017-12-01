package com.demo.diary.adapter;

import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.diary.R;
import com.demo.diary.bean.Diary;
import com.demo.diary.dialog.OptionDiaryDialog;
import com.demo.diary.utils.L;

import java.util.List;


/**
 * Created by GuangSIR on 2017/11/30.
 * Description :
 */

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryItemViewholder> {
    private List<Diary> mDiarylists;
    private RelativeLayout mLayout;
//    private String[] layoutColors = new String[]{
//            "#FECEA8", "#BDF1F6", "#FFCEF3", "#D4D6C8",
//            "#F7E6B5", "#E9DCBE", "#D5DEF5", "#DFF4F3",
//            "#C5ECBE", "#E2EFF1", "#E9E2D0", "#B9BBDF",
//            "#C9FDD7", "#D3E0E2", "#FFD3B6", "#E7F0D2",
//            "#C0D9E5", "#FFE1B6", "#B6CDBD", "#FFE98A",
//            "#DDFEE4", "#EBE9F6", "#C0FFC2", "#E8AA8C",
//            "#F69D9D", "#E7EAA8", "#A7B99E", "#D3D6DB",
//            "#D1E4D1", "#FF9C6D", "#B5CFD8", "#C4E3CB",
//            "#FFFFC1", "#928B8B", "#D4D6C8", "#C0C5CD"};

    public DiaryAdapter(List<Diary> lists) {
        this.mDiarylists = lists;
    }


    class DiaryItemViewholder extends RecyclerView.ViewHolder {

        private CardView diaryCard;
        private ImageView diaryPicIv;
        private TextView diaryDateText, diaryContentText;

        public DiaryItemViewholder(View itemView) {
            super(itemView);
            diaryCard = itemView.findViewById(R.id.cd_diary);
            diaryPicIv = itemView.findViewById(R.id.iv_diary_pic);
            diaryDateText = itemView.findViewById(R.id.tv_diary_date);
            diaryContentText = itemView.findViewById(R.id.tv_diary_content);
        }
    }

    @Override
    public DiaryItemViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        DiaryItemViewholder holder = new DiaryItemViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diary, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final DiaryItemViewholder holder, final int position) {
        // 获取日记照片
        String picPath = mDiarylists.get(position).getPicPath();
        if (picPath != null && picPath.length() != 0) {
            holder.diaryPicIv.setImageURI(Uri.parse(picPath));
        }
        // 获取日记日期
        holder.diaryDateText.setText(mDiarylists.get(position).getDate());
        // 获取日记内容
        String content = mDiarylists.get(position).getContent();
        if (content == null || content.length() == 0) {
            holder.diaryContentText.setGravity(Gravity.CENTER);
            holder.diaryContentText.setText("呀，什么都没写呢~");
        } else {
            holder.diaryContentText.setGravity(Gravity.NO_GRAVITY);
            holder.diaryContentText.setText(content);
        }

        holder.diaryCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new OptionDiaryDialog(v.getContext(), DiaryAdapter.this, mDiarylists.get(holder.getLayoutPosition()), holder.getLayoutPosition()).show();
                return true;
            }
        });


//        changeLayoutColor();

        L.d("onBindViewHolder ---> ItemPosition:" + position);
        L.d("onBindViewHolder ---> holder.getLayoutPosition():" + holder.getLayoutPosition());
    }

    @Override
    public int getItemCount() {
        return mDiarylists.size();
    }


    public void updateItem(int position, Diary diary) {
        mDiarylists.remove(position);
        mDiarylists.add(position, diary);
        notifyItemChanged(position);
    }

    public void addItem(Diary diary) {
        mDiarylists.add(0, diary);
        notifyItemInserted(0);
    }

    public void removeItem(int position) {
        mDiarylists.remove(position);
        notifyItemRemoved(position);
        L.d("Remove a diary", "---> " + position);
    }

    public void setBackLayout(RelativeLayout layout) {
        mLayout = layout;
    }

//    public void changeLayoutColor(){
//        int color = (int) (Math.random() * layoutColors.length);
//        mLayout.setBackgroundColor(Color.parseColor(layoutColors[color]));
//    }
}
