package com.bdjobs.personalnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tabriz on 23-Apr-16.
 */


public class TitleListAdapter extends ArrayAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<String> newsTitles = new ArrayList<>();


    public TitleListAdapter(Context context, ArrayList<String> newsTitles) {
        super(context, R.layout.title_list_view, newsTitles);
        this.context = context;
        this.newsTitles = newsTitles;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.title_list_view, parent, false);
            viewHolder.titleTV = (TextView) convertView.findViewById(R.id.titleTV);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.titleTV.setText(newsTitles.get(position));

        return convertView;
    }


    private class ViewHolder {
        TextView titleTV;
    }
}
