package com.example.eldi.sellfish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by eldi on 18/10/2017.
 */
public class Fragment_profil_penjual extends Fragment {
    private TextView username_penjual, email_penjual;
    Button tentang,profil;

    public Fragment_profil_penjual() {

    }

    public static Fragment_profil_penjual newInstance() {
        Fragment_profil_penjual fragment = new Fragment_profil_penjual();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil_penjual, container, false);
        username_penjual = (TextView) view.findViewById(R.id.username_penjual);
        email_penjual=(TextView) view.findViewById(R.id.email_penjual);
        tentang=(Button)view.findViewById(R.id.about);
        profil=(Button)view.findViewById(R.id.profil);

        String username = getArguments().getString("username");
        String email = getArguments().getString("email");
        int id= Integer.parseInt(getArguments().getString("id"));
        username_penjual.setText(username);
        email_penjual.setText(email);
        // Inflate the layout for this

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(),profil_user.class);
                startActivity(i);
            }
        });

        tentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),about.class);
                startActivity(i);
            }
        });

        return view;

        //return inflater.inflate(R.layout.fragment_profil_penjual, container, false);
    }
    public void tentang(){
        Intent i = new Intent(getActivity(),about.class);
        startActivity(i);
    }
}

