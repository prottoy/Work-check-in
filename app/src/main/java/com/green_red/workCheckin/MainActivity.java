package com.green_red.workCheckin;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;
import com.green_red.workCheckin.ContactsFragment.OnFragmentInteractionListener;

public class MainActivity extends FragmentActivity implements OnFragmentInteractionListener{
    public static final String MY_PREFS_NAME = "gnrcredentials";
    public static FragmentManager fragmentManager;
    public static final int NUM_ITEMS=10;
    public boolean mShowingBack= false;

    public Menu globalMenu;
    private LocationFragment locationFragment= new LocationFragment();
    private ContactsFragment contactsFragment= new ContactsFragment();
    private SettingsFragment settingsFragment= new SettingsFragment();
    private CasualLeaveFragment casualLeaveFragment= new CasualLeaveFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Work Check-in");
        actionBar.setIcon(R.drawable.ic_launcher);

        fragmentManager=getFragmentManager();
        showContactsFragment();
//        showCasualLeaveFragment();

        CheckInList cin = new CheckInList();
        CheckInList.context = getApplicationContext();
        cin.execute("", "");

    }

    private void showCasualLeaveFragment() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        RemoveLocationFragment(ft);
        ft.replace(R.id.placeholder,casualLeaveFragment);
        ft.commit();
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
        FragmentTransaction ft = fragmentManager.beginTransaction();
        RemoveLocationFragment(ft);
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

    private void sendEmail(String[] emails, String subject, String body) {
        final Intent emailIntent = new Intent( Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_EMAIL,
                emails);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                body);
        startActivity(Intent.createChooser(
                emailIntent, "Send mail..."));
    }

    public void askForLeave(View view){
        String[] emails={"internal@gandr.com.bd","shabbir@gandr.com.bd"};
        String subject= "Casual leave request";
        String body="Please grant me a casual leave today.";

        sendEmail(emails, subject,body);
    }

    public void showCasualLeaveFragment(View view) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        RemoveLocationFragment(ft);
        ft.setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out,R.animator.card_flip_left_in, R.animator.card_flip_left_out);
        ft.replace(R.id.placeholder,casualLeaveFragment );
        ft.commit();

//        if (mShowingBack) {
//            fragmentManager.popBackStack();
//            return;
//        }
//
//        // Flip to the back.
//        mShowingBack = true;
//
//        // Create and commit a new fragment transaction that adds the fragment for the back of
//        // the card, uses custom animations, and is part of the fragment manager's back stack.
//        fragmentManager
//                .beginTransaction()
//
//                        // Replace the default fragment animations with animator resources representing
//                        // rotations when switching to the back of the card, as well as animator
//                        // resources representing rotations when flipping back to the front (e.g. when
//                        // the system Back button is pressed).
//                .setCustomAnimations(
//                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
//                        R.animator.card_flip_left_in, R.animator.card_flip_left_out)
//
//                        // Replace any fragments currently in the container view with a fragment
//                        // representing the next page (indicated by the just-incremented currentPage
//                        // variable).
//                .replace(R.id.placeholder, casualLeaveFragment)
//
//                        // Add this transaction to the back stack, allowing users to press Back
//                        // to get to the front of the card.
//                .addToBackStack(null)
//
//                        // Commit the transaction.
//                .commit();
    }

}
