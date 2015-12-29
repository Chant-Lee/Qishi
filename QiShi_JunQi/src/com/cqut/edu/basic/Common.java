package com.cqut.edu.basic;

import android.graphics.Bitmap;
import android.graphics.Matrix;

//公共变量类
public class Common {
	// 棋局像素
	public static float x_Margin = 1;// x边距 左
	public static float y_Margin = 1;// y边距 右
	// 棋子大小
	public static float chessHeight = 15;
	public static float chessWidth = 30;
	// 棋子间空隙
	public static float x_Padding = 15;
	public static float y_Padding = 17;
	// 五六行间的间距
	public static float five_six_Mag = 77;

	public static float sXtart = 0;// 棋盘的起始坐标
	public static float sYtart = 0;

	public static float height;
	public static float width;

	public static int i1;
	public static int j1;
	public static int i2;
	public static int j2;
	public static boolean showVisibleCMap = false;
	public static boolean playSound = false;
	public static boolean showEnemyFlag = false;
	public static int enemyFlag_j = 0;
	public static int gameover = 0;
	public static boolean otherPlayer = true;// true is computer false is
												// connect
	// 是否选中第一个棋子
	public static boolean selectFirstChess = false;
	public static boolean islayouting = true;// 是否正在布局
	public static boolean isgamestart = false;// 是否正在游戏
	public static boolean turnMyGo = true;// 是否轮到我方走

	// 蓝牙设置var
	// 对方是否准备状态
	public static boolean otherReady = false;
	public static boolean connected = false;

	public static float xZoom = 1F;// 缩放比例
	public static float yZoom = 1F;

	public static int wholeWidth = 480;
	public static int wholeHeight = 850;

	// Array Size of the board
	public static final int BOARD_WIDTH = 5;
	public static final int BOARD_HEIGHT = 12;

	public static final int NOCHESS = 0;
	public static final int NOTSHURE = -13;

	public static final int MY_SILING = 1;
	public static final int MY_JUN = 2;
	public static final int MY_SHI = 3;
	public static final int MY_LV = 4;
	public static final int MY_TUAN = 5;
	public static final int MY_YING = 6;
	public static final int MY_LIAN = 7;
	public static final int MY_PAI = 8;
	public static final int MY_GONG = 9;
	public static final int MY_ZHAN = 10;
	public static final int MY_DILEI = 11;
	public static final int MY_FLAG = 12;

	public static final int HIS_SILING = -1;
	public static final int HIS_JUN = -2;
	public static final int HIS_SHI = -3;
	public static final int HIS_LV = -4;
	public static final int HIS_TUAN = -5;
	public static final int HIS_YING = -6;
	public static final int HIS_LIAN = -7;
	public static final int HIS_PAI = -8;
	public static final int HIS_GONG = -9;
	public static final int HIS_ZHAN = -10;
	public static final int HIS_DILEI = -11;
	public static final int HIS_FLAG = -12;

	public static int[][] cMap = new int[BOARD_HEIGHT][BOARD_WIDTH];// 棋盘状态

	// 可视的双明的棋盘
	public static int[][] visableCMap = new int[BOARD_HEIGHT][BOARD_WIDTH];

	public static int[][] enemyCMap = new int[BOARD_HEIGHT][BOARD_WIDTH];

	public static final int[][] startCMap = new int[][] {
			{ NOTSHURE, NOTSHURE, NOTSHURE, NOTSHURE, NOTSHURE },
			{ NOTSHURE, NOTSHURE, NOTSHURE, NOTSHURE, NOTSHURE },
			{ NOTSHURE, NOCHESS, NOTSHURE, NOCHESS, NOTSHURE },
			{ NOTSHURE, NOTSHURE, NOCHESS, NOTSHURE, NOTSHURE },
			{ NOTSHURE, NOCHESS, NOTSHURE, NOCHESS, NOTSHURE },
			{ NOTSHURE, NOTSHURE, NOTSHURE, NOTSHURE, NOTSHURE },

			{ MY_SILING, MY_JUN, MY_LV, MY_GONG, MY_SHI },
			{ MY_LV, NOCHESS, MY_LIAN, NOCHESS, MY_ZHAN },
			{ MY_LIAN, MY_LIAN, NOCHESS, MY_GONG, MY_TUAN },
			{ MY_ZHAN, NOCHESS, MY_TUAN, NOCHESS, MY_YING },
			{ MY_SHI, MY_DILEI, MY_PAI, MY_GONG, MY_YING },
			{ MY_DILEI, MY_FLAG, MY_DILEI, MY_PAI, MY_PAI }, };
	public static float MapChess[][];// 棋盘概率状态

