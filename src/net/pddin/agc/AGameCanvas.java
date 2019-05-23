package net.pddin.agc;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

/**
 * AGameCanvas �Ȃ�ׂ��ȒP��Android��2D�Q�[������邽�߂̃t���[�����[�N
 *
 * @author HARADA Toshinobu
 * @version 0.400 (H26/07/08)
 */

/*
 * ver 0.400 H26/07/08 �`��𑜓x�̒[���ˑ��̖��A���ɏc����̖����Ȃ�ׂ��C���B *
 * �𑜓x�����������ōs���悤�Ɏd�l��ύX�����B����ɂ��c����̖��͉������ꂽ�B *
 * ����ɂƂ��Ȃ��A�ݒ�𑜓x�̏c���̏k�ڗ����[���̉𑜓x�̏c���̏k�ڗ��ƈقȂ�ꍇ�ɂ͏c�̕`��ʒu�𒲐�����悤�ɂ����B *
 * ����āA�ݒ�𑜓x�̏c�������[���̏c���̕����傫���Ȃ�ꍇ�́A�㉺���؂�Ă��܂����ƂɂȂ�B * ��{�I��nexus7v2012�d�l�i800,
 * 1205�j�ō���Ă����Α傫�Ȗ��͖����B
 * AGameCanvas#gameDraw(Canvas)�𒊏ۃ��\�b�h�����ۃ��\�b�h�ɕύX�B�����I�[�o�[���C�h�����Ȃ��悤�ɂ����B *
 * �����Ƃ��ĕ`�揈���ɂ��Ă�AGameCanvas.gameDraw(Graphics)�ōs�����Ƃ𐄏��B
 */
/*
 * ver 0.302 H25/12/24 net.pddin.agc.geom�p�b�P�[�W��ǉ��B
 * net.pddin.agc.geom.Point2D�N���X��ǉ��B net.pddin.agc.geom.Line2D�N���X��ǉ��B *
 * Line2D#intersectsLine���\�b�h�ɂ����Ɛ��̌�������@�\�������B
 */
/*
 * ver 0.301 H25/12/12 gcGetTouchTrigger��TouchPos��null�`�F�b�N��ǉ��B�i�܂�ɗ�����s����������̂ŁB�j
 */
/*
 * ver 0.300 H25/11/04 �|�[�Y����������
 */
/*
 * ver 0.299 H25/10/04 �`��𑜓x��[���ˑ��ł͂Ȃ��w��ł���悤�ɂ����B�ڂ����̓h�L�������g�i�R���X�g���N�^������Q�ƁB�j�B
 * ����ɔ����^�b�`�����Ȃǂ��C���B GalaxyS3�̃^�b�`�L�����Z�������̋����������������Ƃ��m�F�B���[���ł͖��Ȃ��B
 * �\�[�X�R�[�h�̖��ł͂Ȃ��悤�Ɏv����̂ŁAGalaxyS3�̎d�l�ł���Ǝv�����ނ��Ƃɂ���B
 * �ꉞ�Ή���͍u���Ă���̂ŁA�������ȃ^�b�`�����Ȃ���Γ���Ɏx��͂Ȃ��B
 */
/*
 * ver 0.298 H25/03/07 �Ƃ肠�����d�l�͂��̂܂܂ɁA�\�[�X�R�[�h���v�`���t�@�N�^�����O�B
 *
 * �X�}�z���c�������Ў莝���ŗV�ׂ�Q�[������邽�߂̃t���[�����[�N�ł��B �[���̑Ή��o�[�W�����́A2,2.X�A2.3.X�A4.0.X�`�ł��B
 * ��ʃT�C�Y�T�|�[�g�́AP�͏c����PORTRAIT�AL�͉�����LANDSCAPE�B WVGA-P(��width 480, �cheight 800),
 * WVGA-L(��width 800, �cheight 480)
 *
 * WXGA-P(��width 720, �cheight 1280), WXGA-L(��width 1280, �cheight 720)
 *
 * nexus7(2012)�d�l�F��p�L�[������P��L�ƂňقȂ邽�߉�ʃT�C�Y���ϓ�����B��p�L�[�����̃^�b�`�͏����t���B WXGA-nP(��width
 * 800, �cheight 1205(1280)) WXGA-nL(��width 1280, �cheight 736( 800))
 *
 * nexus7(2013)�d�l�F��p�L�[������P��L�ƂňقȂ邽�߉�ʃT�C�Y���ϓ�����B��p�L�[�����̃^�b�`�͏����t���B WXGA-nP(��width
 * 1200, �cheight 1824(1920)) WXGA-nL(��width 1920, �cheight 1104(1200))
 *
 * �W������m�F�[���́A * SC-02B(GalaxyS ): 2.3.6: WVGA: �}���`�^�b�`��5 * SC-06D(GalaxyS3):
 * 4.1.2: WXGA: �}���`�^�b�`��10 * ME370T(Nexus7 2012): 4.2.2: WXGA: �}���`�^�b�`��10 *
 * Nexus7(Nexus7 2013): 4.3.0: : �}���`�^�b�`��10
 *
 * 2.1�ł��g����悤�ɂ��邽�߂ɂ́AonTouchEvent���� me.getActionIndex(); �� (me.getAction() &
 * MotionEvent.ACTION_POINTER_ID_MASK ) >> MotionEvent.ACTION_POINTER_ID_SHIFT ;
 * �ɂ���΂悢�B �ꉞ�������Ƃ�Xperia�Ŋm�F���܂����B
 */

