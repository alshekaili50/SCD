package com.IEEE.SCD;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.journaldev.loginphpmysql.R;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    Context context;
    ArrayList<String> suspect_id = new ArrayList<String>();
    ArrayList<String> location_rating = new ArrayList<String>();
    ArrayList<String> witness_rating = new ArrayList<String>();
    ArrayList<String> case_id = new ArrayList<String>();
    ArrayList<String> type_rating = new ArrayList<String>();
    ArrayList<String> weapon_rating = new ArrayList<String>();
    ArrayList<String> victim_rating = new ArrayList<String>();
    ArrayList<String> evidence_rating = new ArrayList<String>();
    ArrayList<String> overall_rating = new ArrayList<String>();



    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView text;
        public ImageView imageView;


        public ViewHolder(View v) {
            super(v);
            text = (TextView) v.findViewById(R.id.text34);
        //    imageView = (ImageView) v.findViewById(R.id.icon);


        }
    }
   /* public void add(int position, movie item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }
    public void remove(movie item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }*/
    public DataAdapter(ArrayList<String> suspect_id,ArrayList<String> case_id,ArrayList<String> evidence_rating,ArrayList<String> weapon_rating,ArrayList<String> victim_rating,ArrayList<String> location_rating,
                     ArrayList<String> type_rating,ArrayList<String> witness_rating,ArrayList<String> overall_rating) {
        this.case_id=case_id;
        this.suspect_id=suspect_id;
        this.evidence_rating=evidence_rating;
        this.witness_rating=witness_rating;
        this.victim_rating=victim_rating;
        this.overall_rating=overall_rating;
        this.type_rating=type_rating;
        this.weapon_rating=witness_rating;
        this.location_rating=location_rating;
    }
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_scd, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.text.setText(suspect_id.get(position));





       // Glide.with(holder.imageView.getContext()).load(mDataset.get(position).getUrl()).into(holder.imageView);



    }
    @Override
    public int getItemCount() {
        return suspect_id.size();
    }



}
