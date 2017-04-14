package com.IEEE.SCD;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.DecimalFormat;

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
        double victims1 = 0,location1=0,evidences1=0,type1=0,weapon1=0,witness1=0,overall1=0;

        try {
            victims1 = Double.parseDouble(avg_victims);
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        try {
            location1 = Double.parseDouble(avg_location);
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        try {
            evidences1 = Double.parseDouble(avg_evidences);
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        try {
            type1 = Double.parseDouble(avg_type);
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        try {
            weapon1 = Double.parseDouble(avg_weapon);
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        try {
            witness1 = Double.parseDouble(avg_witness);
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        try {
            overall1 = Double.parseDouble(avg_overall);
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        victims1*=100;
        witness1*=100;
        overall1*=100;
        weapon1*=100;
        type1*=100;
        evidences1*=100;
        location1*=100;
        super.onCreate( savedInstanceState );
        setContentView( R.layout.scd_details );
        TextView victims = (TextView) findViewById( R.id.avg_victims );
        TextView location = (TextView) findViewById( R.id.avg_location );
        TextView evidences = (TextView) findViewById( R.id.avg_evidences );
        TextView type = (TextView) findViewById( R.id.avg_type );
        TextView weapon = (TextView) findViewById( R.id.avg_weapon );
        TextView witness = (TextView) findViewById( R.id.avg_witness );
        TextView overall = (TextView) findViewById( R.id.avg_overall );
        victims.setText( "Victim relationship similarity : "+new DecimalFormat("##.##").format(victims1)+"%");
        location.setText( "Location similarity : "+new DecimalFormat("##.##").format(location1)+"%" );
        evidences.setText( "Evidences similarity : "+new DecimalFormat("##.##").format(evidences1)+"%" );
        type.setText( "Crime type similarity : "+new DecimalFormat("##.##").format(type1)+"%");
        weapon.setText( "Weapon similarity : "+new DecimalFormat("##.##").format(weapon1)+"%");
        witness.setText( "Witness similarity : "+new DecimalFormat("##.##").format(witness1)+"%" );
        overall.setText( "Overall similarity : "+new DecimalFormat("##.##").format(overall1)+"%" );
    }

}