package com.example.eldi.sellfish;

import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class PenjualActivity extends AppCompatActivity {
    public static final String FRAGMENT_VIEWPAGER = "FRAGMENT_VIEWPAGER";
    public static final String FRAGMENT_PERTAMA = "FRAGMENT_PERTAMA";
    public static final String FRAGMENT_KEDUA = "FRAGMENT_KEDUA";
    public static final String FRAGMENT_KETIGA = "FRAGMENT_KETIGA";
    public static final String FRAGMENT_KEEMPAT = "FRAGMENT_KEEMPAT";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home_penjual:
                    replaceFragment(Fragment_home_penjual.newInstance(), FRAGMENT_PERTAMA);
                    return true;
                case R.id.navigation_jualan_penjual:
                    replaceFragment(Fragment_jualan_penjual.newInstance(), FRAGMENT_KEDUA);
                    return true;
                case R.id.navigation_transaksi_penjual:
                    replaceFragment(Fragment_transaksi_penjual.newInstance(), FRAGMENT_KETIGA);
                    return true;
                case R.id.navigation_profil_penjual:
                    replaceFragment(Fragment_profil_penjual.newInstance(),FRAGMENT_KEEMPAT);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjual);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, Fragment_home_penjual.newInstance(), FRAGMENT_PERTAMA)
                .commit();

    }
    private void replaceFragment(Fragment newFragment, String tag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, newFragment, tag)
                .commit();

    }
}
