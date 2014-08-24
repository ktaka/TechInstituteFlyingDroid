/**
 * 
 */
package jp.techinstitute.flyingdroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

/**
 * @author ktaka
 *
 */
public abstract class AbstractGameObject {
	protected Drawable drawableImg;
    protected int width;
    protected int height;
    protected int x;
    protected int y;
    protected int left; 
    protected int top;
    protected int right;
    protected int bottom;
    protected double radius;
    protected Context context;
    
	/**
	 * 
	 */
	public AbstractGameObject(Context context, int resourceId, int width, int height) {
		this.context = context;
		drawableImg = context.getResources().getDrawable(resourceId);
        this.width = width;
        this.height = height;
        radius = width * 0.5;
	}
	
	public void setImageResourceId(int resourceId) {
		drawableImg = context.getResources().getDrawable(resourceId);
	}
	
	public void setMovingBoundary(int left, int top, int right, int bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}

	public void draw(Canvas c, int x, int y) {
        drawableImg.setBounds(x, y, x + width, y + height);
        drawableImg.draw(c);
	}
	
	public boolean isHit(AbstractGameObject obj) {
		double xlen = (x + radius) - (obj.x + obj.radius);
		double ylen = (y + radius) - (obj.y + obj.radius);
		double len = Math.sqrt((xlen * xlen) + (ylen * ylen));
		double radiusSum = radius + obj.radius;
		if (len <= radiusSum) {
			return true;
		} else {
			return false;
		}
	}
}
