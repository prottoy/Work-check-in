package com.green_red.workCheckin;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by prottoy on 2/2/15.
 */
public class ContactsAdapter extends BaseAdapter {
    Activity activity;
    ArrayList<ContactsInfo> contactslist;

    public ContactsAdapter(Activity activity, ArrayList contactslist){
        this.activity= activity;
        this.contactslist= contactslist;
    }

    @Override
    public int getCount() {
        return contactslist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater)activity.getApplicationContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listitem, parent,false);
        }

        TextView name = (TextView)convertView.findViewById(R.id.name);
        TextView number = (TextView)convertView.findViewById(R.id.number);

        final ContactsInfo contactinfo= contactslist.get(position);

        name.setText(contactinfo.name);
        number.setText(contactinfo.designation);

        return convertView;
    }

}
