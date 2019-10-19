package com.example.mysqldblogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void buLogin(View view) {
        EditText username = (EditText) findViewById(R.id.ED1);
        EditText pass = (EditText) findViewById(R.id.ED2);

        new MyAsyncTaskresourses().execute(
                "https://harmonious-forehead.000webhostapp.com/?username="
                        +username.getText().toString()
                        +"&password="+pass.getText().toString());

    }


    String result = "";
    public class MyAsyncTaskresourses extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... params) {
            InputStream isr = null;

            try {

                String URL =params[0];
                URL url = new URL(URL);
                URLConnection urlConnection = url.openConnection();
                isr = new BufferedInputStream(urlConnection.getInputStream());

            }catch (Exception e){
                Log.e("Log_tag","Error in http connection "+e.toString());
            }


            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);

                StringBuilder sb = new StringBuilder();
                String line = null ;

                while ((line= reader.readLine()) != null){
                    sb.append(line+"\n");
                }
                isr.close();

                result = sb.toString();
                System.out.println("result do in = "+result);

            }catch (Exception e){
                Log.e("Log_tag","Error in http connection "+e.toString());
            }

            return null;
        }


        @Override
        protected void onPostExecute(String result2) {

            try {
                String s= "";
                JSONArray jArray = new JSONArray(result);

                System.out.println("jArray post = "+jArray);

                for (int i = 0;i<jArray.length();i++){
                    JSONObject json =   jArray.getJSONObject(i);
                    s= s + "login info" +json.getString("id") + " " + json.getString("username") + " " + json.getString("password");
                    System.out.println("result post = "+result);
                    break;
                }
                if (s.length()>0){
                    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                    Intent in = new Intent(getApplicationContext(),Main2Activity.class);
                    startActivity(in);
                }else
                    Toast.makeText(getApplicationContext(),"user name or password isnot correct",Toast.LENGTH_LONG).show();



            }catch (Exception e){
                Log.e("log_tag","Error parsing Data"+e.toString());
            }



        }


    }
}
