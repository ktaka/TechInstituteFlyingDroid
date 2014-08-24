/**
 * 
 */
package jp.techinstitute.flyingdroid;

import android.content.Context;

/**
 * @author ktaka
 *
 */
public class Droid extends AbstractGameObject {

	/**
	 * @param context
	 * @param resourceId
	 * @param width
	 * @param height
	 */
	public Droid(Context context, int width, int height) {
		super(context, R.drawable.andou_diag01, width, height);
	}

}
