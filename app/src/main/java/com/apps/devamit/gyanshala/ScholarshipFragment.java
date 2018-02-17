package com.apps.devamit.gyanshala;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScholarshipFragment extends Fragment {
    RecyclerView recycle;
    List<Scholarship> scholarships =new ArrayList<>();
    RecyclerView.Adapter adapter;
    ProgressBar pb;

    public ScholarshipFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_scholarship, container, false);
        recycle=layout.findViewById(R.id.recycler);
        pb=layout.findViewById(R.id.pb);
        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(new LinearLayoutManager(getContext()));
        new JSoup().execute("https://scholarships.gov.in/homePage");
        return layout;
    }

    public class JSoup extends AsyncTask<String,Void,List<Scholarship>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Scholarship> doInBackground(String... urls) {
            String url=urls[0];
            String  a="";
            int count=0;
            try {
                Document doc = Jsoup.connect(url).get();
                String ids[]={"default-content","full-content"};
                for(String id:ids) {
                    Element loginform = doc.getElementById(id);
                    Elements aa = loginform.getElementsByTag("tr");
                    for (Element ss : aa) {
                        Elements s = ss.select("td");
                        if (!s.get(0).className().equals("header type-2")) {
                            scholarships.add(new Scholarship( s.get(1).text() ,s.get(2).text()));
                        }
                    }
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
            return scholarships;
        }

        @Override
        protected void onPostExecute(List<Scholarship> scholarships) {
            pb.setVisibility(View.INVISIBLE);
            adapter=new My_adaptor(scholarships,getContext());
            recycle.setAdapter(adapter);
        }
    }
}
