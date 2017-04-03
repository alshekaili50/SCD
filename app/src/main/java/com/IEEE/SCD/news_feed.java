package com.IEEE.SCD;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.journaldev.loginphpmysql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class news_feed extends Activity {

    private static final String tag = news_feed.class.getSimpleName();
    private static final String url = "http://scd.net23.net/insert_case.php?set=3";
    private List<FeedItem> list = new ArrayList<FeedItem>();
    private ListView listView;
    private FeedListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_news_feed );

        listView = (ListView) findViewById( R.id.list );
        adapter = new FeedListAdapter( this, list );
        listView.setAdapter( adapter );
        getData();

    }





    void getData(){

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                    try {
                        JSONArray data = new JSONArray(response);
                        for (int i = 0; i < data.length(); i++) {

                        JSONObject obj = data.getJSONObject(i);
                        FeedItem dataSet = new FeedItem();
                        dataSet.setName(obj.getString("name"));
                        dataSet.setId(obj.getString("id"));
                        dataSet.setDate(obj.getString("crime_date"));
                        dataSet.setTime(obj.getString("crime_time"));
                        dataSet.setUploaded_date(obj.getString("uploaded_date"));
                        dataSet.setUploaded_time(obj.getString("uploaded_time"));
                        dataSet.setWeapon(obj.getString("weapon"));
                        dataSet.setType(obj.getString("crime_type"));
                        dataSet.setLang(obj.getString("lang"));
                        dataSet.setLat(obj.getString("lat"));
                        list.add(dataSet);
                    } }catch (JSONException e) {
                        e.printStackTrace();
                    }


                adapter.notifyDataSetChanged();
            }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(news_feed.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);


    }
}