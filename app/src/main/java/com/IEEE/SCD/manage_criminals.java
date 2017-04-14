package com.IEEE.SCD;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.Calendar;
import java.util.HashMap;

public class manage_criminals extends AppCompatActivity {
    private ProgressDialog loading;

    private int mYear;
    private int mMonth;
    private int mDay;
    suspect s;
    int x=1;
    ArrayList<HashMap<String, String>> MyArrList;
    String[] Cmd = {"View","Update"};
     ListView lisView1;

LayoutInflater layout1;
    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_criminals);
         lisView1 = (ListView)findViewById(R.id.listView1);
        lisView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setBackgroundColor(0x0000FF00);
            }
        });



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


        // keySearch
        EditText strKeySearch = (EditText)findViewById(R.id.txtKeySearch);

        // Disbled Keyboard auto focus
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(strKeySearch.getWindowToken(), 0);

        String url = "http://scd.net23.net/jsonData.php?set=0&search="+strKeySearch.getText().toString();
        url = url.replaceAll(" ", "%20");
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
            view_suspect_dialog( MyArrList,info.position );
        }




         else if ("Update".equals(CmdName)) {
            Toast.makeText(manage_criminals.this,"Your Selected Update",Toast.LENGTH_LONG).show();

            String sMemberID = MyArrList.get(info.position).get("id").toString();
            show_suspect_dialog(MyArrList,info.position);


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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     //   getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    void show(String url){
        RequestQueue queue = Volley.newRequestQueue(this);


// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
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
                                    map.put("dob", c.getString("dob"));
                                    map.put("sex", c.getString("sex"));
                                    map.put("body_type", c.getString("body_type"));
                                    map.put("height", c.getString("height"));
                                    map.put("skin_color", c.getString("skin_color"));
                                    map.put("num", Integer.toString(x));
                                    x++;
                                    MyArrList.add(map);
                                    Log.d( "a" ,MyArrList.get( i ).get( "id" ));

                                }}
                            else{
                                Toast.makeText(manage_criminals.this,"0 results",Toast.LENGTH_LONG).show();
                            }

                           lisView1.setAdapter(new ImageAdapter(manage_criminals.this));

                            registerForContextMenu(lisView1);


                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(manage_criminals.this, "Could not fetch information", Toast.LENGTH_LONG).show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);










    }
    private PopupWindow pwindo;

    private void initiatePopupWindow(AdapterView.AdapterContextMenuInfo info) {
        try {
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) manage_criminals.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.view_suspect,
                    (ViewGroup) findViewById(R.id.popup_element));
TextView text1=(TextView)layout.findViewById( R.id.textid );
            TextView text2=(TextView)layout.findViewById( R.id.textName );
            TextView text3=(TextView)layout.findViewById( R.id.textLocation );
            TextView text4=(TextView)layout.findViewById( R.id.textcity );
            TextView text5=(TextView)layout.findViewById( R.id.textType );
            TextView text6=(TextView)layout.findViewById( R.id.textsus );
            TextView text7=(TextView)layout.findViewById( R.id.textDate );
           text1.setText( "ID: "+MyArrList.get(info.position).get("id").toString());
            text2.setText( "Name: "+MyArrList.get(info.position).get("name").toString());
            text3.setText( "Date of birth: "+MyArrList.get(info.position).get("dob").toString());
            text4.setText( "City: "+MyArrList.get(info.position).get("city").toString());
            text5.setText( "Type of past crimes: "+MyArrList.get(info.position).get("type").toString());
            String [] sus={MyArrList.get(info.position).get("s1").toString(),MyArrList.get(info.position).get("s2").toString(),
                    MyArrList.get(info.position).get("s3").toString(),MyArrList.get(info.position).get("s4").toString(),
                    MyArrList.get(info.position).get("s5").toString()};
            String s="";
            for(int i=0;i<5;i++){
               if(sus[i].length()==5){
                   s=s+sus[i]+",";
                   text6.setText( "Suspect crimes: "+s);
               }
            }
            text7.setText( "Gender: "+MyArrList.get(info.position).get("sex").toString());

            PopupWindow popupMenu = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        //    pwindo = new PopupWindow(layout, 400, 600, true);
            popupMenu.showAtLocation(layout, Gravity.CENTER, 0, 0);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
void fetch_suspect_info(String id){
    RequestQueue queue = Volley.newRequestQueue(this);


// Request a string response from the provided URL.
    StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://scd.net23.net/criminals.php?id="+ id,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONArray c = null;


                    try {


                        c = new JSONArray(response);
                        JSONObject b = new JSONObject(response);
                        //b = c.getJSONObject(0);
                        s.setId( b.getString("id") );
                        s.setBody_type( b.getString( "body_type" ) );
                        s.setDob( b.getString( "dob" ) );
                        s.setGender( b.getString( "sex" ) );
                        s.setHeight( b.getString( "height" ) );
                        s.setName( b.getString( "name" ) );
                        s.setSkin_color( b.getString( "skin_color"));



                        }


                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                   // show_suspect_dialog();
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(manage_criminals.this, "Cannot fetch information", Toast.LENGTH_LONG).show();
        }
    });
