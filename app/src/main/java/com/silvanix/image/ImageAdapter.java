package com.silvanix.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayrokid on 17/02/16.
 */
public class ImageAdapter extends ArrayAdapter<Gambar> {

    Context context;
    int layoutResourced;
    List<Gambar> data = new ArrayList<Gambar>();

    public ImageAdapter(Context context, int layoutResourced, List<Gambar> data) {
        super(context, layoutResourced, data);

        this.layoutResourced = layoutResourced;
        this.context = context;
        this.data = data;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        ImageHolder holder = null;
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourced, parent, false);
            holder = new ImageHolder();
            holder.imageURL = (ImageView)row.findViewById(R.id.imageViewGambarList);
            row.setTag(holder);
        }
        else
        {
            holder = (ImageHolder)row.getTag();
        }
        Gambar gambar = data.get(position);
//        holder.imageID.setText(gambar._id);

        // convert byte to bitmap take from Gambar class
        byte[] outImage = gambar._image;
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap bitmap = BitmapFactory.decodeStream(imageStream);


        holder.imageURL.setImageBitmap(bitmap);


        return row;
    }

    static class ImageHolder {
        ImageView imageURL;
        TextView imageID;
    }
}
