package com.example.baithi.NextDay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.baithi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NextDayActivity extends AppCompatActivity {
    static final String API_KEY = "53fbf527d52d4d773e828243b90c1f8e";
    List<Weather> weatherlist;
    WeatherAdapter adapter;
    ListView lvNextDay;TextView txtname;
    ImageView imgBack;
    String tenthanhpho = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_day);
        Anhxa();
        Intent intent = getIntent();
        String city = intent.getStringExtra("city");

        if (city.equals("")){
            tenthanhpho = "Hanoi";
            getJsonNextDay(tenthanhpho);
        }else {
            tenthanhpho = city;
            getJsonNextDay(tenthanhpho);
        }
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void Anhxa() {
        lvNextDay= findViewById(R.id.lvNextDay);
        txtname = findViewById(R.id.textviewTenthanhpho);
        imgBack = findViewById(R.id.imageviewBack);
        weatherlist = new ArrayList<>();
        adapter = new WeatherAdapter(NextDayActivity.this, R.layout.row_weather, weatherlist);
        lvNextDay.setAdapter(adapter);
    }

    private void getJsonNextDay(String  data) {
        String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q="+data+"" +
                "&units=metric&cnt=7&appid="+API_KEY+"";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
        new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                JSONObject jsonObjectcity = response.getJSONObject("city");
                    String name = jsonObjectcity.getString("name");
                    txtname.setText(name);

                JSONArray jsonArrayList =response.getJSONArray("list");
                for (int i=0; i<jsonArrayList.length(); i++){
                    JSONObject jsonObjectList =jsonArrayList.getJSONObject(i);
                        String ngay = jsonObjectList.getString("dt"); //ngày tháng
                        long l = Long.valueOf(ngay);
                        Date date = new Date(l*1000L);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE dd-MM-yyyy");
                        String Day = simpleDateFormat.format(date); //thời gian
                    JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("temp"); //Nhiệt độ
                        String min = jsonObjectTemp.getString("min"); //Nhiệt độ min
                        String max = jsonObjectTemp.getString("max"); //Nhiệt độ max
                        Double b = Double.valueOf(min);
                        Double a = Double.valueOf(max);
                        String NhietdoMin = String.valueOf(b.intValue()); //lấy số nguyên
                        String NhietdoMax = String.valueOf(a.intValue());
                    JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                        String status = jsonObjectWeather.getString("description"); //Trạng Thái

                        String icon = jsonObjectWeather.getString("icon"); //icon
                        String urlIcon = "http://openweathermap.org/img/w/"+icon+".png";
                        weatherlist.add(new Weather(Day,status,urlIcon,NhietdoMax,NhietdoMin));
                    }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace(); }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NextDayActivity.this, "Lỗi nhận thành phố", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

}