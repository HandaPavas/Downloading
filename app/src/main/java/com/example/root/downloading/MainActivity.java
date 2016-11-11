package com.example.root.downloading;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView downloadtextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        downloadtextview= (TextView) findViewById(R.id.downloadtextview);

    }

    public void downloading(View view){

        String Url = "https://iiitd.ac.in/about";
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){

            new DownloadContent().execute(Url);
        }
        else{

            downloadtextview.setText("Nothing Downloaded Yet !!!");
        }
    }

    private class DownloadContent extends AsyncTask<String ,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                return downloadurl(strings[0]);
            }
            catch (IOException e){

                return "Invalid Url";

            }
        }

        @Override
        protected void onPostExecute(String s) {
            downloadtextview.setText(s);
        }

        private String downloadurl(String string) throws IOException{

            InputStream inputstream= null;

            int length = 200;
            try{
                URL url= new URL(string);
                HttpURLConnection connection=(HttpURLConnection) url.openConnection();
                connection.setReadTimeout(20000);
                connection.setConnectTimeout(25000);
                connection.getRequestMethod();
                


            }finally {

                if( inputstream != null){
                    inputstream.close();
                }
            }
            return "";

        }
    }
}