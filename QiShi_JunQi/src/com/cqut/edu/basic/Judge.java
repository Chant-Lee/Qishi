package com.cqut.edu.basic;

import static com.cqut.edu.basic.Common.HIS_DILEI;
import static com.cqut.edu.basic.Common.HIS_FLAG;
import static com.cqut.edu.basic.Common.MY_DILEI;
import static com.cqut.edu.basic.Common.MY_FLAG;
import static com.cqut.edu.basic.Common.MY_SILING;
import static com.cqut.edu.basic.Common.NOCHESS;
import com.cqut.edu.basic.Common;
//军棋裁判
public class Judge {
	public static boolean JudgeIsInBoard(int i, int j) {
		return i >= 0 && i < 12 && j >= 0 && j < 5;
	}

	public static int isuse[][] = new int[12][5];
	public static int[][] Railway_Line = // 左右1， 上下2， 下右3，下左4，
	// 上下右5，上下左6，左右下7，左右上8，上右9
	// 上左11, 没有0
	{ { 0, 0, 0, 0, 0 }, { 3, 1, 1, 1, 4 }, { 2, 0, 0, 0, 2 },
			{ 2, 0, 0, 0, 2 }, { 2, 0, 0, 0, 2 }, { 5, 1, 7, 1, 6 },

			{ 5, 1, 8, 1, 6 }, { 2, 0, 0, 0, 2 }, { 2, 0, 0, 0, 2 },
			{ 2, 0, 0, 0, 2 }, { 9, 1, 1, 1, 11 }, { 0, 0, 0, 0, 0 } };
	// 是否寻找到正确走步
	public static boolean findReghtFly = false;
	// 是否寻找到正确走步
	public static int nSide = 0;

	public static int move_i2;
	public static int move_j2;

	// /生成工兵走步 加入走步链表
	public static void SapperTread2(int cMap[][], int i1, int j1, int x, int y)// 工兵走法生成器(附带算法)
	{
		if (((cMap[i1][j1] < -1 || cMap[i1][j1] > -12) && nSide == 1)
				|| ((cMap[i1][j1] < 1 || cMap[i1][j1] > 12) && nSide == 0)
				&& isuse[i1][j1] == 0) {
			if (move_i2 == i1 && move_j2 == j1)
				findReghtFly = true;
			if (cMap[i1][j1] == NOCHESS && !findReghtFly) {
				SapperTread(cMap, i1, j1, x, y);
			}
		}
	}

	// /选择工兵走步方向。。
	public static void SapperTread(int cMap[][], int i, int j, int x, int y)// 工兵走法生成器
	{
		isuse[i][j] = 1;
		switch (Railway_Line[i][j]) {
		case 0:
			break;
		case 1:
			if (!findReghtFly)
				SapperTread2(cMap, i, j - 1, x, y);// 左
			if (!findReghtFly)
				SapperTread2(cMap, i, j + 1, x, y);// 右
			break;
		case 2:
			if (!findReghtFly)
				SapperTread2(cMap, i - 1, j, x, y);
			if (!findReghtFly)
				SapperTread2(cMap, i + 1, j, x, y);
			break;
		case 3:
			if (!findReghtFly)
				SapperTread2(cMap, i + 1, j, x, y);
			if (!findReghtFly)
				SapperTread2(cMap, i, j + 1, x, y);
			break;
		case 4:
			if (!findReghtFly)
				SapperTread2(cMap, i + 1, j, x, y);
			if (!findReghtFly)
				SapperTread2(cMap, i, j - 1, x, y);
			break;
		case 5:
			if (!findReghtFly)
				SapperTread2(cMap, i - 1, j, x, y);
			if (!findReghtFly)
				SapperTread2(cMap, i + 1, j, x, y);
			if (!findReghtFly)
				SapperTread2(cMap, i, j + 1, x, y);
			break;
		case 6:
			if (!findReghtFly)
				SapperTread2(cMap, i - 1, j, x, y);
			if (!findReghtFly)
				SapperTread2(cMap, i + 1, j, x, y);
			if (!findReghtFly)
				SapperTread2(cMap, i, j - 1, x, y);
			break;
		case 7:
			if (!findReghtFly)
				SapperTread2(cMap, i, j + 1, x, y);
			if (!findReghtFly)
				SapperTread2(cMap, i, j - 1, x, y);
			if (!findReghtFly)
				SapperTread2(cMap, i + 1, j, x, y);
			break;
		case 8:
			if (!findReghtFly)
				SapperTread2(cMap, i, j + 1, x, y);
			if (!findReghtFly)
				SapperTread2(cMap, i, j - 1, x, y);
			if (!findReghtFly)
				SapperTread2(cMap, i - 1, j, x, y);
			break;
		case 9:
			if (!findReghtFly)
				SapperTread2(cMap, i - 1, j, x, y);
			if (!findReghtFly)
				SapperTread2(cMap, i, j + 1, x, y);
			break;
		case 11:
			if (!findReghtFly)
				SapperTread2(cMap, i - 1, j, x, y);
			if (!findReghtFly)
				SapperTread2(cMap, i, j - 1, x, y);
			break;
		default:
			break;
		}
	}

