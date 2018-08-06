package com.tarlic.hyperiongame;

import java.util.Random;
import java.util.Vector;

import android.graphics.Color;

public class ColorGenerator {

	private static final double GOLDEN_RATIO = 1.61803398875;
	private static final float SATURATION = (float) 0.7;
	private static final float VALUE = (float) 0.95;
		
	public Vector<Integer> generateColors(int numberOfColors) {
	
		float[] hsv = new float [ 3 ];
		
		Vector<Integer> colors = new Vector<Integer>();

		Random randomHue = new Random();
		
		float nH = randomHue.nextFloat();
		
		/*
		 * h is defined as its proper value times 6,
		 * which simplifies region determination.
		 */
		nH = nH * 6;
		
		hsv[1] = SATURATION;
		
		hsv[2] = VALUE;
		
		/*
		 *  6 into GOLDEN_RATIO into 6 (approximately ~3.708) is
		 *  used instead of 1/GOLDEN_RATIO, in agreement with the scaling of h.
		 */
		double SIX_INTO_GOLDEN_RATIO = 6/GOLDEN_RATIO;
		
		while ( colors.size() < numberOfColors )
		{
			nH += SIX_INTO_GOLDEN_RATIO;

			nH %= 1;

			hsv[0] = nH;
			
			int rgb[] = hsvToRgb(nH, hsv[1], hsv[2]);
			
			// Get the new color
			Integer color = Color.rgb(rgb[0], rgb[1], rgb[2]);
			
			/*
			 *  Add the color if the array has not the same
			 *  color yet.
			 */
			if (! colors.contains(color)) {
				colors.add(color);
				
				/*
				Log.d("Hyperion", "nH: " + String.valueOf(nH) +
						" Color: " + Color.rgb(rgb[0], rgb[1], rgb[2]) +
						" R: " + rgb[0] + " G: " + rgb[1] + " B: " + rgb[2]);
				*/
			}
		}
		
		return colors;
	}
	
	private int[] hsvToRgb (float h, float s, float v)
	{
		int h_i = (int) Math.floor(h * 6);
	    double f = h * 6 - h_i;
	    double p = v * (1 - s);
	    double q = v * (1 - f * s);
	    double t = v * (1 - (1 - f) * s);
	    
	    double r = 0;
	    double g = 0;
	    double b = 0;
	    
	    switch(h_i) {
	      case 0:
	        r = v; g = t; b = p; break;
	      case 1:
	        r = q; g = v; b = p; break;
	      case 2:
	        r = p; g = v; b = t; break;
	      case 3:
	        r = p; g = q; b = v;  break;
	      case 4:
	        r = t; g = p; b = v;  break;
	      case 5:
	        r = v; g = p; b = q; break;
	    }
	   
	    
	    int rgb[] = { (int) Math.floor(r * 256),
	    		(int) Math.floor(g * 256),
	    		(int) Math.floor(b * 256) };
	    
	    return rgb;
	}
		
}

