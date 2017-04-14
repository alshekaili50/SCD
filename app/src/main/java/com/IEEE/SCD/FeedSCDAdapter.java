package com.IEEE.SCD;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by uae25 on 3/30/2017.
 */

public class FeedSCDAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<SCD_item> DataList;

    public FeedSCDAdapter(Activity activity, List<SCD_item> dataitem) {
        this.activity = activity;
        this.DataList = dataitem;
    }
    private Context context;

//save the context recievied via constructor in a local variable

    public void getContext(Context context){
        this.context=context;
    }

    @Override
    public int getCount() {
        return DataList.size();
    }

    @Override
    public Object getItem(int location) {
        return DataList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate( R.layout.scd_item, null);
        TextView overall = (TextView) convertView.findViewById(R.id.overall);
        ImageView image=(ImageView)convertView.findViewById( R.id.thumbnail ) ;
        TextView header = (TextView) convertView.findViewById(R.id.count);



        //  TextView time = (TextView) convertView.findViewById(R.id.timestamp);
        TextView name = (TextView) convertView.findViewById(R.id.title);
      /*
        TextView source = (TextView) convertView.findViewById(R.id.source);
        TextView year = (TextView) convertView.findViewById(R.id.inYear);

        thumbNail.setImageUrl(m.getImage(), imageLoader);

        source.setText("Wealth Source: " + String.valueOf(m.getSource()));
        worth.setText(String.valueOf(m.getWorth()));
        year.setText(String.valueOf(m.getYear()));
*/      Log.d("pos",String.valueOf( position ));
        final SCD_item m = DataList.get(position);
        name.setText(m.getName());
        header.setText( m.getSuspect_id() );
        double overall1=0;
        try {
            overall1 = Double.parseDouble(m.getOverall());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        overall1*=100;
        overall.setText( new DecimalFormat("##.##").format(overall1)+"%" );

        if(position==0){
            image.setImageResource( R.drawable.ic_one );
        }
        if(position==1){
            image.setImageResource( R.drawable.ic_004_two );
        }
        if(position==2){
            image.setImageResource( R.drawable.ic_003_three );
        }
        if(position==3){
            image.setImageResource( R.drawable.ic_002_four );
        }
        if(position==4){
            image.setImageResource( R.drawable.ic_001_five );
        }


   //     time.setText(m.getUploaded_date()+"  "+m.getUploaded_time()+" GMT");
      //  header.setText( "A new case has been added in "+m.getUploaded_date()+" " );
        //  id.setText( m.getId() );
        // image.setImageResource( R.drawable.ic );
        convertView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context=v.getContext();

                Intent intent = new Intent(context, SCD_details.class);

                intent.putExtra("victims",m.getAvg_victims() );
                intent.putExtra("evidences",m.getAvg_evidence() );
                intent.putExtra("location",  m.getAvg_location());
                intent.putExtra("type",  m.getAvg_type());
                intent.putExtra("weapon",  m.getAvg_weapon());
                intent.putExtra("witness",  m.getAvg_witness());
                intent.putExtra("overall",  m.getOverall());


             context.startActivity(intent);


                // startActivityForResult(intent, 500);

                // String sName = MyArrList.get(info.position).get("name").toString();


                //    startActivity(intent);
            }
        } );

        return convertView;
    }



}
