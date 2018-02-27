package com.example.dell.kobitaapps;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;

/**
 * Created by DELL on 2/18/2018.
 */

public  class Utils {
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
            "Sumit_O3-O9-2005.ttf",
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
}
