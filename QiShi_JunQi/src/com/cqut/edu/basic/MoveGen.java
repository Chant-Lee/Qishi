package com.cqut.edu.basic;


public class MoveGen {
	public static int isuse[][] = new int[12][5];
	public static int nPly;
	public static int nSide;
	public static Move m_MoveList[][] = new Move[8][80];
	public static int m_nMoveCount;

	// /���ɹ����߲� �����߲�����
	public static void SapperTread2(int cMap[][], int i1, int j1, int x, int y)// �����߷�������(�����㷨)
	{
		if (((cMap[i1][j1] < -1 || cMap[i1][j1] > -12) && nSide == 1)
				|| ((cMap[i1][j1] < 1 || cMap[i1][j1] > 12) && nSide == 0)
				&& isuse[i1][j1] == 0) {
			m_MoveList[nPly][m_nMoveCount] = new Move();
			m_MoveList[nPly][m_nMoveCount].From = new ManPosition();
			m_MoveList[nPly][m_nMoveCount].To = new ManPosition();
			m_MoveList[nPly][m_nMoveCount].From.x = x;
			m_MoveList[nPly][m_nMoveCount].From.y = y;
			m_MoveList[nPly][m_nMoveCount].To.x = i1;
			m_MoveList[nPly][m_nMoveCount].To.y = j1;
			m_nMoveCount++;
			if (cMap[i1][j1] == 0) {
				SapperTread(cMap, i1, j1, x, y);
			}
		}
	}

	public static int[][] Railway_Line2 = // ����1�� ����2�� ����3������4��
											// ������5��������6��������7��������8������9
	// ����11, û��0
	{ { 0, 0, 0, 0, 0 }, { 3, 1, 1, 1, 4 }, { 2, 0, 0, 0, 2 },
			{ 2, 0, 0, 0, 2 }, { 2, 0, 0, 0, 2 }, { 5, 1, 7, 1, 6 },

			{ 5, 1, 8, 1, 6 }, { 2, 0, 0, 0, 2 }, { 2, 0, 0, 0, 2 },
			{ 2, 0, 0, 0, 2 }, { 9, 1, 1, 1, 11 }, { 0, 0, 0, 0, 0 } };

	public static int AddOneMove(int nFromX, int nFromY, int nToX, int nToY) {
		m_MoveList[nPly][m_nMoveCount] = new Move();
		m_MoveList[nPly][m_nMoveCount].From = new ManPosition();
		m_MoveList[nPly][m_nMoveCount].To = new ManPosition();
		m_MoveList[nPly][m_nMoveCount].From.x = nFromX;
		m_MoveList[nPly][m_nMoveCount].From.y = nFromY;
		m_MoveList[nPly][m_nMoveCount].To.x = nToX;
		m_MoveList[nPly][m_nMoveCount].To.y = nToY;
		m_nMoveCount++;
		return m_nMoveCount;
	}

	// /ѡ�񹤱��߲����򡣡�
	public static void SapperTread(int cMap[][], int i, int j, int x, int y)// �����߷�������
	{
		isuse[i][j] = 1;
		switch (Railway_Line2[i][j]) {
		case 0:
			break;
		case 1:
			SapperTread2(cMap, i, j - 1, x, y);// ��
			SapperTread2(cMap, i, j + 1, x, y);// ��
			break;
		case 2:
			SapperTread2(cMap, i - 1, j, x, y);
			SapperTread2(cMap, i + 1, j, x, y);
			break;
		case 3:
			SapperTread2(cMap, i + 1, j, x, y);
			SapperTread2(cMap, i, j + 1, x, y);
			break;
		case 4:
			SapperTread2(cMap, i + 1, j, x, y);
			SapperTread2(cMap, i, j - 1, x, y);
			break;
		case 5:
			SapperTread2(cMap, i - 1, j, x, y);
			SapperTread2(cMap, i + 1, j, x, y);
			SapperTread2(cMap, i, j + 1, x, y);
			break;
		case 6:
			SapperTread2(cMap, i - 1, j, x, y);
			SapperTread2(cMap, i + 1, j, x, y);
			SapperTread2(cMap, i, j - 1, x, y);
			break;
		case 7:
			SapperTread2(cMap, i, j + 1, x, y);
			SapperTread2(cMap, i, j - 1, x, y);
			SapperTread2(cMap, i + 1, j, x, y);
			break;
		case 8:
			SapperTread2(cMap, i, j + 1, x, y);
			SapperTread2(cMap, i, j - 1, x, y);
			SapperTread2(cMap, i - 1, j, x, y);
			break;
		case 9:
			SapperTread2(cMap, i - 1, j, x, y);
			SapperTread2(cMap, i, j + 1, x, y);
			break;
		case 11:
			SapperTread2(cMap, i - 1, j, x, y);
			SapperTread2(cMap, i, j - 1, x, y);
			break;
		default:
			break;
		}
	}

