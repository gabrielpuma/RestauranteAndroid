package com.example.restaurante;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText email,pass;
    Button entrar,reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=findViewById(R.id.loginEmail);
        pass=findViewById(R.id.loginPass);
        entrar=findViewById(R.id.loginSend);
        reg=findViewById(R.id.loginRegister);

        reg.setOnClickListener(this);
        entrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.loginRegister){
            Intent in = new Intent(MainActivity.this,register.class);
            startActivity(in);
        }else if (v.getId()==R.id.loginSend){
            cargar();
        }
    }

    private void cargar() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams req = new RequestParams();
        req.put("email",email.getText().toString());
        req.put("password",pass.getText().toString());
        client.post(ip.ip+"/user/login",req,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONObject res = response;
                    if (res.getString("message").equals("el email es incorrecto")){
                        Toast.makeText(MainActivity.this, res.getString("message"), Toast.LENGTH_SHORT).show();
                    }else if (res.getString("message").equals("password incorrecto")){
                        Toast.makeText(MainActivity.this, res.getString("message"), Toast.LENGTH_SHORT).show();
                    }else if (res.getString("message").equals("Bienvenido")){
                        Toast.makeText(MainActivity.this, res.getString("message"), Toast.LENGTH_SHORT).show();
                        SharedPreferences pref =getSharedPreferences("datauser", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit=pref.edit();
                        edit.putString("idUser",res.getString("idUser"));
                        edit.putString("name",res.getString("name"));
                        edit.putString("token",res.getString("token"));
                        edit.commit();
                        Intent in=new Intent(MainActivity.this,Vista.class);
                        in.putExtra("admin",res.getString("admin"));
                        startActivity(in);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}