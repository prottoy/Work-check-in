package com.green_red.workCheckin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;


public class Settings extends ActionBarActivity {
    public static final String MY_PREFS_NAME = "gnrcredentials";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        EditText nameText   = (EditText)findViewById(R.id.nameText);
        EditText emailText   = (EditText)findViewById(R.id.emailText);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString("name", null);
        if (restoredText != null) {
            String name = prefs.getString("name", "");
            String email = prefs.getString("email", "");

            if (name.length()>0 && email.length()>0) {
                nameText.setHint(name);
                emailText.setHint(email);
            }
            Toast.makeText(getApplicationContext(), "Name: " + name + " Email:" + email,
                    Toast.LENGTH_LONG).show();
        }

        Button saveBtn = (Button) findViewById(R.id.savebtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameText   = (EditText)findViewById(R.id.nameText);
                EditText emailText   = (EditText)findViewById(R.id.emailText);

                if (nameText.length()>0 && emailText.length()>0) {
                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("name", nameText.getText().toString());
                    editor.putString("email", emailText.getText().toString());
                    editor.commit();

                    Toast.makeText(getApplicationContext(), "NAME AND EMAIL SAVED!",
                            Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "PLEASE ENTER BOTH NAME AND EMAIL.",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
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
}
