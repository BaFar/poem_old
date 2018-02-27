package com.example.dell.kobitaapps;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


public class PoemShowFragment extends Fragment {


    private ListView poemLV;
    private String[] poemTitles;
    private String[] poems;
    private  int position=0;
    private CardView cardView;
    public PoemShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_single_poem, container, false);
        position =getArguments().getInt("clicked_position",0);

        poemLV = v.findViewById(R.id.poem_listView);

        poems = getActivity().getResources().getStringArray(R.array.poems);
        poemTitles = getActivity().getResources().getStringArray(R.array.poem_titles);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PoemAdapter adapter = new PoemAdapter(getActivity(),poemTitles,poems);
        poemLV.setAdapter(adapter);


    }
}
