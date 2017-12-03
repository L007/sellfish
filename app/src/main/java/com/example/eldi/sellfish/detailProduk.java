package com.example.eldi.sellfish;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class detailProduk extends AppCompatActivity {
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private String urlIkan, namaIkan, hargaIkan, stokIkan, penjualIkan, deskripsiIkan, idIkan, jumlahBeli, alamat, id_user;
    private TextView txtNamaIkan, txtHargaIkan, txtStokIkan, txtPenjualIkan, txtDeskripsiIkan;
    private EditText etJumlahBeli, etAlamat;
    private Button btnBeli;
    private final String urlBeli = "http://10.0.3.2/sellfish/jualan.php?apicall=beli_by_id";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk);


        NetworkImageView thumbnail = (NetworkImageView) findViewById(R.id.img_gambar_ikan);

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        urlIkan = getIntent().getStringExtra("image");
        namaIkan = getIntent().getStringExtra("nama_ikan");
        hargaIkan = getIntent().getStringExtra("harga_ikan");
        stokIkan = getIntent().getStringExtra("stok_ikan");
        penjualIkan = getIntent().getStringExtra("penjual_ikan");
        deskripsiIkan = getIntent().getStringExtra("deskripsi_ikan");
        idIkan = getIntent().getStringExtra("id_produk");

        txtNamaIkan = (TextView) findViewById(R.id.txt_nama_ikan);
        txtHargaIkan = (TextView) findViewById(R.id.txt_harga);
        txtStokIkan = (TextView) findViewById(R.id.txt_jumlah_stok);
        txtPenjualIkan = (TextView) findViewById(R.id.txt_penjual);
        txtDeskripsiIkan = (TextView) findViewById(R.id.txtDeskripsi);
        etJumlahBeli = (EditText) findViewById(R.id.etJumlahBeli);
        etAlamat = (EditText) findViewById(R.id.etAlamat);
        btnBeli = (Button) findViewById(R.id.btnBeli);


        thumbnail.setImageUrl(urlIkan, imageLoader);
        txtNamaIkan.setText(namaIkan);
        txtHargaIkan.setText(formatRupiah.format(Double.parseDouble(hargaIkan)));
        txtStokIkan.setText("stok : " + stokIkan);
        txtPenjualIkan.setText("oleh : " + penjualIkan);
        txtDeskripsiIkan.setText(deskripsiIkan);


        btnBeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Beli();
            }
        });
    }

    public void Beli() {
        //txtHargaIkan.setText("beli");
        jumlahBeli = etJumlahBeli.getText().toString();
        alamat = etAlamat.getText().toString();
        final SharedPreferences pref = detailProduk.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        id_user = pref.getString("id_user", null);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlBeli,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean status = jObj.getBoolean("status");
                            if (status) {
                                //JSONObject user = jObj.getJSONObject("user");
                                Toast.makeText(getApplicationContext(), "Pembelian sukses", Toast.LENGTH_SHORT).show();
                                onBackPressed();

                            } else {
                                // Error in login. Get the error message
                                String errorMsg = jObj.getString("error_msg");
                                Toast.makeText(getApplicationContext(),
                                        errorMsg, Toast.LENGTH_LONG).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                    }



                       /* if(response.trim().equals("success")){
                            openProfile();
                        }else{
                            Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                        }*/

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(detailProduk.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id_user", pref.getString("id_user", null));
                map.put("id_produk", idIkan);
                map.put("jumlahBeli", jumlahBeli);
                map.put("alamat", alamat);

                return map;
            }


        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}