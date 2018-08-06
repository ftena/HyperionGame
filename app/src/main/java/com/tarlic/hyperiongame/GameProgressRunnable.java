package com.tarlic.hyperiongame;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class GameProgressRunnable implements Runnable {

	// Tells the Runnable to pause for a certain number of milliseconds
    private static final long SLEEP_TIME_MILLISECONDS = 1000;
    
    // Class constants defining state of the thread
    final static int DONE = 0;
    final static int RUNNING = 1;
    
    /*
     * Send messages to the view
     */
	private Handler mHandler;
	
	/*
	 * Time increment depending on level
	 */
	
	private int mDifficulty;
	
	/*
	 * Maximum time allowed
	 */
	
	int mMaxTime;
	
	/*
	 * Control the thread state
	 */
	int mState;	
	
	/*
	 * Control the game time
	 */
	int mTime;
	
	GameProgressRunnable(Handler handler, int level) {
		
		mHandler = handler;
		
		switch (level)
		{
			case 1:
				mDifficulty = 1; break;
			case 2:
				mDifficulty = 2; break;
			case 3:
				mDifficulty = 3; break;
			case 4:
				mDifficulty = 4; break;
			case 5:
				mDifficulty = 5; break;
			case 6:
				mDifficulty = 7; break;
			case 7:
				mDifficulty = 10; break;
			default:
				//Log.e("Hyperion", "In GameProgressRunnable: difficulty not found");
		}
		
		mMaxTime = 100;
		mState = RUNNING;
		mTime = 0;
		
	}
	
    /*
     * Defines the code to run for this task.
     */
    @Override
    public void run() {
        /*
		 * At the beginning of the run() method, set the thread to use
		 * background priority by calling Process.setThreadPriority() with
		 * THREAD_PRIORITY_BACKGROUND. This approach reduces resource
		 * competition between the Runnable object's thread and the UI thread.
		 */
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        
        while (mState == RUNNING) {
	        
	        try {
	        	
	        	Thread.sleep(SLEEP_TIME_MILLISECONDS);
	        	
	        	Message msg = mHandler.obtainMessage();
	            Bundle b = new Bundle();
	            
	            mTime = mTime + mDifficulty;
	            
	            b.putInt("total", mTime);
	            
	            msg.setData(b);
	            mHandler.sendMessage(msg);
	            
	            if (mTime >= mMaxTime)
	            	mState = DONE;
	            
	        } catch (InterruptedException e) {
	        	e.printStackTrace();
	        }
        }
    }
    
    // Set current state of thread (use state=DONE to stop thread)
    public void setState(int state) {
        mState = state;
    }
    
    public int getState() {
        return mState;
    }

}