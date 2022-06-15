package com.example.baithi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.baithi.NextDay.NextDayActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static final String API_KEY = "53fbf527d52d4d773e828243b90c1f8e";
    EditText edtProvince;
    Button btnOK, btnNextDay;
    TextView txtProvince, txtNational, txtTemper, txtTemperState, txtCloud, txtHumidity, txtWind, txtCurentTemper;
    ImageView imgIcon;
    String city = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        btnOK.setOnClickListener(this);
        btnNextDay.setOnClickListener(this);

        if (city == "") {
            getJsonWeather("Hanoi");
        } else getJsonWeather(city);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnOK:
                city = edtProvince.getText().toString().trim();
                if (city.equals(""))
                    city = "HaNoi";
                getJsonWeather(city);
                edtProvince.setText("");
                break;
            case R.id.btnNextDay:
                Intent intentNextDay = new Intent(MainActivity.this, NextDayActivity.class);
                intentNextDay.putExtra("city", city);
                startActivity(intentNextDay);
                break;
        }
    }

    public void getJsonWeather(String city) {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric&appid=" + API_KEY + "";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray weatherArray = response.getJSONArray("weather");
                            JSONObject weatherobj = weatherArray.getJSONObject(0);
                            String icon = weatherobj.getString("icon"); //icon
                            String urlIcon = "http://openweathermap.org/img/w/" + icon + ".png";
                            Picasso.get().load(urlIcon).into(imgIcon);
                            String temperState = weatherobj.getString("main");//Trạng Thái
                            txtTemperState.setText(temperState);
                            JSONObject main = response.getJSONObject("main");
                            String temp = main.getString("temp");//Nhiệt độ
                            Double a = Double.valueOf(temp);
                            String Nhietdo = String.valueOf(a.intValue());//Lấy số nguyên cho Nhiệt độ
                            txtTemper.setText(Nhietdo + "°C");
                            String humidity = main.getString("humidity");//Độ ẩm
                            txtHumidity.setText(humidity + "%");
                            JSONObject wind = response.getJSONObject("wind");
                            String speed = wind.getString("speed");//tốc độ gió
                            txtWind.setText(speed + "m/s");
                            JSONObject clouds = response.getJSONObject("clouds");
                            String all = clouds.getString("all");//% Mây
                            txtCloud.setText(all + "%");
                            String sNgay = response.getString("dt");
                            long lNgay = Long.parseLong(sNgay);
                            //dịnh dạng ngày
                            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd-MM-yyyy HH:mm:ss");
                            Date date = new Date(lNgay * 1000);
                            String currentTime = dateFormat.format(date);//Ngày giờ hiện tại
                            txtCurentTemper.setText(currentTime);
                            String name = response.getString("name");//Thành phố
                            txtProvince.setText("Thành Phố: " + name);
                            JSONObject sys = response.getJSONObject("sys");
                            String country = sys.getString("country");
                            txtNational.setText("Quốc gia: " + country);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Không có dữ liệu thành phố!! " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    private void Anhxa() {
        edtProvince = (EditText) findViewById(R.id.edtProvince);
        btnOK = (Button) findViewById(R.id.btnOK);
        txtProvince = (TextView) findViewById(R.id.txtProvince);
        txtNational = (TextView) findViewById(R.id.txtNational);
        txtTemper = (TextView) findViewById(R.id.txtTemper);
        txtTemperState = (TextView) findViewById(R.id.txtTemperState);
        txtCloud = (TextView) findViewById(R.id.txtCloud);
        txtHumidity = (TextView) findViewById(R.id.txtHumidity);
        txtWind = (TextView) findViewById(R.id.txtWind);
        txtCurentTemper = (TextView) findViewById(R.id.txtCurentTemper);
        btnNextDay = (Button) findViewById(R.id.btnNextDay);
        imgIcon = (ImageView) findViewById(R.id.imageWeatherIcon);

    }


}