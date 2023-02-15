package me.chayan.cropsprediction;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

    private final String url = "https://crops-prediction.herokuapp.com/api/predict";

    private EditText nitrogen, phosphorus, potassium, temperature, humidity, ph;
    private Button predict;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nitrogen = findViewById(R.id.nitrogen);
        phosphorus = findViewById(R.id.phosphorus);
        potassium = findViewById(R.id.potassium);
        temperature = findViewById(R.id.temperature);
        humidity = findViewById(R.id.humidity);
        ph = findViewById(R.id.ph);

        predict = findViewById(R.id.predict);
        result = findViewById(R.id.result);

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Request the API -> Volley
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String crop = jsonObject.getString("predicted");
                                    result.setText(String.format("The Crops is %s", crop));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, "Error, Can't predict crops.", Toast.LENGTH_SHORT).show();
                            }
                        }) {

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("n", nitrogen.getText().toString());
                        params.put("p", phosphorus.getText().toString());
                        params.put("k", potassium.getText().toString());
                        params.put("temperature", temperature.getText().toString());
                        params.put("humidity", humidity.getText().toString());
                        params.put("ph", ph.getText().toString());
                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(stringRequest);
            }
        });
    }
}