	// /�õ�nPly ������кϷ��߲�
	public static int GetAllMoves(int cMap[][], int side, int nP) {
		int i, j;
		m_nMoveCount = 0;
		nSide = side;
		nPly = nP;
		// �ж� �õ����кϷ��߲�
		if (nSide == 0) {
			for (i = 0; i < 12; i++) {
				for (j = 0; j < 5; j++) {
					if (cMap[i][j] >= 1 && cMap[i][j] < 12)// �� ���巽������
					{
						if (cMap[i][j] == 9)// �����߷�����
						{
							for (int t_i = 0; t_i < 12; t_i++)
								for (int t_j = 0; t_j < 5; t_j++) {
									isuse[t_i][t_j] = 0;
								}
							SapperTread(cMap, i, j, i, j);
							continue;
						}

						if ((j == 0 || j == 4) && i > 0) {
							int tmpi = i;
							while (true) {
								tmpi++;
								if (Judge.IsValidMove(cMap, i, j, tmpi, j)) // ��
									AddOneMove(i, j, tmpi, j);// ��
								else
									break;
							}
						} else if (Judge.IsValidMove(cMap, i, j, i + 1, j)) // ��
							AddOneMove(i, j, i + 1, j);// ��

						if ((j == 0 || j == 4) && i < 11) {
							int tmpi = i;
							while (true) {
								tmpi--;
								if (Judge.IsValidMove(cMap, i, j, tmpi, j)) // ��
									AddOneMove(i, j, tmpi, j);// ��
								else
									break;

							}
						} else if (Judge.IsValidMove(cMap, i, j, i - 1, j))// ��
							AddOneMove(i, j, i - 1, j);

						if (Judge.IsValidMove(cMap, i, j, i + 1, j - 1))// ����
							AddOneMove(i, j, i + 1, j - 1);
						if (Judge.IsValidMove(cMap, i, j, i + 1, j + 1))// ����
							AddOneMove(i, j, i + 1, j + 1);
						if (Judge.IsValidMove(cMap, i, j, i - 1, j - 1))// ����
							AddOneMove(i, j, i - 1, j - 1);
						if (Judge.IsValidMove(cMap, i, j, i - 1, j + 1))// ����
							AddOneMove(i, j, i - 1, j + 1);
						if ((i == 1 || i == 10 || i == 5 || i == 6) && j < 4) {
							int tmpj = j;
							while (true) {
								tmpj++;
								if (Judge.IsValidMove(cMap, i, j, i, tmpj)) // ��
									AddOneMove(i, j, i, tmpj);// ��
								else
									break;

							}
						} else if (Judge.IsValidMove(cMap, i, j, i, j + 1))// ��
							AddOneMove(i, j, i, j + 1);
						if ((i == 1 || i == 10 || i == 5 || i == 6) && j > 0) {
							int tmpj = j;
							while (true) {
								tmpj--;
								if (Judge.IsValidMove(cMap, i, j, i, tmpj)) // ��
									AddOneMove(i, j, i, tmpj);// ��
								else
									break;

							}
						} else if (Judge.IsValidMove(cMap, i, j, i, j - 1))// ��
							AddOneMove(i, j, i, j - 1);
					}
				}
			}
		} else {
			for (i = 11; i >= 0; i--) {
				for (j = 0; j < 5; j++) {
					if (cMap[i][j] >= -1 && cMap[i][j] < -12)// �� ���巽������
					{
						if (cMap[i][j] == -9)// �����߷�����
						{
							for (int t_i = 0; t_i < 12; t_i++)
								for (int t_j = 0; t_j < 5; t_j++) {
									isuse[t_i][t_j] = 0;
								}
							SapperTread(cMap, i, j, i, j);
							continue;
						}
						if ((j == 0 || j == 4) && i > 0) {
							int tmpi = i;
							while (true) {
								tmpi++;
								if (Judge.IsValidMove(cMap, i, j, tmpi, j)) // ��
									AddOneMove(i, j, tmpi, j);// ��
								else
									break;

							}
						} else if (Judge.IsValidMove(cMap, i, j, i + 1, j)) // ��
							AddOneMove(i, j, i + 1, j);// ��

						if ((j == 0 || j == 4) && i < 11) {
							int tmpi = i;
							while (true) {
								tmpi--;
								if (Judge.IsValidMove(cMap, i, j, tmpi, j)) // ��
									AddOneMove(i, j, tmpi, j);// ��
								else
									break;
							}
						} else if (Judge.IsValidMove(cMap, i, j, i - 1, j))// ��
							AddOneMove(i, j, i - 1, j);

						if (Judge.IsValidMove(cMap, i, j, i + 1, j - 1))// ����
							AddOneMove(i, j, i + 1, j - 1);
						if (Judge.IsValidMove(cMap, i, j, i + 1, j + 1))// ����
							AddOneMove(i, j, i + 1, j + 1);
						if (Judge.IsValidMove(cMap, i, j, i - 1, j - 1))// ����
							AddOneMove(i, j, i - 1, j - 1);
						if (Judge.IsValidMove(cMap, i, j, i - 1, j + 1))// ����
							AddOneMove(i, j, i - 1, j + 1);
						if ((i == 1 || i == 10 || i == 5 || i == 6) && j < 4) {
							int tmpj = j;
							while (true) {
								tmpj++;
								if (Judge.IsValidMove(cMap, i, j, i, tmpj)) // ��
									AddOneMove(i, j, i, tmpj);// ��
								else
									break;

							}
						} else if (Judge.IsValidMove(cMap, i, j, i, j + 1))// ��
							AddOneMove(i, j, i, j + 1);
						if ((i == 1 || i == 10 || i == 5 || i == 6) && j > 0) {
							int tmpj = j;
							while (true) {
								tmpj--;
								if (Judge.IsValidMove(cMap, i, j, i, tmpj)) // ��
									AddOneMove(i, j, i, tmpj);// ��
								else
									break;

							}
						} else if (Judge.IsValidMove(cMap, i, j, i, j - 1))// ��
							AddOneMove(i, j, i, j - 1);
					}
				}
			}
		}
		// count = &m_nMoveCount;
		return m_nMoveCount;
	}

}
