package com.example.restaurante.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurante.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.example.restaurante.ip.ip;

public class HomeFragment extends Fragment {
    GridLayoutManager grid;
    AdapterHome adp;
    RecyclerView rec;
    Context ctx;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ctx=container.getContext();
        grid=new GridLayoutManager(ctx,2);
        rec=root.findViewById(R.id.homeRecycler);
        adp=new AdapterHome(ctx);
        rec.setLayoutManager(grid);
        rec.setAdapter(adp);
        cargar();
        return root;
    }

    private void cargar() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(ip+ "/prod/vistaHome",null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject ob=response.getJSONObject(i);
                        Prod prod=new Prod(ob.getString("title"),
                                ob.getString("price"),
                                ob.getString("url"),
                                ob.getString("_id"),
                                ob.getString("creator"));

                        adp.add(prod);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}