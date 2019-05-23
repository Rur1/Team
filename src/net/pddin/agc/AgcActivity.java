package net.pddin.agc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.KeyEvent;

/**
 * AgcActivity AGameCanvas用ゲーム終了ダイアログ付Activity
 *
 * 継承先でAGameCanvasインスタンスを生成して（セット）して使用します。 ゲーム終了ダイアログについては、タイトルなどが設定可能です。
 * AGameCanvasからAlertDialogを呼び出すためのpostRunnableを実装してあります。
 *
 * @author HARADA Toshinobu
 * @version 0.1 (H26/01/11)
 */
public class AgcActivity extends Activity {
	protected AGameCanvas agc = null;

	/**
	 * setAGC このAgcActivityで動作するAGameCanvasをベースとするインスタンスをセットします。
	 *
	 * @param agc
	 */
	public void setAGC(AGameCanvas agc) {
		this.agc = agc;
	}

	// AGameCanvasからAlertDialogを呼び出すために利用するためのもの。
	private Handler handler = new Handler();

	public void postRunnable(final Runnable run) {
		handler.post(run);
	}

	private String finishDialogTitle = "終了しますか？";

	/**
	 * setFinishDialogTitle ゲーム終了ダイアログのタイトルを設定します
	 */
	public void setFinishDialogTitle(String finishDialogTitle) {
		this.finishDialogTitle = finishDialogTitle;
	}

	private String finishDialogPosMes = "はい";

	/**
	 * setFinishDialogPosMes ゲーム終了ダイアログの「はい」を変更します。
	 *
	 * @param finishDialogPosMes
	 */
	public void setFinishDialogPosMes(String finishDialogPosMes) {
		this.finishDialogPosMes = finishDialogPosMes;
	}

	private String finishDialogNegMes = "いいえ";

	/**
	 * setFinishDialogNegMes ゲーム終了ダイアログの「いいえ」を変更します。
	 *
	 * @param finishDialogPosMes
	 */
	public void setFinishDialogNegMes(String finishDialogNegMes) {
		this.finishDialogNegMes = finishDialogNegMes;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (agc == null) {
			return super.dispatchKeyEvent(event); // agcがnullならばこれ以下の処理を実行しない。
		}
		// キーイベントとして、何かが押されたら、、、
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			// バックキーなのか？バックキーだったら、、、
			if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
				agc.onPause(); // ゲームキャンバスをポーズ（一時停止状態に）

				// ダイアログを出して、終了確認などをする。
				// ダイアログを作る「AlertDialog.Builder」を作る
				AlertDialog.Builder adb = new AlertDialog.Builder(this);

				if (finishDialogTitle == null) {
					finishDialogTitle = "Finish the Game";
				}
				adb.setTitle(finishDialogTitle); // タイトルの設定
				// adb.setMessage("するけどいいの？"); //メッセージの設定

				// はいが押されたときの設定
				if (finishDialogPosMes == null) {
					finishDialogPosMes = "YES";
				}
				adb.setPositiveButton(finishDialogPosMes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								AgcActivity.this.finish();
							}
						});
				// いいえが押されたときの設定
				if (finishDialogNegMes == null) {
					finishDialogNegMes = "NO";
				}
				adb.setNegativeButton(finishDialogNegMes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								agc.offPause();
							}
						});
				adb.setCancelable(false); // アラートダイアログからバックキーの処理を排除
				AlertDialog ad = adb.create(); // ダイアログを生成
				ad.show(); // ダイアログの表示
			}
		}
		return super.dispatchKeyEvent(event);
	}

}
