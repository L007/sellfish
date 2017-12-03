package com.example.eldi.sellfish;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by eldi on 18/10/2017.
 */
public class Fragment_home_pembeli extends Fragment {
    public static final String cuacaURL ="http://himasif.ilkom.unej.ac.id/sellfish/cuaca.php"; //local 10.0.3.2, device 192.168.43.241
    String kota,cuaca,suhu,kelembaban,waktu;
   // private ArrayList<String> cuaca;
    TextView txtKota,txtWaktu,txtTanggal,txtCuaca,txtSuhu,txtKelembaban;

    public Fragment_home_pembeli() {

    }

    public static Fragment_home_pembeli newInstance() {
        Fragment_home_pembeli fragment = new Fragment_home_pembeli();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_pembeli, container, false);


        txtKota=(TextView)view.findViewById(R.id.kota);
        txtWaktu=(TextView)view.findViewById(R.id.waktu);
        txtTanggal=(TextView)view.findViewById(R.id.tanggal);
        txtCuaca=(TextView)view.findViewById(R.id.cuaca);
        txtSuhu=(TextView)view.findViewById(R.id.suhu);
        txtKelembaban=(TextView)view.findViewById(R.id.kelembaban);
        tampilCuaca();

        // Inflate the layout for this fragment
        return view;
    }


    private void tampilCuaca() {
      final ProgressDialog loading = ProgressDialog.show(this.getActivity(), "Please wait...", "Fetching data...", false, false);
       // ProgressBar progressBar = new ProgressBar(this.getActivity(), null, android.R.attr.progressBarStyleSmall);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, cuacaURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");
                            SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                            SharedPreferences.Editor editor = pref.edit();
                            if (!error) {
                                //JSONObject user = jObj.getJSONObject("user");
                                loading.dismiss();
                                kota = jObj.getString("kota").toString();
                                waktu=jObj.getString("waktu").toString();
                                cuaca = jObj.getString("cuaca").toString();
                                suhu=jObj.getString("suhu").toString();
                                kelembaban=jObj.getString("kelembaban").toString();

                                txtKota.setText(kota);
                                txtWaktu.setText(waktu);
                                txtTanggal.setText(getTanggal());
                                txtCuaca.setText(cuaca);
                                txtSuhu.setText(suhu+" \u00b0"+"C");
                                txtKelembaban.setText(kelembaban+" %");

                            } else {
                                // Error in login. Get the error message
                                String errorMsg = jObj.getString("error_msg");
                                Toast.makeText(getActivity(),
                                        errorMsg, Toast.LENGTH_LONG).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(),
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
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
    public String getTanggal(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    }




