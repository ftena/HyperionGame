package com.tarlic.hyperiongame;

import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CustomDrawableView extends View {
	
	protected static final int GAME_STARTED = 1;
	protected static final int GAME_FAIL = 2;
	protected static final int GAME_SUCCESS = 3;
	protected static final int TOP_TEXT_GAMEOVER = 100;
	
	private Activity mActivity;
	
	private Vector<Integer> mColors;
	
	private Vector<Figure> mFigures;
	
	private int mWidth, mHeight;

	private int mGameOver;
	
	private Bitmap mGameOverSuccessBitmap;
	
	private Bitmap mGameOverFailBitmap;
	
	private GameProgressRunnable mGameProgressRunnable;
	
	/*
	 * A Handler allows you to send and process Message and Runnable objects
	 * associated with a thread's MessageQueue. Each Handler instance is
	 * associated with a single thread and that thread's message queue. When you
	 * create a new Handler, it is bound to the thread / message queue of the
	 * thread that is creating it -- from that point on, it will deliver
	 * messages and runnables to that message queue and execute them as they
	 * come out of the message queue.
	 */
	private Handler mHandler = new Handler();
	
	private ProgressBar mProgress;

	
	public CustomDrawableView(Context context, AttributeSet attributeSet)
	{
		super(context, attributeSet);
	}
	
	public CustomDrawableView(Context context, AttributeSet attributeSet,
			Vector<Figure> figures, Vector<Integer> colors, int level) {
		super(context, attributeSet);

		mActivity = (Activity) this.getContext();
		
		mColors = colors;
		
		mGameOver = GAME_STARTED;
		
		mFigures = figures;
		
		Resources res = getContext().getResources();
		
		// GameOver bitmap		
		mGameOverFailBitmap = BitmapFactory.decodeResource(res,
        		R.drawable.game_over);
		
				
		// Level cleared bitmap
		mGameOverSuccessBitmap = BitmapFactory.decodeResource(res,
				R.drawable.level_cleared);

		// Write the level in the text view		
		TextView levelText = (TextView) mActivity.findViewById(R.id.level);
		
		levelText.setText("Level " + level + " - ");
		
		/*
		 * If mProgress is get using:
		 * 		mProgress = (ProgressBar) findViewById(R.id.time);
		 * mProgress is null.
		 */
		
		mProgress = (ProgressBar) mActivity.findViewById(R.id.time);
		
		/*
		 * A Handler allows you to send and process Message and Runnable objects
		 * associated with a thread's MessageQueue.
		 */
		// Defines a Handler object that's attached to the UI thread
	    mHandler = new Handler(Looper.getMainLooper()) {
	        /*
             * handleMessage() defines the operations to perform when the
             * handler receives a new Message to process.
             */
            @Override
            public void handleMessage(Message inputMessage) {
            	// Get the current value of the variable total from the message data
                // and update the progress bar.
                int total = inputMessage.getData().getInt("total");
                
                if (mGameProgressRunnable.getState() != GameProgressRunnable.DONE) {
                	mProgress.setProgress(total);
                } else {
                	mProgress.setProgress(mProgress.getMax());
                }
                
                // Max. progress reached or num of figures is equal or less than one
                if ( mProgress.getProgress() >= mProgress.getMax() || mFigures.size() <= 1 )
                {
                	mGameProgressRunnable.setState(GameProgressRunnable.DONE);
                	
                	if ( mFigures.size() > 1 ) {
                		// Set the game as failed
                    	mGameOver = GAME_FAIL;
                    	
                	} else {
                		// Set the game as successful
                    	mGameOver = GAME_SUCCESS;
                	}
                	
                	// Draw the "Game Over" or "Level cleared!" text
                	invalidate();
                }
                	
                
            }
	    };
	    
	    mGameProgressRunnable = new GameProgressRunnable(mHandler, level);
	    
	    /*
	     * We launch a thread.
	     * Important: do not block the UI thread!
	     */
	    
	    new Thread(new Runnable() {
	        public void run() {
	    
	        		/*
	        		 *  Call the run method in GameProgressRunnable.
	        		 */
	        		mGameProgressRunnable.run();
	        		
	        }
	    }).start();
		
	}
		
	@Override
	protected void onFinishInflate() {
		/*
		 * We've created the view using "new" in MainActivity.
		 * This method is never called.
		 */
		super.onFinishInflate();
		
		mWidth = this.getWidth();
		mHeight = this.getHeight();
		
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		
		mWidth = this.getWidth();
		mHeight = this.getHeight();

		/*
		Log.d("Hyperion", "onLayout: " + String.valueOf(mWidth) 
				+ " x " + String.valueOf(mHeight));
		*/
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		Vector<Figure> foundFigures = new Vector<Figure>();
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			
			if ( mGameOver == GAME_STARTED ) {
				/*
				 *  The games is not over, so we check if there
				 *  is any figure to be processed
				 */
				Figure selectedFigure = null;

				
				/*
				 * Get the figures under the touch gesture
				 */
				for (Figure figure : mFigures)
				{
										
					Rect rect = figure.getBounds();

					if (rect.contains((int)event.getX(), (int)event.getY()))
					{	
						
						foundFigures.add(figure);
						
					}
					
				}
				
				/*
				 * If we've found any figure, get the last one because
				 * it is the top one in the display.
				 */
				if (foundFigures.size() != 0)
				{				
					selectedFigure = foundFigures.lastElement();
					
					selectedFigure.changeColor(mColors);
					
					/*
					 * invalidate()
					 * 
					 * Invalidate the whole view. If the view is visible,
					 * onDraw(android.graphics.Canvas) will be called at some
					 * point in the future. This must be called from a UI
					 * thread. To call from a non-UI thread, call
					 * postInvalidate().
					 */

					postInvalidate();								
				} 
				
				/*
				 * Check more figures with the same color.
				 */
				if (selectedFigure != null) {
					/*
					 *  We've found a figure in the coordinates touched
					 *  by the user.
					 */
					if ( checkIfColorExists(selectedFigure) )					
						postInvalidate();	
				}
				
			} else {
				// The user touch and the game is already finished
				mActivity.finish();
			}
			
			return true;
		case MotionEvent.ACTION_UP:
			
			return true;
		default:
			return false;
		}
	}
		
	private boolean checkIfColorExists(Figure f) {
		
		Vector<Figure> removeFoundFigures = new Vector<Figure>();		
		boolean colorFound = false;
		
		for (Figure figure : mFigures)
		{
			// Do not compare the same figure
			if (!figure.equals(f))
			{			
				if ( f.sameColor(figure.getPaint().getColor()) ) {
					colorFound = true;
										
					removeFoundFigures.add(figure);
				}
			}
			
		}	
		
		if ( colorFound ) {
			removeFoundFigures.add(f);
					
			mFigures.removeAll(removeFoundFigures);
	
			return true;
		}
		
		return false;
	}

	@Override
	protected void onDraw(Canvas canvas) {		
		super.onDraw(canvas);

		if (mGameOver == GAME_FAIL)
		{
			mGameOverFailBitmap = getScaledDimension(mGameOverFailBitmap,
					this.getWidth(), this.getHeight());
		
			canvas.drawBitmap(mGameOverFailBitmap, 0, TOP_TEXT_GAMEOVER, null);
			
		} else if (mGameOver == GAME_SUCCESS) {
			mGameOverSuccessBitmap = getScaledDimension(mGameOverSuccessBitmap,
					this.getWidth(), this.getHeight());

			canvas.drawBitmap(mGameOverSuccessBitmap, 0, TOP_TEXT_GAMEOVER, null);
		} else {
			
			for (Figure figure : mFigures)
			{				
				figure.draw(canvas);
			}	
		}
		 
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);		
	}
	
	public static Bitmap getScaledDimension(Bitmap bitmap,
			int boundary_width, int boundary_height) {

	    int original_width = bitmap.getWidth();
	    int original_height = bitmap.getHeight();
	    int bound_width = boundary_width;
	    int bound_height = boundary_height;
	    int new_width = original_width;
	    int new_height = original_height;
	    
	    // first check if we need to scale width
	    if (original_width > bound_width) {
	        //scale width to fit
	        new_width = bound_width;
	        //scale height to maintain aspect ratio
	        new_height = (new_width * original_height) / original_width;
	    }
	    
	    // then check if we need to scale even with the new height
	    if (new_height > bound_height) {
	        //scale height to fit instead
	        new_height = bound_height;
	        //scale width to maintain aspect ratio
	        new_width = (new_height * original_width) / original_height;
	    }
	    
		return Bitmap.createScaledBitmap(bitmap, new_width, new_height, true);
	}
	
}
