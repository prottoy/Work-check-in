package com.green_red.workCheckin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

public class WifiReceiver extends BroadcastReceiver {
    public static final String MY_PREFS_NAME = "gnrcredentials";
    @Override
    public void onReceive(Context context, Intent intent) {

        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if(info != null) {
            if(info.isConnected()) {
                // Do your work
                // e.g. To check the Network Name or other info:
                WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ssid = wifiInfo.getSSID();

                Toast.makeText(context, ssid,
                        Toast.LENGTH_LONG).show();

                SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE);
                String restoredText = prefs.getString("name", null);

                if (restoredText != null) {
                    String name = prefs.getString("name", "");
                    String email = prefs.getString("email", "");

                    if (name.length() > 0 && email.length() > 0) {
                        Checkin cin = new Checkin();
                        Checkin.context = context;
                        cin.execute(name, email);
                    } else {
                        Toast.makeText(context, "PLEASE SET YOUR NAME AND EMAIL FIRST!",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, "PLEASE SET YOUR NAME AND EMAIL FIRST!",
                            Toast.LENGTH_LONG).show();
                }
//                }

            }
        }
    }

    private void sendMessage(Context context, String message) {
        Intent intent = new Intent("internet-event");
        // You can also include some extra data.
        intent.putExtra("message", message);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
