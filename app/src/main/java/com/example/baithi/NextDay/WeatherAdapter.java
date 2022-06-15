package com.example.baithi.NextDay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.baithi.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WeatherAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<Weather> list;

    public WeatherAdapter(Context context, int layout, List<Weather> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    class ViewHoder{
        TextView txtCurTime, txtState, txtMin, txtMax;
        ImageView imgIcon;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHoder viewHoder = null;
        if (view == null){
            viewHoder = new ViewHoder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout,null);
            viewHoder.txtCurTime = view.findViewById(R.id.txtCurTime);
            viewHoder.txtState = view.findViewById(R.id.txtState);
            viewHoder.imgIcon = view.findViewById(R.id.imgIcon);
            viewHoder.txtMin = view.findViewById(R.id.txtMin);
            viewHoder.txtMax = view.findViewById(R.id.txtMax);
            view.setTag(viewHoder);
        }else{
            viewHoder = (ViewHoder) view.getTag();
        }
        Weather weather = list.get(i);
        viewHoder.txtCurTime.setText(weather.getCurrentTime());
        viewHoder.txtState.setText(weather.getState());
        Picasso.get().load(weather.getUrlIcon()).into(viewHoder.imgIcon);
        viewHoder.txtMin.setText(weather.getMin());
        viewHoder.txtMax.setText(weather.getMax());

        return view;
    }
}
