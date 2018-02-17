package com.example.adi.jsoupwa;

import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jsoup.nodes.Document;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ProgressBar pBar;
    TextView textView;
    public String TAG="Aditya";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pBar=(ProgressBar) findViewById(R.id.pBar);
        textView=(TextView)findViewById(R.id.text);
        new JSoup().execute("https://scholarships.gov.in/homePage");

    }

    public class JSoup extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(String s) {
            pBar.setVisibility(View.INVISIBLE);
            if(s.equals(null)){
                textView.setText("Aditya");
            }
            else{
                textView.setText(s);
            }
        }

        @Override
        protected String doInBackground(String...urls) {
            String url=urls[0];
            String  a="";

            int count=0;
            try {
                Document doc = Jsoup.connect(url).get();
              //  Elements links = doc.select("a[href]");
              //  for (Element link : links) {
                  //  System.out.println("\nlink : " + link.attr("href"));
                    //a=a+link.attr("href")+"\n";
                  //System.out.println("text : " + link.text());
                    String ids[]={"default-content","full-content"};
                    for(String id:ids) {
                        Element loginform = doc.getElementById(id);
                        Elements aa = loginform.getElementsByTag("tr");
                        for (Element ss : aa) {
                            Elements s = ss.select("td");
                            if (!s.get(0).className().equals("header type-2")) {
                                Element link = s.get(5).select("a").first();
                                String relHref = "https://scholarships.gov.in/"+link.attr("href");
                                a = a + s.get(1).text() + "\n" +s.get(2).text()+"\n"+relHref+ "\n\n";

                            }}


                    }
               // }
            return a;

            }
            catch (IOException e){
                e.printStackTrace();
                return null;

            }

        }
    }


}
