package com.green_red.workCheckin;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsFragment extends Fragment {

    public static final String MY_PREFS_NAME = "gnrcredentials";

    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    public SettingsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        EditText nameText   = (EditText)view.findViewById(R.id.nameText);
        EditText emailText   = (EditText)view.findViewById(R.id.emailText);

        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME,getActivity().MODE_PRIVATE);
        String restoredText = prefs.getString("name", null);
        if (restoredText != null) {
            String name = prefs.getString("name", "");
            String email = prefs.getString("email", "");

            if (name.length()>0 && email.length()>0) {
                nameText.setHint(name);
                emailText.setHint(email);
            }
            Toast.makeText(getActivity().getApplicationContext(), "Name: " + name + " Email:" + email,
                    Toast.LENGTH_LONG).show();
        }

        Button saveBtn = (Button) view.findViewById(R.id.savebtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameText   = (EditText)view.findViewById(R.id.nameText);
                EditText emailText   = (EditText)view.findViewById(R.id.emailText);

                if (nameText.length()>0 && emailText.length()>0) {
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences(MY_PREFS_NAME, getActivity().MODE_PRIVATE).edit();
                    editor.putString("name", nameText.getText().toString());
                    editor.putString("email", emailText.getText().toString());
                    editor.commit();

                    Toast.makeText(getActivity().getApplicationContext(), "NAME AND EMAIL SAVED!",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "PLEASE ENTER BOTH NAME AND EMAIL.",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
