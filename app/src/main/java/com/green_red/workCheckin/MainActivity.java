package com.green_red.workCheckin;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ActionBar;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;
import com.green_red.workCheckin.ContactsFragment.OnFragmentInteractionListener;

public class MainActivity extends FragmentActivity implements OnFragmentInteractionListener{
    public static final String MY_PREFS_NAME = "gnrcredentials";
    public static FragmentManager fragmentManager;

    private LocationFragment locationFragment;
    private ContactsFragment contactsFragment= new ContactsFragment();
    private SettingsFragment settingsFragment= new SettingsFragment();
    public Menu globalMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Work Check-in");
        actionBar.setIcon(R.drawable.ic_launcher);

        fragmentManager=getFragmentManager();
        showContactsFragment();
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

        menu.findItem(R.id.action_people).setIcon(
                new IconDrawable(this, Iconify.IconValue.fa_male).
                        colorRes(R.color.red).
                        actionBarSize());

        menu.findItem(R.id.action_settings).setIcon(
                new IconDrawable(this, Iconify.IconValue.fa_gear).
                        colorRes(R.color.white).
                        actionBarSize());

        globalMenu= menu;

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
            showSettingsFragment();
            setMenuSelected(3);

            return true;
        }else if(id == R.id.action_checkin) {
            showLocationFragment();
            setMenuSelected(2);

            return true;
        }else if(id == R.id.action_people) {
            showContactsFragment();
            setMenuSelected(1);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setMenuSelected(int itemNumber) {
        globalMenu.findItem(R.id.action_checkin).setIcon(
                new IconDrawable(this, Iconify.IconValue.fa_map_marker).
                        colorRes(R.color.white).
                        actionBarSize());

        globalMenu.findItem(R.id.action_people).setIcon(
                new IconDrawable(this, Iconify.IconValue.fa_male).
                        colorRes(R.color.white).
                        actionBarSize());

        globalMenu.findItem(R.id.action_settings).setIcon(
                new IconDrawable(this, Iconify.IconValue.fa_gear).
                        colorRes(R.color.white).
                        actionBarSize());

        switch (itemNumber){
            case 1:
                globalMenu.findItem(R.id.action_people).setIcon(
                        new IconDrawable(this, Iconify.IconValue.fa_male).
                                colorRes(R.color.red).
                                actionBarSize());
                break;

            case 2:
                globalMenu.findItem(R.id.action_checkin).setIcon(
                        new IconDrawable(this, Iconify.IconValue.fa_map_marker).
                                colorRes(R.color.red).
                                actionBarSize());
                break;

            case 3:
                globalMenu.findItem(R.id.action_settings).setIcon(
                        new IconDrawable(this, Iconify.IconValue.fa_gear).
                                colorRes(R.color.red).
                                actionBarSize());
                break;

            default:
                globalMenu.findItem(R.id.action_checkin).setIcon(
                        new IconDrawable(this, Iconify.IconValue.fa_map_marker).
                                colorRes(R.color.red).
                                actionBarSize());
                break;
        }
    }

    private void showLocationFragment() {
        locationFragment = new LocationFragment();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.placeholder, locationFragment);
        ft.commit();
    }

    private void showSettingsFragment() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        RemoveLocationFragment(ft);
        ft.replace(R.id.placeholder,settingsFragment );
        ft.commit();
    }


    private void showContactsFragment() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        RemoveLocationFragment(ft);
        ft.replace(R.id.placeholder,contactsFragment );
        ft.commit();
    }

    private void RemoveLocationFragment(FragmentTransaction ft) {
        if (locationFragment!=null) {
            ft.remove(locationFragment);
        }
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
