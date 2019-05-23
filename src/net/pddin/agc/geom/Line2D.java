package net.pddin.agc.geom; //geometric�􉽊w�I�}�`

import java.io.Serializable;

/**
 * Line2D �N���X�́A(x,y) ���W��Ԃ̃��C���Z�O�����g��\���܂��B JDK6.0��Line2D�N���X��Android�p�ȈՎ����ł��B
 */
public class Line2D implements Cloneable, Serializable {
	// JDK 1.6 serialVersionUID
	private static final long serialVersionUID = 6161772511649436349L;

	/** ���C���Z�O�����g�̏I�_�P��x���W�ł��B */
	private float x1;
	/** ���C���Z�O�����g�̏I�_�P��y���W�ł��B */
	private float y1;
	/** ���C���Z�O�����g�̏I�_�Q��x���W�ł��B */
	private float x2;
	/** ���C���Z�O�����g�̏I�_�Q��y���W�ł��B */
	private float y2;

	/**
	 * �R���X�g���N�^ �w�肳�ꂽ���W���� Line ���\�z����я��������܂��B
	 */
	public Line2D(float x1, float y1, float x2, float y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	/**
	 * �R���X�g���N�^ �w�肳�ꂽ Point2D �I�u�W�F�N�g���� Line2D ���\�z����я��������܂��B
	 */
	public Line2D(Point2D p1, Point2D p2) {
		this(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}

	/**
	 * �R���X�g���N�^ ���W(0, 0) -> (0, 0)�� Line2D ���\�z����я��������܂��B
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
	 * ���� Line2D�̗����̏I�_�̈ʒu���A�w�肳�ꂽ Line2D �̏I�_�Ɠ����ʒu�ɐݒ肵�܂��B
	 */
	public void setLine(Line2D l) {
		this.setLine(l.getX1(), l.getY1(), l.getX2(), l.getY2());
	}

	/**
	 * ���� Line2D �̗����̏I�_�̈ʒu���A�w�肳�ꂽ Point2D ���W�ɐݒ肵�܂��B
	 */
	public void setLine(Point2D p1, Point2D p2) {
		this.setLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}

	/**
	 * ���� Line2D �̗����̏I�_�̈ʒu���A�w�肳�ꂽ���W�ɐݒ肵�܂��B
	 */
	public void setLine(float x1, float y1, float x2, float y2) {
		this.setX1(x1);
		this.setY1(y1);
		this.setX2(x2);
		this.setY2(y2);
	}

	/**
	 * ���� Line2D �̎n�_��\�� Point2D ��Ԃ��܂��B
	 */
	public Point2D getP1() {
		return new Point2D(this.getX1(), this.getY2());
	}

	/**
	 * ���� Line2D �̏I�_��\�� Point2D ��Ԃ��܂��B
	 */
	public Point2D getP2() {
		return new Point2D(this.getX1(), this.getY2());
	}

	/**
	 * (x1,y1) ���� (x2,y2) �܂ł̃��C���Z�O�����g�ɑ΂���w�肳�ꂽ�_ (px,py) �̈ʒu�������C���W�P�[�^��Ԃ��܂��B
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
	 * ���̃��C���Z�O�����g����Ƃ��āA�w�肳�ꂽ�_ (px,py) �̈ʒu�������C���W�P�[�^��Ԃ��܂��B
	 */
	public int relativeCCW(float px, float py) {
		return relativeCCW(getX1(), getY1(), getX2(), getY2(), px, py);
	}

	/**
	 * ���̃��C���Z�O�����g����Ƃ��āA�w�肳�ꂽ Point2D �̈ʒu�������C���W�P�[�^��Ԃ��܂��B
	 */
	public int relativeCCW(Point2D p) {
		return relativeCCW(getX1(), getY1(), getX2(), getY2(), p.getX(),
				p.getY());
	}

	/**
	 * (x1,y1) ���� (x2,y2) �܂ł̃��C���Z�O�����g�� (x3,y3) ���� (x4,y4)
	 * �܂ł̃��C���Z�O�����g�ƌ������邩�ǂ����𔻒肵�܂��B
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
	 * (x1,y1) ���� (x2,y2) �܂ł̃��C���Z�O�����g�����̃��C���Z�O�����g�ƌ������邩�ǂ����𔻒肵�܂��B
	 */
	public boolean intersectsLine(float x1, float y1, float x2, float y2) {
		return linesIntersect(x1, y1, x2, y2, getX1(), getY1(), getX2(),
				getY2());
	}

	/**
	 * �w�肳�ꂽ���C���Z�O�����g�����̃��C���Z�O�����g�ƌ������邩�ǂ����𔻒肵�܂��B
	 */
	public boolean intersectsLine(Line2D l) {
		return intersectsLine(l.getX1(), l.getY1(), l.getX2(), l.getY2());
	}

	/**
	 * ���̃I�u�W�F�N�g�Ɠ����N���X�̐V�����I�u�W�F�N�g���쐬���܂��B
	 */
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
	}

	/*
	 * ���������\�b�h�̊T�v
	 *
	 * boolean contains(double x, double y) �w�肳�ꂽ���W���A���� Line2D�̋��E���ɂ��邩�ǂ����𔻒肵�܂��B
	 *
	 * boolean contains(double x, double y, double w, double h) ���� Line2D
	 * �̓������A�w�肳�ꂽ��`���W�Z�b�g�����S�ɓ���邩�ǂ����𔻒肵�܂��B
	 *
	 * boolean contains(Point2D p) �w�肳�ꂽ Point2D ���A���� Line2D �̋��E���ɂ��邩�ǂ����𔻒肵�܂��B
	 *
	 * boolean contains(Rectangle2D r) ���� Line2D �̓������A�w�肳�ꂽ
	 * Rectangle2D�����S�ɓ���邩�ǂ����𔻒肵�܂��B
	 *
	 * Rectangle getBounds() Shape �����S�Ɉ͂ސ����^�� Rectangle��Ԃ��܂��B
	 *
	 * PathIterator getPathIterator(AffineTransform at) ���� Line2D
	 * �̋��E���`����J��Ԃ��I�u�W�F�N�g��Ԃ��܂��B
	 *
	 * PathIterator getPathIterator(AffineTransform at, double flatness) ���R�����ꂽ
	 * Line2D �̋��E���`����J��Ԃ��I�u�W�F�N�g��Ԃ��܂��B
	 *
	 * boolean intersects(double x, double y, double w, double h)
	 * Shape�̓����̈悪�A�w�肳�ꂽ��`�̈�̓����̈�ƌ������邩�ǂ����𔻒肵�܂��B
	 *
	 * boolean intersects(Rectangle2D r) Shape �̓������A�w�肳�ꂽ Rectangle2D
	 * �̓����ƌ������邩�ǂ����𔻒肵�܂��B
	 *
	 * double ptLineDist(double px, double py) �_���炱�̃��C���܂ł̋�����Ԃ��܂��B
	 *
	 * static double ptLineDist(double x1, double y1, double x2, double y2,
	 * double px, double py) �_���烉�C���܂ł̋�����Ԃ��܂��B
	 *
	 * double ptLineDist(Point2D pt) Point2D���炱�̃��C���܂ł̋�����Ԃ��܂��B
	 *
	 * double ptLineDistSq(double px, double py) �_���炱�̃��C���܂ł̋����� 2 ���Ԃ��܂��B
	 *
	 * static double ptLineDistSq(double x1, double y1, double x2, double y2,
	 * double px, double py) �_���烉�C���܂ł̋����� 2 ���Ԃ��܂��B
	 *
	 * double ptLineDistSq(Point2D pt) �w�肳�ꂽ Point2D ���炱�̃��C���܂ł̋����� 2 ���Ԃ��܂��B
	 *
	 * double ptSegDist(double px, double py) �_���炱�̃��C���Z�O�����g�܂ł̋�����Ԃ��܂��B
	 *
	 * static double ptSegDist(double x1, double y1, double x2, double y2,
	 * double px, double py) �_���烉�C���Z�O�����g�܂ł̋�����Ԃ��܂��B
	 *
	 * double ptSegDist(Point2D pt) Point2D���炱�̃��C���Z�O�����g�܂ł̋�����Ԃ��܂��B
	 *
	 * double ptSegDistSq(double px, double py) �_���炱�̃��C���Z�O�����g�܂ł̋����� 2 ���Ԃ��܂��B
	 *
	 * static double ptSegDistSq(double x1, double y1, double x2, double y2,
	 * double px, double py) �_���烉�C���Z�O�����g�܂ł̋����� 2���Ԃ��܂��B
	 *
	 * double ptSegDistSq(Point2D pt) Point2D ���炱�̃��C���Z�O�����g�܂ł̋����� 2���Ԃ��܂��B
	 */

}
