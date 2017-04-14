package com.IEEE.SCD;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SCD_feed extends AppCompatActivity {

    private static final String tag = news_feed.class.getSimpleName();
     String url ;
    private List<SCD_item> list = new ArrayList<SCD_item>();
    private ListView listView;
    private FeedSCDAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_news_feed );

        url=getIntent().getStringExtra( "url" );

        listView = (ListView) findViewById( R.id.list );
        adapter = new FeedSCDAdapter( this, list );
        listView.setAdapter( adapter );
        getData();

    }





    void getData(){

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                try {
                    JSONArray data = new JSONArray(response);
                    if(data.length()==0){
                        SCD_item dataSet = new SCD_item();
                        dataSet.setName( "No suspects to show" );
                        list.add( dataSet );
                    }
                    for (int i = 0,j=0; i < data.length()&&j<5; i++) {

                        JSONObject obj = data.getJSONObject(i);
                        SCD_item dataSet = new SCD_item();
                        dataSet.setName(obj.getString("suspect_name"));
                        dataSet.setSuspect_id(obj.getString("suspect_id"));
                        dataSet.setCase_id(obj.getString("case_reference"));
                        dataSet.setAvg_evidence(obj.getString("avg_evidence"));
                        dataSet.setAvg_location(obj.getString("avg_location"));
                        dataSet.setAvg_type(obj.getString("avg_type"));
                        dataSet.setAvg_victims(obj.getString("avg_victim"));
                        dataSet.setAvg_weapon(obj.getString("avg_weapon"));
                        dataSet.setAvg_witness(obj.getString("avg_witness"));
                        dataSet.setOverall(obj.getString("overall_weight"));
                        double overall1=0;

                        try {
                            overall1 = Double.parseDouble( dataSet.getOverall());
                        } catch(NumberFormatException nfe) {
                            System.out.println("Could not parse " + nfe);
                        }
                        if(overall1<=0){
                            continue;
                        }
                        else{
                            j++;
                        list.add(dataSet);}
                    } }catch (JSONException e) {
                    e.printStackTrace();
                }


                adapter.notifyDataSetChanged();
            }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SCD_feed.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);


    }
}