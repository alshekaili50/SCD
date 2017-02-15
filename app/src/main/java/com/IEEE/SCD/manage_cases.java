package com.IEEE.SCD;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
import java.util.HashMap;
import java.util.List;

public class manage_cases extends Activity {
    int x=1;
    ArrayList<HashMap<String, String>> MyArrList;
    String[] Cmd = {"View","Update","Delete"};
     ListView lisView1;
    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_criminals);

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

        // Disbled Keyboard auto focus
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(strKeySearch.getWindowToken(), 0);



        String url = "http://crimes.6te.net/jsonData.php?set=1&search="+strKeySearch.getText().toString();

        // Paste Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //  params.add(new BasicNameValuePair("txtKeyword", strKeySearch.getText().toString()));
show( url );
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {

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
            Toast.makeText(manage_cases.this,"Your Selected Update",Toast.LENGTH_LONG).show();

            String sMemberID = MyArrList.get(info.position).get("id").toString();
           // String sName = MyArrList.get(info.position).get("name").toString();


            Intent newActivity = new Intent(manage_cases.this,Update_Case.class);
            newActivity.putExtra("MemberID", sMemberID);
            startActivity(newActivity);

        } else if ("Delete".equals(CmdName)) {
            Toast.makeText(manage_cases.this,"Your Selected Delete",Toast.LENGTH_LONG).show();
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
            txtName.setText(MyArrList.get(position).get("name"));

            // R.id.ColTel
            TextView txtTel = (TextView) convertView.findViewById(R.id.ColTel);
            txtTel.setPadding(5, 0, 0, 0);
            txtTel.setText(MyArrList.get(position).get("id"));


            return convertView;

        }

    }


    public String getJSONUrl(String url,List<NameValuePair> params) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Download OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
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
                                    map.put("id", c.getString("id"));
                                    map.put("name", c.getString("name"));
                                    map.put("location", c.getString("location"));
                                    map.put("crime_date", c.getString("crime_date"));
                                    map.put("crime_time", c.getString("crime_time"));
                                    map.put("uploaded_date", c.getString("uploaded_date"));
                                    map.put("uploaded_time", c.getString("uploaded_time"));
                                    map.put("victim1", c.getString("victim1"));
                                    map.put("victim2", c.getString("victim2"));
                                    map.put("victim3", c.getString("victim3"));
                                    map.put("victim4", c.getString("victim4"));
                                    map.put("victim5", c.getString("victim5"));
                                    map.put("suspect1", c.getString("suspect1"));
                                    map.put("suspect2", c.getString("suspect2"));
                                    map.put("suspect3", c.getString("suspect3"));
                                    map.put("suspect4", c.getString("suspect4"));
                                    map.put("suspect5", c.getString("suspect5"));
                                    map.put("evidence", c.getString("evidence"));
                                    map.put("crime_type", c.getString("crime_type"));





                                    map.put("num", Integer.toString(x));

                                    x++;
                                    MyArrList.add(map);

                                }}
                            else{
                                Toast.makeText(manage_cases.this,"0 results",Toast.LENGTH_LONG).show();
                            }

                            lisView1.setAdapter(new ImageAdapter(manage_cases.this));

                            registerForContextMenu(lisView1);


                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(manage_cases.this, "Could not fetch information", Toast.LENGTH_LONG).show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);










    }

    private PopupWindow pwindo;

    private void initiatePopupWindow(AdapterView.AdapterContextMenuInfo info) {
        try {
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) manage_cases.this
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

            text1.setText( "ID: "+MyArrList.get(info.position).get("id").toString());
            text2.setText( "Name: "+MyArrList.get(info.position).get("name").toString());
            text3.setText( "Crime date: "+MyArrList.get(info.position).get("crime_date").toString());
            text4.setText( "Crime time: "+MyArrList.get(info.position).get("crime_time").toString());
            text5.setText( "Location: "+MyArrList.get(info.position).get("location").toString());
            text6.setText( "Crime type: "+MyArrList.get(info.position).get("crime_type").toString());


            text7.setText( "Victims: "+MyArrList.get(info.position).get("victim1").toString());
            text8.setText( "Suspects: "+MyArrList.get(info.position).get("suspect1").toString());
            text9.setText( "Evidences: "+MyArrList.get(info.position).get("evidence").toString());


            PopupWindow popupMenu = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

            //    pwindo = new PopupWindow(layout, 400, 600, true);
            popupMenu.showAtLocation(layout, Gravity.CENTER, 0, 0);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }









}