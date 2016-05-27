package com.itrainasia.weathermaygeoloc;

/**
 * Created by me-techmacprouser2 on 26/05/2016.
 */

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CustomAdapter extends ArrayAdapter<JSONObject> {

    private final Activity context;
    private final ArrayList<JSONObject> weather;

    static class ViewHolder {
        public TextView dateTextView;
        public ImageView imageView;
        public TextView tempTextView;
        public TextView descTextView;

    }
    public CustomAdapter(Activity context, ArrayList<JSONObject> myWeather) {
        super(context, R.layout.row_layout, myWeather);
        this.context = context;
        this.weather = myWeather;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.row_layout, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.dateTextView = (TextView) rowView.findViewById(R.id.textView2);
            viewHolder.descTextView = (TextView) rowView.findViewById(R.id.textView3);
            viewHolder.tempTextView = (TextView) rowView.findViewById(R.id.textView4);
            viewHolder.imageView = (ImageView) rowView.findViewById(R.id.imageView);
            rowView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();
        JSONObject weatherObject = weather.get(position);

        try {
            JSONObject weatherInfoObject = weatherObject.getJSONArray("weather").getJSONObject(0);
            holder.tempTextView.setText( Integer.toString(weatherObject.getJSONObject("main").getInt("temp") -273) + " C");
            holder.descTextView.setText(weatherInfoObject.getString("main"));
            holder.dateTextView.setText(weatherObject.getString("dt_txt"));
            Log.d("DEBUG", "http://openweathermap.org/img/w/"+weatherInfoObject.getString("icon")+".png");
            Picasso.with(context).load("http://openweathermap.org/img/w/"+weatherInfoObject.getString("icon")+".png").into(holder.imageView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rowView;
    }

}