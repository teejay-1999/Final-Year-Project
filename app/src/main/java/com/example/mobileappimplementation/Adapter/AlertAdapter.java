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


public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.holder>{
    JSONArray jsonArray;

    public AlertAdapter(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    @NonNull
    @Override
    public AlertAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.alert_single_row,parent,false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlertAdapter.holder holder, int position) {
        try {
            holder.description.setText(jsonArray.getJSONObject(position).getString("description"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            holder.heading.setText(jsonArray.getJSONObject(position).getString("heading"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }
    class holder extends RecyclerView.ViewHolder{
        private TextView heading,description;
        public holder(@NonNull View itemView) {
            super(itemView);
            heading = (TextView) itemView.findViewById(R.id.heading);
            description = (TextView) itemView.findViewById(R.id.description);

        }
    }
}
