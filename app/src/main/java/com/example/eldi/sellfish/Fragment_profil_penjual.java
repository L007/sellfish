package com.example.eldi.sellfish;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by eldi on 18/10/2017.
 */
public class Fragment_profil_penjual extends Fragment {
    private TextView username_penjual, email_penjual;

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
        String username = getArguments().getString("username");
        username_penjual.setText(username);
        // Inflate the layout for this fragment

        return view;

        //return inflater.inflate(R.layout.fragment_profil_penjual, container, false);
    }
}

