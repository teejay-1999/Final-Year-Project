package com.example.mobileappimplementation.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SprayInformationFragmentController#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SprayInformationFragmentController extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SprayInformationFragmentController() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SprayInformationFragmentController.
     */
    // TODO: Rename and change types and number of parameters
    public static SprayInformationFragmentController newInstance(String param1, String param2) {
        SprayInformationFragmentController fragment = new SprayInformationFragmentController();
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
        View view = inflater.inflate(R.layout.spray_information, container, false);
        retrieve(view,commonVariables);
        return view;
    }
    public void retrieve(View view, CommonVariables commonVariables){
        commonVariables.setAPIName("spray_information_data.php");
        String completeURL = commonVariables.getUrl() + commonVariables.getAPIName();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, completeURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println(response);
                    Spinner cropName = (Spinner) view.findViewById(R.id.crop_name);
                    Spinner diseaseName = (Spinner) view.findViewById(R.id.disease_name);
                    JSONArray jsonArray = new JSONArray(response);
                    ArrayList<String> diseaseList = new ArrayList<String>();
                    ArrayList<String> cropList = new ArrayList<String>();
                    cropList.add("Crop Name");
                    diseaseList.add("Disease Name");
                    String lastInsertedValue = "0";
                    for(int i = 0; i < jsonArray.length(); i++){
                        String temp = jsonArray.getJSONObject(i).getString("crop_name");
                        if(!(lastInsertedValue.equals(temp))){
                            cropList.add(temp);
                            lastInsertedValue = temp;
                        }
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(),R.layout.support_simple_spinner_dropdown_item,cropList);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cropName.setAdapter(arrayAdapter);
                    arrayAdapter = new ArrayAdapter<String>(view.getContext(),R.layout.support_simple_spinner_dropdown_item,diseaseList);
                    diseaseName.setAdapter(arrayAdapter);
                    cropName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String text = adapterView.getItemAtPosition(i).toString();
                            if(!(text.equals("Crop Name")) ){
                                Spinner disease = (Spinner) view.findViewById(R.id.disease_name);
                                ArrayList<String> diseaseList = new ArrayList<String>();
                                String lastInsertedValue = "0";
                                for(int j = 0; j < jsonArray.length(); j++){
                                    String temp = null;
                                    String cropName = null;
                                    try {
                                        cropName = jsonArray.getJSONObject(j).getString("crop_name");
                                        temp = jsonArray.getJSONObject(j).getString("disease_name");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if(!(lastInsertedValue.equals(temp)) && cropName.equals(text)){
                                        diseaseList.add(temp);
                                        lastInsertedValue = temp;
                                    }
                                }
                                ArrayAdapter diseaseAdapter = new ArrayAdapter<String>(view.getContext(),R.layout.support_simple_spinner_dropdown_item,diseaseList);
                                disease.setAdapter(diseaseAdapter);
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }

                    });
                } catch (JSONException e) {
                    e.printStackTrace();
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
                mapObject.put("temp","1");
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

