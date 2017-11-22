package com.example.eldi.sellfish;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

public class FeedBackListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<mFeedback> mFeedbackItems;


    public FeedBackListAdapter(Activity activity, List<mFeedback> mFeedbackItems) {
        this.activity = activity;
        this.mFeedbackItems = mFeedbackItems;
    }

    @Override
    public int getCount() {
        return mFeedbackItems.size();
    }

    @Override
    public Object getItem(int location) {
        return mFeedbackItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row_feedback, null);

        TextView isi = (TextView) convertView.findViewById(R.id.isi_feedback);
        TextView pengirim=(TextView)convertView.findViewById(R.id.pengirim);
        ImageView img_status=(ImageView)convertView.findViewById(R.id.img_status);

        // getting data for the row
        mFeedback f = mFeedbackItems.get(position);

        isi.setText(f.getIsi());
        pengirim.setText(f.getPengirim());
        if (f.getStatus().equalsIgnoreCase("bagus")){
            img_status.setBackgroundResource(R.drawable.thumb_up);
        }
        else if(f.getStatus().equalsIgnoreCase("jelek")){
            img_status.setBackgroundResource(R.drawable.thumb_down);
        }



        return convertView;
    }

}
