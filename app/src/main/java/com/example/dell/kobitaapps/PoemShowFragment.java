package com.example.dell.kobitaapps;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class PoemShowFragment extends Fragment {


    private RecyclerView poemRV;
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

        poemRV = v.findViewById(R.id.poem_recyclerView);

      //  poems = getActivity().getResources().getStringArray(R.array.poems);
      //  poemTitles = getActivity().getResources().getStringArray(R.array.poem_titles);
        Log.d("position","clicked pos: "+position);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       // PoemAdapter adapter = new PoemAdapter(getActivity(),poemTitles,poems);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        poemRV.setLayoutManager(manager);
        PoemAdapter adapter = new PoemAdapter(getActivity());
        poemRV.setAdapter(adapter);
        poemRV.getLayoutManager().scrollToPosition(position);

        //poemRV.findViewHolderForAdapterPosition(position);

      //  poemRV.setSaveEnabled(true);
       // poemRV.set

    }

    @Override
    public void onResume() {
        super.onResume();


       // poemLV.smoothScrollToPosition(position);
       // poemLV.smoothScrollToPosition(position,20);
      //  poemLV.setSelection(position);

    }
}
