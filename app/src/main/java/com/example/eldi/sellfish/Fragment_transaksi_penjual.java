package com.example.eldi.sellfish;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by eldi on 18/10/2017.
 */
public class Fragment_transaksi_penjual extends Fragment {
    private ProgressDialog pDialog;
    private List<mTransaksiPenjual> transaksiList = new ArrayList<mTransaksiPenjual>();

    private ListView listView;
    private TransaksiListAdapter adapter;
    String id;

    public static final String urlGetTransaksi = "http://10.0.3.2/sellfish/jualan.php?apicall=get_transaksi_penjual_id";

    public Fragment_transaksi_penjual() {

    }

    public static Fragment_transaksi_penjual newInstance() {
        Fragment_transaksi_penjual fragment = new Fragment_transaksi_penjual();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaksi_penjual, container, false);


        listView = (ListView) view.findViewById(R.id.list);
        adapter = new TransaksiListAdapter(getActivity(), transaksiList);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mTransaksiPenjual m = transaksiList.get(i);
                Toast.makeText(getActivity(), "clicked on item "+adapter.getItemId(i)+"harga : "+m.getHarga(), Toast.LENGTH_SHORT).show();
            }
        });

        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        id = pref.getString("id_user",null);

        // Creating volley request obj
        StringRequest stringRequest = new StringRequest(Request.Method.POST,urlGetTransaksi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(String.valueOf(getActivity()), response.toString());
                        hidePDialog();

                        // Parsing json

                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray jsonArray = obj.getJSONArray("data");
                            for(int i=0; i<jsonArray.length(); i++) {
                                JSONObject objJualan = jsonArray.getJSONObject(i);
                                final mTransaksiPenjual jualan = new mTransaksiPenjual();
                                jualan.setNamaIkan(objJualan.getString("nama_ikan"));
                                jualan.setThumbnailUrl("http://10.0.3.2/sellfish/gambar/" + objJualan.getString("gambar"));
                                jualan.setHarga(objJualan.getString("total_harga"));
                                jualan.setjumlahPesanan(objJualan.getString("jumlah_beli"));
                                jualan.setpembeli(objJualan.getString("pembeli"));



                                // Genre is json array
                                /*JSONArray jualanArray = obj.getJSONArray("data");*/
                                ArrayList<String> jualan_ikan = new ArrayList<String>();
                              /*  for (int j = 0; j < jualanArray.length(); j++) {
                                    jualan_ikan.add((String) jualanArray.get(j));
                                }*/
                                jualan.setTransaksi(jualan_ikan);

                                // adding jualan to jualans array
                                transaksiList.add(jualan);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(String.valueOf(getActivity()), "Error: " + error.getMessage());
                hidePDialog();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id_user",id);
                return map;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);







        //  getData();
        return view;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }


}

