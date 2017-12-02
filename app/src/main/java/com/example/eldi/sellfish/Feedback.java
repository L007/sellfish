package com.example.eldi.sellfish;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

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
 * Created by eldi on 22/11/2017.
 */
public class Feedback extends AppCompatActivity {

    private ProgressDialog pDialog;
    private List<mFeedback> feedbackList = new ArrayList<mFeedback>();
    private ListView listView;
    private FeedBackListAdapter adapter;
    String id;

    public static final String url = "http://10.0.3.2/sellfish/feedback.php?apicall=get_all_feed_to_penjual";

    @Override
    /*coba push ke github*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        listView = (ListView) findViewById(R.id.list);
        adapter = new FeedBackListAdapter(this, feedbackList);
        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(Feedback.this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        SharedPreferences pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        id = pref.getString("id_user",null);

        // Creating volley request obj
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(String.valueOf(Feedback.this), response.toString());
                        hidePDialog();

                        // Parsing json

                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray jsonArray = obj.getJSONArray("data");
                            for(int i=0; i<jsonArray.length(); i++) {
                                JSONObject objFeedback = jsonArray.getJSONObject(i);
                                mFeedback feed = new mFeedback();
                                feed.setIsi(objFeedback.getString("isi_feedback"));
                                feed.setStatus(objFeedback.getString("status"));
                                feed.setPengirim(objFeedback.getString("pengirim"));

                                // Genre is json array
                                /*JSONArray jualanArray = obj.getJSONArray("data");*/
                                ArrayList<String> feedback = new ArrayList<String>();
                              /*  for (int j = 0; j < jualanArray.length(); j++) {
                                    jualan_ikan.add((String) jualanArray.get(j));
                                }*/
                                feed.setFeedback(feedback);

                                // adding jualan to jualans array
                                feedbackList.add(feed);
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
                VolleyLog.d(String.valueOf(Feedback.this), "Error: " + error.getMessage());
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


    }
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