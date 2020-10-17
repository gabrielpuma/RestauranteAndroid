package com.example.restaurante;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class register extends AppCompatActivity implements View.OnClickListener {
    EditText name,email,pass;
    Button send,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name=findViewById(R.id.regName);
        email=findViewById(R.id.regEmail);
        pass=findViewById(R.id.regPass);

        send=findViewById(R.id.regSend);
        back=findViewById(R.id.regCancel);

        send.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.regSend){
            enviar();
        }else if(v.getId()==R.id.regCancel){
            Intent in=new Intent(register.this,MainActivity.class);
            startActivity(in);
        }
    }

    private void enviar() {
        AsyncHttpClient client=new  AsyncHttpClient();
        RequestParams rq=new RequestParams();
        rq.put("name",name.getText().toString());
        rq.put("email",email.getText().toString());
        rq.put("password",pass.getText().toString());
        client.post(ip.ip+"/user",rq,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String res=response.getString("message");
                    Toast.makeText(getApplicationContext(), res,Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}