package com.cqut.edu.activity;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.qishi_junqi.R;

import com.cqut.edu.activity.LocalActivity;
import com.cqut.edu.basic.Common;
import com.cqut.edu.view.LocalPlayView;

//单机版界面
public class LocalActivity extends Activity{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置全屏显示
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_local);// 显示单机游戏界面
		// //获取屏幕大小
		// DisplayMetrics dm = new DisplayMetrics();
		// getWindowManager().getDefaultDisplay().getMetrics(dm);
		// ShowSomeThing(dm.widthPixels + " " + dm.heightPixels);

		LocalPlayView chessBoardView = new LocalPlayView(LocalActivity.this,
				this);
		setContentView(chessBoardView);
		Toast.makeText(getApplicationContext(), "单机游戏，请完成布局后，开始布局！",
				Toast.LENGTH_SHORT).show();
		// // System.out.print(wholeWidth + " " + wholeHeight);
		// ShowSomeThing(wholeHeight + " " + wholeWidth);
		// imV = (ImageView) findViewById(R.id.imageView_chessBoard);
		// int imgWidth = imV.getWidth();
		// int imgHeight = imV.getHeight();
		// System.out.print(imgWidth + " " + imgHeight);

	}

	// /显示相关信息
	public void ShowSomeThing(String adbString) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				LocalActivity.this);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle("提示")
				.setMessage(adbString)
				.setNegativeButton(getText(R.string.ok),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private MediaPlayer mediaPlayer;

	/**
	 * Play sound for fun
	 */
	private void playSound(final int soundID) {
		new Thread() {
			public void run() {
				mediaPlayer = MediaPlayer.create(LocalActivity.this, soundID);
				mediaPlayer.start();
			}
		}.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		// 完成布局
		case R.id.layout_ok_item:
			if (Common.islayouting) {
				if (Common.playSound)
					playSound(R.raw.noxiaqi);
				AlertDialog.Builder dialog = new AlertDialog.Builder(
						LocalActivity.this);
				dialog.setIcon(R.drawable.ic_launcher);
				dialog.setTitle("提示");
				dialog.setMessage("确定完成布局，开始游戏吗？");
				dialog.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								if (Common.playSound)
									playSound(R.raw.gamestart);
								Common.isgamestart = true;
								Common.islayouting = false;
								Toast.makeText(getApplicationContext(),
										"游戏开始，请您行棋！", Toast.LENGTH_SHORT)
										.show();
							}
						});
				dialog.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								return;
							}
						});
				dialog.create();
				dialog.show();
			}
			return true;
		case R.id.sound_item:
			Common.playSound = false;
			return true;
		case R.id.sound_item_2:
			Common.playSound = true;
			return true;
		case R.id.restart_item:
			Common.initalCMap();
			Common.initalFinalVar();
			LocalPlayView chessBoardView = new LocalPlayView(
					LocalActivity.this, this);
			setContentView(chessBoardView);
			return true;
		case R.id.show_cmap:
			Common.initalDrawInfo();
			Common.showVisibleCMap = true;
			LocalPlayView chessBoardView2 = new LocalPlayView(
					LocalActivity.this, this);
			setContentView(chessBoardView2);
			break;
		case R.id.noshow_cmap:
			Common.initalDrawInfo();
			Common.showVisibleCMap = false;
			LocalPlayView chessBoardView3 = new LocalPlayView(
					LocalActivity.this, this);
			setContentView(chessBoardView3);
			break;
		case R.id.about_item:
			showAbout();
			return true;
		case R.id.quit_item:
			if (Common.isgamestart) {
				AlertDialog.Builder dialog2 = new AlertDialog.Builder(
						LocalActivity.this);
				dialog2.setIcon(R.drawable.ic_launcher);
				dialog2.setTitle("提示");
				dialog2.setMessage("正在单机游戏中，确定要退出吗？");
				dialog2.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog2,
									int which) {
								// TODO Auto-generated method stub
								LocalActivity.this.finish();
							}
						});
				dialog2.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog2,
									int which) {
								return;
							}
						});
				dialog2.create();
				dialog2.show();
			} else {
				LocalActivity.this.finish();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		return false;
	}

	/**
	 * Whether the file save in internal storage exists
	 * 
	 * @param fname
	 * @return
	 */
	public boolean fileExistance(String fname) {
		File file = getBaseContext().getFileStreamPath(fname);
		return file.exists();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 需要处理
			if (Common.isgamestart) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(
						LocalActivity.this);
				dialog.setIcon(R.drawable.ic_launcher);
				dialog.setTitle("提示");
				dialog.setMessage("正在单机游戏中，确定要退出吗？");
				dialog.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								LocalActivity.this.finish();
							}
						});
				dialog.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								return;
							}
						});
				dialog.create();
				dialog.show();
			} else {
				LocalActivity.this.finish();
			}
		}
		return false;
	}

	/**
	 * Show the "About" dialog
	 */
	private void showAbout() {
		StringBuffer sb = new StringBuffer();
		sb.append(getText(R.string.app_name));
		sb.append(getText(R.string.app_ver));
		sb.append("\n");
		sb.append(getText(R.string.app_author));
		sb.append("\n");
		sb.append(getText(R.string.about_info));
		AlertDialog.Builder builder = new AlertDialog.Builder(
				LocalActivity.this);

		builder.setTitle(getText(R.string.about))
				.setMessage(sb.toString())
				.setNegativeButton(getText(R.string.ok),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}
}
