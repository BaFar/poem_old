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
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

/**
 * Created by DELL on 2/15/2018.
 */

public class PoemAdapter  extends RecyclerView.Adapter<ReactivePoemViewHolder> implements ReactivePoemViewHolder.FunctionClickListener{
    private  String poetName= null;
    private Context context;
    private String[] poems;
    private String[] poemTitles;
    private String[] dedications;
    private Typeface tf;
    private boolean[] havededication = new boolean[75];
    private Map<Integer,String> dedicationMap;
    private int[] dedication_poem_list= {6,10,11,12,36,58,60};  //list which poem has dedication: 0 index based
    private String very_bad_part1 ,very_bad_part2,very_bad_part3;

    public PoemAdapter(Context context) {
        this.context = context;
        tf = Utils.getTypeface(context);
        this.poems = context.getResources().getStringArray(R.array.poems);
        this.poemTitles = context.getResources().getStringArray(R.array.poem_titles);
        this.dedications = context.getResources().getStringArray(R.array.dedications);
        this.poetName = context.getResources().getString(R.string.poet_name);

        dedicationMap = new HashMap<>();
        for (int k =0; k<7; k++){  /* here number of dedication is 7*/
            dedicationMap.put(dedication_poem_list[k],dedications[k]);
            havededication[dedication_poem_list[k]]=true;
        }

        very_bad_part1 =  context.getResources().getString(R.string.very_bad_part1);
        very_bad_part2 =  context.getResources().getString(R.string.very_bad_part2);
        very_bad_part3 =  context.getResources().getString(R.string.very_bad_part3);
    }

    @Override
    public ReactivePoemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflater = LayoutInflater.from(context);

        View poemLayoutView = inflater.inflate(R.layout.single_poem_view,parent,false);
        ReactivePoemViewHolder poemViewHolder = new ReactivePoemViewHolder(poemLayoutView, this) ;

        return poemViewHolder;
    }

    @Override
    public void onBindViewHolder(ReactivePoemViewHolder holder, int position) {
        holder.titleTV.setTypeface(tf);
        holder.poemTV.setTypeface(tf);
        if (position == 16){
            holder.titleTV.setText(Html.fromHtml("<font face=\"Choco cooky\">"+poemTitles[16]+"</font>"));

           holder.poemTV.setText(Html.fromHtml(context.getResources().getString(R.string.very_bad_part1)+
                    "<font face=\"Choco cooky\"> Very Bad.</font>"+context.getResources().
                    getString(R.string.very_bad_part2)+"<font face=\"Choco cooky\"> Very Bad.</font>"+
                    context.getResources().getString(R.string.very_bad_part3)));
        }
        else {
           holder.poemTV.setText(poems[position]);
           holder.titleTV.setText(poemTitles[position]);
        }
        if (havededication[position]){
            holder.dedicationTV.setText(dedicationMap.get(position));
        }
        else {
            holder.dedicationTV.setVisibility(View.GONE);
        }

        holder.setPoemPosition(position);


    }

    @Override
    public int getItemCount() {
        return poemTitles.length;
    }

    @Override
    public void copyPoem(int poemPosition) {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip =  ClipData.newPlainText(poemTitles[poemPosition],getCompletePoem(poemPosition));
        manager.setPrimaryClip(clip);
        Toast.makeText(context, "copied to clipboard!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void smsPoem(int poemPosition) {
        // Log.d("clicked","poem list view click pos: "+position);
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:"));
        sendIntent.putExtra("sms_body", getCompletePoem(poemPosition));
        context.startActivity(sendIntent);

    }

    @Override
    public void sharePoem(int poemPosition) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String contentToShare = getCompletePoem(poemPosition);
        String contentSubject = "Poem";
        shareIntent.putExtra(Intent.EXTRA_SUBJECT,contentSubject);
        shareIntent.putExtra(Intent.EXTRA_TEXT,contentToShare);
        context.startActivity(Intent.createChooser(shareIntent,"share with"));
    }
    private String getCompletePoem(int index){
        String dedication = null;
        if (havededication[index]){
            dedication ="\n\n"+ dedicationMap.get(index);
        }
        else dedication = "";
        String completePoem = poemTitles[index]+"\n\n"+poetName+""+dedication+"\n\n\n"+poems[index];
        return completePoem;
    }
}



