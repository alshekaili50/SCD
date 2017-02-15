package com.IEEE.SCD;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.Calendar;
// Test github
//HI
public class Add_criminal extends AppCompatActivity {

    private int mYear;
    private int mMonth;
    private int mDay;
    Button add_criminal,done,con;
    EditText fullname,date;
    TextView id;
    RadioButton male,female;
    private ProgressDialog loading;
    String name,sex,suspects_name_id,suspect_id;
    int step=0;

    ArrayList<String> cases_name = new ArrayList<String>();
    ArrayList<String> cases_id = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_criminal);
        add_criminal = (Button) findViewById(R.id.add_criminal);
        fullname = (EditText) findViewById(R.id.fullname);
        date = (EditText) findViewById(R.id.date);

        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Calendar c = Calendar.getInstance();
                mYear = 1990;
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(Add_criminal.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                dpd.show(); }
        });







        add_criminal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 name = fullname.getText().toString().trim();
                if(name.length()<3){
                    Toast.makeText(getApplicationContext(), "Please enter valid name", Toast.LENGTH_LONG).show();
                }

               else if(date.length()<8){
                    Toast.makeText(getApplicationContext(), "Please enter valid date", Toast.LENGTH_LONG).show();
                }
               else if(!male.isChecked()&&!female.isChecked()){
                    Toast.makeText(getApplicationContext(), "Please select a gender", Toast.LENGTH_LONG).show();
                }
                else{
                    if(male.isChecked()){
                         sex="male";
                    }
                    else{
                        sex="female";
                    }
                    String url = "http://crimes.6te.net/insert_criminal.php?fullname="+name+"&gender="+sex+"&dob="+date.getText().toString().trim();

                    insert_criminal(url);


                }


            }
        });


    }


void insert_info(String url){
    // String id = editTextId.getText().toString().trim();
    //   if (id.equals("")) {
    //      Toast.makeText(this, "Please enter an id", Toast.LENGTH_LONG).show();
    //     return;
    //   }
    loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);


    StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            loading.dismiss();

               if(response.length()==1){
                   Toast.makeText(Add_criminal.this,"Profile has been updated",Toast.LENGTH_LONG).show();


            }
        }
    },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Add_criminal.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                }
            });

    RequestQueue requestQueue = Volley.newRequestQueue(this);

    requestQueue.add(stringRequest);



}
void fetch_cases(String url){
    // String id = editTextId.getText().toString().trim();
    //   if (id.equals("")) {
    //      Toast.makeText(this, "Please enter an id", Toast.LENGTH_LONG).show();
    //     return;
    //   }
    loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);


    StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            loading.dismiss();
            JSONObject j=null;
            try{
                JSONArray json=new JSONArray(response);



                for(int i=0;i<response.length();i++){
                    j=json.getJSONObject(i);
                    cases_name.add(i,j.getString("name")+":"+j.getString("id"));
                    cases_id.add(i,j.getString("id"));



                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Add_criminal.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                }
            });

    RequestQueue requestQueue = Volley.newRequestQueue(this);

    requestQueue.add(stringRequest);


}

void insert_criminal(String url){
    // String id = editTextId.getText().toString().trim();
    //   if (id.equals("")) {
    //      Toast.makeText(this, "Please enter an id", Toast.LENGTH_LONG).show();
    //     return;
    //   }
    loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);


    StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            loading.dismiss();

                if(response.length()==5){
                setContentView(R.layout.add_criminal3);

                done = (Button) findViewById(R.id.done);
                con = (Button) findViewById(R.id.Continue);
                id = (TextView) findViewById(R.id.textView10);
                set_id(response.toString());
                con.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setContentView(R.layout.add_criminal2);
                        set_information();

                    }

                });
                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }

                    });

                }




        }
    },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Add_criminal.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                }
            });

    RequestQueue requestQueue = Volley.newRequestQueue(this);

    requestQueue.add(stringRequest);
}








   public void set_id(String a){

suspect_id=a;
        id.setText("The suspect id is: "+a);
    }
    void set_information(){



      fetch_cases("http://crimes.6te.net/case.php?command=fetch_all_cases");

    Button save=(Button)findViewById(R.id.save) ;

        final EditText city = (EditText) findViewById(R.id.city);
        final MultiAutoCompleteTextView suspect1 = (MultiAutoCompleteTextView) findViewById(R.id.suspect_crime1);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);


        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>

                (this,android.R.layout.simple_list_item_1,cases_name);
        suspect1.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

       suspect1.setThreshold(0);
        suspect1.setAdapter(adapter2);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_of_crimes, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    save.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String City,type,suspect_cases;
            City=city.getText().toString().trim();
            type=spinner.getSelectedItem().toString();
            suspect_cases=suspect1.getText().toString();
            String[] names = suspect_cases.split(",");
            String []suspects=new String[5];
            int a=names.length-1;
            if(a>=0){
            String split[];
            ArrayList<String> id = new ArrayList<String>();
            for(int i=0;i<a;i++){
                split=names[i].split(":");
                id.add(i,split[1]);
            }

            for(int i=0;i<id.size();i++){
                suspects[i]=id.get(i);
            }}
            String url="http://crimes.6te.net/insert_criminal.php?info=1&id="+suspect_id+"&city="+City+"&past_type="+type+"&suspect1="+suspects[0]+"&suspect2="+suspects[1]+"&suspect3="+suspects[2]+"&suspect4="+suspects[3]+"&suspect5="+suspects[4];
            insert_info(url);

        }
    });
    }

}
