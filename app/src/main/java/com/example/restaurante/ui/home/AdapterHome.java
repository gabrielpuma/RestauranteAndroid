package com.example.restaurante.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.restaurante.R;
import com.example.restaurante.detalle.DetalleProducto;
import com.example.restaurante.ip;

import java.util.ArrayList;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.ProductoView>{
    Context ctx;
    ArrayList<Prod> mArray;
    AdapterHome(Context c){
        ctx=c;
        mArray=new ArrayList<>();
    }

    void add(Prod item){
        mArray.add(item);
        notifyItemInserted(mArray.indexOf(item));
    }

    @NonNull
    @Override
    public ProductoView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home,parent,false);
        ProductoView pro=new ProductoView(view);
        return pro;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoView h, int position) {
        final Prod prod = mArray.get(position);
        h.titulo.setText(prod.getTitulo()+"");
        h.precio.setText(prod.getPrecio()+"");
        Glide.with(ctx).load(ip.ip+"/"+prod.getImg()).into(h.img);
        h.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(ctx, DetalleProducto.class);
                in.putExtra("id",prod.getId());
                in.putExtra("creator",prod.getCreator());
                ctx.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArray.size();
    }

    class ProductoView extends RecyclerView .ViewHolder{
        TextView titulo,precio;
        ImageView img;
        Button btn;
        public ProductoView(@NonNull View itemView) {
            super(itemView);
            titulo=itemView.findViewById(R.id.cardTitle);
            precio=itemView.findViewById(R.id.cardPrecio);
            img=itemView.findViewById(R.id.cardImg);
            btn=itemView.findViewById(R.id.cardOpen);

        }
    }
}
