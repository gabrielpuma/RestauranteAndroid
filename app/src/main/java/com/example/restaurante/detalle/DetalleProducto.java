package com.example.restaurante.detalle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurante.R;
import com.example.restaurante.chat.Chat;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.example.restaurante.ip.ip;

public class DetalleProducto extends AppCompatActivity implements View.OnClickListener {
    TextView titulo,precio,detalle;
    GridLayoutManager gr;
    RecyclerView rec;
    Button chat,volver;
    AdaptadorDetalle adp;
    String id;
    String creator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        Bundle b=getIntent().getExtras();
        id=b.getString("id");
        creator=b.getString("creator");

        gr=new GridLayoutManager(this,3);

        titulo=findViewById(R.id.detalleTitle);
        precio=findViewById(R.id.detallePrice);
        detalle=findViewById(R.id.detalleDescription);
        rec=findViewById(R.id.detalleRecycler);
        chat=findViewById(R.id.detalleChat);
        volver=findViewById(R.id.detalleVolver);

        adp= new AdaptadorDetalle(this);
        rec.setLayoutManager(gr);
        rec.setAdapter(adp);

        SharedPreferences pref=this.getSharedPreferences("datauser", Context.MODE_PRIVATE);
        String idUser=pref.getString("idUser","");
        if (idUser.equals((creator))){
            chat.setVisibility(View.INVISIBLE);
        }
        chat.setOnClickListener(this);

        cargar();
    }

    private void cargar() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(ip+"/prod/detalle/"+id,null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JSONObject ob=response;
                try {
                    titulo.setText(ob.getString("title"));
                    precio.setText(ob.getString("price"));
                    detalle.setText(ob.getString("description"));
                    JSONArray arr=ob.getJSONArray("urls");
                    for (int i=0;i<arr.length();i++){
                        JSONObject ob2=new JSONObject();
                        adp.add(new ItemImg(ob2.getString("urls")));
                    }
                    Toast.makeText(DetalleProducto.this,ob.getString("message"), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(DetalleProducto.this,"error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.detalleChat) {
            Intent in=new Intent(DetalleProducto.this, Chat.class);
            in.putExtra("id",id);
            startActivity(in);
        }
    }
}