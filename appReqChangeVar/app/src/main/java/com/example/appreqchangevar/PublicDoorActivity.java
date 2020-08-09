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

public class PublicDoorActivity extends AppCompatActivity {

    private Button btnPubOpenDoor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_door);

        btnPubOpenDoor = findViewById(R.id.apd_post_btn_door);

        this.handleRequest();
        this.handleClickBtnOpenDoor();
    }

    private void handleClickBtnOpenDoor(){
        btnPubOpenDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RequestURL(getApplicationContext(), new RequestURL.MyRequest() {
                    @Override
                    public int getMethod() {
                        return Request.Method.POST;
                    }

                    @Override
                    public String getUrl() {
                        return Config.URL_PUBLIC+"/index.php";
                    }

                    @Override
                    public Map<String, String> param(Map<String, String> data) {
                        return data;
                    }

                    @Override
                    public void response(Object response) throws JSONException {
                        handleRequest();
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

    private  void handleRequest(){
        new RequestURL(getApplicationContext(), new RequestURL.MyRequest() {
            @Override
            public int getMethod() {
                return Request.Method.GET;
            }

            @Override
            public String getUrl() {
                return Config.URL_PUBLIC+"/index.php";
            }

            @Override
            public Map<String, String> param(Map<String, String> data) {
                return data;
            }

            @Override
            public void response(Object response) throws JSONException {
//                {"id":"1","status":"0","keterangan":"TUTUP"}
//                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                JSONObject dataReq = new JSONObject(response.toString()) ;
                String doorStatus = dataReq.getString("status") ;
                if (doorStatus.equals("1")) btnPubOpenDoor.setText("TUTUP PINTU");
                else btnPubOpenDoor.setText("BUKA PINTU");
            }

            @Override
            public void err(VolleyError error, String dataMsgError) throws JSONException {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), dataMsgError, Toast.LENGTH_LONG).show();
            }
        });
    }
}