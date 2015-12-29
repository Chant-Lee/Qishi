package com.cqut.edu.basic;

import android.graphics.Bitmap;
import android.graphics.Matrix;

//����������
public class Common {
	// �������
	public static float x_Margin = 1;// x�߾� ��
	public static float y_Margin = 1;// y�߾� ��
	// ���Ӵ�С
	public static float chessHeight = 15;
	public static float chessWidth = 30;
	// ���Ӽ��϶
	public static float x_Padding = 15;
	public static float y_Padding = 17;
	// �����м�ļ��
	public static float five_six_Mag = 77;

	public static float sXtart = 0;// ���̵���ʼ����
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
	// �Ƿ�ѡ�е�һ������
	public static boolean selectFirstChess = false;
	public static boolean islayouting = true;// �Ƿ����ڲ���
	public static boolean isgamestart = false;// �Ƿ�������Ϸ
	public static boolean turnMyGo = true;// �Ƿ��ֵ��ҷ���

	// ��������var
	// �Է��Ƿ�׼��״̬
	public static boolean otherReady = false;
	public static boolean connected = false;

	public static float xZoom = 1F;// ���ű���
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

	public static int[][] cMap = new int[BOARD_HEIGHT][BOARD_WIDTH];// ����״̬

	// ���ӵ�˫��������
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
	public static float MapChess[][];// ���̸���״̬

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

	// �����߲����
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
		// �������
		x_Margin = 1;// x�߾� ��
		y_Margin = 1;// y�߾� ��
		// ���Ӵ�С
		chessHeight = 15;
		chessWidth = 30;
		// ���Ӽ��϶
		x_Padding = 15;
		y_Padding = 17;
		// �����м�ļ��
		five_six_Mag = 77;
		sXtart = 0;// ���̵���ʼ����
		sYtart = 0;
	}

	// /inital the cmap
	public static void initalFinalVar() {
		// �������
		x_Margin = 1;// x�߾� ��
		y_Margin = 1;// y�߾� ��
		// ���Ӵ�С
		chessHeight = 15;
		chessWidth = 30;
		// ���Ӽ��϶
		x_Padding = 15;
		y_Padding = 17;
		// �����м�ļ��
		five_six_Mag = 77;
		showVisibleCMap = false;
		sXtart = 0;// ���̵���ʼ����
		sYtart = 0;
		gameover = 0;
		showEnemyFlag = false;
		enemyFlag_j = 0;
		// �Ƿ�ѡ�е�һ������
		selectFirstChess = false;
		islayouting = true;// �Ƿ����ڲ���
		isgamestart = false;// �Ƿ�������Ϸ
		turnMyGo = true;// �Ƿ��ֵ��ҷ���
		otherReady = false;
		connected = false;
	}

	// / <summary>
	// / ͨ�������ȡλ�� 2013 12 4 zj
	// / </summary>
	// / <param name="index_i">������������ �к� i</param>
	// / <param name="index_j">������������ �к� j</param>
	// / <returns>�Ƿ���ȷλ��</returns>
	public static Boolean GetIndex(float x, float y, int[] i_j) {
		float temp_x = sXtart;
		float temp_y = sYtart;
		if (x < sXtart
				|| x > (sXtart + 5 * chessWidth + x_Padding * 4)
				|| y < sYtart
				|| y > (sYtart + 12 * chessHeight + 11 * y_Padding + five_six_Mag)) {
			return false;
		}
		// ����
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
		// ����
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
			temp_y += five_six_Mag;// �������� ��� �� 10����
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
	// / ͨ���������� ��ȡ ���� x y 2013 12 4 zj
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

	public static Bitmap scaleToFit(Bitmap bm, float fblRatio)// ����ͼƬ�ķ���
	{
		int width = bm.getWidth(); // ͼƬ���
		int height = bm.getHeight();// ͼƬ�߶�
		Matrix matrix = new Matrix();
		matrix.postScale((float) fblRatio, (float) fblRatio);// ͼƬ�ȱ�����СΪԭ����fblRatio��
		Bitmap bmResult = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
				true);// ����λͼ
		return bmResult;
	}

	public static Bitmap scaleToFitChess(Bitmap bm, float fblRatio)// ����ͼƬ�ķ���
	{
		int width = bm.getWidth(); // ͼƬ���
		int height = bm.getHeight();// ͼƬ�߶�
		Matrix matrix = new Matrix();
		matrix.postScale(chessWidth * fblRatio * 1.4f / width, chessHeight
				* fblRatio * 1.4f / height);// ͼƬ�ȱ�����СΪԭ����fblRatio��
		Bitmap bmResult = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
				true);// ����λͼ
		return bmResult;
	}
}
