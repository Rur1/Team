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
 * AGameCanvas専用Graphicsクラス（Android標準のCanvasクラスおよびPaintクラスを包含したものです。）
 *
 * @author HARADA Toshinobu
 * @version 0.29 (H23/06/10)
 */
/*
 * H26/07/19 各描画メソッドの引数で指定する座標をintからfloatに変更。 H26/07/18
 * drawImageメソッドの角度指定版の仕様を変更。描画速度向上のため。 H26/07/18 drawPolylineメソッドを実装 H26/01/24
 * drawHoleメソッド（四角形に円形の穴をあけた図形を描画する機能）を実装 H26/01/24 drawOval/fillOvalメソッドを実装
 * H26/01/24 drawRoundRect/fillRoundRectメソッドを実装 H25/11/06 drawLineメソッドを実装
 * H25/10/24 setFontTypefaceメソッド（フォント設定機能）を実装
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
	private Matrix matrix; // 回転などに用いる行列
	private Path path; //

	/**
	 * AGCではGraphicsオブジェクトはコンストラクタでは生成出来ません。 getInstanceを用いて取得します。
	 */
	private Graphics() {
		paint = new Paint();
		paint.setTypeface(null); // フォントをデフォルトに設定
		defaultTextSize = paint.getTextSize();

		matrix = new Matrix();
		matrix.reset();

		path = new Path();
		path.reset();
	}

	/**
	 * * getInstance 唯一の描画オブジェクトを返します。
	 *
	 * @return GRPAHICS
	 */
	public static Graphics getInstance() {
		return Graphics.GRAPHICS;
	}

	/**
	 * setCanvas 描画関連処理の前にcanvasを受け取ります。
	 *
	 * @param canvas
	 * @return 指定されたキャンバスが存在していたかどうか
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
	 * setColor 描画対象についての色を設定します。
	 *
	 * @param color
	 */
	public void setColor(int color) {
		paint.setColor(color);
	}

	/**
	 * setColor 描画対象について、色を（赤，緑，青の三つの数値（0～255）で）設定します。数値が範囲を超えた場合にどうなるかはわかりません。
	 *
	 * @param red
	 *            , green, blue
	 */
	public void setColor(int red, int green, int blue) {
		paint.setColor(Color.rgb(red, green, blue));
	}

	/**
	 * setAlpha 描画対象について、α値を0～255の範囲で設定します。数値が範囲を超えた場合にどうなるかはわかりません。
	 *
	 * @param alpha
	 */
	public void setAlpha(int alpha) {
		paint.setAlpha(alpha);
	}

	/**
	 * getAlpha 現在設定されているα値を返します。
	 *
	 * @return alpha
	 */
	public int getAlpha() {
		return paint.getAlpha();
	}

	/**
	 * getColorOfName 引数で指定したカラーに対応する数値を返します。
	 * （com.nttdocomo.ui.Graphicsに合わせる形でとりあえず定義したものです。）
	 *
	 * @param color
	 * @return color
	 */
	public static int getColorOfName(int color) {
		return color;
	}

	/**
	 * setFontSize 引数で指定したフォントサイズに設定します。 フォントサイズの設定はString(Text)描画に対してのみ有効です。
	 * サイズは数値でも指定可能です。
	 *
	 * @param size
	 *            （Graphics.Font.SIZE_TINY, .SIZE_SMALL, SIZE_MEDIUM,
	 *            SIZE_LARGE）
	 */
	public void setFontSize(float size) {
		paint.setTextSize(size);
	}

	/**
	 * setDefaultFontSize デフォルトのフォントサイズに設定します。
	 * フォントサイズの設定はString(Text)描画に対してのみ有効です。
	 */
	public void setDefaultFontSize() {
		paint.setTextSize(defaultTextSize);
	}

	/**
	 * getFontSizse 現在設定されているフォントサイズを返します。
	 *
	 * @return
	 */
	public float getFontSize() {
		return paint.getTextSize();
	}

	/**
	 * setFontTypeface 引数で指定したフォントに設定します。 ただしフォントファイルをassetsフォルダに置いておく必要があります。
	 * （注意） フォントファイルは必ず確認のうえ配布可能なものを使用してください。 権利関係で発生した問題についてこのコードの製作者は責任を負いません。
	 *
	 * @param path
	 */
	public void setFontTypeface(String path) {
		Typeface typeface = Typeface.createFromAsset(agc.getContext()
				.getAssets(), path);
		paint.setTypeface(typeface);
	}

	/**
	 * drawLine 引数で指定したx1,y1～x2,y2までの直線を描画します。
	 *
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void drawLine(float x1, float y1, float x2, float y2) {
		paint.setStrokeWidth(5); // 線の幅は5
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawLine(x1, y1, x2, y2, paint);
	}

	/**
	 * drawPolyline count個の指定された点を結ぶ線分を描画します。
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
	 * drawPolyline count個の指定された点を結ぶ線分を描画します。
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
	 * drawPolyline pointsによって指定された点を順番に結ぶ線分を描画します。
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
	 * drawString 引数で指定した文字列を指定した座標を基準に描画します。
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
	 * drawStringCentering 引数で指定した文字列を指定した座標を基準に中揃えして描画します。
	 *
	 * @param text
	 * @param cx
	 * @param cy
	 */
	public void drawStringCentering(String text, float cx, float cy) {
		drawString(text, cx - paint.measureText(text) / 2, cy);
	}

	/**
	 * drawHole 引数で指定したx,yを基準に幅w高さhの塗りつぶし四角形から、
	 * 左上点をhx,hyとした横幅hw縦幅hhの円をくり抜いたものを描画します。
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
		paint.setStrokeWidth(1); // 線の幅は１
		// paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawPath(path, paint);
		paint.setAntiAlias(false);
	}

	/**
	 * drawHole 引数で指定したx,yを基準に幅w高さhの塗りつぶし四角形から、
	 * 中心点をhcx,hcyとした半径rの円をくり抜いたものを描画します。
	 *
	 * @param x
	 *            , y, w, h, hcx ,hcy, r
	 */
	public void drawHole(float x, float y, float w, float h, float hcx,
			float hcy, float r) {
		this.drawHole(x, y, w, h, hcx - r, hcy - r, r * 2, r * 2);
	}

	/**
	 * drawRect 引数で指定したx,yを基準に幅w高さhの四角形の枠を描画します。
	 *
	 * @param x
	 *            , y, w, h
	 */
	public void drawRect(float x, float y, float w, float h) {
		paint.setStrokeWidth(1); // 線の幅は１
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(x, y, x + w, y + h, paint);
	}

	/**
	 * fillRect 引数で指定したx,yを基準に幅w高さhの四角形を塗りつぶし描画します。
	 *
	 * @param x
	 *            , y, w, h
	 */
	public void fillRect(float x, float y, float w, float h) {
		paint.setStrokeWidth(1); // 線の幅は１
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		canvas.drawRect(x, y, x + w, y + h, paint);
	}

	public void fillRect(float x, float y, float w, float h, int alpha) {
		paint.setAlpha(alpha);
		paint.setStrokeWidth(1); // 線の幅は１
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		canvas.drawRect(x, y, x + w, y + h, paint);
	}

	/**
	 * drawRoundRect 引数で指定したx,yを基準に幅w高さhの四角形の角を横rx縦ryで丸くした角丸四角形の枠を描画します。
	 *
	 * @param x
	 *            , y, w, h, rx, ry
	 */
	public void drawRoundRect(float x, float y, float w, float h, float rx,
			float ry) {
		paint.setStrokeWidth(1); // 線の幅は１
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawRoundRect(new RectF(x, y, x + w, y + h), rx, ry, paint);
	}

	/**
	 * fillRoundRect 引数で指定したx,yを基準に幅w高さhの四角形の角を横rx縦ryで丸くした角丸四角形を塗りつぶし描画します。
	 *
	 * @param x
	 *            , y, w, h, rx, ry
	 */
	public void fillRoundRect(float x, float y, float w, float h, float rx,
			float ry) {
		paint.setStrokeWidth(1); // 線の幅は１
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		canvas.drawRoundRect(new RectF(x, y, x + w, y + h), rx, ry, paint);
	}

	/**
	 * drawOval 引数で指定したx,yを左上点とした幅w高さhの四角形に内接する楕円を描画します。
	 *
	 * @param x
	 *            , y, w, h
	 */
	public void drawOval(float x, float y, float w, float h) {
		paint.setAntiAlias(true);
		paint.setStrokeWidth(1); // 線の幅は１
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawOval(new RectF(x, y, x + w, y + h), paint);
		paint.setAntiAlias(false);
	}

	/**
	 * fillOval 引数で指定したx,yを左上点とした幅w高さhの四角形に内接する楕円を塗りつぶし描画します。
	 *
	 * @param x
	 *            , y, w, h
	 */
	public void fillOval(float x, float y, float w, float h) {
		paint.setAntiAlias(true);
		paint.setStrokeWidth(1); // 線の幅は１
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		canvas.drawOval(new RectF(x, y, x + w, y + h), paint);
		paint.setAntiAlias(false);
	}

	/**
	 * drawArc 引数で指定したx,yを左上点とした幅w高さhの四角形に内接する円の弧をsaを基準にagl度分描画します。
	 *
	 * @param x
	 *            , y, w, h, sa, agl
	 */
	public void drawArc(float x, float y, float w, float h, float sa, float agl) {
		paint.setAntiAlias(true);
		paint.setStrokeWidth(1); // 線の幅は１
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawArc(new RectF(x, y, x + w, y + h), sa, -agl, false, paint);
		paint.setAntiAlias(false);
	}

	/**
	 * fillArc 引数で指定したx,yを左上点とした幅w高さhの四角形に内接する円の弧をsaを基準にagl度分塗りつぶし描画します。
	 *
	 * @param x
	 *            , y, w, h, sa, agl
	 */
	public void fillArc(float x, float y, float w, float h, float sa, float agl) {
		paint.setAntiAlias(true);
		paint.setStrokeWidth(1); // 線の幅は１
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		canvas.drawArc(new RectF(x, y, x + w, y + h), sa, -agl, true, paint);
		paint.setAntiAlias(false);
	}

	/**
	 * drawImage 指定したイメージを２次元アフィン変換をかけて描画します。（未実装です。）
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
	 * drawImage 指定したイメージを指定した場所x,yに描画します。
	 *
	 * @param image
	 *            , x, y
	 */
	public void drawImage(Image image, float x, float y) {
		if (canvas != null && image != null) {
			canvas.drawBitmap(image.getBitmap(), x, y, image.getPaint());
		}
		// canvasやimageがnullのときは何もしないようにしておく。
	}

	public void drawImage(Image image, float x, float y, int alpha) {
		if (canvas != null && image != null) {
			image.setAlpha(alpha);
			canvas.drawBitmap(image.getBitmap(), x, y, image.getPaint());
		}
	}

	/**
	 * drawImage 指定したイメージを指定した場所x,yに、イメージ中心を軸にangle度回転させて描画します。
	 *
	 * @param image
	 *            , x, y, angle
	 */
	@Deprecated
	public void drawImageOld(Image image, int x, int y, int angle) {
		if (canvas != null && image != null) {
			Matrix m = new Matrix();
			m.postRotate(-angle); // 回転行列の設定
			Bitmap bmp = Bitmap.createBitmap(image.getBitmap(), 0, 0,
					image.getWidth(), image.getHeight(), m, true);
			x = x + (image.getBitmap().getWidth() - bmp.getWidth()) / 2;
			y = y + (image.getBitmap().getHeight() - bmp.getHeight()) / 2;
			canvas.drawBitmap(bmp, x, y, image.getPaint());
		}
	}

	/**
	 * drawImage 指定したイメージを指定した場所x,yに、イメージ中心を軸にangle度回転させて描画します。
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
	 * drawImage 指定したイメージの一部（sx, xyから横幅width、縦幅height）を場所x,yに描画します。
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
	 * drawScaledImage 指定したイメージを指定した大きさで拡大・縮小描画します 表示する場所の左上座標（dx,
	 * dy）、横幅width、縦幅height 指定したイメージの左上座標（sx, sy）、横幅swidth、縦幅sheight
	 *
	 * @param image
	 *            , dx, dy, width, height, sx, sy, swidth, sheight
	 */
	public void drawScaledImage(Image image, float dx, float dy, float width,
			float height, float sx, float sy, float swidth, float sheight) {
		if (canvas != null && image != null) {
			canvas.drawBitmap(image.getBitmap(), new Rect((int) sx, (int) sy,
					(int) (sx + swidth), (int) (sy + sheight)), // 描画したいイメージの部分
					new RectF(dx, dy, dx + width, dy + height), // 描画する場所と大きさ
					image.getPaint());
		}
		// canvasやimageがnullのときは何もしないようにしておく。
	}

	/**
	 * drawScaledImageOld 指定したイメージを指定した大きさで拡大・縮小描画します
	 *
	 * @param image
	 *            , dx, dy, width, height, sx, sy, swidth, sheight
	 */
	@Deprecated
	public void drawScaledImageOld(Image image, int dx, int dy, int width,
			int height, int sx, int sy, int swidth, int sheight) {
		if (canvas != null && image != null) {
			canvas.drawBitmap(image.getBitmap(), new Rect(sx, sy, swidth,
					sheight), // 描画したいイメージの部分
					new Rect(dx, dy, dx + width, dy + height), // 描画する場所と大きさ
					image.getPaint());
		}
		// canvasやimageがnullのときは何もしないようにしておく。
	}

	/*
	 * static int FLIP_HORIZONTAL イメージ描画時の反転方法のうち、横方向に鏡像反転することを表します(=1)。 static
	 * int FLIP_NONE イメージ描画時の反転方法のうち、反転しないことを表します(=0)。 static int FLIP_ROTATE
	 * イメージ描画時の反転方法のうち、縦横方向に鏡像反転(180度回転)することを表します(=3)。 static int
	 * FLIP_ROTATE_LEFT イメージ描画時の反転方法のうち、左に90度回転することを表します(=4)。 static int
	 * FLIP_ROTATE_RIGHT イメージ描画時の反転方法のうち、右に90度回転することを表します(=5)。 static int
	 * FLIP_ROTATE_RIGHT_HORIZONTAL イメージ描画時の反転方法のうち、右に90度回転し、
	 * 横方向に鏡像反転することを表します(=6)。 static int FLIP_ROTATE_RIGHT_VERTICAL
	 * イメージ描画時の反転方法のうち、右に90度回転し、 縦方向に鏡像反転することを表します(=7)。 static int FLIP_VERTICAL
	 * イメージ描画時の反転方法のうち、縦方向に鏡像反転することを表します(=2)。
	 */

	/** int AQUA 水色を表します */
	public static final int AQUA = Color.rgb(0, 0, 125); // 適当な水色
	/** int BLACK 黒色を表します */
	public static final int BLACK = Color.BLACK;
	/** int BLUE 青色を表します */
	public static final int BLUE = Color.BLUE;
	/** int FUCHSIA 紫色を表します */
	public static final int FUCHSIA = Color.rgb(255, 0, 255); // 適当な紫
	/** int GRAY 灰色を表します。 */
	public static final int GRAY = Color.GRAY;
	/** int GREEN 暗い緑色を表します。 */
	public static final int GREEN = Color.GREEN;
	/** int LIME 緑色を表します。 */
	public static final int LIME = Color.rgb(0, 128, 0); // 適当な緑
	/** int MAROON 暗い赤色を表します。 */
	public static final int MAROON = Color.rgb(128, 0, 0); // 適当な暗い赤色
	/** int NAVY 暗い青色を表します。 */
	public static final int NAVY = Color.rgb(0, 0, 128); // 適当な暗い青色
	/** int OLIVE 暗い黄色を表します。 */
	public static final int OLIVE = Color.rgb(128, 128, 0); // 適当な暗い黄色
	/** int PURPLE 暗い紫色を表します。 */
	public static final int PURPLE = Color.rgb(128, 0, 128); // 適当な暗い紫色
	/** int RED 赤色を表します。 */
	public static final int RED = Color.RED;
	/** int SILVER 銀色を表します。 */
	public static final int SILVER = Color.rgb(192, 192, 192); // 適当な銀色
	/** int TEAL 暗い水色を表します。 */
	public static final int TEAL = Color.rgb(0, 128, 128);
	/** int CYAN 水色を表します。 */
	public static final int CYAN = Color.CYAN;
	/** int WHITE 白色を表します。 */
	public static final int WHITE = Color.WHITE;
	/** int YELLOW 黄色を表します。 */
	public static final int YELLOW = Color.YELLOW;

}

