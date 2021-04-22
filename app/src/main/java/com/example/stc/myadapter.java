package com.example.stc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class myadapter extends FirebaseRecyclerAdapter<datamodel,myadapter.myviewholder> {

    public myadapter(@NonNull FirebaseRecyclerOptions<datamodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull datamodel model) {
    holder.header.setText(model.getIns_name());
    holder.desc.setText("Degree :"+model.getDegree());
    holder.deadline.setText("Deadline : "+model.getDeadline());
    holder.test.setText("Testing Date: "+model.getTest());
        Glide.with(holder.img.getContext()).load(model.getLogo()).into(holder.img);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView header,desc,deadline,test;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img1);
            header = itemView.findViewById(R.id.t1);
            desc = itemView.findViewById(R.id.t2);
            deadline = itemView.findViewById(R.id.t3);
            test = itemView.findViewById(R.id.t4);
        }
    }

}
