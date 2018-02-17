package com.example.adi.jsoupwa;

import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recycle;
    List<Scholarship> scholarships =new ArrayList<Scholarship>();
    RecyclerView.Adapter adapter;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycle=(RecyclerView)findViewById(R.id.recycler);
        pb=(ProgressBar)findViewById(R.id.pb);
        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        new JSoup().execute("https://scholarships.gov.in/homePage");

    }

    public class JSoup extends AsyncTask<String,Void,List<Scholarship>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(List<Scholarship> scholarships) {
            pb.setVisibility(View.INVISIBLE);

            adapter=new My_adaptor(scholarships,getApplicationContext());
            recycle.setAdapter(adapter);
        }



        @Override
        protected List<Scholarship> doInBackground(String...urls) {
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
                               // Element link = s.get(5).select("a").first();
                             //   String relHref = "https://scholarships.gov.in/"+link.attr("href");

                                scholarships.add(new Scholarship( s.get(1).text() ,s.get(2).text()));

                            }}



                    }
               // }


            }
            catch (IOException e){
                e.printStackTrace();
            }
            return scholarships;

        }
    }


}
