package com.example.dell.kobitaapps;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;

/**
 * Created by DELL on 2/18/2018.
 */

public  class Utils {
    public static  String[] fontsNames= {
            "CHANM___",
            "CHANMB__",
            "CHANMBI_",
            "CHANMI__",
            "DestinyAMotaMJ",
            "DestinyBMotaMJ",
            "DestinyCMotaMJ",
            "DestinyDMotaMJ",
            "DestinyEMotaMJ",
            "DestinyFMotaMJ",
            "DestinyMJ",
            "DestinyMJ-Bold",
            "DestinyMJ-BoldItalic",
            "DestinyMJ-Italic",
            "Sumit_O3-O9-2005",
            "SUTOM___",
            "SutonnyMJ-Bold",
            "SutonnyMJ-BoldItalic",
            "SutonnyMJ-Italic",
            "SutonnyOMJ",
            "GODHULI",
            "Nikosh",
            "PROTIK",
            "Rajon_Shoily",
            "SolaimanLipi"};

    public static Typeface getTypeface(Context context){
        SharedPreferences sp =context.getSharedPreferences(MainActivity.PREFERENCE_FILE_NAME_KEY,context.MODE_PRIVATE);
        String font_name = sp.getString(MainActivity.CHOOSED_FONT_KEY,MainActivity.DEFAULT_FONT);
        String font_path = "Fonts/"+font_name;
        Typeface tf = Typeface.createFromAsset(context.getAssets(),font_path);
        return tf;
    }
}