/*
 *
 * メソッドの概要 void clearClip() 現在設定されているクリッピング領域を解除します。 void clearRect(int x, int
 * y, int width, int height) 矩形領域を背景色で塗りつぶします。 void clipRect(int x, int y, int
 * width, int height) 現在のクリッピング領域と引数によって指定された矩形との積(Intersection)を
 * とって、クリッピング領域として設定します。 Graphics copy() グラフィクスオブジェクトのコピーを生成します。 void
 * copyArea(int x, int y, int width, int height, int dx, int dy) 矩形領域を、dx と dy
 * で指定された距離でコピーします。 void dispose() グラフィックスコンテキストを破棄します。 void
 * drawSpriteSet(SpriteSet sprites) スプライトセットを描画します。 void drawSpriteSet(SpriteSet
 * sprites, int offset, int count) スプライトセットの一部を描画します。 void fillPolygon(int[]
 * xPoints, int[] yPoints, int nPoints) 多角形を塗りつぶします。 void fillPolygon(int[]
 * xPoints, int[] yPoints, int offset, int count) 多角形を塗りつぶします。
 *
 * static int getColorOfName(int name) 名前を指定してその名前に対応するカラーを表す整数値を取得します。 static
 * int getColorOfRGB(int r, int g, int b) RGB 値を指定してそのRGB値に対応するカラーを表す整数値を取得します。
 * static int getColorOfRGB(int r, int g, int b, int a) RGB 値、ならびにアルファ値を指定して、
 * 対応するカラーを表す整数値を取得します。
 *
 * int getPixel(int x, int y) 点(x, y)の右下のピクセル値を取得します。 int[] getPixels(int x, int
 * y, int width, int height, int[] pixels, int off) 指定する領域のピクセル値を取得します。 int
 * getRGBPixel(int x, int y) 点(x, y)の右下の画素の色を0xRRGGBBの形式で取得します。 int[]
 * getRGBPixels(int x, int y, int width, int height, int[] pixels, int off)
 * 矩形領域の画素の色を0xRRGGBBの形式で取得します。 void setClip(int x, int y, int width, int
 * height) 引数によって指定された矩形をクリッピング領域として設定します。 void setColor(int c) 描画に使用する色を設定します。
 * void setFlipMode(int flipmode) イメージの描画時に反転または回転して描画を行うかどうかを設定します。 void
 * setFont(Font f) 描画に使用するフォントを設定します。 void setOrigin(int x, int y)
 * 描画の際の座標原点を設定します。 void setPictoColorEnabled(boolean b)
 * 絵文字を描画する際にグラフィックスに設定された色を有効とするかどうかを指定します。 void setPixel(int x, int y)
 * 点を描画します。 void setPixel(int x, int y, int color) 色を指定して点を描画します。 void
 * setPixels(int x, int y, int width, int height, int[] pixels, int off)
 * 指定する領域のピクセル値を設定します。 void setRGBPixel(int x, int y, int pixel)
 * 0xRRGGBBの形式で色を指定して点を描画します。 void setRGBPixels(int x, int y, int width, int
 * height, int[] pixels, int off) 矩形領域の画素の色を0xRRGGBBの形式で設定します。
 *
 * 不要
 *
 * void lock() 描画デバイスに対して、ダブルバッファリングの開始を宣言します。 void unlock(boolean forced)
 * 描画デバイスに対して、ダブルバッファリングの終了を宣言します。
 */