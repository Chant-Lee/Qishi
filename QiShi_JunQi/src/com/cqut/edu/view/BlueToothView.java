package com.cqut.edu.view;

import static com.cqut.edu.basic.Common.HIS_DILEI;
import static com.cqut.edu.basic.Common.HIS_FLAG;
import static com.cqut.edu.basic.Common.HIS_GONG;
import static com.cqut.edu.basic.Common.HIS_JUN;
import static com.cqut.edu.basic.Common.HIS_LIAN;
import static com.cqut.edu.basic.Common.HIS_LV;
import static com.cqut.edu.basic.Common.HIS_PAI;
import static com.cqut.edu.basic.Common.HIS_SHI;
import static com.cqut.edu.basic.Common.HIS_SILING;
import static com.cqut.edu.basic.Common.HIS_TUAN;
import static com.cqut.edu.basic.Common.HIS_YING;
import static com.cqut.edu.basic.Common.HIS_ZHAN;
import static com.cqut.edu.basic.Common.MY_DILEI;
import static com.cqut.edu.basic.Common.MY_FLAG;
import static com.cqut.edu.basic.Common.MY_GONG;
import static com.cqut.edu.basic.Common.MY_JUN;
import static com.cqut.edu.basic.Common.MY_LIAN;
import static com.cqut.edu.basic.Common.MY_LV;
import static com.cqut.edu.basic.Common.MY_PAI;
import static com.cqut.edu.basic.Common.MY_SHI;
import static com.cqut.edu.basic.Common.MY_SILING;
import static com.cqut.edu.basic.Common.MY_TUAN;
import static com.cqut.edu.basic.Common.MY_YING;
import static com.cqut.edu.basic.Common.MY_ZHAN;
import static com.cqut.edu.basic.Common.NOCHESS;
import static com.cqut.edu.basic.Common.NOTSHURE;
import static com.cqut.edu.basic.Common.scaleToFit;
import static com.cqut.edu.basic.Common.scaleToFitChess;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;

import com.example.qishi_junqi.R;

import com.cqut.edu.activity.BToothPlayActivity;
import com.cqut.edu.basic.Judge;
import com.cqut.edu.basic.Common;

public class BlueToothView extends View {

	private BToothPlayActivity activity;

	public BlueToothView(Context context, BToothPlayActivity activity) {// 构造器
		super(context);
		mContext = context;
		this.activity = activity;// 得到activity引用
		Common.initalCMap();
		Common.initalAICMap();
		Common.initalVisibleCMap();
		initBitmap();// 初始化图片资源
	}

	// 棋盘背景
	private Bitmap background;

	private Bitmap notshurBitmap;
	private Bitmap piecemask;

	private Bitmap armycommander;
	private Bitmap armycommander_r;
	private Bitmap armyflag_r;
	private Bitmap armyflag;
	private Bitmap battalioncommander;
	private Bitmap battalioncommander_r;
	private Bitmap bomb;
	private Bitmap bomb_r;
	private Bitmap brigadier;
	private Bitmap brigadier_r;
	private Bitmap commander;
	private Bitmap commander_r;
	private Bitmap companycommander;
	private Bitmap companycommander_r;
	private Bitmap divisioncommander;
	private Bitmap divisioncommander_r;
	private Bitmap engineeringtroops;
	private Bitmap engineeringtroops_r;
	private Bitmap landmine;
	private Bitmap landmine_r;
	private Bitmap platoonleader;
	private Bitmap platoonleader_r;
	private Bitmap regimentacommander;
	private Bitmap regimentalcommander_r;

