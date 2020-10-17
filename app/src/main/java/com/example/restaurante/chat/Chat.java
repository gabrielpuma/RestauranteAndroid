package com.example.restaurante.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.restaurante.R;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import static com.example.restaurante.ip.ip;

public class Chat extends AppCompatActivity {
    Socket socket;
    RecyclerView rec;
    EditText message;
    LinearLayoutManager ln;
    public AdapterChat adp;
    Button btn;
    String idPr;
    String idUs;
    String name;
    String idChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        try {
            socket= IO.socket(ip);
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        Bundle b=getIntent().getExtras();
        idPr=b.getString("id");

        try {
            idChat=b.getString("idChat");

        }catch (Exception e){
            idChat="";
        }

        SharedPreferences pref =this.getSharedPreferences("datauser", Context.MODE_PRIVATE);
        idUs=pref.getString("idUser","");
        name=pref.getString("name","");
        rec=findViewById(R.id.campoChatRecycler);
        ln=new LinearLayoutManager(this);
        adp=new AdapterChat(this);
        rec.setLayoutManager(ln);
        rec.setAdapter(adp);

        message=findViewById(R.id.campoChatMessage);
        btn=findViewById(R.id.campoChatSend);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Chat.this,"btn",Toast.LENGTH_SHORT).show();
                JSONObject ob=new JSONObject();
                try {
                    ob.put("idPr",idPr);
                    ob.put("idUs",idUs);
                    ob.put("name",name);
                    ob.put("message",message.getText().toString());
                    if (idChat!=null){
                        ob.put("idChat",idChat);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                socket.emit("chat",ob);
                message.setText("");
            }
        });
        socket.on("chat", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject ob=(JSONObject) args[0];
                        try {
                            adp.add(new ItemChat(ob.getString("name"),ob.getString("message")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}