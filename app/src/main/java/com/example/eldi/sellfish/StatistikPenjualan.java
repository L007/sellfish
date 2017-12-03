package com.example.eldi.sellfish;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class StatistikPenjualan extends AppCompatActivity {
    public TextView txtTanggal, txtPendapatan, txtIkanTerjual, txtIkanTerlaris, txtPeramalan;
    private String id_user, totalPendapatan,totalIkan,ikanTerlaris,peramalan;
    final String urlGetPendapatan = "http://10.0.3.2/sellfish/statistik.php?apicall=get_pendapatan";
    final String urlGetTotalIkan = "http://10.0.3.2/sellfish/statistik.php?apicall=get_total_ikan";
    final String urlGetIkanTerlaris = "http://10.0.3.2/sellfish/statistik.php?apicall=get_ikan_terlaris";
    final String urlGetPeramlan = "http://10.0.3.2/sellfish/statistik.php?apicall=get_peramalan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistik_penjualan);
        SharedPreferences pref = StatistikPenjualan.this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        id_user = pref.getString("id_user", null);

        txtTanggal = (TextView) findViewById(R.id.txtTanggal);
        txtPendapatan = (TextView) findViewById(R.id.txtTotalPendapatan);
        txtIkanTerjual = (TextView) findViewById(R.id.txtTotalIkanTerjual);
        txtIkanTerlaris = (TextView) findViewById(R.id.txtNamaIkanTerlaris);
        txtPeramalan = (TextView) findViewById(R.id.txtPeramalan);


        txtTanggal.setText(getTanggal());

        getPendapatan();
        getTotalIkan();
        getIkanTerlaris();
        getPeramalan();
    }

    public String getTanggal() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void getPendapatan() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetPendapatan,
                new Response.Listener<String>() {
                    final ProgressDialog loading = ProgressDialog.show(StatistikPenjualan.this, "Please wait...", "Fetching data...", false, false);
                    Locale localeID = new Locale("in", "ID");
                    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");
                            if (!error) {
                                loading.dismiss();
                                totalPendapatan = jObj.getString("total_pendapatan");
                                txtPendapatan.setText(formatRupiah.format(Double.parseDouble(totalPendapatan)));


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
                        Toast.makeText(StatistikPenjualan.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id_user", id_user);

                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
    public void getTotalIkan() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetTotalIkan,
                new Response.Listener<String>() {

                    NumberFormat formatter = new DecimalFormat("#.###");

                    final ProgressDialog loading = ProgressDialog.show(StatistikPenjualan.this, "Please wait...", "Fetching data...", false, false);


                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");
                            if (!error) {
                                loading.dismiss();
                                totalIkan = jObj.getString("total_ikan");
                                txtIkanTerjual.setText(formatter.format(Double.parseDouble(totalIkan))+" kg");


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
                        Toast.makeText(StatistikPenjualan.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id_user", id_user);

                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    public void getIkanTerlaris() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetIkanTerlaris,
                new Response.Listener<String>() {



                    final ProgressDialog loading = ProgressDialog.show(StatistikPenjualan.this, "Please wait...", "Fetching data...", false, false);


                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");
                            if (!error) {
                                loading.dismiss();
                                ikanTerlaris = jObj.getString("ikan_terlaris");
                                txtIkanTerlaris.setText(ikanTerlaris);



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
                        Toast.makeText(StatistikPenjualan.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id_user", id_user);

                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
    public void getPeramalan() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetPeramlan,
                new Response.Listener<String>() {



                    final ProgressDialog loading = ProgressDialog.show(StatistikPenjualan.this, "Please wait...", "Fetching data...", false, false);


                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");
                            if (!error) {
                                loading.dismiss();
                                peramalan = jObj.getString("peramalan");
                                txtPeramalan.setText(peramalan+" kg ");



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
                        Toast.makeText(StatistikPenjualan.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id_user", id_user);

                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
