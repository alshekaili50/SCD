package com.IEEE.SCD;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.journaldev.loginphpmysql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class add_case extends AppCompatActivity {
    EditText name, date, time, location;
    String name1, date1, time1, location1;
    Button add;
    String case_id;
    private ProgressDialog loading;
    ArrayList<String> suspect_names = new ArrayList<String>();


    private int mYear;
    private int mMonth;
    private int mDay;

int PLACE_PICKER_REQUEST=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.add_case );


        name = (EditText) findViewById( R.id.case_name );
        date = (EditText) findViewById( R.id.case_date );
        time = (EditText) findViewById( R.id.time );
        location = (EditText) findViewById( R.id.location );
        add = (Button) findViewById( R.id.add_case );

        time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(add_case.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(add_case.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
        }});
        location.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(add_case.this),PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });













        add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().trim().length() < 3) {
                    Toast.makeText( getApplicationContext(), "Please enter valid name", Toast.LENGTH_LONG ).show();
                    name.requestFocus();
                } else if (date.getText().toString().trim().length() < 7) {
                    Toast.makeText( getApplicationContext(), "Please enter valid date", Toast.LENGTH_LONG ).show();
                    date.requestFocus();
                } else if (time.getText().toString().trim().length() < 3) {
                    Toast.makeText( getApplicationContext(), "Please enter valid time", Toast.LENGTH_LONG ).show();
                    time.requestFocus();
                } else if (location.getText().toString().trim().length() < 2) {
                    Toast.makeText( getApplicationContext(), "Please enter valid location", Toast.LENGTH_LONG ).show();
                    location.requestFocus();
                } else {
                    insert_case();
                }

            }
        } );


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if(requestCode==PLACE_PICKER_REQUEST){
                Place place = PlacePicker.getPlace(this,data);
                LatLng location1 = place.getLatLng();
                String toastMsg = String.format("Place: %s", place.getName());
                location.setText( place.getName() );
            } }}







    void insert_case() {
        name1 = name.getText().toString().trim();
        date1 = date.getText().toString().trim();
        time1 = time.getText().toString().trim();
        location1 = location.getText().toString().trim();
        String id;

        String url = "http://crimes.6te.net/insert_case.php?name=" + name1 + "&date=" + date1 + "&time=" + time1 + "&location=" + location1;
        getData( url );


    }

    private void getData(String url) {


        loading = ProgressDialog.show( this, "Please wait...", "Connecting to the server...", false, false );


        StringRequest stringRequest = new StringRequest( url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON( response );
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( add_case.this, error.getMessage().toString(), Toast.LENGTH_LONG ).show();
                    }
                } );

        RequestQueue requestQueue = Volley.newRequestQueue( this );
        requestQueue.add( stringRequest );
    }

    private void showJSON(String response) {
        if (response != null) {
            if (response.length() == 5) {
                setContentView( R.layout.add_case3 );
                TextView text = (TextView) findViewById( R.id.textView10 );
                text.setText( "The case ID: " + response.toString() );
                case_id = response.toString();
                Button c = (Button) findViewById( R.id.Continue_case );
                Button d = (Button) findViewById( R.id.done_case );
                d.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                } );
                c.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        set_info();
                    }
                } );
            } else {


                JSONObject j = null;
                try {
                    JSONArray json = new JSONArray( response );


                    for (int i = 0; i < response.length(); i++) {
                        j = json.getJSONObject( i );
                        suspect_names.add( i, j.getString( "name" ) + ":" + j.getString( "id" ) );


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }
    }

    void set_info() {
        setContentView( R.layout.add_case2 );
        final MultiAutoCompleteTextView suspects_case = (MultiAutoCompleteTextView) findViewById( R.id.suspects_case );
        final MultiAutoCompleteTextView victims = (MultiAutoCompleteTextView) findViewById( R.id.victims );
        final MultiAutoCompleteTextView evidence = (MultiAutoCompleteTextView) findViewById( R.id.evidence );

        final Spinner spinner = (Spinner) findViewById( R.id.spinner_type );

        Button save = (Button) findViewById( R.id.Save );

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( this,
                R.array.type_of_crimes, android.R.layout.simple_spinner_item );
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
// Apply the adapter to the spinner
        spinner.setAdapter( adapter );
        String url = "http://crimes.6te.net/case.php?command=fetch_all_suspects";
        getData( url );
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>

                ( this, android.R.layout.simple_list_item_1, suspect_names );
        suspects_case.setTokenizer( new MultiAutoCompleteTextView.CommaTokenizer() );

        suspects_case.setThreshold( 0 );
        suspects_case.setAdapter( adapter2 );
        save.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String type, victim, suspect;
                type = spinner.getSelectedItem().toString().trim();
                victim = victims.getText().toString();
                suspect = suspects_case.getText().toString();
                String ev = evidence.getText().toString();
                String[] names = suspect.split( "," );
                int a = names.length - 1;
                String split[];
                ArrayList<String> id = new ArrayList<String>();
                String[] suspects = new String[5];
                if (a >= 0) {
                    for (int i = 0; i < a; i++) {

                        split = names[i].split( ":" );

                        id.add( i, split[1] );
                    }

                    for (int i = 0; i < id.size(); i++) {
                        suspects[i] = id.get( i );
                    }
                }
                String[] vic = victim.split( "," );
                String[] vics = new String[5];
                if (vic.length - 1 >= 0) {

                    for (int i = 0; i < vic.length; i++) {
                        vics[i] = vic[i];
                    }
                }
                String url = "http://crimes.6te.net/insert_case.php?info=1&id=" + case_id + "&victim1=" + vics[0] + "&victim2=" + vics[1] + "&victim3=" + vics[2] + "&victim4=" + vics[3] + "&victim5=" + vics[4] + "&suspect1=" + suspects[0] + "&suspect2=" + suspects[1] + "&suspect3=" + suspects[2] + "&suspect4=" + suspects[3] + "&suspect5=" + suspects[4] + "&evidence=" + ev + "&type=" + type;
                getData( url );

            }
        } );


    }


}