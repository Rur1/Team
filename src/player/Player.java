package player;

import java.util.List;

import lib.ImageManager;
import lib.Sound;
import net.pddin.agc.AGameCanvas;
import net.pddin.agc.Graphics;
import net.pddin.agc.Graphics.Font;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Surface;

import com.example.team.R;

public class Player implements SensorEventListener {

	AGameCanvas agc;
	ImageManager imageManager;
	Sound sound;
	int hp, alpha;
	float x, y, sy;
	boolean effect;

	float roll;
	float[] magneticValues;
	float[] accelerometerValues;
	float[] orientationValues;
	SensorManager sm;

	public Player(AGameCanvas agc, ImageManager imageManager, Sound sound) {
		this.agc = agc;
		this.imageManager = imageManager;
		this.sound = sound;
	}

	public void initialize() {
		imageManager.load("playerBack", R.drawable.playerhpback);
		imageManager.load("playerHP", R.drawable.playerhp);
		hp = 20;
		alpha = 255;
		x = agc.getWidthA() / 2 - 50;
		y = agc.getHeightA() - 120;
		sy = 0;
		effect = false;

		roll = 0.0f;

		sm = (SensorManager) (agc.getContext()
				.getSystemService(Context.SENSOR_SERVICE));
		List<Sensor> sensors = sm.getSensorList(Sensor.TYPE_ALL);
		for (Sensor s : sensors) {
			if (s.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
				sm.registerListener(this, s, SensorManager.SENSOR_DELAY_GAME);
			}
			if (s.getType() == Sensor.TYPE_ACCELEROMETER) {
				sm.registerListener(this, s, SensorManager.SENSOR_DELAY_GAME);
			}
		}
	}

	public void update() {
		if (effect) {
			alpha -= 51;
		}
		if (alpha <= 0) {
			effect = false;
			alpha = 255;
		}
	}

	public void draw(Graphics g) {
		if (effect) {
			g.setColor(Color.RED);
			g.fillRect(0, 0, 800, 1205, alpha);
		}

		g.setColor(Color.WHITE);
		g.setFontSize(Font.SIZE_LARGE);
		g.drawString("PlayerHP:" + hp, 10, 1100);
		g.drawScaledImage(imageManager.getImage("playerBack"), x,
				agc.getHeightA() - 120, 100, 100, 0, 0, 100, 100);
		g.drawScaledImage(imageManager.getImage("playerHP"), x, y, 100, 100, 0,
				sy, 100, 100);
	}

	public void finish() {
		imageManager.finish("playerBack");
		imageManager.finish("playerHP");
		sm.unregisterListener(this);
	}

	public int getHp() {
		return hp;
	}

	public void addHp(int num) {
		sound.play("damage", false, true);
		effect = true;
		if (hp > 0) {
			hp += num;
			sy += num * -5;
			y += num * -5;
		}
	}

	public float getRoll() {
		return roll;
	}

	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
			magneticValues = event.values.clone();
		}
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			accelerometerValues = event.values.clone();
		}

		if (magneticValues != null && accelerometerValues != null) {
			float[] R = new float[16];
			float[] inclination = new float[16];
			SensorManager.getRotationMatrix(R, inclination,
					accelerometerValues, magneticValues);
			float[] rmR = new float[16];
			orientationValues = new float[3];
			int dispDir = ((Activity) agc.getContext()).getWindowManager()
					.getDefaultDisplay().getRotation();
			if (dispDir == Surface.ROTATION_0) {
				rmR = R;
			}
			if (dispDir == Surface.ROTATION_90) {
				SensorManager.remapCoordinateSystem(R,
						SensorManager.AXIS_MINUS_Y, SensorManager.AXIS_MINUS_X,
						rmR);
			}
			if (dispDir == Surface.ROTATION_180) {
				float[] rmRB = new float[16];
				SensorManager.remapCoordinateSystem(R, SensorManager.AXIS_Y,
						SensorManager.AXIS_MINUS_X, rmRB);
				SensorManager.remapCoordinateSystem(rmRB, SensorManager.AXIS_Y,
						SensorManager.AXIS_MINUS_X, rmR);
			}
			if (dispDir == Surface.ROTATION_270) {
				SensorManager.remapCoordinateSystem(R,
						SensorManager.AXIS_MINUS_Y, SensorManager.AXIS_X, rmR);
			}
			SensorManager.getOrientation(rmR, orientationValues);
			roll = (float) Math.toDegrees(orientationValues[2]);
		}
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

}