	// /inital the cmap
	public static void initalCMap() {
		for (int i = 0; i < BOARD_HEIGHT; i++)
			for (int j = 0; j < BOARD_WIDTH; j++) {
				cMap[i][j] = startCMap[i][j];
			}
	}

	// /inital the cmap
	public static void initalAICMap() {
		for (int i = 0; i < BOARD_HEIGHT; i++)
			for (int j = 0; j < BOARD_WIDTH; j++) {
				enemyCMap[i][j] = startCMap[i][j];
			}
	}

	// /inital the cmap
	public static void initalVisibleCMap() {
		for (int i = 0; i < BOARD_HEIGHT; i++)
			for (int j = 0; j < BOARD_WIDTH; j++) {
				if (cMap[i][j] != NOTSHURE)
					visableCMap[i][j] = cMap[i][j];
				else {
					visableCMap[i][j] = -enemyCMap[11 - i][4 - j];
				}
			}
	}

	// 处理走步结果
	public static void DealRes(String enemyGoString) {
		String[] spiltStrings = enemyGoString.split("-");
		Common.i1 = 11 - Integer.valueOf(spiltStrings[0]);
		Common.j1 = 4 - Integer.valueOf(spiltStrings[1]);
		int i_j[] = new int[2];
		i_j[0] = 11 - Integer.valueOf(spiltStrings[2]);
		i_j[1] = 4 - Integer.valueOf(spiltStrings[3]);

		int res = Judge.JudgeRes(Common.visableCMap, Common.i1, Common.j1,
				i_j[0], i_j[1]);
		if (res == 1) {
			Common.cMap[i_j[0]][i_j[1]] = Common.cMap[Common.i1][Common.j1];
			Common.cMap[Common.i1][Common.j1] = NOCHESS;
			Common.enemyCMap[11 - i_j[0]][4 - i_j[1]] = Common.enemyCMap[11 - Common.i1][4 - Common.j1];
			Common.enemyCMap[11 - Common.i1][4 - Common.j1] = NOCHESS;
			Common.visableCMap[i_j[0]][i_j[1]] = Common.visableCMap[Common.i1][Common.j1];
			Common.visableCMap[Common.i1][Common.j1] = NOCHESS;
		} else if (res == 0) {
			Common.cMap[Common.i1][Common.j1] = NOCHESS;
			Common.cMap[i_j[0]][i_j[1]] = NOCHESS;

			Common.enemyCMap[11 - Common.i1][4 - Common.j1] = NOCHESS;
			Common.enemyCMap[11 - i_j[0]][4 - i_j[1]] = NOCHESS;

			Common.visableCMap[Common.i1][Common.j1] = NOCHESS;
			Common.visableCMap[i_j[0]][i_j[1]] = NOCHESS;
		} else {
			Common.cMap[Common.i1][Common.j1] = NOCHESS;
			Common.visableCMap[Common.i1][Common.j1] = NOCHESS;

			Common.enemyCMap[11 - Common.i1][4 - Common.j1] = NOCHESS;
		}
	}

	// /inital the cmap
	public static void initalDrawInfo() {
		// 棋局像素
		x_Margin = 1;// x边距 左
		y_Margin = 1;// y边距 右
		// 棋子大小
		chessHeight = 15;
		chessWidth = 30;
		// 棋子间空隙
		x_Padding = 15;
		y_Padding = 17;
		// 五六行间的间距
		five_six_Mag = 77;
		sXtart = 0;// 棋盘的起始坐标
		sYtart = 0;
	}