//
//        extends BaseAdapter implements View.OnClickListener{
//    private Context context;
//    private String[] poems;
//    private String[] poemTitles;
//    private String[] dedications;
//    private String poetName = null;
//    private Typeface tf;
//    private boolean[] havededication = new boolean[75];
//    private Map<Integer,String> dedicationMap;
//    private int[] dedication_poem_list= {6,10,11,12,36,58,60};  /ist which poem has dedication: 0 index based
//    private String very_bad_part1 ,very_bad_part2,very_bad_part3;
//
//
//
//    PoemAdapter(){
//        context = null;
//        poems = null;
//        poemTitles = null;
//       // Arrays.fill(havededication,Boolean.FALSE);
//
//    }
//
//    public PoemAdapter(Context context){
//
//        this.context = context;
//        this.poemTitles =context.getResources().getStringArray(R.array.poem_titles);
//        this.poems = context.getResources().getStringArray(R.array.poems);
//        this.dedications = context.getResources().getStringArray(R.array.dedications);
//        poetName = context.getResources().getString(R.string.poet_name);
//        tf = Utils.getTypeface(context);
//
//        dedicationMap = new HashMap<>();
//
//        for (int k =0; k<7; k++){  /* here number of dedication is 7*/
//            dedicationMap.put(dedication_poem_list[k],dedications[k]);
//            havededication[dedication_poem_list[k]]=true;
//        }
//        //very_bad_part1 = Html.fromHtml(" context.getResources().getString(R.string.very_bad_part1))''
//        very_bad_part1 =  context.getResources().getString(R.string.very_bad_part1);
//        very_bad_part2 =  context.getResources().getString(R.string.very_bad_part2);
//        very_bad_part3 =  context.getResources().getString(R.string.very_bad_part3);
//
//
//       // Html.fromHtml("<font face=Verdana>"+very_bad_part1+"</font>"+<fvery_bad_part2+);
//
//
//    }
//
//    @Override
//    public int getCount() {
//        return poemTitles.length;
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        view = inflater.inflate(R.layout.single_poem_view, viewGroup, false);
//        TextView titleTV = view.findViewById(R.id.poem_title);
//        TextView dedicationTV = view.findViewById(R.id.dedication);
//        TextView poemTV = view.findViewById(R.id.poem);
//        Button copy = view.findViewById(R.id.copy);
//        Button sms = view.findViewById(R.id.sms);
//        Button share = view.findViewById(R.id.share);
//
//        copy.setTag(i);/*string tag worked but try with int*/
//        sms.setTag(i);
//        share.setTag(i);
//
//        copy.setOnClickListener(this);
//        sms.setOnClickListener(this);
//        share.setOnClickListener(this);
//
//
//        titleTV.setTypeface(tf);
//        poemTV.setTypeface(tf);
//
//        if (i == 16){
//            titleTV.setText(Html.fromHtml("<font face=\"Choco cooky\">"+poemTitles[16]+"</font>"));
//            poemTV.setText(Html.fromHtml(context.getResources().getString(R.string.very_bad_part1)+"<font face=\"Choco cooky\"> Very Bad.</font>"+context.getResources().getString(R.string.very_bad_part2)+"<font face=\"Choco cooky\"> Very Bad.</font>"+context.getResources().getString(R.string.very_bad_part3)));
//        }
//        else {
//            poemTV.setText(poems[i]);
//            titleTV.setText(poemTitles[i]);
//        }
//
//
//        if (havededication[i]){
//            dedicationTV.setText(dedicationMap.get(i));
//        }
//        else {
//            dedicationTV.setVisibility(View.GONE);
//        }
//
//        return view;
//    }
//
//
//
//    @Override
//    public void onClick(View view) {
//
//        int pos = (int) view.getTag();
//
//        switch (view.getId()){
//            case R.id.copy:
//               copyToClipboard(pos);
//                break;
//            case R.id.sms:
//                smsSelectedPoem(pos);
//                break;
//            case R.id.share:
//                shareWith(pos);
//                break;
//            default:
//                    Toast.makeText(context, "not matched id", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//
//
//   private void smsSelectedPoem(int position){
//      // Log.d("clicked","poem list view click pos: "+position);
//
//        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
//        sendIntent.setData(Uri.parse("sms:"));
//        sendIntent.putExtra("sms_body", getCompletePoem(position));
//        context.startActivity(sendIntent);
//      //  Toast.makeText(context, "clicked on position "+position, Toast.LENGTH_LONG).show();
//
//    }
//
//    private void copyToClipboard(int position){
//
//        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//        ClipData clip =  ClipData.newPlainText(poemTitles[position],getCompletePoem(position));
//        manager.setPrimaryClip(clip);
//        Toast.makeText(context, "copied to clipboard!", Toast.LENGTH_SHORT).show();
//    }
//
//    private void shareWith(int position){
//
//        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//        shareIntent.setType("text/plain");
//        String contentToShare = getCompletePoem(position);
//        String contentSubject = "Poem";
//        shareIntent.putExtra(Intent.EXTRA_SUBJECT,contentSubject);
//        shareIntent.putExtra(Intent.EXTRA_TEXT,contentToShare);
//        context.startActivity(Intent.createChooser(shareIntent,"share with"));
//    }
//
//    private String getCompletePoem(int index){
//        String completePoem = poemTitles[index]+"\n\n"+poetName+"\n\n\n"+poems[index];
//        return completePoem;
//    }
//}
//*/