package com.greenapp.kobitaapps;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by DELL on 3/15/2018.
 */

public class ReactiveTitleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView titleTV;
    private int titlePos;
    private OnTitleClickListener onTitleClickListener;

    public ReactiveTitleViewHolder(View itemView, OnTitleClickListener clickListener) {
        super(itemView);
        titleTV = itemView.findViewById(R.id.single_title_textview);
        itemView.setOnClickListener(this);
        this.onTitleClickListener = clickListener;
    }

    public void setTitlePos(int titlePos) {
        this.titlePos = titlePos;
    }

    @Override
    public void onClick(View view) {
        onTitleClickListener.onTtitleClick(titlePos);
    }


    public static interface OnTitleClickListener{

        void onTtitleClick(int pos);
    }
}
