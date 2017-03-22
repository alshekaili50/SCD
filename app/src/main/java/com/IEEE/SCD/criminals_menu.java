package com.IEEE.SCD;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.journaldev.loginphpmysql.R;

public class criminals_menu extends Fragment {
    Button button;
    Button button2,button3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.criminals, container, false);
          button=(Button) v.findViewById(R.id.button4);
        button2=(Button) v.findViewById(R.id.button3);
        button3=(Button) v.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"click",Toast.LENGTH_SHORT).show();


            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"click",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), manage_criminals.class);
                startActivity(i);

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SCD.class);
                startActivity(i);

            }
        });



        return v;
    }



}