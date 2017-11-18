package com.example.eldi.sellfish;

import android.graphics.Movie;
import android.widget.BaseAdapter;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Jualan> jualanItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<Jualan> jualanItems) {
        this.activity = activity;
        this.jualanItems=jualanItems;
    }

    @Override
    public int getCount() {
        return jualanItems.size();
    }

    @Override
    public Object getItem(int location) {
        return jualanItems.get(location);
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
            convertView = inflater.inflate(R.layout.list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.img_gambar_ikan);
        TextView nama_ikan = (TextView) convertView.findViewById(R.id.txt_nama_ikan);
        TextView harga_ikan = (TextView) convertView.findViewById(R.id.txt_harga);
        TextView jumlah_stok = (TextView) convertView.findViewById(R.id.txt_jumlah_stok);
        TextView penjual_ikan = (TextView) convertView.findViewById(R.id.txt_penjual);

        // getting data for the row
        Jualan m = jualanItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // nama ikan
        nama_ikan.setText(m.getNamaIkan());

        // harga
        harga_ikan.setText("Harga: " + m.getHarga());

        // jumlah stok
        jumlah_stok.setText("Stok: "+m.getJumlahStok());

        // penjual
        penjual_ikan.setText(m.getPenjual());

        return convertView;
    }

}
