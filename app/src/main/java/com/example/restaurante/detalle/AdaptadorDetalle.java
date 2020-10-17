 package com.example.restaurante.detalle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.restaurante.R;

import java.util.ArrayList;

import static com.example.restaurante.ip.ip;

 public class AdaptadorDetalle extends RecyclerView.Adapter<AdaptadorDetalle.ImgHolder>{
    Context context;
    ArrayList<ItemImg> mArray;
    AdaptadorDetalle(Context c){
        context=c;
        mArray=new ArrayList<>();
    }
    void add(ItemImg item){
        mArray.add(item);
        notifyItemInserted(mArray.indexOf(item));
    }

    @NonNull
    @Override
    public ImgHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detalle_img,parent,false);
        ImgHolder im=new ImgHolder(v);
        return im;
    }

    @Override
    public void onBindViewHolder(@NonNull ImgHolder holder, int position) {
        ItemImg it= mArray.get(position);
        Glide.with(context).load(ip+"/"+it.getUrl()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return mArray.size();
    }

    class ImgHolder extends RecyclerView.ViewHolder{
        ImageView img;
        public ImgHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.detalleImages);
        }
    }
}
