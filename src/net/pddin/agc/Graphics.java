package net.pddin.agc;

import net.pddin.agc.geom.Point2D;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;

/**
 * AGameCanvas��pGraphics�N���X�iAndroid�W����Canvas�N���X�����Paint�N���X���܂������̂ł��B�j
 *
 * @author HARADA Toshinobu
 * @version 0.29 (H23/06/10)
 */
/*
 * H26/07/19 �e�`�惁�\�b�h�̈����Ŏw�肷����W��int����float�ɕύX�B H26/07/18
 * drawImage���\�b�h�̊p�x�w��ł̎d�l��ύX�B�`�摬�x����̂��߁B H26/07/18 drawPolyline���\�b�h������ H26/01/24
 * drawHole���\�b�h�i�l�p�`�ɉ~�`�̌����������}�`��`�悷��@�\�j������ H26/01/24 drawOval/fillOval���\�b�h������
 * H26/01/24 drawRoundRect/fillRoundRect���\�b�h������ H25/11/06 drawLine���\�b�h������
 * H25/10/24 setFontTypeface���\�b�h�i�t�H���g�ݒ�@�\�j������
 */
public class Graphics {
	public static class Font {
		public static final float SIZE_TINY = 12.0f;
		public static final float SIZE_SMALL = SIZE_TINY * 1.5f;
		public static final float SIZE_MEDIUM = SIZE_TINY * 2.0f;
		public static final float SIZE_LARGE = SIZE_TINY * 2.5f;
	}

	private static Graphics GRAPHICS = new Graphics();
	private Canvas canvas;
	private Paint paint;
	// private Typeface typeface;
	private AGameCanvas agc;
	private float defaultTextSize;
	private Matrix matrix; // ��]�Ȃǂɗp����s��
	private Path path; //

	/**
	 * AGC�ł�Graphics�I�u�W�F�N�g�̓R���X�g���N�^�ł͐����o���܂���B getInstance��p���Ď擾���܂��B
	 */
	private Graphics() {
		paint = new Paint();
		paint.setTypeface(null); // �t�H���g���f�t�H���g�ɐݒ�
		defaultTextSize = paint.getTextSize();

		matrix = new Matrix();
		matrix.reset();

		path = new Path();
		path.reset();
	}

	/**
	 * * getInstance �B��̕`��I�u�W�F�N�g��Ԃ��܂��B
	 *
	 * @return GRPAHICS
	 */
	public static Graphics getInstance() {
		return Graphics.GRAPHICS;
	}

	/**
	 * setCanvas �`��֘A�����̑O��canvas���󂯎��܂��B
	 *
	 * @param canvas
	 * @return �w�肳�ꂽ�L�����o�X�����݂��Ă������ǂ���
	 */
	public boolean setCanvas(Canvas canvas, AGameCanvas agc) {
		boolean flag = false;
		if (canvas != null && agc != null) {
			this.canvas = canvas;
			this.agc = agc;
			flag = true;
		}
		return flag;
	}

	/**
	 * setColor �`��Ώۂɂ��Ă̐F��ݒ肵�܂��B
	 *
	 * @param color
	 */
	public void setColor(int color) {
		paint.setColor(color);
	}

	/**
	 * setColor �`��Ώۂɂ��āA�F���i�ԁC�΁C�̎O�̐��l�i0�`255�j�Łj�ݒ肵�܂��B���l���͈͂𒴂����ꍇ�ɂǂ��Ȃ邩�͂킩��܂���B
	 *
	 * @param red
	 *            , green, blue
	 */
	public void setColor(int red, int green, int blue) {
		paint.setColor(Color.rgb(red, green, blue));
	}

	/**
	 * setAlpha �`��Ώۂɂ��āA���l��0�`255�͈̔͂Őݒ肵�܂��B���l���͈͂𒴂����ꍇ�ɂǂ��Ȃ邩�͂킩��܂���B
	 *
	 * @param alpha
	 */
	public void setAlpha(int alpha) {
		paint.setAlpha(alpha);
	}

