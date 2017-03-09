package com.IEEE.SCD;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by uae25 on 3/2/2017.
 */

public class victim extends AppCompatActivity {
    private ProgressDialog loading;

    String name;
    String relation;
    String id;
    void set_name(String name1){
        name=name1;
    }
    void set_id(String id1){
        id=id1;
    }
    void set_relation(String relation1){
        relation=relation1;
    }
    String getName(){
        return  name;
    }
    String getRelation(){
        return relation;
    }
    String getId(){
        return id;
    }
    void insert_victim(){
        String url="http://scd.net23.net/victim.php?fullname="+name;
        url = url.replaceAll(" ", "%20");
        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);


        StringRequest stringRequest = new StringRequest( Request.Method.GET,url ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();

                if(response.length()==5){
                    Toast.makeText(victim.this,response.toString(),Toast.LENGTH_LONG).show();

                }




            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(victim.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }


}
