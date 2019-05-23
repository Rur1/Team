package net.pddin.agc.geom;

/**
 * Point2D �N���X�́A(x,y) ���W��Ԃ̈ʒu��\���_���`���܂��B
 */
public class Point2D {
	/** ����Point2D��x���W�ł��B */
	private float x;
	/** ����Point2D��y���W�ł��B */
	private float y;

	/**
	 * �R���X�g���N�^ �w�肳�ꂽ���W��Point2D���\�z����я������������܂��B
	 */
	public Point2D(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * �R���X�g���N�^ ���W(0, 0�j��Point2D���\�z����я������������܂��B
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
	 * distnce���\�b�h ����Point2D����w�肳�ꂽ�_�܂ł̋�����Ԃ��܂��B
	 */
	public float distance(float px, float py) {
		float dx = this.x - px;
		float dy = this.y - py;
		float d = dx * dx + dy * dy;
		return (float) Math.sqrt(d);
	}

	/**
	 * distnce���\�b�h ����Point2D����w�肳�ꂽPoint2D�܂ł̋�����Ԃ��܂��B
	 */
	public float distance(Point2D p) {
		return this.distance(p.getX(), p.getY());
	}

	/**
	 * setLocation���\�b�h ����Point2D�̈ʒu���A�w�肳�ꂽfloat�^�̍��W�ɐݒ肵�܂��B
	 */
	public void setLocation(float x, float y) {
		this.setX(x);
		this.setY(y);
	}

	/**
	 * setLocation���\�b�h ����Point2D�̈ʒu���A�w�肳�ꂽPoint2D�I�u�W�F�N�g�Ɠ������W�ɐݒ肵�܂��B
	 */
	public void setLocation(Point2D p) {
		this.setLocation(p.getX(), p.getY());
	}

}
