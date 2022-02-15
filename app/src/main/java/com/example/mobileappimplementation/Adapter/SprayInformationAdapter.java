package com.example.mobileappimplementation.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileappimplementation.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class SprayInformationAdapter extends RecyclerView.Adapter<SprayInformationAdapter.holder>{
   ArrayList<String> arrayList;

    public SprayInformationAdapter(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.spray_information_single_row,parent,false);
        return new SprayInformationAdapter.holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.description.setText(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    class holder extends RecyclerView.ViewHolder{
        private TextView description;
        public holder(@NonNull View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.description);

        }
    }
}
