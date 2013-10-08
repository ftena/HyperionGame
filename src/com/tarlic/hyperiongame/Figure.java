package com.tarlic.hyperiongame;

import java.util.Random;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.widget.ImageView;

public class Figure {

	private ShapeDrawable mDrawable;

	private int mX, mY, mWidth, mHeight;
	
	private int mID;
	
	private int mColor;
	
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
	
	public Figure(Context context, int id, int x, int y, int width, int height) {
		setID(id);
		mX = x;
		mY = y;
		mWidth = width;
		mHeight = height;

		Random randomColor = new Random();
		
		mColor = Color.parseColor(mColors[randomColor.nextInt(mColors.length)]);
		
		mDrawable = new ShapeDrawable(new OvalShape());
		mDrawable.getPaint().setColor(mColor);
		mDrawable.setBounds(x, y, x + width, y + height);
	}

	public ShapeDrawable getDrawable() {
		return mDrawable;
	}

	public void setDrawable(ShapeDrawable mDrawable) {
		this.mDrawable = mDrawable;
	}

	public int getID() {
		return mID;
	}

	public void setID(int mID) {
		this.mID = mID;
	}
	
}
