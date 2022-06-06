package com.example.mobileappimplementation.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileappimplementation.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.holder> {

    private JSONArray jsonArray;
    private int value;
    private int size;
    private JSONArray orderJsonArray,orderDetailJsonArray,orderStatusJsonArray;

    public OrderAdapter(JSONArray jsonArray) throws JSONException {
        value = 0;
        int i = 0;
        this.jsonArray = jsonArray;
        size = jsonArray.length() / 3;
        int breakPoint = size;
        orderJsonArray = new JSONArray();
        orderDetailJsonArray = new JSONArray();
        orderStatusJsonArray = new JSONArray();
        while (value < breakPoint){
            try {
                orderJsonArray.put(i ,jsonArray.getJSONObject(value));
                ++value;
                i++;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Orders Array");
        System.out.println(orderJsonArray);
        breakPoint += size;
        i = 0;
        while (value < breakPoint){
            try {
                orderDetailJsonArray.put(i ,jsonArray.getJSONObject(value));
                ++value;
                i++;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Order Details Array");
        System.out.println(orderDetailJsonArray);
        breakPoint += size;
        i = 0;
        while (value < breakPoint){
            try {
                orderStatusJsonArray.put(i ,jsonArray.getJSONObject(value));
                ++value;
                i++;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Order Status Array");
        System.out.println(orderStatusJsonArray);
        i = 0;
        int lastPoint = 0;
        while (i < 3){
            if(i == 0) {
                for (int j = 0; j < size; j++) {
                    String value = orderStatusJsonArray.getJSONObject(j).getString("Status");
                    if (value.equals("pending")){
                        JSONObject jsonObject = orderStatusJsonArray.getJSONObject(j);
                        orderStatusJsonArray.put(j,orderStatusJsonArray.getJSONObject(lastPoint));
                        orderStatusJsonArray.put(lastPoint,jsonObject);

                        jsonObject = orderDetailJsonArray.getJSONObject(j);
                        orderDetailJsonArray.put(j,orderDetailJsonArray.getJSONObject(lastPoint));
                        orderDetailJsonArray.put(lastPoint,jsonObject);

                        jsonObject = orderJsonArray.getJSONObject(j);
                        orderJsonArray.put(j,orderJsonArray.getJSONObject(lastPoint));
                        orderJsonArray.put(lastPoint,jsonObject);
                        lastPoint++;
                    }
                }
            }
            else if(i == 1) {
                for (int j = 0; j < size; j++) {
                    String value = orderStatusJsonArray.getJSONObject(j).getString("Status");
                    if (value.equals("accepted")){
                        JSONObject jsonObject = orderStatusJsonArray.getJSONObject(j);
                        orderStatusJsonArray.put(j,orderStatusJsonArray.getJSONObject(lastPoint));
                        orderStatusJsonArray.put(lastPoint,jsonObject);

                        jsonObject = orderDetailJsonArray.getJSONObject(j);
                        orderDetailJsonArray.put(j,orderDetailJsonArray.getJSONObject(lastPoint));
                        orderDetailJsonArray.put(lastPoint,jsonObject);

                        jsonObject = orderJsonArray.getJSONObject(j);
                        orderJsonArray.put(j,orderJsonArray.getJSONObject(lastPoint));
                        orderJsonArray.put(lastPoint,jsonObject);
                        lastPoint++;
                    }
                }
            }
            else{
                for (int j = 0; j < size; j++) {
                    String value = orderStatusJsonArray.getJSONObject(j).getString("Status");
                    if (value.equals("delivered")){
                        JSONObject jsonObject = orderStatusJsonArray.getJSONObject(j);
                        orderStatusJsonArray.put(j,orderStatusJsonArray.getJSONObject(lastPoint));
                        orderStatusJsonArray.put(lastPoint,jsonObject);

                        jsonObject = orderDetailJsonArray.getJSONObject(j);
                        orderDetailJsonArray.put(j,orderDetailJsonArray.getJSONObject(lastPoint));
                        orderDetailJsonArray.put(lastPoint,jsonObject);

                        jsonObject = orderJsonArray.getJSONObject(j);
                        orderJsonArray.put(j,orderJsonArray.getJSONObject(lastPoint));
                        orderJsonArray.put(lastPoint,jsonObject);
                        lastPoint++;
                    }
                }
            }
            i++;
        }
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.order_single_row,parent,false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        try {
            holder.date.setText(orderJsonArray.getJSONObject(position).getString("date"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            holder.droneDescription.setText(orderDetailJsonArray.getJSONObject(position).getString("droneDescription"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            holder.price.setText("Rs "+orderDetailJsonArray.getJSONObject(position).getString("price"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            String status = orderStatusJsonArray.getJSONObject(position).getString("Status");
            holder.status.setText(orderStatusJsonArray.getJSONObject(position).getString("Status"));
            if(status.equals("delivered")){
                holder.status.setTextColor(Color.parseColor("#32CD32"));
            }
            else if(status.equals("pending")){
                holder.status.setTextColor(Color.parseColor("#FF0000"));
            }
            else{
                holder.status.setTextColor(Color.parseColor("#FFA500"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return size;
    }


    class holder extends RecyclerView.ViewHolder{
        private TextView date, droneDescription,price,status;
        public holder(@NonNull View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            droneDescription = (TextView) itemView.findViewById(R.id.drone_description);
            price = (TextView) itemView.findViewById(R.id.price);
            status = (TextView) itemView.findViewById(R.id.status);



        }
    }
}