	// /inital the cmap
	public static void initalFinalVar() {
		// 棋局像素
		x_Margin = 1;// x边距 左
		y_Margin = 1;// y边距 右
		// 棋子大小
		chessHeight = 15;
		chessWidth = 30;
		// 棋子间空隙
		x_Padding = 15;
		y_Padding = 17;
		// 五六行间的间距
		five_six_Mag = 77;
		showVisibleCMap = false;
		sXtart = 0;// 棋盘的起始坐标
		sYtart = 0;
		gameover = 0;
		showEnemyFlag = false;
		enemyFlag_j = 0;
		// 是否选中第一个棋子
		selectFirstChess = false;
		islayouting = true;// 是否正在布局
		isgamestart = false;// 是否正在游戏
		turnMyGo = true;// 是否轮到我方走
		otherReady = false;
		connected = false;
	}

	// / <summary>
	// / 通过坐标获取位置 2013 12 4 zj
	// / </summary>
	// / <param name="index_i">传入棋盘索引 列号 i</param>
	// / <param name="index_j">传出棋盘索引 行号 j</param>
	// / <returns>是否正确位置</returns>
	public static Boolean GetIndex(float x, float y, int[] i_j) {
		float temp_x = sXtart;
		float temp_y = sYtart;
		if (x < sXtart
				|| x > (sXtart + 5 * chessWidth + x_Padding * 4)
				|| y < sYtart
				|| y > (sYtart + 12 * chessHeight + 11 * y_Padding + five_six_Mag)) {
			return false;
		}
		// 横向
		int i = 0;
		for (; i < 5; i++) {
			if (x >= (temp_x + chessWidth * i)
					&& x <= (temp_x + chessWidth + chessWidth * i)) {
				i_j[1] = i;
				break;
			}
			temp_x = temp_x + x_Padding;
		}
		if (i == 5)
			return false;
		// 竖向
		int j = 0;
		for (; j < 6; j++) {
			if (y >= (temp_y + chessHeight * j)
					&& y <= (temp_y + chessHeight + chessHeight * j)) {
				i_j[0] = j;
				break;
			}
			temp_y = temp_y + y_Padding;
		}
		if (j == 6) {
			temp_y += five_six_Mag;// 上下两方 差距 多 10像素
			for (; j < 12; j++) {
				if (y >= (temp_y + chessHeight * j)
						&& y <= (temp_y + chessHeight + chessHeight * j)) {
					i_j[0] = j;
					break;
				}
				temp_y = temp_y + y_Padding;
			}
		}
		if (j == 12)
			return false;
		return true;
	}

	// / <summary>
	// / 通过棋盘坐标 获取 界面 x y 2013 12 4 zj
	// / </summary>
	// / <param name="x"></param>
	// / <param name="y"></param>
	// / <returns></returns>
	public static void GetPicXY(int j, int i, float[] x_y) {
		if (i == 0)
			x_y[0] = sXtart;
		else
			x_y[0] = chessWidth * i + x_Padding * i + sXtart;
		if (j == 0)
			x_y[1] = sYtart;
		else if (j >= 6)
			x_y[1] = chessHeight * j + y_Padding * j + sYtart + five_six_Mag;
		else
			x_y[1] = chessHeight * j + y_Padding * j + sYtart;
	}

	public static Bitmap scaleToFit(Bitmap bm, float fblRatio)// 缩放图片的方法
	{
		int width = bm.getWidth(); // 图片宽度
		int height = bm.getHeight();// 图片高度
		Matrix matrix = new Matrix();
		matrix.postScale((float) fblRatio, (float) fblRatio);// 图片等比例缩小为原来的fblRatio倍
		Bitmap bmResult = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
				true);// 声明位图
		return bmResult;
	}

	public static Bitmap scaleToFitChess(Bitmap bm, float fblRatio)// 缩放图片的方法
	{
		int width = bm.getWidth(); // 图片宽度
		int height = bm.getHeight();// 图片高度
		Matrix matrix = new Matrix();
		matrix.postScale(chessWidth * fblRatio * 1.4f / width, chessHeight
				* fblRatio * 1.4f / height);// 图片等比例缩小为原来的fblRatio倍
		Bitmap bmResult = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
				true);// 声明位图
		return bmResult;
	}
}
