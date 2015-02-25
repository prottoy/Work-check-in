package com.green_red.workCheckin;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

public class ContactDetail extends ActionBarActivity {
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        setTheme(android.R.style.Theme_Dialog);

        String nameString= getIntent().getStringExtra("name");
        TextView name= (TextView)findViewById(R.id.nameText);
        name.setText(nameString);

        String designationString= getIntent().getStringExtra("designation");
        TextView designation= (TextView)findViewById(R.id.designationTextView);
        designation.setText(designationString);

        String phoneString= getIntent().getStringExtra("phone");
        TextView phone= (TextView)findViewById(R.id.phoneTextView);
        phone.setText(phoneString);

        phoneNumber= phoneString;

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(nameString);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contact_detail, menu);

        menu.findItem(R.id.action_settings).setIcon(
                new IconDrawable(this, Iconify.IconValue.fa_gear).
                        colorRes(R.color.white).
                        actionBarSize());

        menu.findItem(R.id.action_checkin).setIcon(
                new IconDrawable(this, Iconify.IconValue.fa_map_marker).
                        colorRes(R.color.white).
                        actionBarSize());

        menu.findItem(R.id.action_refresh).setIcon(
                new IconDrawable(this, Iconify.IconValue.fa_male).
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
            finish();
            return true;
        }else if(id == R.id.action_checkin) {
            Toast.makeText(getApplicationContext(), "Loading map", Toast.LENGTH_SHORT).show();

            Intent mapsIntent= new Intent(getApplicationContext(),Location.class);
            startActivity(mapsIntent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void callNumber(View view){
        Toast.makeText(getApplicationContext(), "Calling "+ phoneNumber ,
                Toast.LENGTH_LONG).show();

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);
    }

    public void showSettings(View view){
        Intent settingsIntent= new Intent(getApplicationContext(),Settings.class);
        view.getContext().startActivity(settingsIntent);
    }
}
