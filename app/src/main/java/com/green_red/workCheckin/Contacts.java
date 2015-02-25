package com.green_red.workCheckin;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Contacts extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        final ArrayList<ContactsInfo> contactsArrayList = new ArrayList<ContactsInfo>();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("People");
        actionBar.setIcon(R.drawable.ic_launcher);

//        ActionBar.Tab tab = actionBar.newTab()
//                .setText("People")
//                .setTabListener(new TabListener<ArtistFragment>(
//                        this, "artist", ArtistFragment.class));
//        actionBar.addTab(tab);
//
//        tab = actionBar.newTab()
//                .setText(R.string.album)
//                .setTabListener(new TabListener<AlbumFragment>(
//                        this, "album", AlbumFragment.class));
//        actionBar.addTab(tab);

        try {
            InputStreamReader is = new InputStreamReader(getAssets().open("numbers.csv"));

            BufferedReader reader = new BufferedReader(is);
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens= line.split(",");
                String name= tokens[1];
                String designation= tokens[2];
                String number= "+"+tokens[3];

                ContactsInfo contactInfo = new ContactsInfo(name, designation,number);
                contactsArrayList.add(contactInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ContactsAdapter contactsAdapter = new ContactsAdapter(this, contactsArrayList);
        ListView contactsList = (ListView) findViewById(R.id.listView);
        contactsList.setAdapter(contactsAdapter);



        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                ContactsInfo item = contactsArrayList.get(position);
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                callIntent.setData(Uri.parse("tel:" + item.number));
//                startActivity(callIntent);

                Intent detailIntent= new Intent(getApplicationContext(), ContactDetail.class);
                detailIntent.putExtra("name",item.name);
                detailIntent.putExtra("designation",item.designation);
                detailIntent.putExtra("phone",item.number);
                startActivity(detailIntent);

                Log.v("test:", "clicked");
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contact_detail, menu);


        menu.findItem(R.id.action_checkin).setIcon(
                new IconDrawable(this, Iconify.IconValue.fa_map_marker).
                        colorRes(R.color.white).
                        actionBarSize());

        menu.findItem(R.id.action_refresh).setIcon(
                new IconDrawable(this, Iconify.IconValue.fa_male).
                        colorRes(R.color.white).
                        actionBarSize());

        menu.findItem(R.id.action_settings).setIcon(
                new IconDrawable(this, Iconify.IconValue.fa_gear).
                        colorRes(R.color.white).
                        actionBarSize());

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsIntent= new Intent(getApplicationContext(),Settings.class);
            startActivity(settingsIntent);
            return true;
        }else if(id == R.id.action_checkin) {
            Toast.makeText(getApplicationContext(), "Loading map", Toast.LENGTH_SHORT).show();

            Intent mapsIntent= new Intent(getApplicationContext(),Location.class);
            startActivity(mapsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
