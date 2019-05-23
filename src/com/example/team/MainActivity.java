package com.example.team;

import net.pddin.agc.AGameCanvas;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.VideoView;

public class MainActivity extends Activity {

	AGameCanvas agc;
	VideoView videoView;
	Handler handler = new Handler();

	public void postRunnable(final Runnable run) {
		handler.post(run);
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FrameLayout fl = new FrameLayout(this);
		videoView = new VideoView(this);
		agc = new Main(this, videoView);
		fl.addView(agc);
		videoView.setVideoURI(Uri.parse("android.resource://"
				+ getPackageName() + "/" + R.raw.demo));
		FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		flp.gravity = Gravity.CENTER;
		fl.addView(videoView, flp);
		videoView.setZOrderOnTop(true);
		videoView.setVisibility(View.VISIBLE);
		setContentView(fl);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

}
