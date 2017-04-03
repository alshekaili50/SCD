package com.IEEE.SCD;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.journaldev.loginphpmysql.R;

import java.util.List;

/**
 * Created by uae25 on 3/30/2017.
 */

public class FeedListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<FeedItem> DataList;

    public FeedListAdapter(Activity activity, List<FeedItem> dataitem) {
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
            convertView = inflater.inflate( R.layout.news_item, null);
        TextView time = (TextView) convertView.findViewById(R.id.time_feed);
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
*/
        final FeedItem m = DataList.get(position);
        name.setText(m.getType());
        if(m.getType().equals( "Homicide" )){
        image.setImageResource( R.mipmap.ic_murder );}
        else if(m.getType().equals( "Kidnapping" )){
            image.setImageResource( R.mipmap.ic_kidnapping );}
        else if(m.getType().equals( "Larceny" )|| m.getType().equals( "Burglary" )){
            image.setImageResource( R.mipmap.ic_theft );
        }
        else if(m.getType().equals( "Assault" )){
            image.setImageResource( R.mipmap.ic_assult );
        }
        else if(m.getType().equals( "Drug Possession" )){
            image.setImageResource( R.mipmap.ic_drug );
        }
        else if(m.getType().equals( "Arson" )){
            image.setImageResource( R.mipmap.ic_arson );
        }
        else if(m.getType().equals( "Forgery" )){
            image.setImageResource( R.mipmap.ic_forgery );
        }
        else if(m.getType().equals( "Other" )){
            image.setImageResource( R.mipmap.ic_other );
        }
        else if(m.getType().equals( "" )){
            image.setImageResource( R.mipmap.ic_dice );
        }
   time.setText(m.getUploaded_date()+"  "+m.getUploaded_time()+" GMT");
        header.setText( "A new case has been added in "+m.getUploaded_date()+" " );
      //  id.setText( m.getId() );
       // image.setImageResource( R.drawable.ic );
        convertView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(FeedListAdapter.this, case_details.class);
                context=v.getContext();
                Intent intent = new Intent(context,case_details.class);
                intent.putExtra("id",m.getId() );
                intent.putExtra("weapon",m.getWeapon() );
                intent.putExtra("type",  m.getType());
                intent.putExtra("name",  m.getName());
                intent.putExtra("date",  m.getDate());
                intent.putExtra("lang",  m.getLang());
                intent.putExtra("lat",  m.getLat());
                intent.putExtra("time",  m.getTime());
                intent.putExtra("uploaded_date",  m.getUploaded_date());
                intent.putExtra("uploaded_time",  m.getUploaded_time());
                context.startActivity(intent);


                // startActivityForResult(intent, 500);

                // String sName = MyArrList.get(info.position).get("name").toString();


            //    startActivity(intent);
            }
        } );

        return convertView;
    }



}
