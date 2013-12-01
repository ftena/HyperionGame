package com.tarlic.hyperiongame;

import java.util.Random;
import java.util.Vector;

import com.tarlic.hyperiongame.R;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.RelativeLayout;

public class GameActivity extends Activity {

	protected static final long COLORS_INCREMENT = 2;
	protected static final int FIGURE_HEIGHT = 70;
	protected static final int FIGURE_WIDTH = 70;
	protected static final long LEVEL_INCREMENT = 6;
	
	public Vector<Integer> mColors;
	
	private CustomDrawableView mCustomDrawableView;
	
	private Vector<Figure> mFigures = new Vector<Figure>();
	
	RelativeLayout.LayoutParams mLayoutParams;
	
	int mLevel;
	
	RelativeLayout mRelativeLayout;
	
	private int mViewWidth, mViewHeight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		// Get the level selected by the user in the title screen		
		Intent intent = getIntent();
		mLevel = intent.getIntExtra(MainActivity.LEVEL_VALUE, 1);

		// Generate an array of colors		
		ColorGenerator cg = new ColorGenerator();
		
		// There will be more colors than figures!
		mColors = cg.generateColors((int) (mLevel*LEVEL_INCREMENT*COLORS_INCREMENT));
		
		mCustomDrawableView = new CustomDrawableView (this, null, mFigures, mColors, mLevel);
		
		ViewTreeObserver observer = mCustomDrawableView.getViewTreeObserver();
		
		/*
		 * Add an observer to get the right width and height.
		 */
		observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() { 
		    @SuppressWarnings("deprecation")
			@SuppressLint("NewApi")
			@Override 
		    public void onGlobalLayout() {
		    	
		    	mViewWidth  = mCustomDrawableView.getMeasuredWidth();
		    	mViewHeight = mCustomDrawableView.getMeasuredHeight(); 
		    	
		    	/*
		    	Log.i("Hyperion", "OnGlobalLayoutListener: " + String.valueOf(mViewWidth)
						+ " x " + String.valueOf(mViewHeight));
		    	*/
		    	Random randomPosition = new Random();
		    	
		    	for (int i = 0; i < mLevel*LEVEL_INCREMENT; i++) {
		    	
		    		int x = randomPosition.nextInt(mViewWidth - FIGURE_WIDTH);
		    		int y = randomPosition.nextInt(mViewHeight - FIGURE_HEIGHT);
		    		
		    		/*
		    		Log.d("Hyperion", "OnGlobalLayoutListener - pos: " + String.valueOf(x)
							+ " - " + String.valueOf(y));
		    		*/
		    		
		    		Figure figure = new Figure(i, x, y, FIGURE_WIDTH,
		    				FIGURE_HEIGHT, mColors.get(i));
		    		
		    		/*
		    		 * Add figures at the beginning so the touch
		    		 * be at the figures displayed on the top in the
		    		 * screen.
		    		 */
		    		mFigures.add(figure);
		    				    		
		    	}
		    	
		    	if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN)
		    		mCustomDrawableView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
		    	else
		    		mCustomDrawableView.getViewTreeObserver().removeGlobalOnLayoutListener(this);

		    } 
		});
		
		mRelativeLayout = (RelativeLayout) findViewById(R.id.custom_relative_layout);
		mRelativeLayout.addView(mCustomDrawableView);
				
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