	/**
	 * getAlpha ���ݐݒ肳��Ă��郿�l��Ԃ��܂��B
	 *
	 * @return alpha
	 */
	public int getAlpha() {
		return paint.getAlpha();
	}

	/**
	 * getColorOfName �����Ŏw�肵���J���[�ɑΉ����鐔�l��Ԃ��܂��B
	 * �icom.nttdocomo.ui.Graphics�ɍ��킹��`�łƂ肠������`�������̂ł��B�j
	 *
	 * @param color
	 * @return color
	 */
	public static int getColorOfName(int color) {
		return color;
	}

	/**
	 * setFontSize �����Ŏw�肵���t�H���g�T�C�Y�ɐݒ肵�܂��B �t�H���g�T�C�Y�̐ݒ��String(Text)�`��ɑ΂��Ă̂ݗL���ł��B
	 * �T�C�Y�͐��l�ł��w��\�ł��B
	 *
	 * @param size
	 *            �iGraphics.Font.SIZE_TINY, .SIZE_SMALL, SIZE_MEDIUM,
	 *            SIZE_LARGE�j
	 */
	public void setFontSize(float size) {
		paint.setTextSize(size);
	}

	/**
	 * setDefaultFontSize �f�t�H���g�̃t�H���g�T�C�Y�ɐݒ肵�܂��B
	 * �t�H���g�T�C�Y�̐ݒ��String(Text)�`��ɑ΂��Ă̂ݗL���ł��B
	 */
	public void setDefaultFontSize() {
		paint.setTextSize(defaultTextSize);
	}

	/**
	 * getFontSizse ���ݐݒ肳��Ă���t�H���g�T�C�Y��Ԃ��܂��B
	 *
	 * @return
	 */
	public float getFontSize() {
		return paint.getTextSize();
	}

	/**
	 * setFontTypeface �����Ŏw�肵���t�H���g�ɐݒ肵�܂��B �������t�H���g�t�@�C����assets�t�H���_�ɒu���Ă����K�v������܂��B
	 * �i���Ӂj �t�H���g�t�@�C���͕K���m�F�̂����z�z�\�Ȃ��̂��g�p���Ă��������B �����֌W�Ŕ����������ɂ��Ă��̃R�[�h�̐���҂͐ӔC�𕉂��܂���B
	 *
	 * @param path
	 */
	public void setFontTypeface(String path) {
		Typeface typeface = Typeface.createFromAsset(agc.getContext()
				.getAssets(), path);
		paint.setTypeface(typeface);
	}

	/**
	 * drawLine �����Ŏw�肵��x1,y1�`x2,y2�܂ł̒�����`�悵�܂��B
	 *
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void drawLine(float x1, float y1, float x2, float y2) {
		paint.setStrokeWidth(5); // ���̕���5
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawLine(x1, y1, x2, y2, paint);
	}

	/**
	 * drawPolyline count�̎w�肳�ꂽ�_�����Ԑ�����`�悵�܂��B
	 *
	 * @param xPoints
	 * @param yPoints
	 * @param count
	 */
	public void drawPolyline(int[] xPoints, int[] yPoints, int count) {
		for (int i = 0; i < count - 1; i = i + 1) {
			drawLine(xPoints[i], yPoints[i], xPoints[i + 1], yPoints[i + 1]);
		}
	}

	/**
	 * drawPolyline count�̎w�肳�ꂽ�_�����Ԑ�����`�悵�܂��B
	 *
	 * @param xPoints
	 * @param yPoints
	 * @param count
	 */
	public void drawPolyline(float[] xPoints, float[] yPoints, int count) {
		for (int i = 0; i < count - 1; i = i + 1) {
			drawLine(xPoints[i], yPoints[i], xPoints[i + 1], yPoints[i + 1]);
		}
	}

	/**
	 * drawPolyline points�ɂ���Ďw�肳�ꂽ�_�����ԂɌ��Ԑ�����`�悵�܂��B
	 *
	 * @param points
	 */
	public void drawPolyline(Point2D[] points) {
		for (int i = 0; i < points.length - 1; i = i + 1) {
			drawLine(points[i].getX(), points[i].getY(), points[i + 1].getX(),
					points[i + 1].getY());
		}
	}

