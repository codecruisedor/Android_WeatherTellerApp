package com.example.weatherteller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import io.alterac.blurkit.BlurKit;
import io.alterac.blurkit.BlurLayout;
import jp.wasabeef.blurry.Blurry;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

//For Api call : http://api.openweathermap.org/data/2.5/weather?q=London&appid=key
public class MainActivity extends AppCompatActivity {
//TODO:Go here: https://github.com/CameraKit/blurkit-android
    Button button;
    EditText city;
    TextView res;
    View linearLayoutView;
    BlurLayout blurLayout;
    ConstraintLayout cons_layout;

    String baseURL = "http://api.openweathermap.org/data/2.5/weather?q=";
    String API = "&appid=key";

    @Override
    protected void onStop() {
        blurLayout.pauseBlur();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BlurKit.init(this);

        button = findViewById(R.id.button_go);
        city = findViewById(R.id.editText_city);
        res = findViewById(R.id.result);
        cons_layout = findViewById(R.id.layout);
        linearLayoutView = findViewById(R.id.linear);
        blurLayout = findViewById(R.id.blurLayout);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (city.getText().toString() == null) {
                    city.setError("Enter city");
                    return;
                } else {
                    String myURL = baseURL + city.getText().toString() + API;
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myURL, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                String weather_main = "",id = "";
                                String main_info = response.getString("weather");
                                String temp = response.getString("main");
                                JSONArray jsonArray = new JSONArray(main_info);
                                JSONObject jsonObject = new JSONObject(temp);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject weatherObj = jsonArray.getJSONObject(i);
                                    //Log.i("ID", "ID:" + weatherObj.getString("id"));
                                    //id = weatherObj.getString("id");
                                    weather_main = weatherObj.getString("main");

                                }
                                Log.i("Desc", "Description: " + weather_main);

                                Double tempObj = jsonObject.getDouble("temp");
                                DecimalFormat df = new DecimalFormat("#.##");
                                res.setText(weather_main+"\n"+"Temp: "+df.format(tempObj-273.15)+ " \u2103");
                                changeBackground(weather_main);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }

                    });

                    VolleySingleton.getInstance(MainActivity.this).addToqueue(jsonObjectRequest);
                }

            }
        });

    }
    public void changeBackground(String weather){
        //change the bakg. image based on the string.
        if(weather.equalsIgnoreCase("Clouds")){
            cons_layout.setBackgroundResource(R.drawable.cloudyday);
        }else if(weather.equalsIgnoreCase("Rain")){
            cons_layout.setBackgroundResource(R.drawable.rainyday2);
        }else if(weather.equalsIgnoreCase("Smoke")){
            cons_layout.setBackgroundResource(R.drawable.smoke);
        }else if(weather.equalsIgnoreCase("Fog")|| weather.equalsIgnoreCase("Mist")){
            cons_layout.setBackgroundResource(R.drawable.fogmist);
        }else if(weather.equalsIgnoreCase("Clear")){
            cons_layout.setBackgroundResource(R.drawable.sunny2);
        }else if(weather.equalsIgnoreCase("Snow")){
            cons_layout.setBackgroundResource(R.drawable.snowyday);
        }else if(weather.equalsIgnoreCase("Haze")) {
            cons_layout.setBackgroundResource(R.drawable.haze);
        }else if(weather.equalsIgnoreCase("Thunderstorm")){
            cons_layout.setBackgroundResource(R.drawable.thenderstorm);
        }else{
            cons_layout.setBackgroundResource(R.color.defaultColor);
        }
        blurLayout.startBlur();
    }
}
//change the font of display of weather and temperature.
