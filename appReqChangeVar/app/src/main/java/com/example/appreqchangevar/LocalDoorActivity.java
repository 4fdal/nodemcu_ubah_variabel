package com.example.appreqchangevar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class LocalDoorActivity extends AppCompatActivity {

    private Button btnOpenDoorLocal ;
    private String stateOpenDoor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_door);

        btnOpenDoorLocal = findViewById(R.id.ald_post_btn_door) ;

        this.handleRequeste();
        this.handleBtnClick();
    }

    private void handleBtnClick(){
        btnOpenDoorLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RequestURL(getApplicationContext(), new RequestURL.MyRequest() {
                    @Override
                    public int getMethod() {
                        return Request.Method.POST;
                    }

                    @Override
                    public String getUrl() {
                        return Config.URL_LOCAL+"/post_door";
                    }

                    @Override
                    public Map<String, String> param(Map<String, String> data) {
                        data.put("password", "@EDPASSWORD") ;
                        return data;
                    }

                    @Override
                    public void response(Object response) throws JSONException {
                        // { "stat": "true", "statOpenDoor": "1" }
                        try {
                            JSONObject dataReq = new JSONObject(response.toString()) ;
                            stateOpenDoor = dataReq.getString("statOpenDoor") ;
                            if (stateOpenDoor.equals("1")) btnOpenDoorLocal.setText("TUTUP PINTU");
                            else btnOpenDoorLocal.setText("BUKA PINTU");
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "ERROR => "+e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void err(VolleyError error, String dataMsgError) throws JSONException {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), dataMsgError, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void handleRequeste(){
        new RequestURL(getApplicationContext(), new RequestURL.MyRequest() {
            @Override
            public int getMethod() {
                return Request.Method.GET;
            }

            @Override
            public String getUrl() {
                return Config.URL_LOCAL+"/stat_door";
            }

            @Override
            public Map<String, String> param(Map<String, String> data) {
                return data;
            }

            @Override
            public void response(Object response) {
//                { "stat": "true", "statOpenDoor": "1" }
                try {
                    JSONObject dataReq = new JSONObject(response.toString()) ;
                    stateOpenDoor = dataReq.getString("statOpenDoor") ;
                    if (stateOpenDoor.equals("1")) btnOpenDoorLocal.setText("TUTUP PINTU");
                    else btnOpenDoorLocal.setText("BUKA PINTU");
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "ERROR => "+e.toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void err(VolleyError error, String dataMsgError) throws JSONException {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), dataMsgError, Toast.LENGTH_LONG).show();
            }
        });
    }


}