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
    
	/**
	 * 
	 */
	public AbstractGameObject(Context context, int resourceId, int width, int height) {
		drawableImg = context.getResources().getDrawable(resourceId);
        this.width = width;
        this.height = height;
	}

	public void draw(Canvas c, int x, int y) {
        drawableImg.setBounds(x, y, x + width, y + height);
        drawableImg.draw(c);
	}
}
