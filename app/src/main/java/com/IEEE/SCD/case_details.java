package com.IEEE.SCD;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.journaldev.loginphpmysql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class case_details extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap googleMap;
    private GoogleMap mMap;
    private MapView mapView;
    TableLayout table,table2,table3,table4;
    String wit_name,wit_height,wit_skin,wit_body;
    String id1,weapon1,type1,name1,date1,time1,lat,lan;
    String []suspect_names,suspect_id,victims_name,victims_id,victims_relation,evidence_id,evidences_detail;





    @Override
    protected void onCreate(Bundle savedInstanceState) {



        lat=getIntent().getStringExtra( "lat" );
        lan=getIntent().getStringExtra( "lang" );
        name1=getIntent().getStringExtra( "name" );
        id1=getIntent().getStringExtra( "id" );
        weapon1=getIntent().getStringExtra( "weapon" );
        type1=getIntent().getStringExtra( "type" );
        date1=getIntent().getStringExtra( "date" );
        time1=getIntent().getStringExtra( "time" );
        super.onCreate( savedInstanceState );
        final LatLng MOUNTAIN_VIEW = new LatLng(Double.valueOf( lat ), Double.valueOf( lan ));
        setContentView( R.layout.activity_case_details );
        table = (TableLayout)findViewById(R.id.tableLayout1);
        table2 = (TableLayout)findViewById(R.id.tableLayout2);
        table3 = (TableLayout)findViewById(R.id.tableLayout3);
        table4 = (TableLayout)findViewById(R.id.tableLayout4);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        fetch_previous_suspects();
        fetch_previous_victims();
        fetch_previous_evidences();
        fetch_previous_witness();
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(MOUNTAIN_VIEW)      // Sets the center of the map to Mountain View
                        .zoom(17)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                        .build();
                // Creates a CameraPosition from the builder
                googleMap.animateCamera( CameraUpdateFactory.newCameraPosition(cameraPosition));
                googleMap.addMarker(new MarkerOptions()
                        .position(MOUNTAIN_VIEW)
                        .title("Crime location"));
            }
        });



        TextView name=(TextView)findViewById( R.id.case_name_detail );
        TextView id=(TextView)findViewById( R.id.case_id_detail );
        TextView date=(TextView)findViewById( R.id.case_date_detail );
        TextView time=(TextView)findViewById( R.id.case_time_detail );
        TextView weapon=(TextView)findViewById( R.id.case_weapon_detail );
        TextView type=(TextView)findViewById( R.id.case_type_detail );
        name.setText( "Case Name : "+name1  );
        id.setText( "Case ID : "+id1 );
        weapon.setText( "Case Weapon : "+weapon1 );
        type.setText( "Case Type : "+type1 );
        date.setText( "Case Date : "+date1 );
        time.setText( "Case Time : "+time1 );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    void fetch_previous_witness(){
        String  url="http://scd.net23.net/witness.php?set=4&case_id="+id1;

        //    loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);


        StringRequest stringRequest = new StringRequest( Request.Method.GET,url ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  loading.dismiss();
                JSONObject j = null;

                try{
                    JSONArray json = new JSONArray( response );
                    for (int i = 0; i < json.length(); i++) {


                        j = json.getJSONObject( i );


                        TableRow row = new TableRow(case_details.this);
                        // create a new TextView for showing xml data
                        TextView t = new TextView(case_details.this);
                        TextView t2 = new TextView(case_details.this);
                        TextView t3 = new TextView(case_details.this);
                        TextView t4 = new TextView(case_details.this);
                        // set the text to "text xx"
                        t.setText( j.getString( "name" ));

                        t2.setText( j.getString( "body_type" ));
                        t2.setPadding( 130,0,0,0 );
                        t3.setText( j.getString( "height" ));
                        t3.setPadding( 130,0,0,0 );
                        t4.setText( j.getString( "skin_color" ));
                        t4.setPadding( 100,0,0,0 );
                        // add the TextView  to the new TableRow
                        row.addView(t);
                        row.addView( t2 );

                        row.addView(t3);
                        row.addView( t4 );
                        row.setBackgroundResource(R.drawable.row_border);

                        // add the TableRow to the TableLayout
                        table4.addView(row, new TableLayout.LayoutParams( ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));





                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(case_details.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);



    }

    void fetch_previous_victims(){
        String  url="http://scd.net23.net/victim.php?set=4&case_id="+id1;
        //    loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);


        StringRequest stringRequest = new StringRequest( Request.Method.GET,url ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //  loading.dismiss();
                JSONObject j = null;

                try{
                    JSONArray json = new JSONArray( response );
                    for (int i = 0; i < json.length(); i++) {
                        j = json.getJSONObject( i );


                        // create a new TableRow
                        TableRow row = new TableRow(case_details.this);
                        // create a new TextView for showing xml data
                        TextView t = new TextView(case_details.this);
                        TextView t2 = new TextView(case_details.this);
                        // set the text to "text xx"
                        t.setText( j.getString( "name" ));

                        t2.setText( j.getString( "victim_id" ));
                        t2.setPadding( 450,0,0,0 );
                        // add the TextView  to the new TableRow
                        row.addView(t);
                        row.addView( t2 );
                        row.setBackgroundResource(R.drawable.row_border);

                        // add the TableRow to the TableLayout
                        table2.addView(row, new TableLayout.LayoutParams( ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));






                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(case_details.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);



    }
    void fetch_previous_suspects(){
        String  url="http://scd.net23.net/criminals.php?set=4&case_id="+id1;
        //   loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);


        StringRequest stringRequest = new StringRequest( Request.Method.GET,url ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //    loading.dismiss();
                JSONObject j = null;

                try{
                    JSONArray json = new JSONArray( response );
                    for (int i = 0; i < json.length(); i++) {
                        j = json.getJSONObject( i );


                        // create a new TableRow
                        TableRow row = new TableRow(case_details.this);
                        // create a new TextView for showing xml data
                        TextView t = new TextView(case_details.this);
                        TextView t2 = new TextView(case_details.this);
                        // set the text to "text xx"
                        t.setText( j.getString( "name" ));

                        t2.setText( j.getString( "suspect_id" ));
                        t2.setPadding( 450,0,0,0 );
                        // add the TextView  to the new TableRow
                        row.addView(t);
                        row.addView( t2 );
                        row.setBackgroundResource(R.drawable.row_border);

                        // add the TableRow to the TableLayout
                        table.addView(row, new TableLayout.LayoutParams( ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));





                    }





                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(case_details.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);



    }
    void fetch_previous_evidences(){
        String  url="http://scd.net23.net/evidences.php?set=4&case_id="+id1;
        //  loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);


        StringRequest stringRequest = new StringRequest( Request.Method.GET,url ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //   loading.dismiss();
                JSONObject j = null;

                try{
                    JSONArray json = new JSONArray( response );
                    for (int i = 0; i < json.length(); i++) {
                        j = json.getJSONObject( i );

  //                      evidences.put( "evidence_id",j.getString( "evidence_id" ) );
//                        evidences.put( "detail",j.getString( "detail" ) );
                        TableRow row = new TableRow(case_details.this);
                        // create a new TextView for showing xml data
                        TextView t = new TextView(case_details.this);
                        TextView t2 = new TextView(case_details.this);
                        // set the text to "text xx"
                        t.setText( j.getString( "detail" ));

                        t2.setText( j.getString( "evidence_id" ));
                        t2.setPadding( 450,0,0,0 );
                        // add the TextView  to the new TableRow
                        row.addView(t);
                        row.addView( t2 );
                        row.setBackgroundResource(R.drawable.row_border);

                        // add the TableRow to the TableLayout
                        table3.addView(row, new TableLayout.LayoutParams( ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));





                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(case_details.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);



    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}


