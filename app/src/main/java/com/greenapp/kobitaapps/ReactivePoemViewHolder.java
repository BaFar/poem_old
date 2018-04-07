package com.greenapp.kobitaapps;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by DELL on 3/16/2018.
 */

public class ReactivePoemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    private FunctionClickListener functionClickListener;
    public TextView titleTV, dedicationTV, poemTV ;
    public Button copybtn ,smsbtn,sharebtn;
    private int poemPosition=0;


    public ReactivePoemViewHolder(View itemView,FunctionClickListener functionClickListener) {
        super(itemView);
        this.functionClickListener = functionClickListener;
        titleTV = itemView.findViewById(R.id.poem_title);
        dedicationTV = itemView.findViewById(R.id.dedication);
        poemTV = itemView.findViewById(R.id.poem);
        copybtn = itemView.findViewById(R.id.copy);
        smsbtn = itemView.findViewById(R.id.sms);
        sharebtn = itemView.findViewById(R.id.share);
        copybtn.setOnClickListener(this);
        smsbtn.setOnClickListener(this);
        sharebtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.copy:
                functionClickListener.copyPoem(poemPosition);
                break;
            case R.id.sms:
                functionClickListener.smsPoem(poemPosition);
                break;
            case R.id.share:
                functionClickListener.sharePoem(poemPosition);
                break;

        }

    }

    public void setPoemPosition(int poemPosition) {
        this.poemPosition = poemPosition;
    }


    public static interface FunctionClickListener{
        void copyPoem(int poemPosition);
        void smsPoem(int poemPosition);
        void sharePoem(int poemPosition);
    }

}
