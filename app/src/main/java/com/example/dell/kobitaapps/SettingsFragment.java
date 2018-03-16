package com.example.dell.kobitaapps;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment{

    private TextView fontchangeTV,demo_text_TV;
    private Spinner fontSpinner;
    private Button applyBtn;
    private String font_name=null;
    private FontChangeListener fontChangeListener;
    private String[] fontnamesWithoutExtension=null;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        fontchangeTV = v.findViewById(R.id.font_change_textview);
        demo_text_TV = v.findViewById(R.id.show_demo_text);
        fontSpinner = v.findViewById(R.id.font_spinner);
        applyBtn = v.findViewById(R.id.apply_btn);

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (font_name!= null){
                   SharedPreferences pref = getActivity().getSharedPreferences(MainActivity.PREFERENCE_FILE_NAME_KEY, Context.MODE_PRIVATE);
                   SharedPreferences.Editor editor = pref.edit();
                   editor.putString(MainActivity.CHOOSED_FONT_KEY,font_name);
                   editor.apply();
                   editor.commit();
                   fontChangeListener.onFontChange(font_name);
                   Toast.makeText(getActivity(), "Font Applied", Toast.LENGTH_SHORT).show();

               }
               else {
                   Toast.makeText(getActivity(), "No font selected", Toast.LENGTH_SHORT).show();
               }
            }

        });

        int numberOfFonts = Utils.fontsNames.length;

        fontnamesWithoutExtension= new String[numberOfFonts+5];

        for (int i=0; i<numberOfFonts; i++){
            fontnamesWithoutExtension[i] = Utils.fontsNames[i];
            fontnamesWithoutExtension[i] = fontnamesWithoutExtension[i].substring(0,fontnamesWithoutExtension[i].indexOf(".")-1);
        }

        ArrayAdapter<String> fontAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,fontnamesWithoutExtension);
        fontSpinner.setAdapter(fontAdapter);
        fontSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                font_name = Utils.fontsNames[i];
                Toast.makeText(getActivity(), ""+font_name, Toast.LENGTH_SHORT).show();
                Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"Fonts/"+font_name);
                demo_text_TV.setTypeface(tf);
              //  demo_text_TV.setText(R.string.sample_text);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                Toast.makeText(getActivity(), "Nothing Selected", Toast.LENGTH_SHORT).show();
            }
        });

        return v;

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public interface FontChangeListener{
       void onFontChange(String font_name);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fontChangeListener = (FontChangeListener) getActivity();

    }
}