	// /得到nPly 层的所有合法走步
	public static boolean JudgeGongBinFly(int cMap[][], int i, int j, int i2,
			int j2) {
		move_i2 = i2;
		move_j2 = j2;
		findReghtFly = false;
		// 初始化标记数组
		for (int t_i = 0; t_i < 12; t_i++)
			for (int t_j = 0; t_j < 5; t_j++) {
				isuse[t_i][t_j] = 0;
			}
		// 开始递归寻路
		SapperTread(cMap, i, j, i, j);
		return findReghtFly;
	}

	// 判断走步是否合法
	public static boolean IsValidMove(int cMap[][], int nFromX, int nFromY,
			int nToX, int nToY) {
		if (nToX < 0 || nToY < 0 || nToX > 11 || nToY > 4)// 边界
			return false;
		if (cMap[nToX][nToY] != NOCHESS
				&& (((nToX == 9 || nToX == 7 || nToX == 2 || nToX == 4) && (nToY == 1 || nToY == 3)) || ((nToX == 3 || nToX == 8) && nToY == 2)))
			return false;
		if (cMap[nFromX][nFromY] == MY_DILEI
				|| cMap[nFromX][nFromY] == HIS_DILEI
				|| ((nFromX == 0 || nFromX == 11) && (nFromY == 1 || nFromY == 3)))
			return false; // 地雷和大本营棋子不能动
		if (Math.abs(cMap[nFromX][nFromY] - cMap[nToX][nToY]) < 12
				&& cMap[nToX][nToY] != NOCHESS
				&& cMap[nToX][nToY] != (cMap[nFromX][nFromY] > MY_SILING ? HIS_FLAG
						: MY_FLAG))
			return false;// //下一步不是敌方棋子或不是空或不是敌方军旗 非法
		if (Math.abs(nFromX - nToX) == 1
				&& !(((nFromX == 5 && nToX == 6) || (nFromX == 6 && nToX == 5)) && (nFromY == 1 || nFromY == 3))
				&& nFromY == nToY)
			return true;// 同列移动一步 山界除外 合法
		if (Math.abs(nFromY - nToY) == 1 && nFromX == nToX)
			return true;// 同行移动一步 合法
		if (Math.abs(nFromX - nToX) == 1
				&& Math.abs(nFromY - nToY) == 1
				&& ((nFromX > 0 && nFromX < 6 && nToX > 0 && nToX < 6 && (nFromX + nFromY) % 2 == 1) || (nFromX > 5
						&& nFromX < 11 && nToX > 5 && nToX < 11 && (nFromX + nFromY) % 2 == 0)))
			return true; // 斜着走一步 1-5 6-11 行 合法
		// 运行到此判断情况只有 轨道情况了 工兵走步

		if (nFromX == nToX
				&& (nFromX == 1 || nFromX == 5 || nFromX == 10 || nFromX == 6))// 横着的轨道上
		{
			for (int j = (nFromY < nToY ? nFromY + 1 : nFromY - 1); (nFromY < nToY) ? j < nToY
					: j > nToY; j = (nFromY < nToY ? j + 1 : j - 1)) {
				if (cMap[nFromX][j] != NOCHESS)
					return false;
			}
			return true;
		}
		if (nFromY == nToY && (nFromY == 0 || nFromY == 4))// 竖着的轨道上
		{
			for (int i = (nFromX < nToX ? nFromX + 1 : nFromX - 1); (nFromX < nToX) ? i < nToX
					: i > nToX; i = (nFromX < nToX ? i + 1 : i - 1)) {
				if (cMap[i][nFromY] != NOCHESS)
					return false;
			}
			return true;
		}
		return false;
	}

	// 判断走步的结果 1 表示move-from 赢 ；0 表示一起死 -1表示 move-to 赢
	public static int JudgeRes(int cMap[][], int i1, int j1, int i2, int j2) {

		// kill 或者走到空处
		if ((Math.abs(cMap[i1][j1]) < Math.abs(cMap[i2][j2]) && Math
				.abs(cMap[i2][j2]) <= 9) || cMap[i2][j2] == 0) {
			return 1;
		}
		// 炸弹
		if ((Math.abs(cMap[i1][j1]) == 10 && cMap[i2][j2] != 0)
				|| (cMap[i1][j1] != 0 && Math.abs(cMap[i2][j2]) == 10)) {
			if (cMap[i2][j2] == -1)
				Common.enemyFlag_j = cMap[0][1] == -12 ? 1 : 3;
			return 0;
		}
		// 双方棋相同
		if (cMap[i1][j1] + cMap[i2][j2] == 0) {
			if (cMap[i2][j2] == -1)
				Common.enemyFlag_j = cMap[0][1] == -12 ? 1 : 3;
			return 0;
		}

		// 工兵挖地雷
		if (Math.abs(cMap[i2][j2]) == 11 && Math.abs(cMap[i1][j1]) == 9) {
			return 1;
		}
		// 军旗被吃
		if (Math.abs(cMap[i2][j2]) == 12) {
			if (cMap[i2][j2] == 12)
				Common.gameover = -1;
			else if (cMap[i2][j2] == -12)
				Common.gameover = 1;
			return 1;
		}
		return -1;
	}
}
