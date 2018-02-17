package com.example.adi.jsoupwa;

import android.app.LauncherActivity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.graphics.Color.rgb;
import static android.net.Uri.*;

public class My_adaptor extends RecyclerView.Adapter<My_adaptor.ViewHolder> {
    List<Scholarship> scholarships;
    Context context;
    public My_adaptor(List<Scholarship> i, Context c){
        this.scholarships=i;
        this.context=c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Scholarship sc=scholarships.get(position);
        holder.Scholarships.setText(sc.getScholarship());
        holder.due_date.setText(sc.getDueDate());

        if(sc.getDueDate().split(" ")[0].equals("Closed"))
            holder.due_date.setTextColor(Color.RED);
        else
            holder.due_date.setTextColor(rgb(0,100,0));



        holder.help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"You are being redirected to web-link!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://scholarships.gov.in/loginPage"));
                v.getContext().startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return scholarships.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView Scholarships;
        public TextView due_date;
        public RelativeLayout help;

        public ViewHolder(View itemView) {
            super(itemView);
            Scholarships =(TextView) itemView.findViewById(R.id.Scholarship);
            due_date =(TextView) itemView.findViewById(R.id.due_date);
            help=(RelativeLayout)itemView.findViewById(R.id.help);
        }
    }
}
