package com.example.dell.kobitaapps;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by DELL on 2/18/2018.
 */

public  class Utils {
    public static final int DEVICE_WIDTH =1;
    public static final int DEVICE_HEIGHT =2;

    public static  String[] fontsNames= {
            "CHANM___.TTF",
            "CHANMB__.TTF",
            "CHANMBI_.TTF",
            "CHANMI__.TTF",
            "DestinyAMotaMJ.ttf",
            "DestinyBMotaMJ.ttf",
            "DestinyCMotaMJ.ttf",
            "DestinyDMotaMJ.ttf",
            "DestinyEMotaMJ.ttf",
            "DestinyFMotaMJ.ttf",
            "DestinyMJ.ttf",
            "DestinyMJ-Bold.ttf",
            "DestinyMJ-BoldItalic.ttf",
            "DestinyMJ-Italic.ttf",
            "SUTOM___.TTF",
            "SutonnyMJ-Bold.ttf",
            "SutonnyMJ-BoldItalic.ttf",
            "SutonnyMJ-Italic.ttf",
            "SutonnyOMJ.ttf",
            "GODHULI.TTF",
            "Nikosh.ttf",
            "PROTIK.TTF",
            "Rajon_Shoily.ttf",
            "SolaimanLipi.ttf"};

    public static Typeface getTypeface(Context context){
        SharedPreferences sp =context.getSharedPreferences(MainActivity.PREFERENCE_FILE_NAME_KEY,context.MODE_PRIVATE);
        String font_name = sp.getString(MainActivity.CHOOSED_FONT_KEY,MainActivity.DEFAULT_FONT);
        String font_path = "Fonts/"+font_name;
        Typeface tf = Typeface.createFromAsset(context.getAssets(),font_path);
        return tf;
    }
    public static int getSizeInPixel(Context context, int widthOrheight){

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        if (widthOrheight == DEVICE_WIDTH){
            return width;
        }else {
            return height;
        }
    }
    public static int convertPixelsToDp(int px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int dp =(int) (px / (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }
}
