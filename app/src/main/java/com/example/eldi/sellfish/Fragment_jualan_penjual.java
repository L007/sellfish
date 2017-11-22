package com.example.eldi.sellfish;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
public class Fragment_jualan_penjual extends Fragment {
    private ProgressDialog pDialog;
    private List<Jualan> jualanList = new ArrayList<Jualan>();
    private ListView listView;
    private CustomListAdapter adapter;
    String id;

    public static final String url = "http://192.168.43.241/sellfish/jualan.php?apicall=get_all_product_by_id";
    private FloatingActionButton fab;
    private GridView gridView;

    //ArrayList for Storing image urls and titles


    public Fragment_jualan_penjual() {

    }

    public static Fragment_jualan_penjual newInstance() {
        Fragment_jualan_penjual fragment = new Fragment_jualan_penjual();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_jualan_penjual, container, false);

        // gridView = (GridView) view.findViewById(R.id.gridView);


        listView = (ListView) view.findViewById(R.id.list);
        adapter = new CustomListAdapter(getActivity(), jualanList);
        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        id = pref.getString("id_user",null);
        
        // Creating volley request obj
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
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
                                    Jualan jualan = new Jualan();
                                    jualan.setNamaIkan(objJualan.getString("nama_ikan"));
                                    jualan.setThumbnailUrl("http://192.168.43.241/sellfish/gambar/" + objJualan.getString("gambar"));
                                    jualan.setHarga(objJualan.getString("harga_ikan"));
                                    jualan.setJumlahStok(objJualan.getString("stok_ikan"));
                                    jualan.setPenjual(objJualan.getString("nama_penjual"));

                                    // Genre is json array
                                /*JSONArray jualanArray = obj.getJSONArray("data");*/
                                    ArrayList<String> jualan_ikan = new ArrayList<String>();
                              /*  for (int j = 0; j < jualanArray.length(); j++) {
                                    jualan_ikan.add((String) jualanArray.get(j));
                                }*/
                                    jualan.setJualan(jualan_ikan);

                                    // adding jualan to jualans array
                                    jualanList.add(jualan);
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


        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), TambahProdcutActivity.class);
                startActivity(i);
            }
        });
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

