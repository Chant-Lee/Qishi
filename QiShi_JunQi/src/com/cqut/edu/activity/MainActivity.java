package com.cqut.edu.activity;
import static com.cqut.edu.basic.Common.height;
import static com.cqut.edu.basic.Common.width;
import static com.cqut.edu.basic.Common.xZoom;
import static com.cqut.edu.basic.Common.yZoom;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cqut.edu.basic.Common;
import com.example.qishi_junqi.R;

public class MainActivity extends Activity{
	private static final String TAG = "MAIN";

	public void initPm() {
		// ��ȡ��Ļ�ֱ���
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int tempHeight = (int) (height = dm.heightPixels);
		int tempWidth = (int) (width = dm.widthPixels);
		//
		if (tempHeight > tempWidth) {
			height = tempHeight;
			width = tempWidth;
		} else {
			height = tempWidth;
			width = tempHeight;
		}
		float zoomx = width / 350;
		float zoomy = height / 720;
		if (zoomx > zoomy) {
			xZoom = yZoom = zoomy;

		} else {
			xZoom = yZoom = zoomx;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e(TAG, "+++ ON CREATE +++");
		// ����ȫ����ʾ
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// ���ò���
		setContentView(R.layout.activity_choice);
		// // ���ú���ģʽ
		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		// // hd.sendEmptyMessage(0);
		// setVolumeControlStream(AudioManager.STREAM_MUSIC);//
		// ��Ϸ������ֻ�����ý������,��������ͨ������
		// initSound();

		initPm();
		/******** ������Ϸ ***********/
		Button localPlayButton = (Button) findViewById(R.id.bt_choice_loacl);
		localPlayButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Common.otherPlayer = true;
				// ����������ϷActivity
				Intent localIntent = new Intent(getApplication(),
						LocalActivity.class);
				Common.initalFinalVar();
				startActivity(localIntent);
			}
		});

		/******** ������Ϸ ***********/
		Button netPlayButton = (Button) findViewById(R.id.bt_choice_netplay);
		netPlayButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Common.otherPlayer = false;
				// ��������������ϷActivity
				Intent blueToothIntent = new Intent(getApplication(),
						BToothPlayActivity.class);
				Common.initalFinalVar();
				startActivity(blueToothIntent);
			}
		});

		/****** ��Ϸ�˳� ********/
		Button myexit = (Button) findViewById(R.id.bt_choice_exit);
		myexit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(
						MainActivity.this);
				dialog.setIcon(R.drawable.ic_launcher);
				dialog.setTitle("��ʾ");
				dialog.setMessage("ȷ���˳�������");
				dialog.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								System.exit(0);
							}
						});
				dialog.setNegativeButton("ȡ��",
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
		});
	}
}
