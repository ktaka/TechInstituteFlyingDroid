/**
 * 
 */
package jp.techinstitute.flyingdroid;

import android.content.Context;
import android.graphics.Canvas;

/**
 * @author ktaka
 *
 */
public class Droid extends AbstractGameObject {
	private int defaultX;
	private int defaultY;
	private static final float DefaultVelocity = 2;
	private float velocity = DefaultVelocity;
	private static final int FullHitPoint = 100;
	private int hitPoint = FullHitPoint;

	/**
	 * @param context
	 * @param resourceId
	 * @param width
	 * @param height
	 */
	public Droid(Context context, int width, int height) {
		super(context, R.drawable.andou_diag01, width, height);
	}
	
	public void hit() {
		if (hitPoint > 0) {
			hitPoint -= 10;
		}
		if (hitPoint <= 0) {
			velocity = DefaultVelocity;
			setImageResourceId(R.drawable.andou_explode10);
		}
	}
	
	public int getHitPoint() {
		return hitPoint;
	}
	
	public void resume() {
		hitPoint = FullHitPoint;
		setImageResourceId(R.drawable.andou_diag01);
	}

	@Override
	public void setMovingBoundary(int left, int top, int right, int bottom) {
		super.setMovingBoundary(left, top, right, bottom);
		this.bottom -= height;
	}

	public void uplift(boolean on) {
		if (hitPoint > 0) {
			if (on) {
				velocity = -DefaultVelocity;
				setImageResourceId(R.drawable.andou_diagmore01);
			} else {
				velocity = DefaultVelocity;
				setImageResourceId(R.drawable.andou_diag01);
			}
		}
	}
	
	public void setInitialPosition(int x, int y) {
		defaultX = x;
		defaultY = y;
		this.x = defaultX;
		this.y = defaultY;
	}
	
	public void draw(Canvas c) {
		draw(c, defaultX, y);
		y += velocity;
		
		if(y < top) {
			y = top;
		} else if(y > bottom) {
			y = bottom;
		}
	}
}
