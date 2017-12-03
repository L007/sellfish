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
public class Fragment_katalog_pembeli extends Fragment {
    private ProgressDialog pDialog;
    private List<Jualan> jualanList = new ArrayList<Jualan>();

    private ListView listView;
    private KatalogGridAdapter adapter;
    String id;

    public static final String url = "http://10.0.3.2/sellfish/jualan.php?apicall=get_all_product";
    private FloatingActionButton fab;
    private GridView gridView;

    //ArrayList for Storing image urls and titles


    public Fragment_katalog_pembeli() {

    }

    public static Fragment_katalog_pembeli newInstance() {
        Fragment_katalog_pembeli fragment = new Fragment_katalog_pembeli();
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

        View view = inflater.inflate(R.layout.fragment_katalog_pembeli, container, false);

        // gridView = (GridView) view.findViewById(R.id.gridView);


       // listView = (ListView) view.findViewById(R.id.list);
        gridView=(GridView)view.findViewById(R.id.gridViewKatalog);
        adapter = new KatalogGridAdapter(getActivity(), jualanList);
       // listView.setAdapter(adapter);
        gridView.setAdapter(adapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Jualan m = jualanList.get(i);
                Intent intent = new Intent(getActivity(),detailProduk.class);
                intent.putExtra("image", m.getThumbnailUrl());
                intent.putExtra("id_produk",m.getId());
                intent.putExtra("nama_ikan",m.getNamaIkan());
                intent.putExtra("harga_ikan",m.getHarga());
                intent.putExtra("stok_ikan",m.getJumlahStok());
                intent.putExtra("penjual_ikan",m.getPenjual());
                intent.putExtra("deskripsi_ikan",m.getDeskripsi());
                startActivity(intent);
                //Toast.makeText(getActivity(), "clicked on item "+adapter.getItemId(i)+"deskripsi : "+m.getDeskripsi(), Toast.LENGTH_SHORT).show();
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
                                    final Jualan jualan = new Jualan();
                                    jualan.setId(objJualan.getString("id_produk"));
                                    jualan.setNamaIkan(objJualan.getString("nama_ikan"));
                                    jualan.setThumbnailUrl("http://10.0.3.2/sellfish/gambar/" + objJualan.getString("gambar"));
                                    jualan.setHarga(objJualan.getString("harga_ikan"));
                                    jualan.setJumlahStok(objJualan.getString("stok_ikan"));
                                    jualan.setPenjual(objJualan.getString("nama_penjual"));
                                    jualan.setDeskripsi(objJualan.getString("deskripsi_ikan"));



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


  /*      fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), TambahProdcutActivity.class);
                startActivity(i);
            }
        });
*/



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

