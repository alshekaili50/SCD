package com.IEEE.SCD;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.journaldev.loginphpmysql.R;

import java.util.Calendar;

public class main_menu extends AppCompatActivity {
    private int mYear;
    private int mMonth;
    private int mDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main_menu );
      Button  add_case=(Button)findViewById(R.id.new_case);
        add_case.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(main_menu.this, add_case.class);
                startActivity(i);
            }
        } );
       Button add_evidence=(Button)findViewById(R.id.new_evidence);
        add_evidence.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_evidence_dialog();
            }
        } );

        Button add_suspect=(Button)findViewById(R.id.new_suspect);
        add_suspect.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_suspect_dialog();
            }
        } );
        Button add_victim=(Button)findViewById(R.id.new_victim);
        add_victim.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_victim_dialog();
            }
        } );
        Button manage_cases=(Button)findViewById(R.id.manage_cases);
        manage_cases.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(main_menu.this, manage_cases.class);
                startActivity(i);
            }
        } );
        Button manage_criminals=(Button)findViewById(R.id.manage_suspects);
        manage_criminals.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(main_menu.this, manage_criminals.class);
                startActivity(i);
            }
        } );
        Button scd=(Button)findViewById(R.id.SCD);
        scd.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(main_menu.this, SCD.class);
                startActivity(i);
            }
        } );




    }
    void add_victim_dialog(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.new_victim, null);
        dialogBuilder.setView(dialogView);
        final EditText full_name = (EditText) dialogView.findViewById(R.id.fullname_victim);


        dialogBuilder.setTitle("New victim");
        dialogBuilder.setMessage("Enter victim's name");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(full_name.getText().length()<3)
                {
                    Toast.makeText(main_menu.this,"Please enter valid name",Toast.LENGTH_LONG).show();

                }
                else{
                    String name;
                    name=full_name.getText().toString();
                    String url="http://scd.net23.net/victim.php?fullname="+name;
                    url = url.replaceAll(" ", "%20");
                    insert_into_db( url);



                }

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();





    }



    void add_suspect_dialog(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.new_suspect, null);
        dialogBuilder.setView(dialogView);
        final EditText full_name = (EditText) dialogView.findViewById(R.id.fullname);
        final EditText date_of_suspect = (EditText) dialogView.findViewById( R.id.dob );
        final RadioButton male = (RadioButton) dialogView.findViewById(R.id.male);
        final RadioButton female = (RadioButton) dialogView.findViewById(R.id.female);
        final EditText height=(EditText)dialogView.findViewById( R.id.height );
        final Spinner body_type=(Spinner)dialogView.findViewById( R.id.body );
        final Spinner sking=(Spinner)dialogView.findViewById( R.id.skin );
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.skin, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sking.setAdapter(adapter2);
        ArrayAdapter<CharSequence> adapter3= ArrayAdapter.createFromResource(this,
                R.array.body, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        body_type.setAdapter(adapter3);

        date_of_suspect.setOnClickListener( new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Calendar c = Calendar.getInstance();
                mYear = 2000;
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(main_menu.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                date_of_suspect.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                dpd.show();

            } });

        dialogBuilder.setTitle("New suspect");

        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(full_name.getText().length()<3)
                {
                    Toast.makeText(main_menu.this,"Please enter valid name",Toast.LENGTH_LONG).show();

                }
                else if(date_of_suspect.getText().length()<3){
                    Toast.makeText(main_menu.this,"Please enter valid date",Toast.LENGTH_LONG).show();

                }
                else if(!male.isChecked()&&!female.isChecked()){
                    Toast.makeText(main_menu.this,"Please select gender",Toast.LENGTH_LONG).show();

                }
                else{
                    String name,date,gender;
                    name=full_name.getText().toString();
                    date=date_of_suspect.getText().toString();
                    if(male.isChecked())
                        gender="male";
                    else gender="female";
                    String color,body,h,et;
                    color=sking.getSelectedItem().toString();
                    body=body_type.getSelectedItem().toString();
                    h=height.getText().toString();


                    String url="http://scd.net23.net/insert_criminal.php?name="+name+"&gender="+gender+"&dob="+date+"&height="+h+"&body_type="+body+"&skin_color="+color;
                    url = url.replaceAll(" ", "%20");
                    insert_into_db( url );



                }

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();

    }




    void add_evidence_dialog(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.new_evidence, null);
        dialogBuilder.setView(dialogView);
        final EditText detail = (EditText) dialogView.findViewById(R.id.new_evidence);


        dialogBuilder.setTitle("New evidence");
        dialogBuilder.setMessage("Enter the evidence");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(detail.getText().length()<10)
                {
                    Toast.makeText(main_menu.this,"Please enter valid evidence",Toast.LENGTH_LONG).show();

                }
                else{
                    String name;
                    name=detail.getText().toString();
                    String url="http://scd.net23.net/evidences.php?detail="+name;
                    url = url.replaceAll(" ", "%20");
                    insert_into_db( url);



                }

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();





    }
    void insert_into_db(String url){



        StringRequest stringRequest = new StringRequest( Request.Method.GET,url ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.length()==5){
                    Toast.makeText(main_menu.this,response.toString(),Toast.LENGTH_LONG).show();

                }




            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(main_menu.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }



}