	/**
	 * drawString �����Ŏw�肵����������w�肵�����W����ɕ`�悵�܂��B
	 *
	 * @param text
	 * @param x
	 * @param y
	 */
	public void drawString(String text, float x, float y) {
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		canvas.drawText(text, x, y, paint);
		paint.setAntiAlias(false);
	}

	/**
	 * drawStringCentering �����Ŏw�肵����������w�肵�����W����ɒ��������ĕ`�悵�܂��B
	 *
	 * @param text
	 * @param cx
	 * @param cy
	 */
	public void drawStringCentering(String text, float cx, float cy) {
		drawString(text, cx - paint.measureText(text) / 2, cy);
	}

	/**
	 * drawHole �����Ŏw�肵��x,y����ɕ�w����h�̓h��Ԃ��l�p�`����A
	 * ����_��hx,hy�Ƃ�������hw�c��hh�̉~�����蔲�������̂�`�悵�܂��B
	 *
	 * @param x
	 *            , y, w, h, hx ,hy, hw, hh
	 */
	public void drawHole(float x, float y, float w, float h, float hx,
			float hy, float hw, float hh) {
		path.reset();
		path.addRect(new RectF(x, y, x + w, y + h), Direction.CW);
		path.addOval(new RectF(hx, hy, hx + hw, hy + hh), Direction.CCW);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(1); // ���̕��͂P
		// paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawPath(path, paint);
		paint.setAntiAlias(false);
	}

	/**
	 * drawHole �����Ŏw�肵��x,y����ɕ�w����h�̓h��Ԃ��l�p�`����A
	 * ���S�_��hcx,hcy�Ƃ������ar�̉~�����蔲�������̂�`�悵�܂��B
	 *
	 * @param x
	 *            , y, w, h, hcx ,hcy, r
	 */
	public void drawHole(float x, float y, float w, float h, float hcx,
			float hcy, float r) {
		this.drawHole(x, y, w, h, hcx - r, hcy - r, r * 2, r * 2);
	}

	/**
	 * drawRect �����Ŏw�肵��x,y����ɕ�w����h�̎l�p�`�̘g��`�悵�܂��B
	 *
	 * @param x
	 *            , y, w, h
	 */
	public void drawRect(float x, float y, float w, float h) {
		paint.setStrokeWidth(1); // ���̕��͂P
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(x, y, x + w, y + h, paint);
	}

	/**
	 * fillRect �����Ŏw�肵��x,y����ɕ�w����h�̎l�p�`��h��Ԃ��`�悵�܂��B
	 *
	 * @param x
	 *            , y, w, h
	 */
	public void fillRect(float x, float y, float w, float h) {
		paint.setStrokeWidth(1); // ���̕��͂P
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		canvas.drawRect(x, y, x + w, y + h, paint);
	}

	public void fillRect(float x, float y, float w, float h, int alpha) {
		paint.setAlpha(alpha);
		paint.setStrokeWidth(1); // ���̕��͂P
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		canvas.drawRect(x, y, x + w, y + h, paint);
	}

	/**
	 * drawRoundRect �����Ŏw�肵��x,y����ɕ�w����h�̎l�p�`�̊p����rx�cry�Ŋۂ������p�ێl�p�`�̘g��`�悵�܂��B
	 *
	 * @param x
	 *            , y, w, h, rx, ry
	 */
	public void drawRoundRect(float x, float y, float w, float h, float rx,
			float ry) {
		paint.setStrokeWidth(1); // ���̕��͂P
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawRoundRect(new RectF(x, y, x + w, y + h), rx, ry, paint);
	}

	/**
	 * fillRoundRect �����Ŏw�肵��x,y����ɕ�w����h�̎l�p�`�̊p����rx�cry�Ŋۂ������p�ێl�p�`��h��Ԃ��`�悵�܂��B
	 *
	 * @param x
	 *            , y, w, h, rx, ry
	 */
	public void fillRoundRect(float x, float y, float w, float h, float rx,
			float ry) {
		paint.setStrokeWidth(1); // ���̕��͂P
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		canvas.drawRoundRect(new RectF(x, y, x + w, y + h), rx, ry, paint);
	}

