package com.IEEE.SCD;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Update_Case extends AppCompatActivity {
    EditText name, date, time, location;
    String name1, date1, time1, location1;
    Button add;
    String case_id;
    private ProgressDialog loading;
    ArrayList<String> suspect_names = new ArrayList<String>();
    ArrayList<String> victim_names = new ArrayList<String>();
    ArrayList<String> evidences = new ArrayList<String>();
    Boolean success=false;
    private int mYear;
    private int mMonth;
    private int mDay;
    double lat,lang;
    int PLACE_PICKER_REQUEST=1;
    case_object a=new case_object();
    MultiAutoCompleteTextView witness;
    MultiAutoCompleteTextView suspects_case;
    MultiAutoCompleteTextView victims1 ;
    MultiAutoCompleteTextView evidence1;
    Spinner spinner ;

    String weapo;
    String type;
    String ida;
    Spinner weapon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.add_case2);
         ida=getIntent().getStringExtra( "id" );
        weapo=getIntent().getStringExtra( "weapon" );
        type=getIntent().getStringExtra( "type" );
        Log.d( "ali",ida );
        Log.d( "ali",weapo );

        Log.d( "ali",type );
        case_id=ida;
        a.setId( ida );


        set_info();


    }


    void fetch_suspects() {
        suspect_names.clear();
        String url = "http://scd.net23.net/case.php?command=fetch_all_suspects";
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             //   loading.dismiss();
                JSONObject j = null;
                try {
                    JSONArray json = new JSONArray( response );
                    for (int i = 0; i < json.length(); i++) {
                        j = json.getJSONObject( i );
                        suspect_names.add( i, j.getString( "name" ) + ":" + j.getString( "id" ) );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Update_Case.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);



    }
    void update_info(String url) {
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                if(response.length()==1)
                    Toast.makeText(Update_Case.this,"Information has been updated",Toast.LENGTH_LONG).show();

            }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Update_Case.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);



    }
    void fetch_previous_witness(){
        String  url="http://scd.net23.net/witness.php?set=4&case_id="+ida;

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
                        a.setWit_name( j.getString( "name" ) );
                        a.setWit_height( j.getString( "height" ) );
                        a.setWit_body( j.getString( "body_type" ) );
                        a.setWit_skin( j.getString( "skin_color" ) );
                        witness.setText( "name:"+a.getWit_name()+"\nbody type:"+a.getWit_body()+"\nheight:"+a.getWit_height()+"\nskin color:"+a.getWit_skin());

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Update_Case.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);



    }

    void fetch_previous_victims(){
      String  url="http://scd.net23.net/victim.php?set=4&case_id="+ida;
        victim_names.clear();
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
                        victim_names.add( i, j.getString( "name" ) + ":" + j.getString( "victim_id" ) );
                        a.setVictim( victim_names.get( i ).toString() );
                        victims1.setText( a.getVictim() );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Update_Case.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);



    }
    void fetch_previous_suspects(){
        String  url="http://scd.net23.net/criminals.php?set=4&case_id="+ida;
        suspect_names.clear();
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
                        suspect_names.add( i, j.getString( "name" ) + ":" + j.getString( "suspect_id" ) );
                        a.setSuspect( suspect_names.get( i ).toString() );
                        suspects_case.setText( a.getSuspect() );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Update_Case.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);



    }
    void fetch_previous_evidences(){
        String  url="http://scd.net23.net/evidences.php?set=4&case_id="+ida;
        evidences.clear();
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
                        evidences.add( i, j.getString( "detail" ) + ":" + j.getString( "evidence_id" ) );
                        a.setEvidence( evidences.get( i ).toString() );
                        evidence1.setText( a.getEvidence() );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Update_Case.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);



    }




    void set_info() {
        fetch_previous_witness();
fetch_previous_victims();
        fetch_previous_evidences();
        fetch_previous_suspects();
       // setContentView( R.layout.add_case2 );
        ImageView add_suspect=(ImageView) findViewById( R.id.btn_add );
        add_suspect.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_suspect_dialog();
            }
        } );
        ImageView add_victim=(ImageView) findViewById( R.id.btn_add_victim );
        add_victim.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_victim_dialog();
            }
        } );
        ImageView add_evidence=(ImageView) findViewById( R.id.add_evidence );
        add_evidence.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_evidence_dialog();
            }
        } );
        ImageView edit_victim=(ImageView)findViewById( R.id.edit );
        edit_victim.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_victims();
            }
        } );
        ImageView edit_suspect=(ImageView)findViewById( R.id.edit2 );
        edit_suspect.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_suspects();
            }
        } );
        ImageView edit_evidences=(ImageView)findViewById( R.id.edit3 );
        edit_evidences.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_evidence();
            }
        } );
        final ImageView edit_witness=(ImageView)findViewById( R.id.edit4 );
        edit_witness.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_witness();
            }
        } );
        suspects_case = (MultiAutoCompleteTextView) findViewById( R.id.suspects_case );
        victims1 = (MultiAutoCompleteTextView) findViewById( R.id.victims );
        evidence1 = (MultiAutoCompleteTextView) findViewById( R.id.evidence );
        spinner = (Spinner) findViewById( R.id.spinner_type );
        weapon=(Spinner)findViewById( R.id.weapon );
        witness=(MultiAutoCompleteTextView) findViewById( R.id.witness );
        witness.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                witness_dialog();
            }
        } );

        victims1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type_victim();

            }
        } );
        evidence1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_evidence();
            }
        } );
        suspects_case.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_suspect_case();
            }
        } );
        Button save = (Button) findViewById( R.id.Save );
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( this,
                R.array.type_of_crimes, android.R.layout.simple_spinner_item );
        String[] myResArray = getResources().getStringArray(R.array.type_of_crimes);
        String[] myResArray2 = getResources().getStringArray(R.array.weapons);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource( this,
                R.array.weapons, android.R.layout.simple_spinner_item );
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
// Apply the adapter to the spinner
        weapon.setAdapter( adapter2 );

        Log.d("m", type);




       // weapon.setText( weapo );

// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
// Apply the adapter to the spinner
        spinner.setAdapter( adapter );

        int index=0;
        int index2=0;
        for (int i = 0; i < myResArray.length; i++) {
            Log.d("m", myResArray[i]);
            if (type.equals(myResArray[i])) {
                index = i;
            }

        }spinner.setSelection( index );
        for (int i = 0; i < myResArray2.length; i++) {

            if (weapo.equals(myResArray2[i])) {
                index2 = i;
            }

        }weapon.setSelection( index2 );

        save.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a.setWeapon( weapon.getSelectedItem().toString() );
                a.setType( spinner.getSelectedItem().toString() );
                String type,weapon;
                type=a.getType();
                weapon=a.getWeapon();
                Toast.makeText(Update_Case.this,a.getId(),Toast.LENGTH_LONG).show();

                String url = "http://scd.net23.net/insert_case.php?set=2&type="+type+"&weapon="+weapon+"&id="+a.getId();
                url = url.replaceAll(" ", "%20");
                insert_suspect_case( url );





            }
        } );
    }
    void Edit_witness(){String ab;
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(Update_Case.this);
        builderSingle.setTitle("Select the witness:-");
        if (TextUtils.isEmpty(a.getWit_name())) {
            return;
        }else{
            ab=a.getWit_name();
        }



        List<String> stringList = new ArrayList<String>( Arrays.asList(ab)); //new ArrayList is only needed if you absolutely need an ArrayList

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Update_Case.this, android.R.layout.select_dialog_singlechoice,stringList);


        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(Update_Case.this);
                builderInner.setMessage("Are sure you want to remove "+
                        strName);
                builderInner.setTitle("Remove a witness");
                builderInner.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {

                        String url="http://scd.net23.net/witness.php?set=2&case_id="+a.getId();
                        deleteDb(url);

                        witness.setText( "");
                        a.setWit_name( "" );

                    }
                });
                builderInner.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();


    }
    void witness_dialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_wittness, null);
        dialogBuilder.setView(dialogView);
        final EditText wit_name = (EditText) dialogView.findViewById(R.id.wit_name);
        final Spinner wit_height = (Spinner) dialogView.findViewById( R.id.wit_height );
        final Spinner body_type=(Spinner)dialogView.findViewById( R.id.wit_body );
        final Spinner wit_color=(Spinner)dialogView.findViewById( R.id.wit_color );
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.heights, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wit_height.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.skin, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wit_color.setAdapter(adapter2);
        ArrayAdapter<CharSequence> adapter3= ArrayAdapter.createFromResource(this,
                R.array.body, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        body_type.setAdapter(adapter3);


        dialogBuilder.setTitle("New Witness");
        dialogBuilder.setMessage("Enter the information");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(wit_name.getText().length()<3)
                {
                    Toast.makeText(Update_Case.this,"Please enter valid name",Toast.LENGTH_LONG).show();

                }

                else{
                    a.setWit_name( wit_name.getText().toString() );
                    a.setWit_body( body_type.getSelectedItem().toString() );
                    a.setWit_height( wit_height.getSelectedItem().toString() );
                    a.setWit_skin( wit_color.getSelectedItem().toString() );
                    String url="http://scd.net23.net/witness.php?set=1&case_id="+a.getId()+"&name="+a.getWit_name()+"&height="+a.getWit_height()+"&body="+a.getWit_body()+"&skin="+a.getWit_skin();
                    url = url.replaceAll(" ", "%20");
                    insert_suspect_case( url );
                    witness.setText( "name:"+a.getWit_name()+"\nbody type:"+a.getWit_body()+"\nheight:"+a.getWit_height()+"\nskin color:"+a.getWit_skin());

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
    @RequiresApi(api = Build.VERSION_CODES.M)
    void show_suspect_dialog(){

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

                DatePickerDialog dpd = new DatePickerDialog(Update_Case.this,
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
                    Toast.makeText(Update_Case.this,"Please enter valid name",Toast.LENGTH_LONG).show();

                }
                else if(date_of_suspect.getText().length()<3){
                    Toast.makeText(Update_Case.this,"Please enter valid date",Toast.LENGTH_LONG).show();

                }
                else if(!male.isChecked()&&!female.isChecked()){
                    Toast.makeText(Update_Case.this,"Please select gender",Toast.LENGTH_LONG).show();

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


                    String url="http://scd.net23.net/insert_criminal.php?fullname="+name+"&gender="+gender+"&dob="+date+"&height="+h+"&body_type="+body+"&skin_color="+color;
                    url = url.replaceAll(" ", "%20");
                    insert_criminal( url );



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
    void insert_criminal(String url){

       // loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);


        StringRequest stringRequest = new StringRequest( Request.Method.GET,url ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  loading.dismiss();

                if(response.length()==5){
                    Toast.makeText(Update_Case.this,response.toString(),Toast.LENGTH_LONG).show();

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Update_Case.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }
    void show_victim_dialog(){

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
                    Toast.makeText(Update_Case.this,"Please enter valid name",Toast.LENGTH_LONG).show();

                }
                else{
                    String name;
                    name=full_name.getText().toString();
                    String url="http://scd.net23.net/victim.php?fullname="+name;
                    url = url.replaceAll(" ", "%20");
                    insert_victim( url);



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
    void insert_victim(String url){

     //   loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);


        StringRequest stringRequest = new StringRequest( Request.Method.GET,url ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // loading.dismiss();

                if(response.length()==5){
                    Toast.makeText(Update_Case.this,response.toString(),Toast.LENGTH_LONG).show();

                }




            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Update_Case.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
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
                    Toast.makeText(Update_Case.this,"Please enter valid evidence",Toast.LENGTH_LONG).show();

                }
                else{
                    String name;
                    name=detail.getText().toString();
                    String url="http://scd.net23.net/evidences.php?detail="+name;
                    url = url.replaceAll(" ", "%20");
                    insert_evidence( url);



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
    void insert_evidence(String url){

     //   loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);


        StringRequest stringRequest = new StringRequest( Request.Method.GET,url ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
        //        loading.dismiss();

                if(response.length()==5){
                    Toast.makeText(Update_Case.this,response.toString(),Toast.LENGTH_LONG).show();

                }




            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Update_Case.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }
    void fetch_all_victims(){
        victim_names.clear();
        String url="http://scd.net23.net/victim.php?set=1";
       // loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);


        StringRequest stringRequest = new StringRequest( Request.Method.GET,url ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  loading.dismiss();
                JSONObject j = null;

                try{
                    JSONArray json = new JSONArray( response );
                    for (int i = 0; i < json.length(); i++) {
                        j = json.getJSONObject( i );
                        victim_names.add( i, j.getString( "name" ) + ":" + j.getString( "id" ) );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Update_Case.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);



    }
    void type_victim(){
        fetch_all_victims();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_victim, null);
        dialogBuilder.setView(dialogView);
        final AutoCompleteTextView victims = (AutoCompleteTextView) dialogView.findViewById(R.id.autocomeple_victim);
        final AutoCompleteTextView relation = (AutoCompleteTextView) dialogView.findViewById(R.id.relation_suspect);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>
                ( this, android.R.layout.simple_list_item_1, victim_names );

        victims.setThreshold( 0 );
        victims.setAdapter( adapter2 );


        dialogBuilder.setTitle("New victim");
        dialogBuilder.setMessage("Enter victim's name");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(victims.getText().length()<3)
                {
                    Toast.makeText(Update_Case.this,"Please enter valid name",Toast.LENGTH_LONG).show();

                }
                else{
                    String victim_id,relationship,name;
                    name=victims.getText().toString();
                    String[] split=name.split( ":" );
                    victim_id=split[1];
                    relationship=relation.getText().toString();
                    String url="http://scd.net23.net/victim.php?set=2&relation="+relationship+"&case_id="+case_id+"&victim_id="+victim_id;
                    url = url.replaceAll(" ", "%20");

                    insert_victim_case( url);
                    a.setVictim( name );
                    victims1.setText( a.getVictim() );



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

    void insert_victim_case(String url){
      //  loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);


        StringRequest stringRequest = new StringRequest( Request.Method.GET,url ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
           //     loading.dismiss();
                if(response.toString().length()==1) {

                    Toast.makeText( Update_Case.this, "Information has been added successfully", Toast.LENGTH_LONG ).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Update_Case.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);



    }
    void fetch_all_evidences(){
        evidences.clear();
        String url="http://scd.net23.net/evidences.php?set=1";
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
                        evidences.add( i, j.getString( "detail" )+":"+j.getString( "id" ));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Update_Case.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);


    }

    void new_evidence(){
        fetch_all_evidences();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_evidence, null);
        dialogBuilder.setView(dialogView);
        final AutoCompleteTextView evidence = (AutoCompleteTextView) dialogView.findViewById(R.id.auto_evidence);


        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>
                ( this, android.R.layout.simple_list_item_1, evidences );

        evidence.setThreshold( 0 );
        evidence.setAdapter( adapter2 );
        //  `evidence.showDropDown();


        dialogBuilder.setTitle("New evidence");
        dialogBuilder.setMessage("Enter evidence detail");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(evidence.getText().length()<3)
                {
                    Toast.makeText(Update_Case.this,"Please enter valid evidence",Toast.LENGTH_LONG).show();

                }
                else{
                    String ev;
                    ev=evidence.getText().toString();

                    String []ab;
                    ab=ev.split( ":" );

                    String url="http://scd.net23.net/evidences.php?set=2&case_id="+case_id+"&evidence_id="+ab[1];
                    url = url.replaceAll(" ", "%20");
                    insert_evidence_Case( url);
                    a.setEvidence(ev);

                    evidence1.setText( a.getEvidence() );



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
    void insert_evidence_Case(String url){
        //loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);


        StringRequest stringRequest = new StringRequest( Request.Method.GET,url ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // loading.dismiss();
                if(response.length()==1){
                    Toast.makeText(Update_Case.this,"Evidence has been inserted successfully",Toast.LENGTH_LONG).show();

                }




            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Update_Case.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);


    }

    void add_suspect_case(){
        fetch_suspects();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_suspect, null);
        dialogBuilder.setView(dialogView);
        final AutoCompleteTextView sus = (AutoCompleteTextView) dialogView.findViewById(R.id.autocomeple_add_suspect);


        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>
                ( this, android.R.layout.simple_list_item_1, suspect_names );

        sus.setThreshold( 0 );
        sus.setAdapter( adapter2 );


        dialogBuilder.setTitle("New suspect");
        dialogBuilder.setMessage("Enter suspect name");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(sus.getText().length()<3)
                {
                    Toast.makeText(Update_Case.this,"Please enter valid suspect's name",Toast.LENGTH_LONG).show();

                }
                else{
                    String s;
                    s=sus.getText().toString();
                    a.setSuspect( s );
                    String []ab;
                    ab=s.split( ":" );

                    String url="http://scd.net23.net/insert_criminal.php?set=2&case_id="+case_id+"&suspect_id="+ab[1];
                    url = url.replaceAll(" ", "%20");
                    insert_suspect_case( url);
                    suspects_case.setText( a.getSuspect() );



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
    void insert_suspect_case(String url){
       loading = ProgressDialog.show(this,"Please wait...","Contacting the server...",true,true);


        StringRequest stringRequest = new StringRequest( Request.Method.GET,url ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              loading.dismiss();
                if(response.length()==1){
                    Toast.makeText(Update_Case.this,"information has been inserted successfully",Toast.LENGTH_LONG).show();
                }




            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Update_Case.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);




    }
    void edit_victims(){
        String ab;
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(Update_Case.this);
        builderSingle.setTitle("Select the victim:-");
        if (TextUtils.isEmpty(a.getVictim())) {
            return;
        }else{
            ab=a.getVictim();
        }


        String [] as=ab.split( "," );
        List<String> stringList = new ArrayList<String>( Arrays.asList(as)); //new ArrayList is only needed if you absolutely need an ArrayList

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Update_Case.this, android.R.layout.select_dialog_singlechoice,stringList);


        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(Update_Case.this);
                builderInner.setMessage("Are sure you want to remove "+
                        strName);
                builderInner.setTitle("Remove a victim");
                builderInner.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        String ids[]=strName.split( ":" );

                        String url="http://scd.net23.net/victim.php?set=3&victim_id="+ids[1]+"&case_id="+a.getId();
                        deleteDb(url);

                        String victims=a.getVictim().replace( strName+",","" );
                        a.setDeletevictim( victims );
                        victims1.setText( a.getVictim() );

                    }
                });
                builderInner.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();



    }
    void edit_suspects(){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(Update_Case.this);
        builderSingle.setTitle("Select the suspect:-");
        String ab;
        if (TextUtils.isEmpty(a.getSuspect())) {
            return;
        }else{
            ab=a.getSuspect();
        }
        String [] as=ab.split( "," );
        List<String> stringList = new ArrayList<String>( Arrays.asList(as)); //new ArrayList is only needed if you absolutely need an ArrayList

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Update_Case.this, android.R.layout.select_dialog_singlechoice,stringList);


        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(Update_Case.this);
                builderInner.setMessage("Are sure you want to remove "+
                        strName);
                builderInner.setTitle("Remove a suspect");
                builderInner.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        String ids[]=strName.split( ":" );

                        String url="http://scd.net23.net/criminals.php?set=3&suspect_id="+ids[1]+"&case_id="+a.getId();
                        deleteDb(url);

                        String suspects=a.getSuspect().replace( strName+",","" );
                        a.setDeletesuspect( suspects );
                        suspects_case.setText( a.getSuspect() );

                    }
                });
                builderInner.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();



    }
    void edit_evidence(){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(Update_Case.this);
        builderSingle.setTitle("Select the evidence:-");
        String ab;
        if (TextUtils.isEmpty(a.getEvidence())) {
            return;
        }else{
            ab=a.getEvidence();
        }
        String [] as=ab.split( "," );
        List<String> stringList = new ArrayList<String>( Arrays.asList(as)); //new ArrayList is only needed if you absolutely need an ArrayList

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Update_Case.this, android.R.layout.select_dialog_singlechoice,stringList);


        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(Update_Case.this);
                builderInner.setMessage("Are sure you want to remove "+
                        strName);
                builderInner.setTitle("Remove an evidence");
                builderInner.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        String ids[]=strName.split( ":" );

                        String url="http://scd.net23.net/evidences.php?set=3&evidence_id="+ids[1]+"&case_id="+a.getId();
                        deleteDb(url);

                        String evidences=a.getEvidence().replace( strName+",","" );
                        a.setDeleteEvidence( evidences );
                        evidence1.setText( a.getEvidence() );

                    }
                });
                builderInner.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();



    }

    void deleteDb(String url){
       // loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);


        StringRequest stringRequest = new StringRequest( Request.Method.GET,url ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  loading.dismiss();
                if(response.length()==1){
                    Toast.makeText(Update_Case.this,"Successfully deleted",Toast.LENGTH_LONG).show();
                    success=true;

                }




            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Update_Case.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);

    }
}