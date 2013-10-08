package com.tarlic.hyperiongame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CustomDrawableView extends View {
	private ShapeDrawable mDrawable;

	public CustomDrawableView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);

		int x = 100;
		int y = 100;
		int width = 50;
		int height = 50;

		mDrawable = new ShapeDrawable(new OvalShape());
		mDrawable.getPaint().setColor(0xff74AC23);
		mDrawable.setBounds(x, y, x + width, y + height);
		
		this.setOnTouchListener(new OnTouchListener()
		{

		@Override
		public boolean onTouch(View arg0, MotionEvent motionEvent) {
			// TODO Auto-generated method stub
			
			
			if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){

				Log.i("mytag", "ACTION_UP");
				
				Resources r = getContext().getResources();
				
				r.getLayout(R.layout.activity_main);
				
				
				
	            // Do what you want
	            return true;
	        }
	        return false;
		}
		
		});
		
	}

	protected void onDraw(Canvas canvas) {
		mDrawable.draw(canvas);
	}
	
	
}