public abstract class AGameCanvas extends SurfaceView implements
		SurfaceHolder.Callback, Runnable {
	/**
	 * WAIT_10
	 * 10.000000ms(10000000ns)�E�F�C�g�p�t�B�[���h�BFPS100�����B�i�X�y�b�N�̂��Ȃ荂�����@����Ȃ��Əo�܂���B�j
	 */
	public static final long WAIT_10 = 10000000L;
	/**
	 * WAIT_17
	 * 16.666667ms(16666667ns)�E�F�C�g�p�t�B�[���h�BFPS60�����B�i2011�N�t�ă��f�����炢����Ȃ炢���邩���B�j
	 */
	public static final long WAIT_17 = 16666667L;
	/** WAIT_20 20.000000ms(20000000ns)�E�F�C�g�p�t�B�[���h�BFPS50�����B */
	public static final long WAIT_20 = 20000000L;
	/** WAIT_30 30.000000ms(30000000ns)�E�F�C�g�p�t�B�[���h�BFPS33�����B�W���B */
	public static final long WAIT_30 = 30000000L;
	/** WAIT_50 50.000000ms(50000000ns)�E�F�C�g�p�t�B�[���h�BFPS20�����B���[�X�y�b�N���f���ɂ͂��ꂭ�炢�ŁB */
	public static final long WAIT_50 = 50000000L;

	private int fps; // FPS�i�t���[���p�[�Z�J���h�F�P�b���Ƃ̃t���[�����j
	private int frameCounter; // �t���[���J�E���g�p�ϐ�
	private long fpsCalcTime; // FPS�Z�o�p�ϐ�
	private long ideWaitTime; // �z��E�F�C�g�^�C��
	private long actWaitTime; // ���E�F�C�g�^�C��
	private long gameTime; // �Q�[�����s����
	private long firstTime; // �Q�[���J�n����

	// �Q�[���|�[�Y�i�ꎞ���f�j�����֘A
	private boolean pause;

	/**
	 * onPause �Q�[�����|�[�Y��Ԃɂ��郁�\�b�h
	 */
	public final void onPause() {
		this.setPause(true);
	}

	/**
	 * offPause �Q�[���̃|�[�Y��Ԃ��������郁�\�b�h
	 */
	public final void offPause() {
		this.setPause(false);
	}

	/**
	 * getPause �Q�[�������f��Ԃ��ǂ�����Ԃ����\�b�h
	 *
	 * @return �ʏ퓮���ԁifalse�j�A���f��ԁitrue�j
	 */
	public final boolean getPause() {
		return pause;
	}

	/**
	 * setPause �Q�[���𒆒f���邩�������邩��ݒ肷�郁�\�b�h
	 *
	 * @param pause
	 *            �itrue�Œ��f�Afalse�ŉ����j
	 */
	private final void setPause(boolean pause) {
		this.pause = pause;
	}

	// �`��𑜓x�����֘A
	private float rateW; // �����̒������[�g ������width / ��������widthA
	private float rateH; // �c���̒������[�g ���c��height / �����c��heightA
	private int widthA; // �����iAdjust�j����
	private int heightA; // �����iAdjust�j�c��
	private float offsetY; // �c�ʒu�����p

	/**
	 * getRateW �����̒������[�g��Ԃ����\�b�h
	 *
	 * @return ������width / ��������widthA �̒l��Ԃ�
	 */
	public final float getRateW() {
		return rateW;
	}

	/**
	 * getRateH �c���̒������[�g��Ԃ����\�b�h
	 *
	 * @return ���c��height / �����c��heightA �̒l��Ԃ�
	 */
	public final float getRateH() {
		return rateH;
	}

	/**
	 * getWidthA ����������Ԃ����\�b�h �R���X�g���N�^�ɂ���Ďw�肳�ꂽ�l����Z�o���ꂽ����������Ԃ��܂��B
	 * �[���̎��ۂ̉������~�����ꍇ��View#getWidth��p���܂��B
	 *
	 * @return ��������
	 */
	public final int getWidthA() {
		return widthA;
	}

	/**
	 * getHeightA �����c����Ԃ����\�b�h �R���X�g���N�^�ɂ���Ďw�肳�ꂽ�l����Z�o���ꂽ�����c����Ԃ��܂��B
	 * �[���̎��ۂ̏c�����~�����ꍇ��View#getHeight��p���܂��B
	 *
	 * @return �����c��
	 */
	public final int getHeightA() {
		return heightA;
	}

	/**
	 * getFPS ���b���Ƃ�FPS�l��Ԃ����\�b�h
	 *
	 * @return FPS ���b���Ƃ�FPS�l
	 */
	public final int getFPS() {
		return fps;
	}

	/**
	 * getWaitTime �z��E�F�C�g�^�C����Ԃ����\�b�h
	 *
	 * @return �z��E�F�C�g�^�C��
	 */
	public final long getWaitTime() {
		return ideWaitTime;
	}

	/**
	 * setWaitTime �E�F�C�g�^�C����ݒ肷�郁�\�b�h
	 *
	 * @param wait
	 *            �ݒ肵�����E�F�C�g�^�C���iWAIT_10, WAIT_17, WAIT_30, WAIT_50�j
	 */
	public final void setWaitTime(long wait) {
		if ((AGameCanvas.WAIT_10 <= wait) && (wait <= AGameCanvas.WAIT_50)) {
			this.ideWaitTime = wait;
		}
	}

	/**
	 * getGameTime �Q�[���J�n������̌o�ߎ��Ԃ�Ԃ����\�b�h return �Q�[���o�ߎ��ԁims�j
	 */
	public final long getGameTime() {
		return gameTime;
	}

	/**
	 * gcGetMediaPlayer �u\res\raw�v�ɕۑ�����ogg,midi���̉��y�f�[�^��R.raw.****�Ŏw�肵�A
	 * ����MediaPlayer�I�u�W�F�N�g���擾���郁�\�b�h
	 *
	 * @param res_raw_id
	 *            , agc(�����삵�Ă���agc���w�肷��)
	 * @return MediaPlayer�I�u�W�F�N�g
	 */
	public MediaPlayer gcGetMediaPlayer(int res_raw_id, AGameCanvas agc) {
		return MediaPlayer.create(agc.getContext(), res_raw_id);
	}

	/**
	 * gcGetBitmap �u\res\drawable-nodpi�v�ɕۑ�����png���̃��\�[�X�f�[�^��R.drawable.****�Ŏw�肵�A
	 * ����Bitmap�I�u�W�F�N�g���擾���郁�\�b�h
	 *
	 * @param res_drawable_id
	 *            , agc(�����삵�Ă���agc���w�肷��)
	 * @return Bitmap�I�u�W�F�N�g
	 */
	public Bitmap gcGetBitmap(int res_drawable_id, AGameCanvas agc) {
		return BitmapFactory.decodeResource(agc.getContext().getResources(),
				res_drawable_id);
	}

	/**
	 * gcGetImage �u\res\drawable-nodpi�v�ɕۑ�����png���̃��\�[�X�f�[�^��R.drawable.****�Ŏw�肵�A
	 * ����Image�I�u�W�F�N�g���擾���郁�\�b�h
	 *
	 * @param res_drawable_id
	 *            , agc(�����삵�Ă���agc���w�肷��)
	 * @return AGameCanvas�p��Image�I�u�W�F�N�g
	 */
	public Image gcGetImage(int res_drawable_id, AGameCanvas agc) {
		return new Image(gcGetBitmap(res_drawable_id, agc));
	}

	private SurfaceHolder holder;
	private Thread thread;
	private boolean threadFlag;

	/**
	 * �R���X�g���N�^ �i��ʂ̌����ݒ�A�`��𑜓x��������j ��P�����ɂ́AActivity�̃C���X�^���X���w�肷��i�ʏ��this�j�B
	 * ��Q�����ɂ́A��ʂ̌������w�肷��
	 * �B�iScreenOrientation.PORTRAIT�i�c�j�AScreenOrientation.LANDSCAPE�i���j�j�B
	 * ��R���S�����ɂ́A�w�肵���������������c�����w�肷��B�i�Ⴆ�΁A720, 1280�j�B
	 */
	@SuppressWarnings("deprecation")
	public AGameCanvas(Context context, ScreenOrientation so, int widthA,
			int heightA) {
		super(context);

		this.pause = false; // �Q�[���N�����͔񒆒f���

		// �`��𑜓x�����֘A
		Display display = ((Activity) context).getWindowManager()
				.getDefaultDisplay();
		int w = 0;
		w = display.getWidth();
		int h = 0;
		h = display.getHeight();
		// APILevel13(3.2)�ȏ�Ȃ牺�L�̏����őΉ��\�A��L��ApiLevel8(2.2)�őΉ��\�Ƃ��邽�߂��傤���Ȃ�
		// Point p = new Point();
		// display.getSize(p);
		// w = p.x;
		// h = p.y;

		if (widthA == 0) {
			widthA = w;
		}
		if (heightA == 0) {
			heightA = h;
		}

		this.widthA = widthA; // ���������̏�����
		this.heightA = heightA; // �����c���̏�����

		// �������c���ƁA���������c������A�������[�g���Z�o
		this.rateW = (float) w / (float) widthA;
		// this.rateH = (float)h / (float)heightA;
		this.rateH = this.rateW;

		// offsetY�̎Z�o
		this.offsetY = ((float) h - rateW * (float) this.heightA) * 0.5f;

		// �R���X�g���N�^
		// ���Q�[�����ԁCFPS�֘A�̏����̓T�[�t�F�X�����̃^�C�~���O�ɋL�q���Ă��܂��B

		// ���R�[���o�b�N�֘A�̐ݒ�
		holder = getHolder(); // SurfaceHolder�̎擾
		holder.addCallback(this); // �R�[���o�b�N�ǉ��B

		// ��ʂ̌����ݒ�
		// �{���́AAndroidManifest.xml���J���A�A�v���P�[�V�����^�u�́A
		// Application Nodes�ŁAActivity��I�сA
		// �E���ڂ�Screen orientation ��portrait��landscape�ɂ���B
		// ���̏����ŁA����xml�t�@�C���̐ݒ�̑�p�ƂȂ�B
		// �{���I�ɂ�xml�Őݒ肷�ׂ�����AGC�t���[�����[�N�ł͂������Ȃ��B
		if (so == ScreenOrientation.PORTRAIT) { // �c��ʌŒ胂�[�h
			((Activity) context)
					.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // �c��ʌŒ�
		} else if (so == ScreenOrientation.LANDSCAPE) { // ����ʌŒ胂�[�h
			((Activity) context)
					.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // ����ʌŒ�
		}
		((Activity) context).getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // �X���[�v�����Ȃ�
		((Activity) context).getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// �t���X�N���[���ɐݒ�
		((Activity) context).requestWindowFeature(Window.FEATURE_NO_TITLE);// �^�C�g����\���ɐݒ�
		gameInitBefore(); // �Q�[�����������O����
		onTouchEventInit(); // �^�b�`�C�x���g�����֘A�̏�����

	}

	/**
	 * �R���X�g���N�^�i��ʂ̌����ݒ肠��A�`��𑜓x�����Ȃ��j
	 */
	public AGameCanvas(Context context, ScreenOrientation so) {
		this(context, so, 0, 0);
	}

	/**
	 * �R���X�g���N�^ �i��ʂ̌����ݒ�Ȃ��i�c�Œ�iPORTRAIT�j�A�`��𑜓x�w�肠��j
	 */
	public AGameCanvas(Context context, int widthA, int heightA) {
		this(context, ScreenOrientation.PORTRAIT, widthA, heightA);
	}

	/**
	 * �R���X�g���N�^ �i��ʂ̌����ݒ�Ȃ��i�c�Œ�iPORTRAIT�j�j�A�`��𑜓x�w��Ȃ��j
	 */
	public AGameCanvas(Context context) {
		this(context, ScreenOrientation.PORTRAIT);
	}

	/** gameInitBefore gameInit�����O�ɌĂ΂Ȃ���΂Ȃ�Ȃ������͂��̃��\�b�h���I�[�o�[���C�h���ċL�q����B */
	protected void gameInitBefore() {
	}

	/** gameInit �Q�[���������������\�b�h */
	public abstract void gameInit();

	/** gameMainBefore gameMain�������O�ɌĂ΂Ȃ���΂Ȃ�Ȃ������͂��̃��\�b�h���I�[�o�[���C�h���ċL�q����B */
	protected void gameMainBefore() {
	}

	/** gameMain �Q�[�����C���������\�b�h */
	public abstract void gameMain();

	/** gameMainAfter gameMain��������ɌĂ΂Ȃ���΂Ȃ�Ȃ������͂��̃��\�b�h���I�[�o�[���C�h���ċL�q����B */
	protected void gameMainAfter() {
	}

	/**
	 * gameDraw �Q�[���`�揈�����\�b�h�B
	 * �ǂ����Ă�Android�W����Canvas��p�����`�揈�����L�q�������ꍇ�͂�������I�[�o�[���C�h���ď������������ĉ������B
	 * �������A�𑜓x�����Ȃǂ͑Ή����Ă��܂���B
	 * */
	public void gameDraw(Canvas canvas) {
	}

	/**
	 * gameDraw �Q�[���`�揈�����\�b�h
	 * AGameCanvas�ɂ͕`�揈�����L�q���邽�߂̃��\�b�h��������`���Ă���܂����A����������g�p���������B
	 * */
	public abstract void gameDraw(Graphics g);

	/** gameFinishBefore gameFinish�������O�ɌĂ΂Ȃ���΂Ȃ�Ȃ������͂��̃��\�b�h���I�[�o�[���C�h���ċL�q����B */
	protected void gameFinishBefore() {
	}

	/** gameFinish �Q�[���I�����������\�b�h */
	public abstract void gameFinish();

	public final void run() {
		// �X���b�h���s�O�̏����ݒ�
		gameTime = 0L;
		setWaitTime(AGameCanvas.WAIT_30); // �W���̃E�F�C�g�ɐݒ肷��B�i�e�o�r�R�R���x�B�j
		frameCounter = 0;
		fps = 1000000000 / (int) ideWaitTime;
		firstTime = System.nanoTime();
		fpsCalcTime = firstTime;

		this.pause = false; // �Q�[���͒ʏ��ԁi�񒆒f��ԁj

		gameInit();
		Canvas canvas = null;
		Graphics g = Graphics.getInstance();

		// �w�i�̐ݒ�
		Rect bgRect = new Rect(0, 0, this.getWidth(), this.getHeight());
		Paint bgPaint = new Paint(Color.BLACK);

		// ���f���̃}�X�N�̐ݒ�
		Paint pausePaint = new Paint(Color.BLACK);
		pausePaint.setAlpha(127); // ����ݒ�

		while (threadFlag) {
			long startTime = System.nanoTime(); // �}�C�t���[�������J�n�������擾

			// ���C�������i�ʏ��Ԃ̂Ƃ��̂ݍX�V���������s����j
			if (!pause) {
				gameMainBefore();
				gameMain();
				gameMainAfter();
			}

			canvas = holder.lockCanvas(); // �L�����o�X�̎擾�i���b�N�j
			// �`��
			if (canvas != null) {
				canvas.drawRect(bgRect, bgPaint);
				gameDraw(canvas);
				// �c�ʒu�̒����B
				canvas.translate(0.0f, offsetY);
				// ����ɂ��`��𑜓x�̒������\�ƂȂ�B
				canvas.scale(rateW, rateH);

				g.setCanvas(canvas, this); // �L�����o�X��Graphics�ɓn��
				gameDraw(g);
				// ���������`��g����������`��
				g.setColor(Color.DKGRAY);
				g.drawRect(1, 1, getWidthA() - 2, getHeightA() - 2);

				// ���f��Ԃł��`��͂�߂Ȃ��B�������኱�Â�����ˏ���ɈÂ��Ȃ�̂ŕs�v�ł����B�B
				/*
				 * if(pause) { canvas.drawRect(bgRect, pausePaint); }
				 */
				holder.unlockCanvasAndPost(canvas); // �L�����o�X�̊J���i�A�����b�N�j
			}

			// �Q�[���E�F�C�g�n����
			long frameTime = System.nanoTime() - startTime; // �P�t���[�������ɗv�������Ԃ��擾
			actWaitTime = ideWaitTime - frameTime; // ���҂����Ԃ̎Z�o
			if (actWaitTime < 0) {
				actWaitTime = 0; // �������҂����Ԃ��}�C�i�X�Ȃ�҂����Ɏ��̃t���[����
			}
			// ���҂����ԃE�F�C�g
			long sTime = System.nanoTime();
			while ((System.nanoTime() - sTime) < actWaitTime)
				;

			// ���݂̃Q�[�����Ԃ��Z�o
			gameTime = (System.nanoTime() - firstTime) / 1000000L;

			// �e�o�r�Z�o����
			frameCounter = frameCounter + 1;
			if ((System.nanoTime() - fpsCalcTime) > 1000000000L) {
				fps = frameCounter; // �e�o�r���X�V
				frameCounter = 0; // �t���[���J�E���^�[�����Z�b�g
				fpsCalcTime = System.nanoTime(); // FPS�Z�o�p���Ԃ����Z�b�g
			}
		}
		gameFinishBefore();
		gameFinish(); // �X���b�h���I������^�C�~���O��Finish���Ă�ł����B
	}

	// �T�[�t�F�X���`�F���W�����Ƃ��̑Ή��͂��Ȃ��B�\����Ȃ����ǁB
	public final void surfaceChanged(SurfaceHolder holder, int format,
			int width, int height) {
	}

	// �T�[�t�F�X���������ꂽ�Ƃ��ɃX���b�h�����s����悤�ɂ���B
	public final void surfaceCreated(SurfaceHolder holder) {
		threadFlag = true;
		thread = new Thread(this);
		thread.start();
	}

	// �T�[�t�F�X���폜���ꂽ�Ƃ��ɃX���b�h���I������悤�ɂ���B
	public final void surfaceDestroyed(SurfaceHolder holder) {
		threadFlag = false;
	}

	// �^�b�`�C�x���g�����p�̃t�B�[���h�̏����������p���\�b�h
	private final void onTouchEventInit() {
		// touchPosList�̎��̂�LinkedList����CopyOnWriteArrayList�ɕύX�B
		// touchPosList = new LinkedList<TouchPos>();
		touchPosList = new CopyOnWriteArrayList<TouchPos>();
		deleteTouchPosList = new CopyOnWriteArrayList<TouchPos>();
	}

	// �^�b�`�C�x���g�������ɌĂ΂�郁�\�b�honTouchEvent
	public final boolean onTouchEvent(MotionEvent me) {
		int pointerCount = me.getPointerCount();
		int action = me.getAction();
		int type = action & MotionEvent.ACTION_MASK;
		// ��ʂ��^�b�`���ꂽ�Ƃ��̏���
		// �^�b�`���ꂽ�|�W�V�����̒l��AGameCanvas.TouchPos�I�u�W�F�N�g�𐶐����AtouchPosList�Ɋi�[���܂��B
		// ACTION_DOWN�̓^�b�`����Ă��Ȃ���ԂŃ^�b�`���������ꍇ�AACTION_POINTER_DOWN�͂��łɈ�ȏ�^�b�`����Ă��ԂŃ^�b�`���������ꍇ
		if (type == MotionEvent.ACTION_DOWN
				|| type == MotionEvent.ACTION_POINTER_DOWN) {
			int i = me.getActionIndex();
			// int i = (me.getAction() & MotionEvent.ACTION_POINTER_ID_MASK ) >>
			// MotionEvent.ACTION_POINTER_ID_SHIFT ;

			int id = me.getPointerId(i);
			float x = me.getX(i);
			float y = me.getY(i);
			float xa = x / this.rateW;
			// float ya = y/this.rateH;
			float ya = (y - offsetY) / this.rateH;
			if (ya < 0.0f) {
				ya = 0.0f;
			}
			if (ya > this.getHeightA()) {
				ya = this.getHeightA() - 1;
			}
			float p = me.getPressure(i);
			touchPosList.add(new TouchPos(id, x, y, xa, ya, p));
		}
		// ��ʂɃ^�b�`����Ă���w���������Ƃ��̏���
		// �ړ����ꂽ�w�ɑΉ�����AGameCanvas.TouchPos�I�u�W�F�N�g�̍��W�l���A�ړ���̍��W�l�ɏ��������܂��B
		else if (type == MotionEvent.ACTION_MOVE) {
			for (int i = 0; i < pointerCount; i = i + 1) {
				int id = me.getPointerId(i);
				float x = me.getX(i);
				float y = me.getY(i);
				float xa = x / this.rateW;
				// float ya = y/this.rateH;
				float ya = (y - offsetY) / this.rateH;
				if (ya < 0.0f) {
					ya = 0.0f;
				}
				if (ya > this.getHeightA()) {
					ya = this.getHeightA() - 1;
				}
				float p = me.getPressure(i);
				for (TouchPos tp : touchPosList) {
					if (tp.id == id) {
						tp.x = x;
						tp.y = y;
						tp.xa = xa;
						tp.ya = ya;
						tp.p = p;
					}
				}
			}
		}
		// ��ʂɃ^�b�`����Ă���w�������ꂽ�Ƃ��̏���
		// �����ꂽ�w�ɑΉ�����AGameCanvas.TouchPos�I�u�W�F�N�g���AtouchPosList����폜���܂��B
		// ACTION_UP�͂���ɂ���Ĉ���^�b�`�������Ȃ�ꍇ�AACTION_POINTER_UP�͂��̃^�b�`�����ꂽ�Ƃ��ɂق��̃^�b�`���c��ꍇ
		// else if( type == MotionEvent.ACTION_UP || type ==
		// MotionEvent.ACTION_POINTER_UP ) {
		else if (type == MotionEvent.ACTION_POINTER_UP) {
			int i = me.getActionIndex();
			// int i = (me.getAction() & MotionEvent.ACTION_POINTER_ID_MASK ) >>
			// MotionEvent.ACTION_POINTER_ID_SHIFT ;
			// �폜�ΏۗpTouchPos�̊i�[�pList����������
			// List<TouchPos> dtpl = new LinkedList<TouchPos>();
			// List<TouchPos> dtpl = new CopyOnWriteArrayList<TouchPos>();
			deleteTouchPosList.clear();

			// �폜�Ώۂ�TouchPos�I�u�W�F�N�g�𒲂ׂ�
			int id = me.getPointerId(i);
			for (TouchPos t : touchPosList) {
				if (t.id == id) { // ��v����id�������
					deleteTouchPosList.add(t); // �폜�ΏۂƂ��Ċi�[����
				}
			}
			// �폜�ΏۗpTouchPos�̃��X�g���ATouchPosList����폜����B
			touchPosList.removeAll(deleteTouchPosList);

		} else if (type == MotionEvent.ACTION_UP) {
			touchPosList.clear();
		}
		// �^�b�`�L�����Z���Ή��i�������Ȃ��悤�ɂ���j
		else if (type == MotionEvent.ACTION_CANCEL) {
		}

		return true;
	}

	/**
	 * �w�肵��ID��TouchPos�����݂��邩�ǂ����i�w�肵��ID��TouchPos��������Ă��邩�ǂ����j
	 *
	 * @param id
	 * @return
	 */
	public final boolean isTouchPos(int id) {
		if (touchPosList.isEmpty()) {
			return false; // �^�b�`���X�g����Ȃ�ǂ�ID��������Ă�킯�Ȃ��̂�false
		}
		// �^�b�`���X�g����łȂ���΁A�`�F�b�N����
		for (TouchPos tp : touchPosList) {
			if (tp.id == id) { // �w�肵��ID�̃^�b�`�𔭌�������
				return true; // ����͉�����Ă��邱�Ƃ̏ؖ��Ȃ̂�true
			}
		}
		return false; // �����܂œ��B����Ƃ������Ƃ͉�����Ă��Ȃ��̂�false
	}

	/**
	 * �w�肵��ID��TouchPos�I�u�W�F�N�g��Ԃ��i������Ă��Ȃ��ꍇ��null�j
	 *
	 * @param id
	 * @return
	 */
	public final TouchPos getTouchPos(int id) {
		for (TouchPos tp : touchPosList) {
			if (tp.id == id) { // �w�肵��ID�̃^�b�`�𔭌�������
				return tp; // ����͉�����Ă��邱�Ƃ̏ؖ��Ȃ̂�true
			}
		}
		return null;
	}

	/**
	 * gcGetTouchState ID�ɂ�炸�A�w�肵���ꏊ��������Ă��邩�ǂ�����Ԃ�
	 *
	 * @param AGameCanvas
	 *            .Position.****
	 *
	 *            �i��ʍ���1/3�j �i��ʒ���1/3�j �i��ʉE��1/3�j
	 *            =============================================== �i��ʏ㕔1/3�j:
	 *            [LEFT_TOP ] [TOP ] [RIGHT_TOP ] | [TOP_SIDE]�Ŕ���� �i��ʒ���1/3�j:
	 *            [LEFT ] [CENTER ] [RIGHT ] | �i��ʉ���1/3�j: [LEFT_BOTTOM ] [BOTTOM
	 *            ] [RIGHT_BOTTOM ] | [BOTTOM_SIDE]�Ŕ����
	 *            -----------------------------------------------
	 *            [LEFT_SIDE]�Ŕ���� [RIGHT_SIDE]�Ŕ����
	 *
	 * @return �w�肵���ꏊ��������Ă��邩�ǂ���
	 */
	public final boolean gcGetTouchState(Position pos) {
		boolean flag = false;
		for (TouchPos tp : touchPosList) {
			flag = flag || gcGetTouchState(tp.id, pos);
		}
		return flag;
	}

	/**
	 * gcGetTouchState �w�肵��ID��TouchPos�ɂ��Ďw�肵���ꏊ��������Ă��邩�ǂ�����Ԃ�
	 *
	 * @param AGameCanvas
	 *            .Position.****
	 *
	 *            �i��ʍ���1/3�j �i��ʒ���1/3�j �i��ʉE��1/3�j
	 *            =============================================== �i��ʏ㕔1/3�j:
	 *            [LEFT_TOP ] [TOP ] [RIGHT_TOP ] | [TOP_SIDE]�Ŕ���� �i��ʒ���1/3�j:
	 *            [LEFT ] [CENTER ] [RIGHT ] | �i��ʉ���1/3�j: [LEFT_BOTTOM ] [BOTTOM
	 *            ] [RIGHT_BOTTOM ] | [BOTTOM_SIDE]�Ŕ����
	 *            -----------------------------------------------
	 *            [LEFT_SIDE]�Ŕ���� [RIGHT_SIDE]�Ŕ����
	 *
	 * @return �w�肵���ꏊ��������Ă��邩�ǂ���
	 */
	public final boolean gcGetTouchState(int id, Position pos) {
		TouchPos tp = getTouchPos(id);
		if (tp != null) {
			// �㕔
			if (0 <= tp.y && tp.y < this.getHeight() / 3) {
				if (0 <= tp.x && tp.x < this.getWidth() / 3) {
					if (pos == Position.LEFT_SIDE) {
						return true;
					}
					if (pos == Position.TOP_SIDE) {
						return true;
					}
					if (pos == Position.LEFT_TOP) {
						return true;
					}
				}
				if (this.getWidth() / 3 <= tp.x
						&& tp.x < this.getWidth() / 3 * 2) {
					if (pos == Position.TOP_SIDE) {
						return true;
					}
					if (pos == Position.TOP) {
						return true;
					}
				}
				if (this.getWidth() / 3 * 2 <= tp.x && tp.x < this.getWidth()) {
					if (pos == Position.RIGHT_SIDE) {
						return true;
					}
					if (pos == Position.TOP_SIDE) {
						return true;
					}
					if (pos == Position.RIGHT_TOP) {
						return true;
					}
				}
			}
			// �����i���C���C�E�j
			if (this.getHeight() / 3 <= tp.y && tp.y < this.getHeight() / 3 * 2) {
				if (0 <= tp.x && tp.x < this.getWidth() / 3) {
					if (pos == Position.LEFT_SIDE) {
						return true;
					}
					if (pos == Position.LEFT) {
						return true;
					}
				}
				if (this.getWidth() / 3 <= tp.x
						&& tp.x < this.getWidth() / 3 * 2) {
					if (pos == Position.CENTER) {
						return true;
					}
				}
				if (this.getWidth() / 3 * 2 <= tp.x && tp.x < this.getWidth()) {
					if (pos == Position.RIGHT_SIDE) {
						return true;
					}
					if (pos == Position.RIGHT) {
						return true;
					}
				}
			}
			// ����
			if (this.getHeight() / 3 * 2 <= tp.y && tp.y < this.getHeight()) {
				if (0 <= tp.x && tp.x < this.getWidth() / 3) {
					if (pos == Position.LEFT_SIDE) {
						return true;
					}
					if (pos == Position.BOTTOM_SIDE) {
						return true;
					}
					if (pos == Position.LEFT_BOTTOM) {
						return true;
					}
				}
				if (this.getWidth() / 3 <= tp.x
						&& tp.x < this.getWidth() / 3 * 2) {
					if (pos == Position.BOTTOM_SIDE) {
						return true;
					}
					if (pos == Position.BOTTOM) {
						return true;
					}
				}
				if (this.getWidth() / 3 * 2 <= tp.x && tp.x < this.getWidth()) {
					if (pos == Position.RIGHT_SIDE) {
						return true;
					}
					if (pos == Position.BOTTOM_SIDE) {
						return true;
					}
					if (pos == Position.RIGHT_BOTTOM) {
						return true;
					}
				}
			}
		}
		// TouchPos�������i������Ă��Ȃ���΁j���_false
		return false;
	}

	/**
	 * gcGetTouchState �w�肵��ID��TouchPos�����݂��邩 �i�ꏊ�ɂ�炸�ǂ�����������Ă��邩�j��Ԃ�
	 *
	 * @param id
	 * @return �w�肵��ID�ɂ���āA�ǂ�����������Ă��邩�ǂ���
	 */
	public final boolean gcGetTouchState(int id) {
		TouchPos tp = getTouchPos(id);
		if (tp != null) {
			return true; // �w�肳�ꂽID��TouchPos�I�u�W�F�N�g�������
		}
		return false; // �w�肳�ꂽID��TouchPos�I�u�W�F�N�g���Ȃ����
	}

	/**
	 * gcGetTouchState �Ƃɂ����ǂ�����������Ă��邩��Ԃ�
	 *
	 * @return �ǂ�����������Ă��邩�ǂ���
	 */
	public final boolean gcGetTouchState() {
		boolean flag = false;
		for (TouchPos tp : touchPosList) {
			flag = flag || gcGetTouchState(tp.id);
		}
		return flag;
	}

	/**
	 * gcGetTouchTrigger �w�肵��ID��TouchPos�����̂Ƃ��^�b�`���ꂽ�̂��ǂ�����Ԃ�
	 *
	 * @param id
	 * @return
	 */
	public final boolean gcGetTouchTrigger(int id) {
		boolean flag = this.gcGetTouchState(id);
		if (flag) {
			TouchPos tp = this.getTouchPos(id);
			if (tp != null) {
				long sTime = tp.sTime;
				if (System.nanoTime() - sTime <= this.getWaitTime()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * gcGetTouchTrigger �Ƃɂ����ǂ��������܃^�b�`���ꂽ�̂���Ԃ�
	 *
	 * @return
	 */
	public final boolean gcGetTouchTrigger() {
		boolean flag = false;
		for (TouchPos tp : touchPosList) {
			flag = flag || gcGetTouchTrigger(tp.id);
		}
		return flag;
	}

	/**
	 * gcGetTouchTrigger ID�ɂ�炸�A�w�肵���ꏊ�����̂Ƃ������ꂽ�̂��ǂ�����Ԃ�
	 *
	 * @param AGameCanvas
	 *            .Position.****
	 *
	 *            �i��ʍ���1/3�j �i��ʒ���1/3�j �i��ʉE��1/3�j
	 *            =============================================== �i��ʏ㕔1/3�j:
	 *            [LEFT_TOP ] [TOP ] [RIGHT_TOP ] | [TOP_SIDE]�Ŕ���� �i��ʒ���1/3�j:
	 *            [LEFT ] [CENTER ] [RIGHT ] | �i��ʉ���1/3�j: [LEFT_BOTTOM ] [BOTTOM
	 *            ] [RIGHT_BOTTOM ] | [BOTTOM_SIDE]�Ŕ����
	 *            -----------------------------------------------
	 *            [LEFT_SIDE]�Ŕ���� [RIGHT_SIDE]�Ŕ����
	 *
	 * @return �w�肵���ꏊ�����̃^�C�~���O�ŉ����ꂽ�̂��ǂ���
	 */
	public final boolean gcGetTouchTrigger(Position pos) {
		boolean flag = false;
		for (TouchPos tp : touchPosList) {
			flag = flag || gcGetTouchTrigger(tp.id, pos);
		}
		return flag;
	}

	/**
	 * gcGetTouchTrigger �w�肵��ID��TouchPos�ɂ��Ďw�肵���ꏊ�����̂Ƃ������ꂽ�̂��ǂ�����Ԃ�
	 *
	 * @param AGameCanvas
	 *            .Position.****
	 *
	 *            �i��ʍ���1/3�j �i��ʒ���1/3�j �i��ʉE��1/3�j
	 *            =============================================== �i��ʏ㕔1/3�j:
	 *            [LEFT_TOP ] [TOP ] [RIGHT_TOP ] | [TOP_SIDE]�Ŕ���� �i��ʒ���1/3�j:
	 *            [LEFT ] [CENTER ] [RIGHT ] | �i��ʉ���1/3�j: [LEFT_BOTTOM ] [BOTTOM
	 *            ] [RIGHT_BOTTOM ] | [BOTTOM_SIDE]�Ŕ����
	 *            -----------------------------------------------
	 *            [LEFT_SIDE]�Ŕ���� [RIGHT_SIDE]�Ŕ����
	 *
	 * @return �w�肵���ꏊ�����̃^�C�~���O�ŉ����ꂽ�̂��ǂ���
	 */
	public final boolean gcGetTouchTrigger(int id, Position pos) {
		boolean flag = gcGetTouchState(id, pos); // �Ƃ肠����������Ă���̂��ǂ���
		if (flag) { // ������Ă����ꍇ�ɁA���ꂪ�������ꂽ�̂��𒲂ׂ�
			TouchPos tp = this.getTouchPos(id);
			if (tp != null) {
				long sTime = tp.sTime;
				if (System.nanoTime() - sTime <= getWaitTime()) { // ���̃t���[�����ɉ�����Ă�����n�j
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * ���݂�TouchPos�I�u�W�F�N�g���i�[�������X�g��Ԃ�
	 *
	 * @return AGameCanvas�����ݕێ����Ă���TouchPos���i�[����List�I�u�W�F�N�g
	 */
	public List<TouchPos> getTouchPosList() {
		return touchPosList;
	}

	private List<TouchPos> touchPosList;
	private List<TouchPos> deleteTouchPosList;

	/** AGameCanvas.TouchPos AGameCanvas�ɂ����āA��ʂɃ^�b�`����Ă���|�W�V������\���N���X */
	public class TouchPos {
		/** TouchPos�I�u�W�F�N�g�̊Ǘ��pID�ł��B */
		private int id;

		/** TouchPos�I�u�W�F�N�g�̊Ǘ��pID��getter�ł��B */
		public int getId() {
			return id;
		}

		/** TouchPos�I�u�W�F�N�g�̂����W�l�ł��B */
		private float x;

		/** TouchPos�I�u�W�F�N�g�̂����W�l��getter�ł��B */
		public float getX() {
			return x;
		}

		/** TouchPos�I�u�W�F�N�g�̂����W�l�ł��B */
		private float y;

		/** TouchPos�I�u�W�F�N�g�̂����W�l��getter�ł��B */
		public float getY() {
			return y;
		}

		/** TouchPos�I�u�W�F�N�g�̕`�撲�������W�l�ł��B */
		private float xa;

		/** TouchPos�I�u�W�F�N�g�̕`�撲�������W�l��getter�ł��B */
		public float getXA() {
			return xa;
		}

		/** TouchPos�I�u�W�F�N�g�̕`�撲�������W�l�ł��B */
		private float ya;

		/** TouchPos�I�u�W�F�N�g�̕`�撲�������W�l��getter�ł��B */
		public float getYA() {
			return ya;
		}

		/** TouchPos�I�u�W�F�N�g�̉�����Ă��鋭���ipressure�j�ł��B�[���ɂ���Ă͌Œ�l�ɂȂ�܂��B */
		private float p;

		/** TouchPos�I�u�W�F�N�g�̉�����Ă��鋭���ipressure�j��getter�ł��B */
		public float getPressure() {
			return p;
		}

		/**
		 * TouchPos�I�u�W�F�N�g�̉�����Ă��鋭���ipressure�j��getter�ł��B�g�p�������Ă��܂��B
		 * getPressure���g�p���Ă��������B
		 */
		@Deprecated
		public float getP() {
			return getPressure();
		}

		/* TouchPos�I�u�W�F�N�g�̉�����Ă��鋭���ipressure�j��setter�ł��B */
		// public void setPressure(float p) { this.p = p; }
		// public void setP(float p) { setPressure(p); }

		/** TouchPos�I�u�W�F�N�g�̉����ꂽ����sTime�ins�j�ł��B */
		private long sTime;

		/** TouchPos�I�u�W�F�N�g���������ꂽ�����isTime�j��getter�ł��B */
		public float getSTime() {
			return sTime;
		}

		/** TouchPos�I�u�W�F�N�g���������ꂽ�����isTime�j��getter�ł��B�g�p�������Ă��܂��BgetSTime���g�p���Ă��������B */
		@Deprecated
		public float getST() {
			return getSTime();
		}

		/* TouchPos�I�u�W�F�N�g���������ꂽ�����isTime�j��setter�ł��B */
		// public void setSTime(long st) { this.sTime = st; }
		// public void setST(long st) { setSTime(st); }

		/**
		 * AGameCanvas.TouchPos �R���X�g���N�^ ��P�����ɂ́AID���w�肵�܂��B ��Q�����ɂ́A�����W�l���w�肵�܂��B
		 * ��R�����ɂ́A�����W�l���w�肵�܂��B ��S�����ɂ́A�`�撲�������W�l���w�肵�܂��B ��T�����ɂ́A�`�撲�������W�l���w�肵�܂��B
		 * ��U�����ɂ́A�������w�肵�܂��B
		 */
		public TouchPos(int id, float x, float y, float xa, float ya, float p) {
			this.id = id;
			this.x = x;
			this.y = y;
			this.xa = xa;
			this.ya = ya;
			this.p = p;
			this.sTime = System.nanoTime();
		}

		/** toString TouchPos�I�u�W�F�N�g�̕�����\���iID�Ax�Ay�j��Ԃ��܂��B */
		public String toString() {
			return "ID: " + id + " [X: " + (int) x + " ,Y: " + (int) y + " ]"
					+ " [xA: " + (int) xa + " ,yA: " + (int) ya + " ]";
		}
	}

	/**
	 * AGameCanvas.Position AGameCanvas�ɂ����āA�^�b�`����Ă���ꏊ��\�����߂̗񋓃N���X
	 */
	public enum Position {
		/** TOP_SIDE ��ʏ㕔��\���܂� */
		TOP_SIDE,
		/** RIGHT_SIDE ��ʉE����\���܂� */
		RIGHT_SIDE,
		/** BOTTOM_SIDE ��ʉ�����\���܂� */
		BOTTOM_SIDE,
		/** LEFT_SIDE ��ʍ�����\���܂� */
		LEFT_SIDE,
		/** CENTER ��ʒ�������\���܂� */
		CENTER,
		/** TOP ��ʁA�㒆����\���܂� */
		TOP,
		/** RIGHT_TOP ��ʁA�E���\���܂� */
		RIGHT_TOP,
		/** RIGHT ��ʁA�E������\���܂� */
		RIGHT,
		/** RIGHT_BOTTOM ��ʁA�E������\���܂� */
		RIGHT_BOTTOM,
		/** BOTTOM ��ʁA��������\���܂� */
		BOTTOM,
		/** LEFT_BOTTOM ��ʁA������\���܂� */
		LEFT_BOTTOM,
		/** LEFT ��ʁA��������\���܂� */
		LEFT,
		/** LEFT_TOP ��ʁA�����\���܂� */
		LEFT_TOP,
	}

	/**
	 * AGameCanvas.ScreenOrientation
	 * AGameCanvas�ɂ����āA�c�����iPORTRAIT�j���������iLANDSCAPE�j����ݒ肷�邽�߂̗񋓃N���X
	 */
	public enum ScreenOrientation {
		/** �c�����ݒ� */
		PORTRAIT,
		/** �������ݒ� */
		LANDSCAPE,
	}

	/**
	 * �����Ŏw�肵�����b�Z�[�W�t���̃_�C�A���O��\�����܂��B
	 *
	 * @param message
	 *            �\�����������b�Z�[�W���w�肵�܂��B
	 */
	public void showMessageDialog(final String message) {
		showMessageDialog(message, "Back to the Game.");
	}

	/**
	 * �����Ŏw�肵�����b�Z�[�W�t���̃_�C�A���O��\�����܂��B
	 *
	 * @param message
	 *            �\�����������b�Z�[�W���w�肵�܂��B
	 * @param backMessage
	 *            �Q�[���ɖ߂邽�߂̃{�^���ɕ\������郁�b�Z�[�W���w�肵�܂��B
	 */
	public void showMessageDialog(final String message, final String backMessage) {
		onPause();
		final AgcActivity activity = (AgcActivity) this.getContext();
		activity.postRunnable(new Runnable() {
			@Override
			public void run() {
				AlertDialog.Builder adb = new AlertDialog.Builder(activity);
				adb.setTitle(message);
				adb.setCancelable(false); // �A���[�g�_�C�A���O����o�b�N�L�[�̏�����r��
				adb.setNegativeButton(backMessage,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								offPause();
							}
						});
				AlertDialog ad = adb.create(); // �_�C�A���O�𐶐�
				ad.show(); // �_�C�A���O�̕\��
			}
		});
	}

	private String inputDialogText = "";

	/**
	 * ���b�Z�[�W�t�����͗p�_�C�A���OshowInputDialog�ɂ���ē��͂��ꂽ�e�L�X�g��Ԃ��܂��B
	 *
	 * @return ���b�Z�[�W�t�����͗p�_�C�A���OshowInputDialog�ɂ���ē��͂��ꂽ�e�L�X�g��Ԃ��܂��B
	 */
	public String getInputDialogText() {
		return inputDialogText;
	}

	/**
	 * ���b�Z�[�W�t�����͗p�_�C�A���O��\�����܂��B ���̓_�C�A���O�̃e�L�X�g��inputDialogText�ɕۑ�����܂��B
	 * ���������͂܂���Yes�ɑ�������{�^���������ꂽ�ꍇ�̓e�L�X�g�{�b�N�X�̕����񂪂��̂܂ܓ���܂��B
	 * �s���ȓ��͂܂���No�ɑ�������{�^���������ꂽ�ꍇ�͋󕶎���""������܂��B getInputDialogText�Ŏ擾�ł��܂��B
	 *
	 * @param message
	 *            ���͂𑣂����b�Z�[�W���w�肵�܂��B
	 * @param yesMessage
	 *            Yes�̃{�^���ɕ\������郁�b�Z�[�W���w�肵�܂��B
	 * @param noMessage
	 *            No�̃{�^���ɕ\������郁�b�Z�[�W���w�肵�܂��B
	 */
	public void showInputDialog(final String message, final String yesMessage,
			final String noMessage) {
		onPause();
		final AgcActivity activity = (AgcActivity) this.getContext();
		// final EditText et = new EditText(activity);
		activity.postRunnable(new Runnable() {
			@Override
			public void run() {
				AlertDialog.Builder adb = new AlertDialog.Builder(activity);
				adb.setTitle(message);
				final EditText et = new EditText(activity);
				adb.setView(et);
				adb.setCancelable(false); // �A���[�g�_�C�A���O����o�b�N�L�[�̏�����r��
				adb.setPositiveButton(yesMessage,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								AGameCanvas.this.inputDialogText = et.getText()
										.toString();
								offPause();
							}
						});
				adb.setNegativeButton(noMessage,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								AGameCanvas.this.inputDialogText = "";
								offPause();
							}
						});
				AlertDialog ad = adb.create(); // �_�C�A���O�𐶐�
				ad.show(); // �_�C�A���O�̕\��
			}
		});
	}

	/**
	 * ���b�Z�[�W�t�����͗p�_�C�A���O��\�����܂��B ���̓_�C�A���O�̃e�L�X�g��inputDialogText�ɕۑ�����܂��B
	 * ���������͂܂���Yes�ɑ�������{�^���������ꂽ�ꍇ�̓e�L�X�g�{�b�N�X�̕����񂪂��̂܂ܓ���܂��B
	 * �s���ȓ��͂܂���No�ɑ�������{�^���������ꂽ�ꍇ�͋󕶎���""������܂��B getInputDialogText�Ŏ擾�ł��܂��B
	 *
	 * @param message
	 *            ���͂𑣂����b�Z�[�W���w�肵�܂��B
	 */
	public void showInputDialog(String message) {
		showInputDialog(message, "YES", "NO");
	}
}
