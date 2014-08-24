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
public class Enemy extends AbstractGameObject {

	/**
	 * @param context
	 * @param resourceId
	 * @param width
	 * @param height
	 */
	public Enemy(Context context, int width, int height) {
		super(context, R.drawable.enemy_pinkdude_jump, width, height);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setMovingBoundary(int left, int top, int right, int bottom) {
		super.setMovingBoundary(left, top, right, bottom);
		left -= width;
		x = right;
		y = 300;
	}

	public void draw(Canvas c) {
		draw(c, x, y);
		x -= 5;
		if(x < left) {
			x = right;
		}
	}

}
