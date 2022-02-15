package com.example.mobileappimplementation.Model;

import com.example.mobileappimplementation.Controller.CommonVariables;

public class Drone extends CommonVariables {
    private int id;
    private int price;
    private String heading;
    private String description;

    public Drone(int id, int price, String heading, String description) {
        this.id = id;
        this.price = price;
        this.heading = heading;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    @Override
//    public void retrieve(Context applicationContext) {
//        setAPIName("drone_data.php");
//        String completeURL = getUrl() + getAPIName();
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, completeURL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                listener.onResult(response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        }
//        ){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> mapObject = new HashMap<String, String>();
//                return mapObject;
//            }
//        };
//        stringRequest.setRetryPolicy(new RetryPolicy() {
//            @Override
//            public int getCurrentTimeout() {
//                return 50000;
//            }
//
//            @Override
//            public int getCurrentRetryCount() {
//                return 50000;
//            }
//
//            @Override
//            public void retry(VolleyError error) throws VolleyError {
//
//            }
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(applicationContext);
//        requestQueue.add(stringRequest);
//    }
}
