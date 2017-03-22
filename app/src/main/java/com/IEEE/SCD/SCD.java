package com.IEEE.SCD;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.StrictMode;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.IEEE.SCD.DataAdapter;
import com.IEEE.SCD.add_case;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.journaldev.loginphpmysql.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SCD extends Activity {
    int x=1;
    ArrayList<HashMap<String, String>> MyArrList;
    String[] Cmd = {"View","Update","Delete"};
    ListView lisView1;
    String value;
    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_criminals);
        Intent intent = getIntent();
         value = intent.getStringExtra("key"); //if it's a string you stored.


        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ShowData();


        // btnSearch
        final Button btnSearch = (Button) findViewById(R.id.btnSearch);
        //btnSearch.setBackgroundColor(Color.TRANSPARENT);
        // Perform action on click
        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ShowData();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish(); // or do something else
    }


    public void ShowData()
    {
        // listView1
        lisView1 = (ListView)findViewById(R.id.listView1);

        // keySearch
        EditText strKeySearch = (EditText)findViewById(R.id.txtKeySearch);
        strKeySearch.setText( value );
        // Disbled Keyboard auto focus
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(strKeySearch.getWindowToken(), 0);


       // String url = "http://scd.net23.net/jsonData.php?set=1&search="+strKeySearch.getText().toString();
        String url="http://scd.net23.net/SCD.php?case_id="+strKeySearch.getText().toString();
        if(value!=null){
         url = "http://scd.net23.net/SCD.php?case_id="+value;
        value=null;}
        url = url.replaceAll(" ", "%20");
        // Paste Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //  params.add(new BasicNameValuePair("txtKeyword", strKeySearch.getText().toString()));
        show( url );
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        menu.setHeaderIcon(android.R.drawable.btn_star_big_on);
        menu.setHeaderTitle("Command");
        String[] menuItems = Cmd;
        for (int i = 0; i<menuItems.length; i++) {
            menu.add(Menu.NONE, i, i, menuItems[i]);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String[] menuItems = Cmd;
        String CmdName = menuItems[menuItemIndex];

        // Check Event Command
        if ("View".equals(CmdName)) {
            initiatePopupWindow(info);
            /**
             * Call the mthod
             */
        } else if ("Update".equals(CmdName)) {
            Toast.makeText(SCD.this,"Your Selected Update",Toast.LENGTH_LONG).show();

            String sMemberID = MyArrList.get(info.position).get("id").toString();
            // String sName = MyArrList.get(info.position).get("name").toString();


            Intent newActivity = new Intent(SCD.this,Update_Case.class);
            newActivity.putExtra("MemberID", sMemberID);
            startActivity(newActivity);

        } else if ("Delete".equals(CmdName)) {
            Toast.makeText(SCD.this,"Your Selected Delete",Toast.LENGTH_LONG).show();
            /**
             * Call the mthod
             */
        }
        return true;
    }


    public class ImageAdapter extends BaseAdapter
    {
        private Context context;

        public ImageAdapter(Context c)
        {
            // TODO Auto-generated method stub
            context = c;
        }

        public int getCount() {
            // TODO Auto-generated method stub
            return MyArrList.size();
        }

        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.manage_criminals_col, null);
            }

            // ColMemberID
            TextView txtMemberID = (TextView) convertView.findViewById(R.id.ColMemberID);
            txtMemberID.setPadding(10, 0, 0, 0);
            txtMemberID.setText(MyArrList.get(position).get("num"));


            // R.id.ColName
            TextView txtName = (TextView) convertView.findViewById(R.id.ColName);
            txtName.setPadding(5, 0, 0, 0);
            txtName.setText(MyArrList.get(position).get("suspect_id"));

            // R.id.ColTel
            TextView txtTel = (TextView) convertView.findViewById(R.id.ColTel);
            txtTel.setPadding(5, 0, 0, 0);
            txtTel.setText(MyArrList.get(position).get("overall"));


            return convertView;

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //   getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    void show(String url){
        RequestQueue queue = Volley.newRequestQueue(this);


// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest( Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONArray data = new JSONArray(response);


                            MyArrList = new ArrayList<HashMap<String, String>>();
                            HashMap<String, String> map;
                            if(data!=null){
                                x=1;
                                for(int i = 0; i < data.length(); i++){

                                    JSONObject c = data.getJSONObject(i);

                                    map = new HashMap<String, String>();
                                    map.put("suspect_id", c.getString("suspect_id"));
                                    map.put("case_id", c.getString("case_reference"));
                                    map.put("weapon_rating", c.getString("avg_weapon"));
                                    map.put("victim_rating", c.getString("avg_victim"));
                                    map.put("location_rating", c.getString("avg_location"));
                                    map.put("type_rating", c.getString("avg_type"));
                                    map.put("witness_rating", c.getString("avg_witness"));
                                    map.put("overall", c.getString("overall_weight"));
                                    map.put("evidence_rating", c.getString("avg_evidence"));



                                    map.put("num", Integer.toString(x));

                                    x++;
                                    MyArrList.add(map);

                                }
                         }
                            else{
                                Toast.makeText(SCD.this,"0 results",Toast.LENGTH_LONG).show();
                            }

                            lisView1.setAdapter(new ImageAdapter(SCD.this));

                            registerForContextMenu(lisView1);


                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SCD.this, "Could not fetch information", Toast.LENGTH_LONG).show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);










    }

    private PopupWindow pwindo;

    private void initiatePopupWindow(AdapterView.AdapterContextMenuInfo info) {
        try {
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) SCD.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.view_case,
                    (ViewGroup) findViewById(R.id.popup_element));
            TextView text1=(TextView)layout.findViewById( R.id.textId );
            TextView text2=(TextView)layout.findViewById( R.id.textName );
            TextView text3=(TextView)layout.findViewById( R.id.textDate );
            TextView text4=(TextView)layout.findViewById( R.id.textTime );
            TextView text5=(TextView)layout.findViewById( R.id.textLocation );
            TextView text6=(TextView)layout.findViewById( R.id.textType );
            TextView text7=(TextView)layout.findViewById( R.id.textVictims );
            TextView text8=(TextView)layout.findViewById( R.id.textSus );
            TextView text9=(TextView)layout.findViewById( R.id.textEvidence );

            text1.setText( "ID: "+MyArrList.get(info.position).get("suspect_id").toString());
            text2.setText( "Name: "+MyArrList.get(info.position).get("case_id").toString());
            text3.setText( "Crime date: "+MyArrList.get(info.position).get("crime_date").toString());
            text4.setText( "Crime time: "+MyArrList.get(info.position).get("crime_time").toString());
            text5.setText( "Latitude: "+MyArrList.get(info.position).get("lat").toString()+ " Longitude:"+MyArrList.get(info.position).get("lang").toString());
            text6.setText( "Crime type: "+MyArrList.get(info.position).get("crime_type").toString());
            String [] vics={MyArrList.get(info.position).get("victim1").toString(),MyArrList.get(info.position).get("victim2").toString(),
                    MyArrList.get(info.position).get("victim3").toString(),MyArrList.get(info.position).get("victim4").toString(),
                    MyArrList.get(info.position).get("victim5").toString()};
            String v="";
            for(int i=0;i<5;i++){
                if(vics[i].length()>4){
                    v=v+vics[i]+",";
                    text7.setText( "Victims: "+v);
                }
            }
            String [] sus={MyArrList.get(info.position).get("suspect1").toString(),MyArrList.get(info.position).get("suspect2").toString(),
                    MyArrList.get(info.position).get("suspect3").toString(),MyArrList.get(info.position).get("suspect4").toString(),
                    MyArrList.get(info.position).get("suspect5").toString()};
            String s="";
            for(int i=0;i<5;i++){
                if(sus[i].length()==5){
                    s=s+sus[i]+",";
                    text8.setText( "Suspects: "+s);
                }
            }
            text9.setText( "Evidences: "+MyArrList.get(info.position).get("evidence").toString());
            PopupWindow popupMenu = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

            //    pwindo = new PopupWindow(layout, 400, 600, true);
            popupMenu.showAtLocation(layout, Gravity.CENTER, 0, 0);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }









}

/*
public class SCD extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    ArrayList<String> suspect_id = new ArrayList<String>();
    ArrayList<String> location_rating = new ArrayList<String>();
    ArrayList<String> witness_rating = new ArrayList<String>();
    ArrayList<String> case_id = new ArrayList<String>();
    ArrayList<String> type_rating = new ArrayList<String>();
    ArrayList<String> weapon_rating = new ArrayList<String>();
    ArrayList<String> victim_rating = new ArrayList<String>();
    ArrayList<String> evidence_rating = new ArrayList<String>();
    ArrayList<String> overall_rating = new ArrayList<String>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scd);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);


        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        fetch_ratings();





    }

    void fetch_ratings(){

        String url = "http://scd.net23.net/SCD.php?case_id=10228";
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject j = null;
                try {
                    JSONArray json = new JSONArray( response );
                    for (int i = 0; i < json.length(); i++) {
                        j = json.getJSONObject( i );
                        suspect_id.add( i,j.getString( "suspect id" ) );
                        case_id.add( i,j.getString( "case id" ) );
                        weapon_rating.add( i,j.getString( "weapon" ) );
                        evidence_rating.add( i,j.getString( "evidences" ) );
                        victim_rating.add( i,j.getString( "victims" ) );
                        location_rating.add( i,j.getString( "location" ) );
                        type_rating.add( i,j.getString( "type" ) );
                        witness_rating.add( i,j.getString( "witness" ) );
                       //   overall_rating.add( i,j.getString( "overall" ) );
                        mAdapter = new DataAdapter(suspect_id, case_id, evidence_rating, weapon_rating, victim_rating, location_rating,
                                type_rating, witness_rating, overall_rating);

                        mRecyclerView.setAdapter(mAdapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SCD.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);



    }



}*/
