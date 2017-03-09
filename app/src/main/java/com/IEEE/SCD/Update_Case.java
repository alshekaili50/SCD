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

public class Update_Case extends AppCompatActivity {
    boolean fetch_cases = false, fetch_info = false, update_info = false;
    static int n=0;
    JSONArray a;

    int index;
    String res;
    private ProgressDialog loading;
    String id = "";
    String sus1 = "";
    String sus2 = "";
    String sus3 = "";
    String sus4 = "";
    String sus5 = "";
    String victim1,victim2,victim3,victim4,victim5,ev;
    String type = "";
    String MemberID;
    Button btnSave;
    Button btnCancel;
    EditText city;
    MultiAutoCompleteTextView suspect1,evidence,victims;
    ArrayList<String> cases_id = new ArrayList<String>();


    Spinner spinner;

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        show_criminals();
        Log.d("d","ali");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_case2);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        showInfo();

        // btnSave
        Button btnSave = (Button) findViewById(R.id.Save);
        // Perform action on click
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (SaveData()) {
                    // When Save Complete
                    Intent newActivity = new Intent(Update_Case.this, manage_cases.class);
                    startActivity(newActivity);
                    finish();
                }
            }
        });


        // btnCancel
        final Button btnCancel = (Button) findViewById(R.id.cancel_case);
        // Perform action on click
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent newActivity = new Intent(Update_Case.this, manage_cases.class);
                startActivity(newActivity);
            }
        });

    }

    public void showInfo()

    {



        suspect1 = (MultiAutoCompleteTextView) findViewById(R.id.suspects_case);


        spinner = (Spinner) findViewById(R.id.spinner_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_of_crimes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        victims=(MultiAutoCompleteTextView) findViewById(R.id.victims);
        evidence=(MultiAutoCompleteTextView) findViewById(R.id.evidence);



        btnSave = (Button) findViewById(R.id.save);
        btnCancel = (Button) findViewById(R.id.cancel);

        Intent intent = getIntent();

        MemberID = intent.getStringExtra("MemberID");

        String url = "http://scd.net23.net/criminals.php?id=" + MemberID;

        showinfo();


    }


    public boolean SaveData() {



        final AlertDialog.Builder ad = new AlertDialog.Builder(this);
       ;
        type=spinner.getSelectedItem().toString();
        String [] sus_ids=new String[5];
        if(suspect1.getText().toString()!=null){
            String s=suspect1.getText().toString();
            String[]ids=s.split(",");

            for(int i=0;i<ids.length;i++){
                if(ids[i].length()==5){
                    sus_ids[i]=ids[i];
                }else{
                    break;
                }
            }



        }
        String vics[]=new String[5];
        if(victims.getText().toString()!=null){
            String v=victims.getText().toString();
            String[] vis =v.split(",");

            for(int i=0;i<vis.length;i++){
                if(vis[i].length()>=3){
                    vics[i]=vis[i];
                }
            }

        }
        if(evidence.getText().toString()!=null){
            ev=evidence.getText().toString();
        }

        String url="http://scd.net23.net/insert_case.php?info=1&id="+MemberID+"&victim1="+vics[0]+"&victim2="+vics[1]+"&victim3="+vics[2]+"&victim4="+vics[3]+"&victim5="+vics[4]+"&suspect1="+sus_ids[0]+"&suspect2="+sus_ids[1]+"&suspect3="+sus_ids[2]+"&suspect4="+sus_ids[3]+"&suspect5="+sus_ids[4]+"&evidence="+ev+"&type="+type;
        url = url.replaceAll(" ", "%20");
        update(url);


        return true;
    }



    private void showinfo()  {


        RequestQueue queue = Volley.newRequestQueue(this);


// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://scd.net23.net/case.php?command=fetch_case_info&id="+ MemberID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray c = null;


                        try {


                            c = new JSONArray(response);
                            JSONObject b = new JSONObject();
                            b = c.getJSONObject(0);


                            id = b.getString("id");

                            Log.d("n", id);
                            sus1 = b.getString("suspect1");
                            sus2 = b.getString("suspect2");
                            sus3 = b.getString("suspect3");
                            sus4 = b.getString("suspect4");
                            sus5 = b.getString("suspect5");
                            victim1=b.getString("victim1");
                            victim2=b.getString("victim2");
                            victim3=b.getString("victim3");
                            victim4=b.getString("victim4");
                            victim5=b.getString("victim5");
                            ev=b.getString("evidence");

                            type = b.getString("crime_type");
                            Log.d("n", sus1);
                            Log.d("n", type);

                            if (!id.equals("")) {

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
                                evidence.setText(ev);
                                String v = "";
                                String[] vis = {victim1, victim2, victim3, victim4, victim5};
                                for (int i = 0; i <= 4; i++) {
                                    if (vis[i].length()>4) {
                                        v = v + vis[i] + ",";
                                    }
                                }
                                victims.setText( v );
                            } else {


                                btnSave.setEnabled(false);
                                btnCancel.requestFocus();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Update_Case.this, "Cannot fetch information", Toast.LENGTH_LONG).show();
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

    private void show_criminals()  {

        RequestQueue queue = Volley.newRequestQueue(this);


// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://scd.net23.net/case.php?command=fetch_all_suspects",
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

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>

                                (Update_Case.this, android.R.layout.simple_list_item_1, cases_id);
                        suspect1.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

                        suspect1.setThreshold(0);
                        suspect1.setAdapter(adapter2);



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Update_Case.this, "Could not fetch information", Toast.LENGTH_LONG).show();
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
                            Toast.makeText(Update_Case.this, "Information has been updated", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Update_Case.this, "Could not fetch information", Toast.LENGTH_LONG).show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }



}

