package com.example.dell.kobitaapps;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by DELL on 2/15/2018.
 */

public class TitleAdapter extends ArrayAdapter<String> {
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
    }
}
