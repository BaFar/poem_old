package com.greenapp.kobitaapps;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by DELL on 2/15/2018.
 */

public class TitleAdapter extends RecyclerView.Adapter<ReactiveTitleViewHolder> {

    private Context context;
    private String[] poemTitles;
    private ReactiveTitleViewHolder.OnTitleClickListener listener;

    public TitleAdapter(Context context, String[] poemTitles, ReactiveTitleViewHolder.OnTitleClickListener clickListener) {
        this.context = context;
        this.poemTitles = poemTitles;
        this.listener = clickListener;
    }

    @Override
    public ReactiveTitleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
       View v =  inflater.inflate(R.layout.single_title_view,parent,false);
        ReactiveTitleViewHolder titleViewHolder = new ReactiveTitleViewHolder(v, listener);
        return titleViewHolder;
    }

    @Override
    public void onBindViewHolder(ReactiveTitleViewHolder holder, int position) {
        int counter = position +1;
        holder.titleTV.setTypeface(Utils.getTypeface(context));
        holder.titleTV.setText(String.valueOf(counter)+". "+poemTitles[position]);
        if (position == 16 ){
            holder.titleTV.setText(Html.fromHtml("<font face=\"Choco cooky\">"+poemTitles[position]+"</font>"));
            //String.valueOf(counter)+". "+
        }
        holder.setTitlePos(position);
    }

    @Override
    public int getItemCount() {
        return poemTitles.length;
    }

}
/*
 private Context context;
    private String[] poemTitles;


   public TitleAdapter(@NonNull Context context,  @NonNull String[] objects) {
        super(context,R.layout.single_title_view ,objects);
        this.context = context;
        this.poemTitles = objects;
   }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
     // convertView =  inflater.inflate(R.layout.single_title_view,parent,false);
        convertView = inflater.inflate(R.layout.single_title_view,parent,false);

        TextView titleTV = convertView.findViewById(R.id.single_title_textview);

        titleTV.setTypeface(Utils.getTypeface(context));

        titleTV.setText(poemTitles[position]);
        if (position == 16 ){
            titleTV.setText(Html.fromHtml("<font face=\"Choco cooky\">"+poemTitles[position]+"</font>"));
        }

        return convertView;
    }*/
