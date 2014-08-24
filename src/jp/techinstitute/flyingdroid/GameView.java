/**
 * 
 */
package jp.techinstitute.flyingdroid;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * @author ktaka
 *
 */
public class GameView extends SurfaceView implements Callback {

	class GameThread extends Thread {
		SurfaceHolder surfaceHolder;
		boolean shouldContinue = true;
		Droid droid;
		static final int droidSize = 200;
		
		public GameThread(SurfaceHolder surfaceHolder, Context context,
				Handler handler) {
			this.surfaceHolder = surfaceHolder;
			droid = new Droid(context, droidSize, droidSize);
		}
		
		@Override
		public void run() {
			while(shouldContinue) {
				Canvas c = surfaceHolder.lockCanvas();
				draw(c);
				surfaceHolder.unlockCanvasAndPost(c);
			}
		}
		
		public void draw(Canvas c) {
			c.drawARGB(255, 0, 0, 0);
			droid.draw(c, 100, 100);
		}
	}
	GameThread gameThread;
	
	/**
	 * @param context
	 */
	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        gameThread = new GameThread(holder, context, new Handler() {
        	@Override
            public void handleMessage(Message msg) {
        		super.handleMessage(msg);
        	}
        });
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		gameThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		gameThread = null;
	}

}
