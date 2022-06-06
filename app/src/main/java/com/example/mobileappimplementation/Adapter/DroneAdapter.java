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
import org.w3c.dom.Text;


public class DroneAdapter extends RecyclerView.Adapter<DroneAdapter.holder>{
    JSONArray jsonArray;
    OnNoteListener onNoteListener;

    public DroneAdapter(JSONArray jsonArray, OnNoteListener onNoteListener) {
        this.jsonArray = jsonArray;
        this.onNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public DroneAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.drone_single_row,parent,false);
        return new holder(view, onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DroneAdapter.holder holder, int position) {
        try {
            holder.description.setText(jsonArray.getJSONObject(position).getString("DroneDescription"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            holder.heading.setText(jsonArray.getJSONObject(position).getString("Heading"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            holder.price.setText("Rs " + jsonArray.getJSONObject(position).getString("price"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }
    class holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView heading,description,price;
        private OnNoteListener onNoteListener;
        public holder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            heading = (TextView) itemView.findViewById(R.id.drone_heading);
            description = (TextView) itemView.findViewById(R.id.aboutDrone);
            price = (TextView) itemView.findViewById(R.id.drone_price);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}