	/**
	 * drawOval �����Ŏw�肵��x,y������_�Ƃ�����w����h�̎l�p�`�ɓ��ڂ���ȉ~��`�悵�܂��B
	 *
	 * @param x
	 *            , y, w, h
	 */
	public void drawOval(float x, float y, float w, float h) {
		paint.setAntiAlias(true);
		paint.setStrokeWidth(1); // ���̕��͂P
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawOval(new RectF(x, y, x + w, y + h), paint);
		paint.setAntiAlias(false);
	}

	/**
	 * fillOval �����Ŏw�肵��x,y������_�Ƃ�����w����h�̎l�p�`�ɓ��ڂ���ȉ~��h��Ԃ��`�悵�܂��B
	 *
	 * @param x
	 *            , y, w, h
	 */
	public void fillOval(float x, float y, float w, float h) {
		paint.setAntiAlias(true);
		paint.setStrokeWidth(1); // ���̕��͂P
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		canvas.drawOval(new RectF(x, y, x + w, y + h), paint);
		paint.setAntiAlias(false);
	}

	/**
	 * drawArc �����Ŏw�肵��x,y������_�Ƃ�����w����h�̎l�p�`�ɓ��ڂ���~�̌ʂ�sa�����agl�x���`�悵�܂��B
	 *
	 * @param x
	 *            , y, w, h, sa, agl
	 */
	public void drawArc(float x, float y, float w, float h, float sa, float agl) {
		paint.setAntiAlias(true);
		paint.setStrokeWidth(1); // ���̕��͂P
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawArc(new RectF(x, y, x + w, y + h), sa, -agl, false, paint);
		paint.setAntiAlias(false);
	}

	/**
	 * fillArc �����Ŏw�肵��x,y������_�Ƃ�����w����h�̎l�p�`�ɓ��ڂ���~�̌ʂ�sa�����agl�x���h��Ԃ��`�悵�܂��B
	 *
	 * @param x
	 *            , y, w, h, sa, agl
	 */
	public void fillArc(float x, float y, float w, float h, float sa, float agl) {
		paint.setAntiAlias(true);
		paint.setStrokeWidth(1); // ���̕��͂P
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		canvas.drawArc(new RectF(x, y, x + w, y + h), sa, -agl, true, paint);
		paint.setAntiAlias(false);
	}

	/**
	 * drawImage �w�肵���C���[�W���Q�����A�t�B���ϊ��������ĕ`�悵�܂��B�i�������ł��B�j
	 *
	 * @param image
	 * @param matrix
	 */
	public void drawImage(Image image, Matrix matrix) {
		if (canvas != null && image != null && matrix != null) {
			canvas.drawBitmap(image.getBitmap(), matrix, image.getPaint());
		}
	}

	/**
	 * drawImage �w�肵���C���[�W���w�肵���ꏊx,y�ɕ`�悵�܂��B
	 *
	 * @param image
	 *            , x, y
	 */
	public void drawImage(Image image, float x, float y) {
		if (canvas != null && image != null) {
			canvas.drawBitmap(image.getBitmap(), x, y, image.getPaint());
		}
		// canvas��image��null�̂Ƃ��͉������Ȃ��悤�ɂ��Ă����B
	}

	public void drawImage(Image image, float x, float y, int alpha) {
		if (canvas != null && image != null) {
			image.setAlpha(alpha);
			canvas.drawBitmap(image.getBitmap(), x, y, image.getPaint());
		}
	}

