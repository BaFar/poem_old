package com.greenapp.kobitaapps;


import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,SettingsFragment.FontChangeListener {

    public final static String POEM_TITLE_FRAGMENT_TAG="poem_title_fragment";
    public final static String POET_FRAGMENT_TAG = "poet_fragmnet";
    public final static String SETTINGS_FRAGMENT_TAG = "settings_fragment";
    public final static String PREFERENCE_FILE_NAME_KEY ="setting_preference_file";
    public final static String LATEST_VERSION_NO_KEY ="latestVersion";
    public final static String LATEST_VERSION_NAME_KEY = "latestVersionName";
    public final static String CHOOSED_FONT_KEY = "choosed_font";
    public final static String DEFAULT_FONT="DestinyMJ.ttf";

    public final static String MARKET_AMAZON_URL ="amzn://apps/android?p=";
    public final static String MARKET_AMAZON_SEARCH_URL ="amzn://apps/android?s=";
    public final static String MARKET_AMAZON_WEB_URL ="http://www.amazon.com/gp/mas/dl/android?p=";

    public final static String MARKET_AMAZON_DEVELOPER_ID ="Abisoft";
    public final static String IS_DATA_AVAILABLE_PREF_KEY ="isDataAvailable";


    private int localVersionNo = 0;
    private int remoteVersionNo =0;
    private String remoteVersionName = null;

    private  SharedPreferences sp = null;
    private TextView appTitleTV,nav_header_poem_book_name;



    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       //  pref = getSharedPreferences("market_pref",MODE_PRIVATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nav_header_poem_book_name = findViewById(R.id.nav_header_poem_book_name);
         appTitleTV = toolbar.findViewById(R.id.appTitle);
         appTitleTV.setText(R.string.poet_name);
        // nav_header_poem_book_name.setText("নতুন চাঁদ");

         sp = getSharedPreferences(PREFERENCE_FILE_NAME_KEY,MODE_PRIVATE);
        String font_name = sp.getString(CHOOSED_FONT_KEY,null);
        if (font_name==null){
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(CHOOSED_FONT_KEY,DEFAULT_FONT);
            editor.apply();
            editor.commit();
        }

        Typeface tf = Utils.getTypeface(this);
       // nav_header_poem_book_name.setTypeface(tf);
        appTitleTV.setTypeface(tf);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null){

            PoemTitleFragment titleFragment = (PoemTitleFragment) fm.findFragmentByTag(POEM_TITLE_FRAGMENT_TAG);
            if (titleFragment == null){
                titleFragment = new PoemTitleFragment();
                ft.add(R.id.fragment_container,titleFragment,POEM_TITLE_FRAGMENT_TAG);
                ft.addToBackStack(POEM_TITLE_FRAGMENT_TAG);
            }
            else {
                ft.replace(R.id.fragment_container,titleFragment,POEM_TITLE_FRAGMENT_TAG);
                ft.addToBackStack(POEM_TITLE_FRAGMENT_TAG);
            }
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();
        }

        versionCheck();

    }

    private void versionCheck() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("store").child("amazon");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                remoteVersionNo = Integer.parseInt(dataSnapshot.child("versionNo").getValue(String.class));
                remoteVersionName = dataSnapshot.child("versionName").getValue(String.class);

                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt(LATEST_VERSION_NO_KEY,remoteVersionNo);
                    editor.putString(LATEST_VERSION_NAME_KEY, remoteVersionName);
                    editor.commit();
                    editor.apply();
                    Log.d("version","Latest version: name: "+ remoteVersionName +" and versionNo: "+remoteVersionNo);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("updateInfo","Update info load failed");
            }
        });
    }


    @Override
    public void onBackPressed() {
       // int  i = FragmentManager.BackStackEntry
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if(fm.getBackStackEntryCount() > 1){
//            String tag = fm.
//            fm.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fm.popBackStackImmediate();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            ft.commit();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Exit Application");
            builder.setMessage("Are you sure you want to exit the app?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    MainActivity.super.onBackPressed();
                    MainActivity.super.onBackPressed();

                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_index){
            PoemTitleFragment titleFragment = (PoemTitleFragment) fm.findFragmentByTag(POEM_TITLE_FRAGMENT_TAG);
            FragmentTransaction ft = fm.beginTransaction();
            if (titleFragment == null){
                titleFragment = new PoemTitleFragment();

            }
            else {
                //fm.popBackStackImmediate(POEM_TITLE_FRAGMENT_TAG,FragmentManager.POP_BACK_STACK_INCLUSIVE);

            }
            ft.replace(R.id.fragment_container,titleFragment,POEM_TITLE_FRAGMENT_TAG);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.addToBackStack(POEM_TITLE_FRAGMENT_TAG);
            ft.commit();

        }

        else if (id == R.id.nav_author) {
            PoetFragment poetFragment = (PoetFragment) fm.findFragmentByTag(POET_FRAGMENT_TAG);
            FragmentTransaction ft = fm.beginTransaction();
            if (poetFragment == null){
                poetFragment = new PoetFragment();
            }
            ft.replace(R.id.fragment_container, poetFragment,POET_FRAGMENT_TAG);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.addToBackStack(POET_FRAGMENT_TAG);

            ft.commit();

        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, "Settings will be added soon", Toast.LENGTH_SHORT).show();
            SettingsFragment settingsFragment = (SettingsFragment) fm.findFragmentByTag(SETTINGS_FRAGMENT_TAG);
            FragmentTransaction ft = fm.beginTransaction();
            if (settingsFragment == null){
                settingsFragment = new SettingsFragment();
            }

            ft.replace(R.id.fragment_container,settingsFragment,SETTINGS_FRAGMENT_TAG);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.addToBackStack(SETTINGS_FRAGMENT_TAG);

            ft.commit();

        } else if (id == R.id.nav_share) {
           shareApkItself();

        }else if(id == R.id.nav_update){
            checkForUpdate();

        }else if (id == R.id.nav_rating){
            openAppOnStore();

        }  else if (id == R.id.nav_more_apps) {
            openAllAppsInStore();

        }else {

        Toast.makeText(this, "Unknown Action", Toast.LENGTH_SHORT).show();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private void checkForUpdate() {

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
             localVersionNo = pInfo.versionCode;
            Toast.makeText(this, "Current app version: "+localVersionNo, Toast.LENGTH_LONG).show();
            Log.d("update","current app version: "+localVersionNo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

                // already version info locally upated
            if(ConnectivityReceiver.isConnected()){

               int latestVerison = sp.getInt(LATEST_VERSION_NO_KEY,0);
               if (localVersionNo!=0 &&  localVersionNo < latestVerison){
                   openAppOnStore();
               }else {
                   Toast.makeText(this, "No Update Available", Toast.LENGTH_SHORT).show();
               }

            }
            else {
                Toast.makeText(this, "No Connection, Connect Internet then try Again", Toast.LENGTH_LONG).show();
            }
     }

    private void openAppOnStore() {
        String packageName ="com.abisoft.wordflash"; //getPackageName();
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MARKET_AMAZON_URL + packageName));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MARKET_AMAZON_WEB_URL + packageName));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
    private void openAllAppsInStore() {
        String searchTail ="&showAll=1";
        String packageName = "com.abisoft.wordflash"; //getPackageName();
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MARKET_AMAZON_SEARCH_URL+ MARKET_AMAZON_DEVELOPER_ID));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MARKET_AMAZON_URL + packageName + searchTail));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
    private void shareApkItself() {
        ApplicationInfo app = getApplicationContext().getApplicationInfo();
        String apkPath = app.sourceDir;

        Intent apkIntent = new Intent(Intent.ACTION_SEND);
        apkIntent.setType("*/*");

        File originalApk = new File(apkPath);
        try {
            File tempFile = new File(getExternalCacheDir()+"/ExtractedApk");
            //If directory doesn't exists create new
            if (!tempFile.isDirectory()){
                if (!tempFile.mkdirs())    return;
            }
            //Get application's name and convert to lowercase
            String appname = getString(app.labelRes).replace(" ","").toLowerCase()+".apk";
            tempFile = new File(tempFile.getPath()+"/"+appname);

            //If file doesn't exists create new
            if (!tempFile.exists()){
                if (!tempFile.createNewFile()) return;
            }
            //Copy file to new location

            InputStream in = new FileInputStream(originalApk);
            OutputStream out = new FileOutputStream(tempFile);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0){
                out.write(buf,0,len);
            }
            in.close();
            out.close();

            Log.d("apkShare","apk copied");

            apkIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(tempFile));
            startActivity(Intent.createChooser(apkIntent,"share via"));


        } catch (IOException e) {
            e.printStackTrace();
            Log.d("apkShare","share failed: "+e.getMessage());
        }



    }

    @Override
    public void onFontChange(String font_name) {
     //   fm.popBackStackImmediate(SETTINGS_FRAGMENT_TAG,FragmentManager.POP_BACK_STACK_INCLUSIVE);
       // fm.popBackStack(POET_FRAGMENT_TAG,FragmentManager.POP_BACK_STACK_INCLUSIVE);

        Typeface tf = Typeface.createFromAsset(getAssets(),"Fonts/"+font_name);
        appTitleTV.setTypeface(tf);


    }

}


/*F:\Android\sdk\Platform-toolsadb connect 192.168.0.100
First you will have to know your id in Google Play. To find out search your app in Google Play and check the url.
For example:
https://play.google.com/store/apps/details?id=com.cubeactive.qnotelistfree
This is the url of one of my apps. The id is this part: com.cubeactive.qnotelistfree
Note this id because we will need it in our code.
 https://github.com/bashir-fardoush/poem_old.git
new location

Intent goToMarket = new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=com.yapp.blah"));
    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(goToMarket);

*/

 /*
 * amzn://apps/android?p=com.abisoft.wordflashcom.abisoft.wordflash
 * http://www.amazon.com/gp/mas/dl/android?p=com.abisoft.slappy
 * amzn://apps/android?p=package_name
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 * */