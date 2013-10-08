package com.tarlic.hyperiongame;

import java.util.Vector;

import com.tarlic.hyperiongame.R;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.view.View;

public class MainActivity extends Activity {
	
	RelativeLayout.LayoutParams mLayoutParams;
	
	RelativeLayout mRelativeLayout;
	
	private CustomDrawableView mCustomDrawableView;
	
	private Vector<Figure> mFigures = new Vector<Figure>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//mFigure1 = new Figure(this);
		
		/*GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new ImageAdapter(this));
	    
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            //Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
	            
	            ColorDrawable drawable = (ColorDrawable) v.getBackground();
	            
	            Toast.makeText(MainActivity.this, "" + drawable.getColor(), Toast.LENGTH_SHORT).show();

	        }
	    });*/
		
		mRelativeLayout = (RelativeLayout) findViewById(R.id.custom_relative_layout);
		
		Figure mFigure1 = new Figure(this, 0, 50, 50, 50, 50);
		Figure mFigure2 = new Figure(this, 1, 200, 200, 50, 50);
		Figure mFigure3 = new Figure(this, 2, 100, 100, 50, 50);
		Figure mFigure4 = new Figure(this, 3, 250, 250, 50, 50);
		
		mFigures.add(mFigure1);		
		mFigures.add(mFigure2);
		mFigures.add(mFigure3);
		mFigures.add(mFigure4);
		
		mCustomDrawableView = new CustomDrawableView (this, null, mFigures);
		
		mRelativeLayout.addView(mCustomDrawableView);
		
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
