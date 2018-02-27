package com.example.dell.kobitaapps;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PoemTitleFragment extends Fragment implements ListView.OnItemClickListener{

    public PoemTitleFragment() {
        // Required empty public constructor
    }

    private String[] poemTitles;
    private ListView poemTitleLV;
    public final static String POEM_SHOW_FRAGMENT_TAG = "poem_show_fragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_poem_title, container, false);
        poemTitleLV = v.findViewById(R.id.poem_title_listview);
        poemTitles = getResources().getStringArray(R.array.poem_titles);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TitleAdapter adapter = new TitleAdapter(getActivity(),poemTitles);
        poemTitleLV.setAdapter(adapter);

       // ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,poemTitles);
      //  poemTitleLV.setAdapter(arrayAdapter);
        poemTitleLV.setOnItemClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //Toast.makeText(getActivity(), "clicked on: "+i+" position", Toast.LENGTH_SHORT).show();
        Log.d("clicked on"," clicked on "+i);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("clicked_position",i);


        PoemShowFragment spf = (PoemShowFragment) fm.findFragmentByTag(POEM_SHOW_FRAGMENT_TAG);
        if (spf == null){
            spf = new PoemShowFragment();
            spf.setArguments(bundle);
            ft.replace(R.id.fragment_container,spf);
            ft.addToBackStack(POEM_SHOW_FRAGMENT_TAG);
        }
        else{
            spf.setArguments(bundle);
            ft.replace(R.id.fragment_container,spf);
        }


      //  ft.addToBackStack(POEM_SHOW_FRAGMENT_TAG);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

    }
}