// Add the request to the RequestQueue.
    queue.add(stringRequest);

}




    void show_suspect_dialog(final ArrayList<HashMap<String, String>> Myarraylist, final int pos){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.new_suspect, null);
        dialogBuilder.setView(dialogView);
         final EditText full_name = (EditText) dialogView.findViewById(R.id.fullname);
       full_name.setText(Myarraylist.get( pos ).get( "name" ).toString());

         final EditText date_of_suspect = (EditText) dialogView.findViewById( R.id.dob );
         final RadioButton male = (RadioButton) dialogView.findViewById(R.id.male);
         final RadioButton female = (RadioButton) dialogView.findViewById(R.id.female);
         final EditText height=(EditText)dialogView.findViewById( R.id.height );
         final Spinner body_type=(Spinner)dialogView.findViewById( R.id.body );
         final Spinner sking=(Spinner)dialogView.findViewById( R.id.skin );
        date_of_suspect.setText(Myarraylist.get( pos ).get( "dob" ).toString() );
        height.setText( Myarraylist.get( pos ).get( "height" ).toString( ));
        String c=Myarraylist.get( pos ).get( "sex" ).toString( );
        if(c.equalsIgnoreCase( "male" )){
        male.setChecked( true );

        }else if(c.equalsIgnoreCase(  "female")){
            female.setChecked( true );
        }

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.skin, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sking.setAdapter(adapter2);
        ArrayAdapter<CharSequence> adapter3= ArrayAdapter.createFromResource(this,
                R.array.body, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        body_type.setAdapter(adapter3);
        for(int i=0;i<body_type.getCount();i++){
            if(body_type.getItemAtPosition( i ).equals( Myarraylist.get( pos ).get( "body_type" ).toString( ) )){
                body_type.setSelection( i );
            }
        }
        for(int i=0;i<sking.getCount();i++){
            if(sking.getItemAtPosition( i ).equals( Myarraylist.get( pos ).get( "skin_color" ).toString( ) )){
                sking.setSelection( i );
            }
        }

        date_of_suspect.setOnClickListener( new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Calendar c = Calendar.getInstance();
                mYear = 2000;
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(manage_criminals.this,
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
                    Toast.makeText(manage_criminals.this,"Please enter valid name",Toast.LENGTH_LONG).show();

                }
                else if(date_of_suspect.getText().length()<3){
                    Toast.makeText(manage_criminals.this,"Please enter valid date",Toast.LENGTH_LONG).show();

                }
                else if(!male.isChecked()&&!female.isChecked()){
                    Toast.makeText(manage_criminals.this,"Please select gender",Toast.LENGTH_LONG).show();

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


                    Log.d("ali",name);

                    String url="http://scd.net23.net/insert_criminal.php?set=1&id="+Myarraylist.get( pos ).get( "id" ).toString()+"&skin_color="+color+"&height="+h+"&body_type="+body+"&dob="+date+"&gender="+gender+"&name="+name;
                    url = url.replaceAll(" ", "%20");
                    update( url );




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

    void view_suspect_dialog(final ArrayList<HashMap<String, String>> Myarraylist, final int pos){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.new_suspect, null);
        dialogBuilder.setView(dialogView);
        final EditText full_name = (EditText) dialogView.findViewById(R.id.fullname);
        full_name.setText(Myarraylist.get( pos ).get( "name" ).toString());

        final EditText date_of_suspect = (EditText) dialogView.findViewById( R.id.dob );
        final RadioButton male = (RadioButton) dialogView.findViewById(R.id.male);
        final RadioButton female = (RadioButton) dialogView.findViewById(R.id.female);
        final EditText height=(EditText)dialogView.findViewById( R.id.height );
        final Spinner body_type=(Spinner)dialogView.findViewById( R.id.body );
        final Spinner sking=(Spinner)dialogView.findViewById( R.id.skin );
        date_of_suspect.setText(Myarraylist.get( pos ).get( "dob" ).toString() );
        height.setText( Myarraylist.get( pos ).get( "height" ).toString( ));
        String c=Myarraylist.get( pos ).get( "sex" ).toString( );
        if(c.equalsIgnoreCase( "male" )){
            male.setChecked( true );

        }else if(c.equalsIgnoreCase(  "female")){
            female.setChecked( true );
        }

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.skin, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sking.setAdapter(adapter2);
        ArrayAdapter<CharSequence> adapter3= ArrayAdapter.createFromResource(this,
                R.array.body, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        body_type.setAdapter(adapter3);
        for(int i=0;i<body_type.getCount();i++){
            if(body_type.getItemAtPosition( i ).equals( Myarraylist.get( pos ).get( "body_type" ).toString( ) )){
                body_type.setSelection( i );
            }
        }
        for(int i=0;i<sking.getCount();i++){
            if(sking.getItemAtPosition( i ).equals( Myarraylist.get( pos ).get( "skin_color" ).toString( ) )){
                sking.setSelection( i );
            }
        }

        date_of_suspect.setOnClickListener( new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Calendar c = Calendar.getInstance();
                mYear = 2000;
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(manage_criminals.this,
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
        date_of_suspect.setEnabled( false );
        full_name.setEnabled( false );
        male.setEnabled( false );
        female.setEnabled( false );
        sking.setEnabled( false );
        height.setEnabled( false );
        body_type.setEnabled( false );


        dialogBuilder.setTitle("Suspect details");

        dialogBuilder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();

    }




    void update(String url){
        RequestQueue queue = Volley.newRequestQueue(this);

        loading = ProgressDialog.show(this,"Please wait...","Contacting the server...",true,true);
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        if(response.length()==1){
                            Toast.makeText(manage_criminals.this, "Information has been updated", Toast.LENGTH_LONG).show();
                            ShowData();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(manage_criminals.this, "Could not update the information", Toast.LENGTH_LONG).show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }






}