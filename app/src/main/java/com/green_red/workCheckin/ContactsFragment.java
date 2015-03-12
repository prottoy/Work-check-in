package com.green_red.workCheckin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;



public class ContactsFragment extends Fragment {
    public static final String MY_PREFS_NAME = "gnrcredentials";

    private OnFragmentInteractionListener mListener;


    // TODO: Rename and change types and number of parameters
    public static ContactsFragment newInstance(String param1, String param2) {
        ContactsFragment fragment = new ContactsFragment();
        return fragment;
    }

    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_contacts, container, false);

        final ArrayList<ContactsInfo> contactsArrayList = new ArrayList<ContactsInfo>();

        try {
            InputStreamReader is = new InputStreamReader(getActivity().getAssets().open("numbers.csv"));

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

        ContactsAdapter contactsAdapter = new ContactsAdapter(getActivity(), contactsArrayList);
        ListView contactsList = (ListView) view.findViewById(R.id.contactsList);
        contactsList.setAdapter(contactsAdapter);


        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                ContactsInfo item = contactsArrayList.get(position);

                Intent detailIntent= new Intent(getActivity().getApplicationContext(), ContactDetail.class);
                detailIntent.putExtra("name",item.name);
                detailIntent.putExtra("designation",item.designation);
                detailIntent.putExtra("phone",item.number);
                startActivity(detailIntent);

                Log.v("test:", "clicked");
            }
        });

        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, getActivity().MODE_PRIVATE);
        String restoredText = prefs.getString("name", null);

        if (restoredText == null) {
            Toast.makeText(getActivity().getApplicationContext(), "PLEASE SET YOUR NAME AND EMAIL FIRST!",
                    Toast.LENGTH_LONG).show();

//            Intent settingsIntent= new Intent(getActivity().getApplicationContext(),Settings.class);
//            startActivity(settingsIntent);
        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
