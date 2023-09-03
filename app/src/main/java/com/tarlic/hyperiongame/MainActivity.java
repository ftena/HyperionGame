package com.tarlic.hyperiongame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends Activity {

	public static final String LEVEL_STRING = "com.tarlic.hyperiongame.LEVEL_STRING";
	public static final String LEVEL_VALUE = "com.tarlic.hyperiongame.LEVEL_VALUE";
	
	private Spinner mSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_title);

		/*
		 * Set spinner values and listener
		 */

		mSpinner = (Spinner) findViewById(R.id.title_spinner);

		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.levels_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		mSpinner.setAdapter(adapter);

		mSpinner.setOnItemSelectedListener(new SpinnerListener());
	}

	public void startGame(View view) {
		Intent intent = new Intent(this, GameActivity.class);
		
		// Get the selected level in the spinner
		Object selectedLevel = mSpinner.getSelectedItem();
		
		// Get the level as string
		String levelString = selectedLevel.toString();
		
		/*
		 *  Get the level as long (adding 1 because the first
		 *  element in the spinner has the index 0)
		 */
		
		int levelValue = (int) (mSpinner.getSelectedItemId() + 1);
		
	    intent.putExtra(LEVEL_STRING, levelString);
	    intent.putExtra(LEVEL_VALUE, levelValue);
	    
	    startActivityForResult (intent, 0);
	}

}
