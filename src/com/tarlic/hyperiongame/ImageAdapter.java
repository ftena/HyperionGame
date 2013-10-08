package com.tarlic.hyperiongame;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.BaseAdapter;

/*
Black	#000000
Red	#FF0000
Lime	#00FF00
Blue	#0000FF
Yellow	#FFFF00
Cyan / Aqua	#00FFFF
Magenta / Fuchsia	#FF00FF
Silver	#C0C0C0
Gray	#808080
Maroon	#800000
Olive	#808000
Green	#008000
Purple	#800080
Teal	#008080
Navy	#000080
*/

public class ImageAdapter extends BaseAdapter  {

	private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mColors.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        //imageView.setImageResource(mThumbIds[position]);
        imageView.setBackgroundColor(Color.parseColor(mColors[position]));
        return imageView;
    }

    // references to our images
    private String[] mColors = {
    		"#000000",
    		"#FF0000",
    		"#00FF00",
    		"#0000FF",
    		"#FFFF00",
    		"#00FFFF",
    		"#FF00FF",
    		"#C0C0C0",
    		"#808080",
    		"#800000",
    		"#808000",
    		"#008000",
    		"#800080",
    		"#008080",
    		"#000080"
    };

}
