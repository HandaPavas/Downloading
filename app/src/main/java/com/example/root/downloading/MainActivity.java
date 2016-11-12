package com.example.root.downloading;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.root.downloading.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private Document Document;
    Button downloadbutton;
    private String Url = "https://iiitd.ac.in/about";
    private TextView title,content;
    private String ContentToString,TitleToString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (TextView)findViewById(R.id.downloadtextview);
        content = (TextView)findViewById(R.id.downloadcontent);
        downloadbutton = (Button)findViewById(R.id.downloadbutton);
        downloadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // String Url = "https://iiitd.ac.in/about";
                ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if(networkInfo != null && networkInfo.isConnected()){

                    JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                    jsoupAsyncTask.execute();
                }
                else{

                    title.setText("Nothing Downloaded Yet !!!");
                }

            }
        });
    }

    @Override
    public void onSaveInstanceState (Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("Title", TitleToString);
        savedInstanceState.putString("Content", ContentToString);

    }
    @Override
    public void onRestoreInstanceState (Bundle restoreInstanceState){
        super.onRestoreInstanceState(restoreInstanceState);
        TitleToString = restoreInstanceState.getString("Title");
        ContentToString=restoreInstanceState.getString("Content");
        title.setText(TitleToString);
        content.setText(ContentToString);

    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document = Jsoup.connect(Url).get();
                TitleToString = Document.title();
                Elements para=Document.select("p");
                ContentToString = para.text();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            title.setText(TitleToString);
            content.setText(ContentToString);
        }

    }

}