	/**
	 * drawImage �w�肵���C���[�W���w�肵���ꏊx,y�ɁA�C���[�W���S������angle�x��]�����ĕ`�悵�܂��B
	 *
	 * @param image
	 *            , x, y, angle
	 */
	@Deprecated
	public void drawImageOld(Image image, int x, int y, int angle) {
		if (canvas != null && image != null) {
			Matrix m = new Matrix();
			m.postRotate(-angle); // ��]�s��̐ݒ�
			Bitmap bmp = Bitmap.createBitmap(image.getBitmap(), 0, 0,
					image.getWidth(), image.getHeight(), m, true);
			x = x + (image.getBitmap().getWidth() - bmp.getWidth()) / 2;
			y = y + (image.getBitmap().getHeight() - bmp.getHeight()) / 2;
			canvas.drawBitmap(bmp, x, y, image.getPaint());
		}
	}

	/**
	 * drawImage �w�肵���C���[�W���w�肵���ꏊx,y�ɁA�C���[�W���S������angle�x��]�����ĕ`�悵�܂��B
	 *
	 * @param image
	 * @param x
	 * @param y
	 * @param angle
	 */
	public void drawImage(Image image, float x, float y, float angle) {
		if (canvas != null && image != null && matrix != null) {
			matrix.reset();
			// matrix.postRotate(-angle, image.getWidth() / 2,
					// image.getHeight() / 2);
			matrix.preTranslate(-image.getWidth() / 2, 0);
			matrix.postRotate(-angle);
			matrix.postTranslate(x, y);
			canvas.drawBitmap(image.getBitmap(), matrix, image.getPaint());
			matrix.reset();
		}
	}

	/**
	 * drawImage �w�肵���C���[�W�̈ꕔ�isx, xy���牡��width�A�c��height�j���ꏊx,y�ɕ`�悵�܂��B
	 *
	 * @param image
	 *            , x, y, sx, sy, width, height
	 */
	public void drawImage(Image image, float x, float y, float sx, float sy,
			float width, float height) {
		this.drawScaledImage(image, x, y, width, height, sx, sy, width, height);
	}

	public void drawImage(Image image, float x, float y, float sx, float sy,
			float width, float height, int alpha) {
		image.setAlpha(alpha);
		this.drawScaledImage(image, x, y, width, height, sx, sy, width, height);
	}

	/**
	 * drawScaledImage �w�肵���C���[�W���w�肵���傫���Ŋg��E�k���`�悵�܂� �\������ꏊ�̍�����W�idx,
	 * dy�j�A����width�A�c��height �w�肵���C���[�W�̍�����W�isx, sy�j�A����swidth�A�c��sheight
	 *
	 * @param image
	 *            , dx, dy, width, height, sx, sy, swidth, sheight
	 */
	public void drawScaledImage(Image image, float dx, float dy, float width,
			float height, float sx, float sy, float swidth, float sheight) {
		if (canvas != null && image != null) {
			canvas.drawBitmap(image.getBitmap(), new Rect((int) sx, (int) sy,
					(int) (sx + swidth), (int) (sy + sheight)), // �`�悵�����C���[�W�̕���
					new RectF(dx, dy, dx + width, dy + height), // �`�悷��ꏊ�Ƒ傫��
					image.getPaint());
		}
		// canvas��image��null�̂Ƃ��͉������Ȃ��悤�ɂ��Ă����B
	}

	/**
	 * drawScaledImageOld �w�肵���C���[�W���w�肵���傫���Ŋg��E�k���`�悵�܂�
	 *
	 * @param image
	 *            , dx, dy, width, height, sx, sy, swidth, sheight
	 */
	@Deprecated
	public void drawScaledImageOld(Image image, int dx, int dy, int width,
			int height, int sx, int sy, int swidth, int sheight) {
		if (canvas != null && image != null) {
			canvas.drawBitmap(image.getBitmap(), new Rect(sx, sy, swidth,
					sheight), // �`�悵�����C���[�W�̕���
					new Rect(dx, dy, dx + width, dy + height), // �`�悷��ꏊ�Ƒ傫��
					image.getPaint());
		}
		// canvas��image��null�̂Ƃ��͉������Ȃ��悤�ɂ��Ă����B
	}

