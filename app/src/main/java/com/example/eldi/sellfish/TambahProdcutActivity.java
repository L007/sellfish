package com.example.eldi.sellfish;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TambahProdcutActivity extends AppCompatActivity {
    String nama_ikan,harga_ikan,stok_ikan,deskripsi_ikan,gambar;
    int id;
    ImageView ikan;
    Button btnTambah;
    ImageButton btnUpload;
    EditText namaIkan, stokIkan, hargaIkan, deskripsiIkan;
    private final int IMG_REQUEST = 1;
    private Bitmap bitmap;
    private String uploadURL = "http://10.0.3.2/sellfish/jualan.php?apicall=insert_jualan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_prodcut);
        ikan = (ImageView) findViewById(R.id.imgIkan);
        btnTambah = (Button) findViewById(R.id.btnBrowseProduk);
        btnUpload=(ImageButton)findViewById(R.id.btnUploadProduk);
        namaIkan = (EditText) findViewById(R.id.etNamaIkan);
        stokIkan = (EditText) findViewById(R.id.etStokIkan);
        hargaIkan = (EditText) findViewById(R.id.etHargaIkan);
        deskripsiIkan = (EditText) findViewById(R.id.etDeskripsiIkan);


        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pilihFoto();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            uploadImage();
            }
        });
    }



    private void pilihFoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                ikan.setImageBitmap(bitmap);
                ikan.setBackground(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        id = Integer.parseInt(pref.getString("id_user",null));
        nama_ikan=namaIkan.getText().toString().trim();
        harga_ikan=hargaIkan.getText().toString().trim();
        stok_ikan=stokIkan.getText().toString().trim();
        deskripsi_ikan=deskripsiIkan.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uploadURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean status = jObj.getBoolean("status");
                            if (status) {
                                //JSONObject user = jObj.getJSONObject("user");
                                Toast.makeText(getApplicationContext(), "upload sukses", Toast.LENGTH_SHORT).show();
                                onBackPressed();


                               // onResume();


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
                        Toast.makeText(TambahProdcutActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id_user",String.valueOf(id));
                map.put("nama_ikan", nama_ikan);
                map.put("harga_ikan", harga_ikan);
                map.put("stok_ikan", stok_ikan);
                map.put("deskripsi_ikan", deskripsi_ikan);
                map.put("gambar", imageToString(bitmap));
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
       /* RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest); */
    }


    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }


}
