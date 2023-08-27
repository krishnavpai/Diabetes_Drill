package com.example.diabetesdrill;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    EditText glucose, bp, insulin, dpf, age, bmi;
    Button predict;
    TextView result;
    String api = "https://diabetes-test-proj.vercel.app/api/predict";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        glucose = findViewById(R.id.glucose);
        bp = findViewById(R.id.bp);
        insulin = findViewById(R.id.insulin);
        dpf = findViewById(R.id.dpf);
        age = findViewById(R.id.age);
        bmi = findViewById(R.id.bmi);
        predict = findViewById(R.id.predict);


        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, api, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String data = jsonObject.getString("result");
//                            Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
//                            result.setText(data);
                            Toast toast = new Toast(getApplicationContext());
                            toast.setGravity(Gravity.CENTER,0,800);

                            TextView tv = new TextView(MainActivity.this);
                            tv.setText(data);
                            tv.setTextColor(Color.BLACK);
                            tv.setTextSize(25);
                            tv.setBackgroundColor( Color.rgb(140, 238, 237));

                            tv.setPadding(30, 10, 30, 10);
                            tv.getShadowRadius();
                            toast.setView(tv);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.show();

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("glucose", glucose.getText().toString());
                        params.put("bp", bp.getText().toString());
                        params.put("insulin", insulin.getText().toString());
                        params.put("dpf", dpf.getText().toString());
                        params.put("age", age.getText().toString());
                        params.put("bmi", bmi.getText().toString());
                        return params;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(stringRequest);

            }
        });
    }
}