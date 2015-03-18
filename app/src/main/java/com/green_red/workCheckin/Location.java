package com.green_red.workCheckin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;


public class Location extends Activity{
    private GoogleMap mMap;
    public static final String MY_PREFS_NAME = "gnrcredentials";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Location");

        autoCheckIn();
        setUpMapIfNeeded();
    }

    private void autoCheckIn() {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString("name", null);

        if (restoredText != null) {
            String name = prefs.getString("name", "");
            String email = prefs.getString("email", "");

            if (name.length() > 0 && email.length() > 0) {
                Toast.makeText(getApplicationContext(), "CHECKING IN!",
                        Toast.LENGTH_LONG).show();
                if (isNetworkAvailable()) {
                    Checkin cin = new Checkin();
                    Checkin.context = getApplicationContext();
                    cin.execute(name, email);

                } else {
                    Toast.makeText(getApplicationContext(), "NO INTERNET CONNECTION! PLEASE CONNECT TO GNR WIFI",
                            Toast.LENGTH_LONG).show();
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                }
            } else {
                Toast.makeText(getApplicationContext(), "PLEASE SET YOUR NAME AND EMAIL FIRST!",
                        Toast.LENGTH_LONG).show();

//                Intent settingsIntent= new Intent(getApplicationContext(),Settings.class);
//                startActivity(settingsIntent);
//                finish();
            }
        } else {
            Toast.makeText(getApplicationContext(), "PLEASE SET YOUR NAME AND EMAIL FIRST!",
                    Toast.LENGTH_LONG).show();

//            Intent settingsIntent= new Intent(getApplicationContext(),Settings.class);
//            startActivity(settingsIntent);
//            finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contact_detail, menu);


        menu.findItem(R.id.action_checkin).setIcon(
                new IconDrawable(this, Iconify.IconValue.fa_map_marker).
                        colorRes(R.color.red).
                        actionBarSize());

        menu.findItem(R.id.action_people).setIcon(
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
//            Intent settingsIntent= new Intent(getApplicationContext(),Settings.class);
//            startActivity(settingsIntent);
//            finish();
            return true;
        }else if(id == R.id.action_checkin) {
            Intent mapsIntent= new Intent(getApplicationContext(),Location.class);
            startActivity(mapsIntent);
            finish();
            return true;
        }else if(id == R.id.action_people) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpMapIfNeeded()
    {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null)
        {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

            // Check if we were successful in obtaining the map.
            if (mMap != null)
            {
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(23.794796, 90.403887))
                        .title("G&R Technologies ltd"));

                CameraUpdate center=
                        CameraUpdateFactory.newLatLng(new LatLng(23.794796, 90.403887));
                CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

                mMap.moveCamera(center);
                mMap.animateCamera(zoom);

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
                {
                    @Override
                    public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker)
                    {
                        marker.showInfoWindow();
                        return true;
                    }
                });
            }
            else
                Toast.makeText(getApplicationContext(), "Unable to create Maps", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
