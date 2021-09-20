package com.example.bitcointracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String result = "";
    String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
    TextView textView;
    private Button infoBTN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView1);
        //SET FINNISH TIMEZONE TO CLOCK
        TextClock kello = (TextClock) findViewById(R.id.tclock);
        kello.setTimeZone("EET");

        //LAUNCHING jSON FETCH ON THE BACKGROUND
        new jsonTask().execute();

        //FETCH UPDATED PRICE
        Button RFbtn = (Button) findViewById(R.id.refreshBTN);
        RFbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new jsonTask().execute();
            }
        });
        // INFO PAGE ACTIVITY LAUNCHER
        infoBTN = (Button) findViewById(R.id.infoBTN);
        infoBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInfo();
            }
        });

    }

        public void openInfo(){
            Intent intent = new Intent(this, infoPage.class);
            startActivity(intent);
        }




    class jsonTask extends AsyncTask<Void,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL myurl = new URL(url);
                HttpURLConnection urlConnection = (HttpURLConnection)myurl.openConnection();
                InputStreamReader streamReader = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder builder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null){
                        builder.append(line);
                }

                  result =  builder.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                StringBuilder builder = new StringBuilder();
                JSONObject object = new JSONObject(s);

                        // Objectin haku
                        JSONObject bpiObject = object.getJSONObject("bpi");
                        JSONObject usdObject = bpiObject.getJSONObject("USD");

                        // String builderiin hinta ja dollarilogo
                        builder.append(usdObject.getString("rate")).append("$").append("\n");

                        // formatointi
                        builder. delete(9,11);

                        // builder textview hinnan näyttöön.
                        textView.setText(builder);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}