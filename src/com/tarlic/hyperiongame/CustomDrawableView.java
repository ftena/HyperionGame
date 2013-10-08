package com.tarlic.hyperiongame;

import java.util.Iterator;
import java.util.Vector;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CustomDrawableView extends View {
	private ShapeDrawable mDrawable;
	
	private Vector<Figure> mFigures;
	
	private int i, x, y;

	public CustomDrawableView(Context context, AttributeSet attributeSet, Vector<Figure> figures) {
		super(context, attributeSet);

		mFigures = figures;
			
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			
			for (Figure figure : mFigures)
			{
				Rect rect = figure.getDrawable().getBounds();
				
				if (rect.contains((int)event.getX(), (int)event.getY()))
				{
					Log.i("mytag", String.valueOf(figure.getID()));
					
					isExist();
				}				
			}
			
			return true;
		case MotionEvent.ACTION_UP:
			
			return true;
		default:
			return false;
		}
	}
		
	private boolean isExist() {
		
		
		return false;		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		for (Figure figure : mFigures)
		{
			figure.getDrawable().draw(canvas);
		}
		
		//mDrawable.draw(canvas);
		
	}
	
	
}
