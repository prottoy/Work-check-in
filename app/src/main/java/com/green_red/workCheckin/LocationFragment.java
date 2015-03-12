package com.green_red.workCheckin;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationFragment extends Fragment {
    private GoogleMap mMap;
    private static View view;
    public static final String MY_PREFS_NAME = "gnrcredentials";

    public static LocationFragment newInstance(String param1, String param2) {
        LocationFragment fragment = new LocationFragment();
        return fragment;
    }

    public LocationFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        autoCheckIn();
        if (view==null) {
            view = (RelativeLayout) inflater.inflate(R.layout.fragment_location, container, false);
            setUpMapIfNeeded();
        }
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void autoCheckIn() {
        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, getActivity().MODE_PRIVATE);
        String restoredText = prefs.getString("name", null);

        if (restoredText != null) {
            String name = prefs.getString("name", "");
            String email = prefs.getString("email", "");

            if (name.length() > 0 && email.length() > 0) {
                Toast.makeText(getActivity().getApplicationContext(), "CHECKING IN!",
                        Toast.LENGTH_LONG).show();
                if (isNetworkAvailable()) {
                    Checkin cin = new Checkin();
                    Checkin.context = getActivity().getApplicationContext();
                    cin.execute(name, email);

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "NO INTERNET CONNECTION! PLEASE CONNECT TO GNR WIFI",
                            Toast.LENGTH_LONG).show();
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                }
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "PLEASE SET YOUR NAME AND EMAIL FIRST!",
                        Toast.LENGTH_LONG).show();

            }
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "PLEASE SET YOUR NAME AND EMAIL FIRST!",
                    Toast.LENGTH_LONG).show();

        }
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
                Toast.makeText(getActivity().getApplicationContext(), "Unable to create Maps", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(getActivity().getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
