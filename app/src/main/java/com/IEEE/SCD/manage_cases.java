package com.IEEE.SCD;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
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

public class manage_cases extends AppCompatActivity {
    int x=1;
    ArrayList<HashMap<String, String>> MyArrList;
    String[] Cmd = {"View","Update","Serial criminal detector","Generate case report"};
     ListView lisView1;
    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.manage_criminals);
        // listView1
        lisView1 = (ListView)findViewById(R.id.listView1);
        lisView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setBackgroundColor(0x0000FF00);
            }
        });

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

        // keySearch
        EditText strKeySearch = (EditText)findViewById(R.id.txtKeySearch);

        // Disbled Keyboard auto focus
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(strKeySearch.getWindowToken(), 0);



        String url = "http://scd.net23.net/jsonData.php?set=1&search="+strKeySearch.getText().toString();
        url = url.replaceAll(" ", "%20");
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

            String sMemberID = MyArrList.get(info.position).get("id").toString();
            Intent intent = new Intent(this, case_details.class);


            intent.putExtra("id", MyArrList.get(info.position).get( "id" ));
            intent.putExtra("weapon", MyArrList.get(info.position).get( "weapon" ));
            intent.putExtra("type", MyArrList.get(info.position).get( "crime_type" ));




            intent.putExtra("name", MyArrList.get(info.position).get( "name" ));
            intent.putExtra("date",  MyArrList.get(info.position).get( "crime_date" ));
            intent.putExtra("lang",  MyArrList.get(info.position).get( "lang" ));
            intent.putExtra("lat", MyArrList.get(info.position).get( "lat" ));
            intent.putExtra("time",  MyArrList.get(info.position).get( "crime_time" ));
         //   intent.putExtra("uploaded_date", MyArrList.get(info.position).get( "uploaded_date" ));
       //     intent.putExtra("uploaded_time",  MyArrList.get(info.position).get( "uploaded_time" ));




            startActivity(intent);

        } else if ("Update".equals(CmdName)) {
            Toast.makeText(manage_cases.this,"Your Selected Update",Toast.LENGTH_LONG).show();

            String sMemberID = MyArrList.get(info.position).get("id").toString();
            Intent intent = new Intent(this, Update_Case.class);


            intent.putExtra("id", MyArrList.get(info.position).get( "id" ));
            intent.putExtra("weapon", MyArrList.get(info.position).get( "weapon" ));
            intent.putExtra("type", MyArrList.get(info.position).get( "crime_type" ));

           // startActivityForResult(intent, 500);

            // String sName = MyArrList.get(info.position).get("name").toString();


            startActivity(intent);

        } else if ("Generate case report".equals(CmdName)) {
            Toast.makeText(manage_cases.this,"Your Selected Generate case report",Toast.LENGTH_LONG).show();
            String urlString="http://scd.net23.net/pdf/report_generator.php?case_id="+MyArrList.get(info.position).get( "id");
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage("com.android.chrome");
            try {
               startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                // Chrome browser presumably not installed so allow user to choose instead
                intent.setPackage(null);
                startActivity(intent);
            }}
             else if ("Serial criminal detector".equals(CmdName)) {
              //  Toast.makeText(manage_cases.this,"Your Selected Generate case report",Toast.LENGTH_LONG).show();
                String urlString="http://scd.net23.net/SCD.php?case_id="+MyArrList.get(info.position).get( "id");
                Intent intent = new Intent(this, SCD_feed.class);
                intent.putExtra("url", urlString);
                startActivity(intent);


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
    }@Override
    public void onResume(){
        super.onResume();
        ShowData();

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
                                    map.put("lat", c.getString("lat"));
                                    map.put("lang", c.getString("lang"));
                                    map.put("crime_date", c.getString("crime_date"));
                                    map.put("crime_time", c.getString("crime_time"));
                                    map.put("uploaded_date", c.getString("uploaded_date"));
                                    map.put("uploaded_time", c.getString("uploaded_time"));
                                    map.put("crime_type", c.getString("crime_type"));
                                    map.put("weapon", c.getString("weapon"));





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
                    .getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View layout = inflater.inflate( R.layout.view_case,
                    (ViewGroup) findViewById( R.id.popup_element ) );
            TextView text1 = (TextView) layout.findViewById( R.id.textId );
            TextView text2 = (TextView) layout.findViewById( R.id.textName );
            TextView text3 = (TextView) layout.findViewById( R.id.textDate );
            TextView text4 = (TextView) layout.findViewById( R.id.textTime );
            TextView text5 = (TextView) layout.findViewById( R.id.textLocation );
            TextView text6 = (TextView) layout.findViewById( R.id.textType );
            TextView text7 = (TextView) layout.findViewById( R.id.textVictims );
            TextView text8 = (TextView) layout.findViewById( R.id.textSus );
            TextView text9 = (TextView) layout.findViewById( R.id.textEvidence );

            text1.setText( "ID: " + MyArrList.get( info.position ).get( "id" ).toString() );
            text2.setText( "Name: " + MyArrList.get( info.position ).get( "name" ).toString() );
            text3.setText( "Crime date: " + MyArrList.get( info.position ).get( "crime_date" ).toString() );
            text4.setText( "Crime time: " + MyArrList.get( info.position ).get( "crime_time" ).toString() );
            text5.setText( "Latitude: " + MyArrList.get( info.position ).get( "lat" ).toString() + " Longitude:" + MyArrList.get( info.position ).get( "lang" ).toString() );
            text6.setText( "Crime type: " + MyArrList.get( info.position ).get( "crime_type" ).toString() );
            String[] vics = {MyArrList.get( info.position ).get( "victim1" ).toString(), MyArrList.get( info.position ).get( "victim2" ).toString(),
                    MyArrList.get( info.position ).get( "victim3" ).toString(), MyArrList.get( info.position ).get( "victim4" ).toString(),
                    MyArrList.get( info.position ).get( "victim5" ).toString()};
            String v = "";
            for (int i = 0; i < 5; i++) {
                if (vics[i].length() > 4) {
                    v = v + vics[i] + ",";
                    text7.setText( "Victims: " + v );
                }
            }
            String[] sus = {MyArrList.get( info.position ).get( "suspect1" ).toString(), MyArrList.get( info.position ).get( "suspect2" ).toString(),
                    MyArrList.get( info.position ).get( "suspect3" ).toString(), MyArrList.get( info.position ).get( "suspect4" ).toString(),
                    MyArrList.get( info.position ).get( "suspect5" ).toString()};
            String s = "";
            for (int i = 0; i < 5; i++) {
                if (sus[i].length() == 5) {
                    s = s + sus[i] + ",";
                    text8.setText( "Suspects: " + s );
                }
            }
            text9.setText( "Evidences: " + MyArrList.get( info.position ).get( "evidence" ).toString() );
            PopupWindow popupMenu = new PopupWindow( layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true );

            //    pwindo = new PopupWindow(layout, 400, 600, true);
            popupMenu.showAtLocation( layout, Gravity.CENTER, 0, 0 );


        } catch (Exception e) {
            e.printStackTrace();
        }
    }}

