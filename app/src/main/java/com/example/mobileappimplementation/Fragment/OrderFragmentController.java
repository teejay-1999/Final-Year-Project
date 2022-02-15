package com.example.mobileappimplementation.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileappimplementation.Controller.CommonVariables;
import com.example.mobileappimplementation.R;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragmentController#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragmentController extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public OrderFragmentController() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderFragmentController.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderFragmentController newInstance(String param1, String param2) {
        OrderFragmentController fragment = new OrderFragmentController();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CommonVariables commonVariables = new CommonVariables();
        View view = inflater.inflate(R.layout.order, container, false);
        retrieve(view, commonVariables);

        //System.out.print(noOrderTextMessage);
        return view;
    }
    public void retrieve(View view, CommonVariables commonVariables) {
        commonVariables.setAPIName("order_data.php");
        String completeURL = commonVariables.getUrl() + commonVariables.getAPIName();
        SharedPreferences preference = view.getContext().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        String email = preference.getString("email","0");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, completeURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("You have not placed any order yet!")) {
                    TextView textView = (TextView) view.findViewById(R.id.no_order_text);
                    textView.setText(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> mapObject = new HashMap<String, String>();
                mapObject.put("email", email);
                return mapObject;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(stringRequest);
    }
}