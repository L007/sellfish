package com.example.eldi.sellfish;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class TransaksiListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<mTransaksiPenjual> transaksiItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public TransaksiListAdapter(Activity activity, List<mTransaksiPenjual>transaksiItems) {
        this.activity = activity;
        this.transaksiItems=transaksiItems;
    }

    @Override
    public int getCount() {
        return transaksiItems.size();
    }

    @Override
    public Object getItem(int location) {
        return transaksiItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_transaksi, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.img_gambar_ikan);
        TextView nama_ikan = (TextView) convertView.findViewById(R.id.txt_nama_ikan);
        TextView harga_ikan = (TextView) convertView.findViewById(R.id.txt_harga);
        TextView jumlah_pesanan = (TextView) convertView.findViewById(R.id.txt_jumlah_pesanan);
        TextView pembeli_ikan = (TextView) convertView.findViewById(R.id.txt_pembeli);

        // getting data for the row
        mTransaksiPenjual m = transaksiItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // nama ikan
        nama_ikan.setText(m.getNamaIkan());

        // harga
        harga_ikan.setText(formatRupiah.format(Double.parseDouble(m.getHarga())));

        // jumlah stok
        jumlah_pesanan.setText("Jumlah pesanan: "+m.getjumlahPesanan());

        // penjual
        pembeli_ikan.setText(m.getpembeli());

        return convertView;
    }

}
