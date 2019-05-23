package lib;

public class Vector2 {

	public float x, y;

	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public boolean less(Vector2 other) {
		if (x > other.x) return false;
		if (y > other.y) return false;
		return true;
	}

	public void add(Vector2 other) {
		x += other.x;
		y += other.y;
	}

	public void subtract(Vector2 other) {
		x -= other.x;
		y -= other.y;
	}

}