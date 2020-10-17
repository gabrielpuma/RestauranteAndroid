package com.example.restaurante;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Vista extends AppCompatActivity {
    //nav_slideshow
    private AppBarConfiguration mAppBarConfiguration;
    Boolean admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Vista.this,Add.class);
                startActivity(intent);
            }
        });
        /*Bundle b=getIntent().getExtras();
        admin=b.getBoolean("admin");*/
        admin=false;
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        if (admin){
            Menu menu= navigationView.getMenu();
            menu.add(R.id.menuVista,R.id.nav_slideshow,Menu.NONE,"Usuarios");
            menu.getItem(menu.size()-1).setIcon(ContextCompat.getDrawable(this,R.drawable.ic_menu_slideshow));
            menu.add(R.id.menuVista,R.id.productos_view,Menu.NONE,"Productos");
            menu.getItem(menu.size()-1).setIcon(ContextCompat.getDrawable(this,R.drawable.ic_menu_slideshow));
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.productos_view)
                    .setDrawerLayout(drawer)
                    .build();
        }else {
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_gallery)
                    .setDrawerLayout(drawer)
                    .build();

        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.vista, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}