	public void initBitmap() {

		float xZoom = Common.xZoom;

		background = scaleToFit(BitmapFactory.decodeResource(getResources(),
				R.drawable.chessboard), xZoom);
		notshurBitmap = scaleToFitChess(BitmapFactory.decodeResource(
				getResources(), R.drawable.piecebackground), xZoom);
		piecemask = scaleToFitChess(BitmapFactory.decodeResource(
				getResources(), R.drawable.piecemask), xZoom + 0.2f);

		armycommander = scaleToFitChess(BitmapFactory.decodeResource(
				getResources(), R.drawable.armycommander), xZoom);
		armycommander_r = scaleToFitChess(BitmapFactory.decodeResource(
				getResources(), R.drawable.armycommander_r), xZoom);
		armyflag_r = scaleToFitChess(BitmapFactory.decodeResource(
				getResources(), R.drawable.armyflag_r), xZoom);
		armyflag = scaleToFitChess(BitmapFactory.decodeResource(getResources(),
				R.drawable.armyflag), xZoom);
		battalioncommander = scaleToFitChess(BitmapFactory.decodeResource(
				getResources(), R.drawable.battalioncommander), xZoom);
		battalioncommander_r = scaleToFitChess(BitmapFactory.decodeResource(
				getResources(), R.drawable.battalioncommander_r), xZoom);
		bomb = scaleToFitChess(
				BitmapFactory.decodeResource(getResources(), R.drawable.bomb),
				xZoom);
		bomb_r = scaleToFitChess(
				BitmapFactory.decodeResource(getResources(), R.drawable.bomb_r),
				xZoom);
		brigadier = scaleToFitChess(BitmapFactory.decodeResource(
				getResources(), R.drawable.brigadier), xZoom);
		brigadier_r = scaleToFitChess(BitmapFactory.decodeResource(
				getResources(), R.drawable.brigadier_r), xZoom);
		commander = scaleToFitChess(BitmapFactory.decodeResource(
				getResources(), R.drawable.commander), xZoom);
		commander_r = scaleToFitChess(BitmapFactory.decodeResource(
				getResources(), R.drawable.commander_r), xZoom);
		companycommander = scaleToFitChess(BitmapFactory.decodeResource(
				getResources(), R.drawable.companycommander), xZoom);
		companycommander_r = scaleToFitChess(BitmapFactory.decodeResource(
				getResources(), R.drawable.companycommander_r), xZoom);
		divisioncommander = scaleToFitChess(BitmapFactory.decodeResource(
				getResources(), R.drawable.divisioncommander), xZoom);
		divisioncommander_r = scaleToFitChess(BitmapFactory.decodeResource(
				getResources(), R.drawable.divisioncommander_r), xZoom);
		engineeringtroops = scaleToFitChess(BitmapFactory.decodeResource(
				getResources(), R.drawable.engineeringtroops), xZoom);
		engineeringtroops_r = scaleToFitChess(BitmapFactory.decodeResource(
				getResources(), R.drawable.engineeringtroops_r), xZoom);
		landmine = scaleToFitChess(BitmapFactory.decodeResource(getResources(),
				R.drawable.landmine), xZoom);
		landmine_r = scaleToFitChess(BitmapFactory.decodeResource(
				getResources(), R.drawable.landmine_r), xZoom);
		platoonleader = scaleToFitChess(BitmapFactory.decodeResource(
				getResources(), R.drawable.platoonleader), xZoom);
		platoonleader_r = scaleToFitChess(BitmapFactory.decodeResource(
				getResources(), R.drawable.platoonleader_r), xZoom);
		regimentacommander = scaleToFitChess(BitmapFactory.decodeResource(
				getResources(), R.drawable.regimentacommander), xZoom);
		regimentalcommander_r = scaleToFitChess(BitmapFactory.decodeResource(
				getResources(), R.drawable.regimentalcommander_r), xZoom);

		Common.y_Margin = 0.5f * (Common.height - background.getHeight());
		Common.x_Margin = 0.5f * (Common.width - background.getWidth());
		Common.sXtart = Common.x_Margin + 1;
		Common.sYtart = Common.y_Margin + 3;
		Common.chessHeight = notshurBitmap.getHeight();
		Common.chessWidth = notshurBitmap.getWidth();
		Common.x_Padding = (background.getWidth() - Common.x_Margin * 2 - Common.chessWidth * 5) / 4;
		Common.y_Padding = notshurBitmap.getHeight() + 3;
		Common.five_six_Mag = background.getHeight() - Common.chessHeight * 12
				- Common.y_Padding * 10 - 40;
	}