	/*
	 * static int FLIP_HORIZONTAL �C���[�W�`�掞�̔��]���@�̂����A�������ɋ������]���邱�Ƃ�\���܂�(=1)�B static
	 * int FLIP_NONE �C���[�W�`�掞�̔��]���@�̂����A���]���Ȃ����Ƃ�\���܂�(=0)�B static int FLIP_ROTATE
	 * �C���[�W�`�掞�̔��]���@�̂����A�c�������ɋ������](180�x��])���邱�Ƃ�\���܂�(=3)�B static int
	 * FLIP_ROTATE_LEFT �C���[�W�`�掞�̔��]���@�̂����A����90�x��]���邱�Ƃ�\���܂�(=4)�B static int
	 * FLIP_ROTATE_RIGHT �C���[�W�`�掞�̔��]���@�̂����A�E��90�x��]���邱�Ƃ�\���܂�(=5)�B static int
	 * FLIP_ROTATE_RIGHT_HORIZONTAL �C���[�W�`�掞�̔��]���@�̂����A�E��90�x��]���A
	 * �������ɋ������]���邱�Ƃ�\���܂�(=6)�B static int FLIP_ROTATE_RIGHT_VERTICAL
	 * �C���[�W�`�掞�̔��]���@�̂����A�E��90�x��]���A �c�����ɋ������]���邱�Ƃ�\���܂�(=7)�B static int FLIP_VERTICAL
	 * �C���[�W�`�掞�̔��]���@�̂����A�c�����ɋ������]���邱�Ƃ�\���܂�(=2)�B
	 */

	/** int AQUA ���F��\���܂� */
	public static final int AQUA = Color.rgb(0, 0, 125); // �K���Ȑ��F
	/** int BLACK ���F��\���܂� */
	public static final int BLACK = Color.BLACK;
	/** int BLUE �F��\���܂� */
	public static final int BLUE = Color.BLUE;
	/** int FUCHSIA ���F��\���܂� */
	public static final int FUCHSIA = Color.rgb(255, 0, 255); // �K���Ȏ�
	/** int GRAY �D�F��\���܂��B */
	public static final int GRAY = Color.GRAY;
	/** int GREEN �Â��ΐF��\���܂��B */
	public static final int GREEN = Color.GREEN;
	/** int LIME �ΐF��\���܂��B */
	public static final int LIME = Color.rgb(0, 128, 0); // �K���ȗ�
	/** int MAROON �Â��ԐF��\���܂��B */
	public static final int MAROON = Color.rgb(128, 0, 0); // �K���ȈÂ��ԐF
	/** int NAVY �Â��F��\���܂��B */
	public static final int NAVY = Color.rgb(0, 0, 128); // �K���ȈÂ��F
	/** int OLIVE �Â����F��\���܂��B */
	public static final int OLIVE = Color.rgb(128, 128, 0); // �K���ȈÂ����F
	/** int PURPLE �Â����F��\���܂��B */
	public static final int PURPLE = Color.rgb(128, 0, 128); // �K���ȈÂ����F
	/** int RED �ԐF��\���܂��B */
	public static final int RED = Color.RED;
	/** int SILVER ��F��\���܂��B */
	public static final int SILVER = Color.rgb(192, 192, 192); // �K���ȋ�F
	/** int TEAL �Â����F��\���܂��B */
	public static final int TEAL = Color.rgb(0, 128, 128);
	/** int CYAN ���F��\���܂��B */
	public static final int CYAN = Color.CYAN;
	/** int WHITE ���F��\���܂��B */
	public static final int WHITE = Color.WHITE;
	/** int YELLOW ���F��\���܂��B */
	public static final int YELLOW = Color.YELLOW;

}

