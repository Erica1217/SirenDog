package com.teamteam.sirendog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.teamteam.sirendog.list.PhoneArrayList;

import java.util.ArrayList;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.MyViewHolder>{
    PhoneArrayList<Phone> data;
    Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name;
        public TextView phone;
        public ImageView callIcon;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            phone = itemView.findViewById(R.id.item_phone);
            callIcon = itemView.findViewById(R.id.item_icon);
        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public PhoneAdapter(Context context, PhoneArrayList<Phone> data) {
        this.context = context;
        this.data = data;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PhoneAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        View view = inflater.inflate(R.layout.item_phone, parent, false) ;
        MyViewHolder vh = new MyViewHolder(view);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.name.setText(data.getValue(position).name);
        holder.phone.setText(data.getValue(position).phone);

        holder.callIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tt = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + data.getValue(position).phone));
                context.startActivity(tt);
            }
        });


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return data.length();
    }
}
