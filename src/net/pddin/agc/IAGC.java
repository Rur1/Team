package net.pddin.agc;

/**
 * AGCインターフェース「IAGC」
 *
 * @version 1.0 H25/06/26 @author HARADA
 */
public interface IAGC {
	public void init(); // 初期化処理メソッド

	public void update(); // 更新処理メソッド

	public void draw(Graphics g); // 描画処理メソッド

	public void finish(); // 終了処理メソッド
}
