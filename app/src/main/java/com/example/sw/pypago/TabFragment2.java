package com.example.sw.pypago;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sw.pypago.R;


public class TabFragment2 extends Fragment {
    ViewGroup btn1;
    ViewGroup btn2;
    ViewGroup btn3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_tab_fragment2, container, false);

        btn2 = (ViewGroup) view.findViewById(R.id.frag2_button2);


        btn2.setOnClickListener(new View.OnClickListener() //교재 추천
        {
            @Override
            public void onClick(View v)
            {

                Intent project = new Intent(getActivity(),BookListActivity.class);
                startActivity(project);
            }
        });


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



}

