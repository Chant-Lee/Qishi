package com.cqut.edu.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qishi_junqi.R;

import com.cqut.edu.activity.BToothPlayActivity;
import com.cqut.edu.activity.DeviceListActivity;
import com.cqut.edu.basic.Common;
import com.cqut.edu.bluetooth.BluetoothPlayService;
import com.cqut.edu.view.BlueToothView;

public class BToothPlayActivity extends Activity {
	// Debugging
	private static final String TAG = "Bluetooth";
	private static final boolean D = true;

	// Message types sent from the BluetoothPlayService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;

	// Key names received from the BluetoothPlayService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";

	// Intent request codes
	private static final int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_ENABLE_BT = 2;

	// Name of the connected device
	private String mConnectedDeviceName = null;

	// String buffer for outgoing messages
	private StringBuffer mOutStringBuffer;
	// Local Bluetooth adapter
	private BluetoothAdapter mBluetoothAdapter = null;
	// Member object for the Play services
	private BluetoothPlayService mPlayService = null;

	BlueToothView chessBoardView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (D)
			Log.e(TAG, "+++ ON CREATE +++");

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main_layout);// 显示单机游戏界面
		// Set up the window layout
		// requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		chessBoardView = new BlueToothView(BToothPlayActivity.this, this);
		setContentView(chessBoardView);
		// getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
		// R.layout.custom_title);

		// Set up the custom title
		// mTitle = (TextView) findViewById(R.id.title_left_text);
		// mTitle.setText(R.string.app_name);
		// mTitle = (TextView) findViewById(R.id.title_right_text);

		// Get local Bluetooth adapter
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		// If the adapter is null, then Bluetooth is not supported
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, "蓝牙不可用", Toast.LENGTH_LONG).show();
			finish();
			return;
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		if (D)
			Log.e(TAG, "++ ON START ++");

		// If BT is not on, request that it be enabled.
		// setupPlay() will then be called during onActivityResult
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
			// Otherwise, setup the Play session
		} else {
			if (mPlayService == null)
				setupPlay();
		}
	}

	@Override
	public synchronized void onResume() {
		super.onResume();
		if (D)
			Log.e(TAG, "+ ON RESUME +");

		// Performing this check in onResume() covers the case in which BT was
		// not enabled during onStart(), so we were paused to enable it...
		// onResume() will be called when ACTION_REQUEST_ENABLE activity
		// returns.
		if (mPlayService != null) {
			// Only if the state is STATE_NONE, do we know that we haven't
			// started already
			if (mPlayService.getState() == BluetoothPlayService.STATE_NONE) {
				// Start the Bluetooth Play services
				mPlayService.start();
			}
		}
	}

	private void setupPlay() {
		Log.d(TAG, "setupPlay()");

		// Initialize the BluetoothPlayService to perform bluetooth connections
		mPlayService = new BluetoothPlayService(this, mHandler);

		// Initialize the buffer for outgoing messages
		mOutStringBuffer = new StringBuffer("");
	}

	@Override
	public synchronized void onPause() {
		super.onPause();
		if (D)
			Log.e(TAG, "- ON PAUSE -");
	}

	@Override
	public void onStop() {
		super.onStop();
		if (D)
			Log.e(TAG, "-- ON STOP --");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// Stop the Bluetooth Play services
		if (mPlayService != null)
			mPlayService.stop();
		if (D)
			Log.e(TAG, "--- ON DESTROY ---");
	}

	private void ensureDiscoverable() {
		if (D)
			Log.d(TAG, "ensure discoverable");
		if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
			Intent discoverableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(
					BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			startActivity(discoverableIntent);
		}
	}

	/**
	 * Sends a message.
	 * 
	 * @param message
	 *            A string of text to send.
	 */
	public boolean sendBtoothMessage(String message) {
		// Check that we're actually connected before trying anything
		if (mPlayService.getState() != BluetoothPlayService.STATE_CONNECTED) {
			Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT)
					.show();
			return false;
		}

		// Check that there's actually something to send
		if (message.length() > 0) {
			// Get the message bytes and tell the BluetoothPlayService to write
			byte[] send = message.getBytes();
			mPlayService.write(send);
			// Reset out string buffer to zero and clear the edit text field
			mOutStringBuffer.setLength(0);
			// mOutEditText.setText(mOutStringBuffer);
			return true;
		}
		return false;
	}

	// 获取本机的布局
	private String getCMapString() {
		String cmapString = "lay=";
		for (int i = 6; i < 12; i++) {
			for (int j = 0; j < 5; j++) {
				cmapString += Common.cMap[i][j] + "-";
			}
		}
		return cmapString;
	}

	// 设置本对方的布局
	private boolean setEnemyCMap(String cmapString) {
		String[] spiltStrings = cmapString.split("-");
		int spiltIndex = 0;
		for (int i = 6; i < 12; i++) {
			for (int j = 0; j < 5; j++) {
				Common.visableCMap[11 - i][4 - j] = -Integer
						.valueOf(spiltStrings[spiltIndex]);
				spiltIndex++;
			}
		}
		return true;
	}

	// Toast/显示相关信息
	private void showToast(CharSequence charSequence) {
		Toast.makeText(this, charSequence, Toast.LENGTH_SHORT).show();
	}

	// /显示相关信息
	public void ShowSomeThing(String adbString) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle("提示").setMessage(adbString)
				.setNegativeButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	// The action listener for the EditText widget, to listen for the return key
	private TextView.OnEditorActionListener mWriteListener = new TextView.OnEditorActionListener() {
		public boolean onEditorAction(TextView view, int actionId,
				KeyEvent event) {
			// If the action is a key-up event on the return key, send the
			// message
			if (actionId == EditorInfo.IME_NULL
					&& event.getAction() == KeyEvent.ACTION_UP) {
				String message = view.getText().toString();
				sendBtoothMessage(message);
			}
			if (D)
				Log.i(TAG, "END onEditorAction");
			return true;
		}
	};

	// The Handler that gets information back from the BluetoothPlayService
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_STATE_CHANGE:
				if (D)
					Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
				switch (msg.arg1) {
				case BluetoothPlayService.STATE_CONNECTED:
					// mTitle.setText(R.string.title_connected_to);
					showToast(getText(R.string.title_connected_to)
							+ mConnectedDeviceName);
					Common.connected = true;
					if (!Common.islayouting) {
						if (sendBtoothMessage(getCMapString())) {
							if (Common.otherReady) {
								Common.turnMyGo = false;
								if (Common.playSound)
									playSound(R.raw.gamestart);
								Common.isgamestart = true;
								showToast("游戏开始，对方先手！");
							} else
								Common.turnMyGo = true;
						}
					}
					// mTitle.append(mConnectedDeviceName);
					// mConversationArrayAdapter.clear();
					break;
				case BluetoothPlayService.STATE_CONNECTING:
					// mTitle.setText(R.string.title_connecting);
					showToast(getText(R.string.title_connecting));
					break;
				case BluetoothPlayService.STATE_LISTEN:
				case BluetoothPlayService.STATE_NONE:
					// mTitle.setText(R.string.title_not_connected);
					showToast(getText(R.string.title_not_connected));
					break;
				}
				break;
			// 写入信息
			case MESSAGE_WRITE:
				// byte[] writeBuf = (byte[]) msg.obj;
				// // construct a string from the buffer
				// String writeMessage = new String(writeBuf);
				// mConversationArrayAdapter.add("Me:  " + writeMessage);
				break;
			// 读取信息
			case MESSAGE_READ:
				byte[] readBuf = (byte[]) msg.obj;
				// construct a string from the valid bytes in the buffer
				String readMessage = new String(readBuf, 0, msg.arg1);
				// mConversationArrayAdapter.add(mConnectedDeviceName + ":  "
				// + readMessage);
				// command=
				// lay = 1,2,3,4,5,6,....//接收到对方布局
				// go = 5,0,6,0//对方移动
				// msg = 信息内容
				String[] spiltStrings = readMessage.split("=");
				if (spiltStrings[0].equals("lay")) {
					if (Common.playSound)
						playSound(R.raw.equal);
					showToast("对方已准备！");
					Common.otherReady = true;
					setEnemyCMap(spiltStrings[1]);
					if (Common.islayouting)
						Common.turnMyGo = false;
					else {
						if (Common.playSound)
							playSound(R.raw.gamestart);
						Common.turnMyGo = true;
						Common.isgamestart = true;
						Toast.makeText(getApplicationContext(), "游戏开始,我方行棋！",
								Toast.LENGTH_SHORT).show();
					}
				} else if (spiltStrings[0].equals("go")) {
					if (Common.playSound)
						playSound(R.raw.move);
					// 对方移动
					Common.DealRes(spiltStrings[1]);
					Common.turnMyGo = true;
					if (Common.enemyFlag_j != 0)
						Common.cMap[0][Common.enemyFlag_j] = -12;
					chessBoardView.invalidate();
					if (Common.gameover != 0) {
						if (Common.gameover == 1) {
							ShowSomeThing("恭喜你，赢了！");
							// Toast.makeText(mContext.getApplicationContext(),
							// "恭喜你，赢了！",
							// Toast.LENGTH_SHORT).show();
							if (Common.playSound)
								playSound(R.raw.win);
						} else {
							// Toast.makeText(mContext.getApplicationContext(),
							// "你输了！",
							// Toast.LENGTH_SHORT).show();
							ShowSomeThing("你输了，继续加油！");
							if (Common.playSound)
								playSound(R.raw.win);
						}
						Common.isgamestart = false;
					} else {
						showToast("我方行棋！");
					}
				} else {
					if (Common.playSound)
						playSound(R.raw.equal);
					showToast(readMessage);
				}
				break;
			case MESSAGE_DEVICE_NAME:
				// save the connected device's name
				mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
				Toast.makeText(getApplicationContext(),
						"Connected to " + mConnectedDeviceName,
						Toast.LENGTH_SHORT).show();
				break;
			case MESSAGE_TOAST:
				Toast.makeText(getApplicationContext(),
						msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
						.show();
				break;
			}
		}
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (D)
			Log.d(TAG, "onActivityResult " + resultCode);
		switch (requestCode) {
		case REQUEST_CONNECT_DEVICE:
			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) {
				// Get the device MAC address
				String address = data.getExtras().getString(
						DeviceListActivity.EXTRA_DEVICE_ADDRESS);
				// Get the BLuetoothDevice object
				BluetoothDevice device = mBluetoothAdapter
						.getRemoteDevice(address);
				// Attempt to connect to the device
				mPlayService.connect(device);
			}
			break;
		case REQUEST_ENABLE_BT:
			// When the request to enable Bluetooth returns
			if (resultCode == Activity.RESULT_OK) {
				// Bluetooth is now enabled, so set up a Play session
				setupPlay();
			} else {
				// User did not enable Bluetooth or an error occured
				Log.d(TAG, "BT not enabled");
				Toast.makeText(this, R.string.bt_not_enabled_leaving,
						Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}

	// 播放器
	private MediaPlayer mediaPlayer;

	/**
	 * Play sound for fun
	 */
	private void playSound(final int soundID) {
		new Thread() {
			public void run() {
				mediaPlayer = MediaPlayer.create(BToothPlayActivity.this,
						soundID);
				mediaPlayer.start();
			}
		}.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.option_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.scan:
			// Launch the DeviceListActivity to see devices and do scan
			Intent serverIntent = new Intent(this, DeviceListActivity.class);
			startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
			return true;
		case R.id.discoverable:
			// Ensure this device is discoverable by others
			ensureDiscoverable();
			return true;
		case R.id.open_sound_item:
			Common.playSound = true;
			return true;
		case R.id.close_sound_item:
			Common.playSound = false;
			return true;
		case R.id.show_cmap:
			Common.initalDrawInfo();
			Common.showVisibleCMap = true;
			BlueToothView chessBoardView2 = new BlueToothView(
					BToothPlayActivity.this, this);
			setContentView(chessBoardView2);
			break;
		case R.id.noshow_cmap:
			Common.initalDrawInfo();
			Common.showVisibleCMap = false;
			BlueToothView chessBoardView3 = new BlueToothView(
					BToothPlayActivity.this, this);
			setContentView(chessBoardView3);
			break;
		// 完成布局
		case R.id.layout_ok_item:
			if (Common.islayouting) {
				if (Common.playSound)
					playSound(R.raw.noxiaqi);
				AlertDialog.Builder dialog = new AlertDialog.Builder(
						BToothPlayActivity.this);
				dialog.setIcon(R.drawable.ic_launcher);
				dialog.setTitle("提示");
				dialog.setMessage("确定完成布局了吗？");
				dialog.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Common.islayouting = false;
								// 发送完成的布局
								if (sendBtoothMessage(getCMapString())) {
									Common.connected = true;
									if (Common.otherReady) {
										Common.turnMyGo = false;
										Common.isgamestart = true;
										if (Common.playSound)
											playSound(R.raw.gamestart);
										showToast("游戏开始，对方先手！");
									} else
										Common.turnMyGo = true;
								}
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
			// 退出游戏
		case R.id.quit_item:
			if (!Common.islayouting) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(
						BToothPlayActivity.this);
				dialog.setIcon(R.drawable.ic_launcher);
				dialog.setTitle("提示");
				dialog.setMessage("确定要退出蓝牙联机游戏吗？");
				dialog.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								BToothPlayActivity.this.finish();
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
		case R.id.send_item:
			// 编辑
			final EditText meditText = new EditText(this);
			new AlertDialog.Builder(this)
					.setTitle("请输入聊天内容：")
					.setIcon(R.drawable.ic_launcher)
					.setView(meditText)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									// Toast.makeText(BToothPlayActivity.this,
									// meditText.getText().toString(),
									// Toast.LENGTH_LONG).show();
									String message = meditText.getText()
											.toString();
									// 发送信息
									sendBtoothMessage(message);
								}
							}).setNegativeButton("取消", null).show();

			// AlertDialog.Builder dialog = new AlertDialog.Builder(
			// BToothPlayActivity.this);
			// // 添加属性
			// dialog.setIcon(R.drawable.ic_launcher);
			// dialog.setTitle("Sidney军棋");
			// dialog.setMessage("请输入您的聊天内容：");
			// // 获得样式填充器
			// LayoutInflater inflater = BToothPlayActivity.this
			// .getLayoutInflater();
			// // 填充样式
			// dialog.setView(inflater.inflate(R.layout.cell, null));
			// // mOutEditText = (EditText)
			// findViewById(R.id.editText_sendText);
			// // mOutEditText.setOnEditorActionListener(mWriteListener);
			// // 确定按钮
			// dialog.setPositiveButton("发送",
			// new DialogInterface.OnClickListener() {
			// @Override
			// public void onClick(DialogInterface dialog, int which) {
			// // Toast.makeText(BToothPlayActivity.this,
			// // "您点击了确定按钮",
			// // Toast.LENGTH_LONG).show();
			// // Send a message using content of the edit text
			// // widget
			// EditText view = (EditText) findViewById(R.id.editText_sendText);
			// String message = view.getText().toString();
			// // sendMessage(message);
			// Toast.makeText(BToothPlayActivity.this, message,
			// Toast.LENGTH_LONG).show();
			// }
			// });
			// // 取消按钮
			// dialog.setNegativeButton("取消",
			// new DialogInterface.OnClickListener() {
			// @Override
			// public void onClick(DialogInterface dialog, int which) {
			// Toast.makeText(BToothPlayActivity.this, "您点击了取消按钮",
			// Toast.LENGTH_LONG).show();
			// }
			// });
			//
			// // 创建
			// dialog.create();
			// // 显示
			// dialog.show();
			return true;
		}
		return false;
	}
}