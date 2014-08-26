/**
 * 
 */
package jp.techinstitute.flyingdroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
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
		int width;
		int height;
		Droid droid;
		static final int droidSize = 200;
		long frameNo = 0;
		long nextGenFrame = 100;
		
		Context context;
		
		static final int EnemyNum = 5;
		Enemy[] enemys;
		static final int enemySize = 200;
		
		SoundPool sound;
		int hitSoundId;
		int rocketSoundId;
		int rocketStreamId;
		long resumeFrame = -1;
		
		public GameThread(SurfaceHolder surfaceHolder, Context context,
				Handler handler) {
			this.surfaceHolder = surfaceHolder;
			droid = new Droid(context, droidSize, droidSize);
			droid.setInitialPosition(100, 0);

			this.context = context;
            enemys = new Enemy[EnemyNum];
            enemys[0] = new Enemy(context, enemySize, enemySize);
            
            setupSoundPool();
		}
		
		public void setupSoundPool() {
			sound = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
			hitSoundId = sound.load(context, R.raw.quick_explosion, 1);
			rocketSoundId = sound.load(context, R.raw.rockets, 1); 
		}
		
		public void releaseSoundPool() {
			sound.release();
		}
		
		public void setViewSize(int width, int height) {
			this.width = width;
			this.height = height;
			
			droid.setMovingBoundary(0, 0, width, height);
			for (int i = 0; i < EnemyNum; i++) {
				if (enemys[i] != null) {
					enemys[i].setMovingBoundary(0, 0, width, height);
				}
			}
		}
		
		public void upliftDroid(boolean on) {
			droid.uplift(on);
			if (on) {
				rocketStreamId = sound.play(rocketSoundId, 0.5f, 0.5f, 0, -1, 1.0f);
			} else {
				sound.stop(rocketStreamId);
			}
		}
		
		@Override
		public void run() {
			while(shouldContinue) {
				Canvas c = surfaceHolder.lockCanvas();
				if (c != null) {
					draw(c);
					surfaceHolder.unlockCanvasAndPost(c);
				}
			}
		}
		
		public void drawHitPoint(Canvas c, int point) {
			Paint paint = new Paint();
			paint.setColor(Color.WHITE);
			paint.setTextSize(80);
			c.drawText("HP: " + point, 20, 100, paint);
		}
		
		public void draw(Canvas c) {
			int hitPoint = droid.getHitPoint();
			for (int i = 0; i < EnemyNum; i++) {
				if (enemys[i] != null && enemys[i].isHit(droid)) {
					droid.setImageResourceId(R.drawable.andou_die01);
					droid.hit();
					hitPoint = droid.getHitPoint();
					if (hitPoint == 0) {
						resumeFrame = frameNo + 300;
					}
					sound.play(hitSoundId, 1.0f, 1.0f, 0, 0, 1.0f);
				}
			}
			c.drawARGB(255, 0, 0, 0);
			droid.draw(c);
			for (int i = 0; i < EnemyNum; i++) {
				if (enemys[i] != null) {
					enemys[i].draw(c);
				}
			}
			
			drawHitPoint(c, hitPoint);
			if (frameNo == resumeFrame) {
				droid.resume();
			}
			
			if (frameNo == nextGenFrame) {
				for (int i = 0; i < EnemyNum; i++) {
					if (enemys[i] == null) {
						enemys[i] = new Enemy(context, enemySize, enemySize);
						enemys[i].setMovingBoundary(0, 0, width, height);
						nextGenFrame += 100;
						break;
					}
				}
			}
			frameNo++;
		}
	}
	
	GameThread gameThread;
	Context context;
	
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
		
		this.context = context;
		SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        
        setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return dispatchEvent(event);
			}
		});
	}
	
	private boolean dispatchEvent(MotionEvent event) {
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			gameThread.upliftDroid(true);
			return true;
		case MotionEvent.ACTION_UP:
			gameThread.upliftDroid(false);
			return false;
		default:
			return false;
		}
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
		gameThread.setViewSize(width, height);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
        gameThread = new GameThread(holder, context, new Handler() {
        	@Override
            public void handleMessage(Message msg) {
        		super.handleMessage(msg);
        	}
        });
		
		gameThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		gameThread.shouldContinue = false;
		gameThread = null;
	}

}
