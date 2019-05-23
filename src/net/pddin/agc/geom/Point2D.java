package net.pddin.agc.geom;

/**
 * Point2D クラスは、(x,y) 座標空間の位置を表す点を定義します。
 */
public class Point2D {
	/** このPoint2Dのx座標です。 */
	private float x;
	/** このPoint2Dのy座標です。 */
	private float y;

	/**
	 * コンストラクタ 指定された座標でPoint2Dを構築および初期化生成します。
	 */
	public Point2D(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * コンストラクタ 座標(0, 0）でPoint2Dを構築および初期化生成します。
	 */
	public Point2D() {
		this(0, 0);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	/**
	 * distnceメソッド このPoint2Dから指定された点までの距離を返します。
	 */
	public float distance(float px, float py) {
		float dx = this.x - px;
		float dy = this.y - py;
		float d = dx * dx + dy * dy;
		return (float) Math.sqrt(d);
	}

	/**
	 * distnceメソッド このPoint2Dから指定されたPoint2Dまでの距離を返します。
	 */
	public float distance(Point2D p) {
		return this.distance(p.getX(), p.getY());
	}

	/**
	 * setLocationメソッド このPoint2Dの位置を、指定されたfloat型の座標に設定します。
	 */
	public void setLocation(float x, float y) {
		this.setX(x);
		this.setY(y);
	}

	/**
	 * setLocationメソッド このPoint2Dの位置を、指定されたPoint2Dオブジェクトと同じ座標に設定します。
	 */
	public void setLocation(Point2D p) {
		this.setLocation(p.getX(), p.getY());
	}

}
