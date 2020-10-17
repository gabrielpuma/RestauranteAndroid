package com.example.restaurante.ui.home;

public class Prod {
    String titulo;
    String precio;
    String img;
    String id;
    String creator;
    Prod(String a, String b, String c, String d, String e){
        titulo=a;
        precio=b;
        img=c;
        id=d;
        creator=e;
    }

    public String getImg() {
        return img;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getId() {
        return id;
    }

    public String getPrecio() {
        return precio;
    }

    public String getCreator() {
        return creator;
    }
}
