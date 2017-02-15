package com.journaldev.Police_app;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.journaldev.loginphpmysql.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class cases_menu extends Fragment {
    Button add_case,update_case,search;


    public cases_menu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cases_menu, container, false);
        add_case=(Button)v.findViewById(R.id.button7);
        add_case.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), add_case.class);
                startActivity(i);
            }
        });
        update_case=(Button)v.findViewById(R.id.update_case);
        update_case.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), manage_cases.class);
                startActivity(i);
            }
        });

        search=(Button)v.findViewById(R.id.button9);

        return v;
    }

}
