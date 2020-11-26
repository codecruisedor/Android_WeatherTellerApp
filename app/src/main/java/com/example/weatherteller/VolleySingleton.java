package com.example.weatherteller;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static VolleySingleton mInstance;
    private RequestQueue mRequestQueue;
    private static Context mcX;

    public VolleySingleton(Context context) {
        mcX = context;
        mRequestQueue = getRequestQueue();
        //mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());

    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mcX.getApplicationContext());
        }
        return mRequestQueue;
    }
    public static synchronized VolleySingleton getInstance(Context context){
        if (mInstance == null){
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }
    public void addToqueue(Request req){
        mRequestQueue.add(req);
    }
}


