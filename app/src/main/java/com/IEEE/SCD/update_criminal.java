package com.IEEE.SCD;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.journaldev.loginphpmysql.R;

public class update_criminal extends AppCompatActivity {


    int index;

    private ProgressDialog loading;
    String id = "";
    String location = "";
    String sus1 = "";
    String sus2 = "";
    String sus3 = "";
    String sus4 = "";
    String sus5 = "";
    String type = "";
    String MemberID;
    Button btnSave;
    Button btnCancel;
    EditText city;
    MultiAutoCompleteTextView suspect1;
    ArrayList<String> cases_id = new ArrayList<String>();


    Spinner spinner;

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_criminal2);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        showcases();
        showInfo();

        // btnSave
        Button btnSave = (Button) findViewById(R.id.save);
        // Perform action on click
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (SaveData()) {
                    // When Save Complete
                    Intent newActivity = new Intent(update_criminal.this, manage_criminals.class);
                    startActivity(newActivity);
                    finish();
                }
            }
        });


        // btnCancel
        final Button btnCancel = (Button) findViewById(R.id.cancel);
        // Perform action on click
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent newActivity = new Intent(update_criminal.this, manage_criminals.class);
                startActivity(newActivity);
            }
        });

    }

    public void showInfo()

    {

        city = (EditText) findViewById(R.id.city);
        suspect1 = (MultiAutoCompleteTextView) findViewById(R.id.suspect_crime1);


        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_of_crimes, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        //      fetch_cases=true;
//getData("http://crimes.6te.net/case.php?command=fetch_all_cases");
        spinner.setAdapter(adapter);



        btnSave = (Button) findViewById(R.id.save);
        btnCancel = (Button) findViewById(R.id.cancel);

        Intent intent = getIntent();

        MemberID = intent.getStringExtra("MemberID");

        String url = "http://crimes.6te.net/criminals.php?id=" + MemberID;

        showinfo();


    }


    public boolean SaveData() {



        final AlertDialog.Builder ad = new AlertDialog.Builder(this);
       location=city.getText().toString().trim();
        type=spinner.getSelectedItem().toString().trim();
        String [] sus_ids=new String[5];
        if(suspect1.getText().toString()!=null){
            String s=suspect1.getText().toString().trim();
            String[]ids=s.split(",");

            for(int i=0;i<ids.length;i++){
                if(ids[i]!=null&&ids[i].length()==5){
                    sus_ids[i]=ids[i];
                }else{
                    break;
                }
            }



        }

        String url="http://crimes.6te.net/insert_criminal.php?info=1&id="+MemberID+"&city="+location+"&past_type="+type+"&suspect1="+sus_ids[0]+"&suspect2="+sus_ids[1]+"&suspect3="+sus_ids[2]+"&suspect4="+sus_ids[3]+"&suspect5="+sus_ids[4];
        update(url);


        return true;
    }



    private void showinfo()  {


        RequestQueue queue = Volley.newRequestQueue(this);


// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://crimes.6te.net/criminals.php?id="+ MemberID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray c = null;


                        try {


                            c = new JSONArray(response);
                            JSONObject b = new JSONObject();
                            b = c.getJSONObject(0);

                            location = b.getString("city");
                            id = b.getString("id");
                            type = b.getString("past_crimes_type");
                            Log.d("n", id);
                            sus1 = b.getString("suspect_crimes1");
                            sus2 = b.getString("suspect_crimes2");
                            sus3 = b.getString("suspect_crimes3");
                            sus4 = b.getString("suspect_crimes4");
                            sus5 = b.getString("suspect_crimes5");
                            type = b.getString("past_crimes_type");
                            Log.d("n", sus1);
                            Log.d("n", type);

                            if (!id.equals("")) {
                                Log.d("m", location);
                                city.setText(location);
                                String[] myResArray = getResources().getStringArray(R.array.type_of_crimes);
                                for (int i = 0; i < myResArray.length; i++) {
                                    Log.d("m", myResArray[i]);
                                    if (type.equals(myResArray[i])) {
                                        index = i;
                                    }
                                }
                                Log.d("ty", Integer.toString(index));
                                spinner.setSelection(index);
                                String s = "";
                                String[] sus = {sus1, sus2, sus3, sus4, sus5};
                                for (int i = 0; i <= 4; i++) {
                                    if (sus[i].length() == 5) {
                                        s = s + sus[i] + ",";
                                    }
                                }
                                suspect1.setText(s);


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(update_criminal.this, "Cannot fetch information", Toast.LENGTH_LONG).show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish(); // or do something else
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //   getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    private void showcases()  {

        RequestQueue queue = Volley.newRequestQueue(this);


// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://crimes.6te.net/case.php?command=fetch_all_cases",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            JSONArray json = new JSONArray(response);


                            for (int i = 0; i < json.length(); i++) {
                                j = json.getJSONObject(i);

                                cases_id.add(i, j.getString("id"));
                                Log.d("s",cases_id.get(i));

                            }

                        }

                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>

                                (update_criminal.this, android.R.layout.simple_list_item_1, cases_id);
                        suspect1.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

                        suspect1.setThreshold(0);
                        suspect1.setAdapter(adapter2);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(update_criminal.this, "Please enter an id", Toast.LENGTH_LONG).show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
    void update(String url){
        RequestQueue queue = Volley.newRequestQueue(this);


// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       if(response.length()==1){
                           Toast.makeText(update_criminal.this, "Information has been updated", Toast.LENGTH_LONG).show();
                       }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(update_criminal.this, "Could not update the information", Toast.LENGTH_LONG).show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }



    }

