package net.pddin.agc.geom; //geometric幾何学的図形

import java.io.Serializable;

/**
 * Line2D クラスは、(x,y) 座標空間のラインセグメントを表します。 JDK6.0のLine2DクラスのAndroid用簡易実装です。
 */
public class Line2D implements Cloneable, Serializable {
	// JDK 1.6 serialVersionUID
	private static final long serialVersionUID = 6161772511649436349L;

	/** ラインセグメントの終点１のx座標です。 */
	private float x1;
	/** ラインセグメントの終点１のy座標です。 */
	private float y1;
	/** ラインセグメントの終点２のx座標です。 */
	private float x2;
	/** ラインセグメントの終点２のy座標です。 */
	private float y2;

	/**
	 * コンストラクタ 指定された座標から Line を構築および初期化します。
	 */
	public Line2D(float x1, float y1, float x2, float y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	/**
	 * コンストラクタ 指定された Point2D オブジェクトから Line2D を構築および初期化します。
	 */
	public Line2D(Point2D p1, Point2D p2) {
		this(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}

	/**
	 * コンストラクタ 座標(0, 0) -> (0, 0)で Line2D を構築および初期化します。
	 */
	public Line2D() {
		this(0, 0, 0, 0);
	}

	public float getX1() {
		return x1;
	}

	public void setX1(float x1) {
		this.x1 = x1;
	}

	public float getX2() {
		return x2;
	}

	public void setX2(float x2) {
		this.x2 = x2;
	}

	public float getY1() {
		return y1;
	}

	public void setY1(float y1) {
		this.y1 = y1;
	}

	public float getY2() {
		return y2;
	}

	public void setY2(float y2) {
		this.y2 = y2;
	}

	/**
	 * この Line2Dの両方の終点の位置を、指定された Line2D の終点と同じ位置に設定します。
	 */
	public void setLine(Line2D l) {
		this.setLine(l.getX1(), l.getY1(), l.getX2(), l.getY2());
	}

	/**
	 * この Line2D の両方の終点の位置を、指定された Point2D 座標に設定します。
	 */
	public void setLine(Point2D p1, Point2D p2) {
		this.setLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}

	/**
	 * この Line2D の両方の終点の位置を、指定された座標に設定します。
	 */
	public void setLine(float x1, float y1, float x2, float y2) {
		this.setX1(x1);
		this.setY1(y1);
		this.setX2(x2);
		this.setY2(y2);
	}

	/**
	 * この Line2D の始点を表す Point2D を返します。
	 */
	public Point2D getP1() {
		return new Point2D(this.getX1(), this.getY2());
	}

	/**
	 * この Line2D の終点を表す Point2D を返します。
	 */
	public Point2D getP2() {
		return new Point2D(this.getX1(), this.getY2());
	}

	/**
	 * (x1,y1) から (x2,y2) までのラインセグメントに対する指定された点 (px,py) の位置を示すインジケータを返します。
	 */
	public static int relativeCCW(float x1, float y1, float x2, float y2,
			float px, float py) {
		x2 -= x1;
		y2 -= y1;
		px -= x1;
		py -= y1;
		float ccw = px * y2 - py * x2;
		if (ccw == 0.0f) {
			ccw = px * x2 + py * y2;
			if (ccw > 0.0f) {
				px -= x2;
				py -= y2;
				ccw = px * x2 + py * y2;
				if (ccw < 0.0f) {
					ccw = 0.0f;
				}
			}
		}
		return (ccw < 0.0f) ? -1 : ((ccw > 0.0f) ? 1 : 0);
	}

	/**
	 * このラインセグメントを基準として、指定された点 (px,py) の位置を示すインジケータを返します。
	 */
	public int relativeCCW(float px, float py) {
		return relativeCCW(getX1(), getY1(), getX2(), getY2(), px, py);
	}

	/**
	 * このラインセグメントを基準として、指定された Point2D の位置を示すインジケータを返します。
	 */
	public int relativeCCW(Point2D p) {
		return relativeCCW(getX1(), getY1(), getX2(), getY2(), p.getX(),
				p.getY());
	}

	/**
	 * (x1,y1) から (x2,y2) までのラインセグメントが (x3,y3) から (x4,y4)
	 * までのラインセグメントと交差するかどうかを判定します。
	 */
	public static boolean linesIntersect(float x1, float y1, float x2,
			float y2, float x3, float y3, float x4, float y4) {
		boolean b1 = relativeCCW(x1, y1, x2, y2, x3, y3)
				* relativeCCW(x1, y1, x2, y2, x4, y4) <= 0;
		boolean b2 = relativeCCW(x3, y3, x4, y4, x1, y1)
				* relativeCCW(x3, y3, x4, y4, x2, y2) <= 0;
		return b1 && b2;
	}

	/**
	 * (x1,y1) から (x2,y2) までのラインセグメントがこのラインセグメントと交差するかどうかを判定します。
	 */
	public boolean intersectsLine(float x1, float y1, float x2, float y2) {
		return linesIntersect(x1, y1, x2, y2, getX1(), getY1(), getX2(),
				getY2());
	}

	/**
	 * 指定されたラインセグメントがこのラインセグメントと交差するかどうかを判定します。
	 */
	public boolean intersectsLine(Line2D l) {
		return intersectsLine(l.getX1(), l.getY1(), l.getX2(), l.getY2());
	}

	/**
	 * このオブジェクトと同じクラスの新しいオブジェクトを作成します。
	 */
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
	}

	/*
	 * 未実装メソッドの概要
	 *
	 * boolean contains(double x, double y) 指定された座標が、この Line2Dの境界内にあるかどうかを判定します。
	 *
	 * boolean contains(double x, double y, double w, double h) この Line2D
	 * の内部が、指定された矩形座標セットを完全に内包するかどうかを判定します。
	 *
	 * boolean contains(Point2D p) 指定された Point2D が、この Line2D の境界内にあるかどうかを判定します。
	 *
	 * boolean contains(Rectangle2D r) この Line2D の内部が、指定された
	 * Rectangle2Dを完全に内包するかどうかを判定します。
	 *
	 * Rectangle getBounds() Shape を完全に囲む整数型の Rectangleを返します。
	 *
	 * PathIterator getPathIterator(AffineTransform at) この Line2D
	 * の境界を定義する繰り返しオブジェクトを返します。
	 *
	 * PathIterator getPathIterator(AffineTransform at, double flatness) 平坦化された
	 * Line2D の境界を定義する繰り返しオブジェクトを返します。
	 *
	 * boolean intersects(double x, double y, double w, double h)
	 * Shapeの内部領域が、指定された矩形領域の内部領域と交差するかどうかを判定します。
	 *
	 * boolean intersects(Rectangle2D r) Shape の内部が、指定された Rectangle2D
	 * の内部と交差するかどうかを判定します。
	 *
	 * double ptLineDist(double px, double py) 点からこのラインまでの距離を返します。
	 *
	 * static double ptLineDist(double x1, double y1, double x2, double y2,
	 * double px, double py) 点からラインまでの距離を返します。
	 *
	 * double ptLineDist(Point2D pt) Point2Dからこのラインまでの距離を返します。
	 *
	 * double ptLineDistSq(double px, double py) 点からこのラインまでの距離の 2 乗を返します。
	 *
	 * static double ptLineDistSq(double x1, double y1, double x2, double y2,
	 * double px, double py) 点からラインまでの距離の 2 乗を返します。
	 *
	 * double ptLineDistSq(Point2D pt) 指定された Point2D からこのラインまでの距離の 2 乗を返します。
	 *
	 * double ptSegDist(double px, double py) 点からこのラインセグメントまでの距離を返します。
	 *
	 * static double ptSegDist(double x1, double y1, double x2, double y2,
	 * double px, double py) 点からラインセグメントまでの距離を返します。
	 *
	 * double ptSegDist(Point2D pt) Point2Dからこのラインセグメントまでの距離を返します。
	 *
	 * double ptSegDistSq(double px, double py) 点からこのラインセグメントまでの距離の 2 乗を返します。
	 *
	 * static double ptSegDistSq(double x1, double y1, double x2, double y2,
	 * double px, double py) 点からラインセグメントまでの距離の 2乗を返します。
	 *
	 * double ptSegDistSq(Point2D pt) Point2D からこのラインセグメントまでの距離の 2乗を返します。
	 */

}
