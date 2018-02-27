package com.example.dell.kobitaapps;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by DELL on 2/15/2018.
 */

public class PoemAdapter extends BaseAdapter implements View.OnClickListener{
    private Context context;
    private String[] poems;
    private String[] poemTitles;
    private String poetName = null;

    PoemAdapter(){
        context = null;
        poems = null;
        poemTitles = null;
    }

    public PoemAdapter(Context context, String[] poemTitles, String[] poems){
        this.context = context;
        this.poemTitles = poemTitles;
        this.poems = poems;
        poetName = context.getResources().getString(R.string.poet_name);
    }

    @Override
    public int getCount() {
        return poemTitles.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.single_poem_view, viewGroup, false);
        TextView titleTV = view.findViewById(R.id.poem_title);
        TextView poemTV = view.findViewById(R.id.poem);
        Button copy = view.findViewById(R.id.copy);
        Button sms = view.findViewById(R.id.sms);
        Button share = view.findViewById(R.id.share);

        //String tag = String.valueOf(i);

        copy.setTag(i);/*string tag worked but try with int*/
        sms.setTag(i);
        share.setTag(i);


        copy.setOnClickListener(this);
        sms.setOnClickListener(this);
        share.setOnClickListener(this);

       // Typeface tf = Typeface.createFromAsset(context.getAssets(), "Fonts/DestinyMJ.ttf");
        Typeface tf = Utils.getTypeface(context);
        titleTV.setTypeface(tf);
        poemTV.setTypeface(tf);

        poemTV.setText(poems[i]);
        titleTV.setText(poemTitles[i]);

        return view;
    }



    @Override
    public void onClick(View view) {

        int pos = (int) view.getTag();

        switch (view.getId()){
            case R.id.copy:
               copyToClipboard(pos);
                break;
            case R.id.sms:
                smsSelectedPoem(pos);
                break;
            case R.id.share:
                shareWith(pos);
                break;
            default:
                    Toast.makeText(context, "not matched id", Toast.LENGTH_SHORT).show();
        }
    }



   private void smsSelectedPoem(int position){
      // Log.d("clicked","poem list view click pos: "+position);

        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:"));
        sendIntent.putExtra("sms_body", getCompletePoem(position));
        context.startActivity(sendIntent);
      //  Toast.makeText(context, "clicked on position "+position, Toast.LENGTH_LONG).show();

    }

    private void copyToClipboard(int position){

        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip =  ClipData.newPlainText(poemTitles[position],getCompletePoem(position));
        manager.setPrimaryClip(clip);
        Toast.makeText(context, "copied to clipboard!", Toast.LENGTH_SHORT).show();
    }

    private void shareWith(int position){

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String contentToShare = getCompletePoem(position);
        String contentSubject = "Poem";
        shareIntent.putExtra(Intent.EXTRA_SUBJECT,contentSubject);
        shareIntent.putExtra(Intent.EXTRA_TEXT,contentToShare);
        context.startActivity(Intent.createChooser(shareIntent,"share with"));
    }

    private String getCompletePoem(int index){
        String completePoem = poemTitles[index]+"\n\n"+poetName+"\n\n\n"+poems[index];
        return completePoem;
    }
}
