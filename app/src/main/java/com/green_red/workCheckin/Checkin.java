package com.green_red.workCheckin;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by prottoy on 2/25/15.
 */
public class Checkin extends AsyncTask<String, String, String> {
    static Context context;

    @Override
    protected String doInBackground(String... params) {
        String response= postData(params[0], params[1]);
        String value= null;
        try {
            JSONObject jObj= new JSONObject(response);
            value= (String)jObj.get("response");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("response", response);

        return value;
    }

    protected void onPostExecute(String result) {
        if (result.equals("success")) {
            Toast.makeText(context, result,
                    Toast.LENGTH_LONG).show();

            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            // mId allows you to update the notification later on.
            Notification n = new NotificationCompat.Builder(context)
                    .setContentTitle("GNR Checkin Noification")
                    .setContentText("You have successfully checked in to GNR!")
                    .setSmallIcon(R.drawable.ic_launcher)
                    .build();
            mNotificationManager.notify(Integer.parseInt("100"), n);
        }
    }


    public String postData(String name, String email) {
        String jresponse= null;

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(
                "http://www.green-red.com/square/checkin");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("name",
                    name));
            nameValuePairs.add(new BasicNameValuePair("email",
                    email));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            String json_string = EntityUtils.toString(response.getEntity());
            jresponse= json_string;

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        return jresponse;
    }
}