	private MediaPlayer mediaPlayer;
	private Context mContext;

	/**
	 * Play sound for fun
	 */
	private void playSound(final int soundID) {
		new Thread() {
			public void run() {
				mediaPlayer = MediaPlayer.create(mContext, soundID);
				mediaPlayer.start();
			}
		}.start();
	}

	public void onDraw(Canvas canvas) {
		float[] x_y = new float[2];
		// 画棋盘背景
		canvas.drawBitmap(background, Common.x_Margin, Common.y_Margin, null);
		// 选中棋子背景
		if (Common.selectFirstChess) {
			Common.GetPicXY(Common.i1, Common.j1, x_y);
			canvas.drawBitmap(piecemask, x_y[0] - 2, x_y[1] - 2, null);
		}
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 5; j++) {
				Common.GetPicXY(i, j, x_y);
				switch (Common.showVisibleCMap ? Common.visableCMap[i][j]
						: Common.cMap[i][j]) {
				case NOTSHURE:
					canvas.drawBitmap(notshurBitmap, x_y[0], x_y[1], null);
					break;
				case NOCHESS:
					break;

				case MY_SILING:
					canvas.drawBitmap(commander, x_y[0], x_y[1], null);
					break;
				case MY_JUN:
					canvas.drawBitmap(armycommander, x_y[0], x_y[1], null);
					break;
				case MY_SHI:
					canvas.drawBitmap(divisioncommander, x_y[0], x_y[1], null);
					break;
				case MY_LV:
					canvas.drawBitmap(brigadier, x_y[0], x_y[1], null);
					break;
				case MY_TUAN:
					canvas.drawBitmap(regimentacommander, x_y[0], x_y[1], null);
					break;
				case MY_YING:
					canvas.drawBitmap(battalioncommander, x_y[0], x_y[1], null);
					break;
				case MY_LIAN:
					canvas.drawBitmap(companycommander, x_y[0], x_y[1], null);
					break;
				case MY_PAI:
					canvas.drawBitmap(platoonleader, x_y[0], x_y[1], null);
					break;
				case MY_GONG:
					canvas.drawBitmap(engineeringtroops, x_y[0], x_y[1], null);
					break;
				case MY_ZHAN:
					canvas.drawBitmap(bomb, x_y[0], x_y[1], null);
					break;
				case MY_DILEI:
					canvas.drawBitmap(landmine, x_y[0], x_y[1], null);
					break;
				case MY_FLAG:
					canvas.drawBitmap(armyflag, x_y[0], x_y[1], null);
					break;

				case HIS_SILING:
					canvas.drawBitmap(commander_r, x_y[0], x_y[1], null);
					break;
				case HIS_JUN:
					canvas.drawBitmap(armycommander_r, x_y[0], x_y[1], null);
					break;
				case HIS_SHI:
					canvas.drawBitmap(divisioncommander_r, x_y[0], x_y[1], null);
					break;
				case HIS_LV:
					canvas.drawBitmap(brigadier_r, x_y[0], x_y[1], null);
					break;
				case HIS_TUAN:
					canvas.drawBitmap(regimentalcommander_r, x_y[0], x_y[1],
							null);
					break;
				case HIS_YING:
					canvas.drawBitmap(battalioncommander_r, x_y[0], x_y[1],
							null);
					break;
				case HIS_LIAN:
					canvas.drawBitmap(companycommander_r, x_y[0], x_y[1], null);
					break;
				case HIS_PAI:
					canvas.drawBitmap(platoonleader_r, x_y[0], x_y[1], null);
					break;
				case HIS_GONG:
					canvas.drawBitmap(engineeringtroops_r, x_y[0], x_y[1], null);
					break;
				case HIS_ZHAN:
					canvas.drawBitmap(bomb_r, x_y[0], x_y[1], null);
					break;
				case HIS_DILEI:
					canvas.drawBitmap(landmine_r, x_y[0], x_y[1], null);
					break;
				case HIS_FLAG:
					canvas.drawBitmap(armyflag_r, x_y[0], x_y[1], null);
					break;
				}
			}
		}
	}

	// /显示相关信息
	public void ShowSomeThing(String adbString) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);

		builder.setTitle("提示").setMessage(adbString)
				.setNegativeButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		int[] i_j = new int[2];

		switch (e.getAction()) {
		// 触摸屏幕时刻
		case MotionEvent.ACTION_DOWN:

			if (Common.GetIndex(e.getX(), e.getY(), i_j)
					&& Judge.JudgeIsInBoard(i_j[0], i_j[1])) {
				// 布局中
				if (Common.islayouting) {
					if (Common.cMap[i_j[0]][i_j[1]] > 0)
						if (!Common.selectFirstChess) {
							if (Common.playSound)
								playSound(R.raw.move);
							Common.selectFirstChess = true;
							Common.i1 = i_j[0];
							Common.j1 = i_j[1];

						} else {// 交换棋子
							if ((Common.cMap[Common.i1][Common.j1] == MY_ZHAN && i_j[0] == 6)
									|| (Common.cMap[i_j[0]][i_j[1]] == MY_ZHAN && Common.i1 == 6)) {
								if (Common.playSound)
									playSound(R.raw.noxiaqi);
								ShowSomeThing("布局错误：炸弹不能放第一排！");
								// Toast.makeText(
								// mContext.getApplicationContext(),
								// "布局错误：炸弹不能放第一排！！", Toast.LENGTH_SHORT)
								// .show();
							} else if ((Common.cMap[Common.i1][Common.j1] == MY_DILEI && i_j[0] < 10)
									|| (Common.cMap[i_j[0]][i_j[1]] == MY_DILEI && Common.i1 < 10)) {
								if (Common.playSound)
									playSound(R.raw.noxiaqi);
								ShowSomeThing("布局错误：地雷只能放在倒数两排！");
								// Toast.makeText(
								// mContext.getApplicationContext(),
								// "布局错误：地雷只能放在倒数两排！", Toast.LENGTH_SHORT)
								// .show();
							} else if ((Common.cMap[i_j[0]][i_j[1]] == MY_FLAG && !(Common.i1 == 11 && (Common.j1 == 1 || Common.j1 == 3)))
									|| (Common.cMap[Common.i1][Common.j1] == MY_FLAG && !(i_j[0] == 11 && (i_j[1] == 1 || i_j[1] == 3)))) {
								if (Common.playSound)
									playSound(R.raw.noxiaqi);
								ShowSomeThing("布局错误：军棋必须在大本营！");
							} else {
								if (Common.playSound)
									playSound(R.raw.move);
								int tmp = Common.cMap[Common.i1][Common.j1];
								Common.cMap[Common.i1][Common.j1] = Common.cMap[i_j[0]][i_j[1]];
								Common.cMap[i_j[0]][i_j[1]] = tmp;

								tmp = Common.visableCMap[Common.i1][Common.j1];
								Common.visableCMap[Common.i1][Common.j1] = Common.visableCMap[i_j[0]][i_j[1]];
								Common.visableCMap[i_j[0]][i_j[1]] = tmp;

							}
							Common.selectFirstChess = false;
						}
				} else if (Common.isgamestart && Common.turnMyGo) {
					if (!Common.selectFirstChess
							&& Common.cMap[i_j[0]][i_j[1]] > 0) {
						if (Common.playSound)
							playSound(R.raw.kill);
						Common.selectFirstChess = true;
						Common.i1 = i_j[0];
						Common.j1 = i_j[1];

					} else {// 行棋判断 // 行棋中

						if (Judge.IsValidMove(Common.cMap, Common.i1,
								Common.j1, i_j[0], i_j[1])) {
							String message = "go=" + Common.i1 + "-"
									+ Common.j1 + "-" + i_j[0] + "-" + i_j[1];
							activity.sendBtoothMessage(message);
							DealRes(i_j);
							Common.turnMyGo = false;
						} else if (Common.cMap[Common.i1][Common.j1] == MY_GONG) {// 工兵是可以飞的
							if (Judge.JudgeGongBinFly(Common.cMap,
									Common.i1, Common.j1, i_j[0], i_j[1])) {
								String message = "go=" + Common.i1 + "-"
										+ Common.j1 + "-" + i_j[0] + "-"
										+ i_j[1];
								activity.sendBtoothMessage(message);
								DealRes(i_j);
								// 工兵正常飞行 允许
								Common.turnMyGo = false;
							}
						}
						// 标记未选中棋子
						Common.selectFirstChess = false;
					}
				}
			}
			break;
		default:
			break;
		}

		if (Common.enemyFlag_j != 0)
			Common.cMap[0][Common.enemyFlag_j] = -12;

		if (Common.gameover != 0) {
			if (Common.gameover == 1) {
				ShowSomeThing("恭喜你，赢了！");
				// Toast.makeText(mContext.getApplicationContext(), "恭喜你，赢了！",
				// Toast.LENGTH_SHORT).show();
				if (Common.playSound)
					playSound(R.raw.win);
			} else {
				// Toast.makeText(mContext.getApplicationContext(), "你输了！",
				// Toast.LENGTH_SHORT).show();
				ShowSomeThing("你输了，继续加油！");
				if (Common.playSound)
					playSound(R.raw.win);
			}
			Common.isgamestart = false;
		}
		this.invalidate();// 刷新棋盘
		return super.onTouchEvent(e);
	}

	// 处理走步结果
	public void DealRes(int i_j[]) {
		int res = Judge.JudgeRes(Common.visableCMap, Common.i1, Common.j1,
				i_j[0], i_j[1]);
		if (res == 1) {
			Common.cMap[i_j[0]][i_j[1]] = Common.cMap[Common.i1][Common.j1];
			Common.cMap[Common.i1][Common.j1] = NOCHESS;
			Common.enemyCMap[11 - i_j[0]][4 - i_j[1]] = Common.enemyCMap[11 - Common.i1][4 - Common.j1];
			Common.enemyCMap[11 - Common.i1][4 - Common.j1] = NOCHESS;
			Common.visableCMap[i_j[0]][i_j[1]] = Common.visableCMap[Common.i1][Common.j1];
			Common.visableCMap[Common.i1][Common.j1] = NOCHESS;
			if (Common.playSound)
				playSound(R.raw.kill);
		} else if (res == 0) {
			Common.cMap[Common.i1][Common.j1] = NOCHESS;
			Common.cMap[i_j[0]][i_j[1]] = NOCHESS;

			Common.enemyCMap[11 - Common.i1][4 - Common.j1] = NOCHESS;
			Common.enemyCMap[11 - i_j[0]][4 - i_j[1]] = NOCHESS;

			Common.visableCMap[Common.i1][Common.j1] = NOCHESS;
			Common.visableCMap[i_j[0]][i_j[1]] = NOCHESS;
			if (Common.playSound)
				playSound(R.raw.equal);
		} else {
			Common.cMap[Common.i1][Common.j1] = NOCHESS;
			Common.visableCMap[Common.i1][Common.j1] = NOCHESS;

			Common.enemyCMap[11 - Common.i1][4 - Common.j1] = NOCHESS;

			if (Common.playSound)
				playSound(R.raw.loss);
		}
	}
}