/*
 *
 * ���\�b�h�̊T�v void clearClip() ���ݐݒ肳��Ă���N���b�s���O�̈���������܂��B void clearRect(int x, int
 * y, int width, int height) ��`�̈��w�i�F�œh��Ԃ��܂��B void clipRect(int x, int y, int
 * width, int height) ���݂̃N���b�s���O�̈�ƈ����ɂ���Ďw�肳�ꂽ��`�Ƃ̐�(Intersection)��
 * �Ƃ��āA�N���b�s���O�̈�Ƃ��Đݒ肵�܂��B Graphics copy() �O���t�B�N�X�I�u�W�F�N�g�̃R�s�[�𐶐����܂��B void
 * copyArea(int x, int y, int width, int height, int dx, int dy) ��`�̈���Adx �� dy
 * �Ŏw�肳�ꂽ�����ŃR�s�[���܂��B void dispose() �O���t�B�b�N�X�R���e�L�X�g��j�����܂��B void
 * drawSpriteSet(SpriteSet sprites) �X�v���C�g�Z�b�g��`�悵�܂��B void drawSpriteSet(SpriteSet
 * sprites, int offset, int count) �X�v���C�g�Z�b�g�̈ꕔ��`�悵�܂��B void fillPolygon(int[]
 * xPoints, int[] yPoints, int nPoints) ���p�`��h��Ԃ��܂��B void fillPolygon(int[]
 * xPoints, int[] yPoints, int offset, int count) ���p�`��h��Ԃ��܂��B
 *
 * static int getColorOfName(int name) ���O���w�肵�Ă��̖��O�ɑΉ�����J���[��\�������l���擾���܂��B static
 * int getColorOfRGB(int r, int g, int b) RGB �l���w�肵�Ă���RGB�l�ɑΉ�����J���[��\�������l���擾���܂��B
 * static int getColorOfRGB(int r, int g, int b, int a) RGB �l�A�Ȃ�тɃA���t�@�l���w�肵�āA
 * �Ή�����J���[��\�������l���擾���܂��B
 *
 * int getPixel(int x, int y) �_(x, y)�̉E���̃s�N�Z���l���擾���܂��B int[] getPixels(int x, int
 * y, int width, int height, int[] pixels, int off) �w�肷��̈�̃s�N�Z���l���擾���܂��B int
 * getRGBPixel(int x, int y) �_(x, y)�̉E���̉�f�̐F��0xRRGGBB�̌`���Ŏ擾���܂��B int[]
 * getRGBPixels(int x, int y, int width, int height, int[] pixels, int off)
 * ��`�̈�̉�f�̐F��0xRRGGBB�̌`���Ŏ擾���܂��B void setClip(int x, int y, int width, int
 * height) �����ɂ���Ďw�肳�ꂽ��`���N���b�s���O�̈�Ƃ��Đݒ肵�܂��B void setColor(int c) �`��Ɏg�p����F��ݒ肵�܂��B
 * void setFlipMode(int flipmode) �C���[�W�̕`�掞�ɔ��]�܂��͉�]���ĕ`����s�����ǂ�����ݒ肵�܂��B void
 * setFont(Font f) �`��Ɏg�p����t�H���g��ݒ肵�܂��B void setOrigin(int x, int y)
 * �`��̍ۂ̍��W���_��ݒ肵�܂��B void setPictoColorEnabled(boolean b)
 * �G������`�悷��ۂɃO���t�B�b�N�X�ɐݒ肳�ꂽ�F��L���Ƃ��邩�ǂ������w�肵�܂��B void setPixel(int x, int y)
 * �_��`�悵�܂��B void setPixel(int x, int y, int color) �F���w�肵�ē_��`�悵�܂��B void
 * setPixels(int x, int y, int width, int height, int[] pixels, int off)
 * �w�肷��̈�̃s�N�Z���l��ݒ肵�܂��B void setRGBPixel(int x, int y, int pixel)
 * 0xRRGGBB�̌`���ŐF���w�肵�ē_��`�悵�܂��B void setRGBPixels(int x, int y, int width, int
 * height, int[] pixels, int off) ��`�̈�̉�f�̐F��0xRRGGBB�̌`���Őݒ肵�܂��B
 *
 * �s�v
 *
 * void lock() �`��f�o�C�X�ɑ΂��āA�_�u���o�b�t�@�����O�̊J�n��錾���܂��B void unlock(boolean forced)
 * �`��f�o�C�X�ɑ΂��āA�_�u���o�b�t�@�����O�̏I����錾���܂��B
 */