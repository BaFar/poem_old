package com.example.dell.kobitaapps;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PoetFragment extends Fragment {




    public PoetFragment() {
        // Required empty public constructor
    }

    private ImageView poet_pictureIV;
    private TextView poet_nameTV,poet_detailsTV;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_poet, container, false);

         poet_pictureIV = v.findViewById(R.id.poet_picture);
         poet_nameTV = v.findViewById(R.id.poet_name);
         poet_detailsTV = v.findViewById(R.id.poet_details);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Typeface tf = Utils.getTypeface(getActivity());

        poet_pictureIV.setImageResource(R.drawable.poet_photo);
        poet_nameTV.setText(R.string.poet_name);
        poet_detailsTV.setText(R.string.poet_details);
        poet_nameTV.setTypeface(tf);
        poet_detailsTV.setTypeface(tf);


    }
}
