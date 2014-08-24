package jp.techinstitute.flyingdroid;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	MediaPlayer mediaPlayer;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

	@Override
	protected void onPause() {
		super.onPause();
		mediaPlayer.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mediaPlayer.start();
		mediaPlayer.setLooping(true);
	}

	@Override
	protected void onStart() {
		super.onStart();
		mediaPlayer = MediaPlayer.create(this, R.raw.boss);
	}

	@Override
	protected void onStop() {
		super.onStop();
		mediaPlayer.stop();
		mediaPlayer.release();
	}
    
    
}
