package com.IEEE.SCD;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SCD_details extends AppCompatActivity {

String avg_victims,avg_location,avg_evidences,avg_type,avg_weapon,avg_witness,avg_overall;
    protected void onCreate(Bundle savedInstanceState) {


        avg_victims = getIntent().getStringExtra( "victims" );
        avg_location = getIntent().getStringExtra( "location" );
        avg_evidences = getIntent().getStringExtra( "evidences" );
        avg_type = getIntent().getStringExtra( "type" );
        avg_weapon = getIntent().getStringExtra( "weapon" );
        avg_witness = getIntent().getStringExtra( "witness" );
        avg_overall = getIntent().getStringExtra( "overall" );

        super.onCreate( savedInstanceState );
        setContentView( R.layout.scd_details );
        TextView victims = (TextView) findViewById( R.id.avg_victims );
        TextView location = (TextView) findViewById( R.id.avg_location );
        TextView evidences = (TextView) findViewById( R.id.avg_evidences );
        TextView type = (TextView) findViewById( R.id.avg_type );
        TextView weapon = (TextView) findViewById( R.id.avg_weapon );
        TextView witness = (TextView) findViewById( R.id.avg_witness );
        TextView overall = (TextView) findViewById( R.id.avg_overall );
        victims.setText( "Victim relationship similarity : "+avg_victims );
        location.setText( "Location similarity : "+avg_location );
        evidences.setText( "Evidences similarity : "+avg_evidences );
        type.setText( "Crime type similarity : "+avg_type );
        weapon.setText( "Weapon similarity : "+avg_weapon );
        witness.setText( "Witness similarity : "+avg_witness );
        overall.setText( "Overall similarity : "+avg_overall );
    }

}