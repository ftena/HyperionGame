package com.tarlic.hyperiongame;

import java.util.Random;
import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

public class Figure extends ShapeDrawable {
	
	private int mID;
	
	private int mColor;
	
	private GradientDrawable gradient;
	
	public Figure(int id, int x, int y, int width, int height, Integer color) {
		setID(id);
		
		mColor = color;
				
		this.setShape(new OvalShape());
		this.getPaint().setColor(mColor);
		this.setBounds(x, y, x + width, y + height);	
		
		gradient = new GradientDrawable(Orientation.BOTTOM_TOP,
				new int[]{Color.TRANSPARENT, Color.TRANSPARENT});
		gradient.setStroke(5, Color.BLACK);
		
		gradient.setShape(GradientDrawable.OVAL);
		gradient.setBounds(x, y, x + width, y + height);
	}

	public void changeColor(Vector<Integer> colors) {
		Random randomColor = new Random();
		
		mColor = colors.get(randomColor.nextInt(colors.size()));
		
		this.getPaint().setColor(mColor);
	}
	
	public int getID() {
		return mID;
	}

	public void setID(int mID) {
		this.mID = mID;
	}
	
	public boolean sameColor(int mColor) {
		if (this.mColor == mColor)
			return true;
		else
			return false;
	}

	public int getColor() {
		return mColor;
	}

	public void setColor(int mColor) {
		this.mColor = mColor;
	}


	@Override
	  public void draw(Canvas canvas) {
		super.draw(canvas);

		// draw the gradient (black oval)
		gradient.draw(canvas);
		
	   }
	
}
