package com.example.tetrixtest;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Block {
	
	private final int blockSize = 50; //������ �� ��Ʈ�� ũ��
	
	private int currentX;
	private int currentY;
	
	private int[][] currentBlock;
	
	/*
	 * ���� Ÿ��
	 */
	private final int[][] block01 = {
		{0, 0, 1, 0},
		{0, 0, 1, 0},
		{0, 0, 1, 0},
		{0, 0, 1, 0}};
	
	private final int[][] block02 = {
		{0, 0, 1, 0},
		{0, 0, 1, 0},
		{0, 0, 1, 1},
		{0, 0, 0, 0}};
	
	private final int[][] block03 = {
		{0, 0, 1, 0},
		{0, 1, 1, 0},
		{0, 1, 0, 0},
		{0, 0, 0, 0}};
	
	private final int[][] block04 = {
		{0, 0, 1, 1},
		{0, 0, 1, 1},
		{0, 0, 0, 0},
		{0, 0, 0, 0}};
	
	private final int[][] block05 = {
		{0, 0, 1, 0},
		{0, 0, 1, 0},
		{0, 1, 1, 0},
		{0, 0, 0, 0}};
	
	private final int[][] block06 = {
		{0, 0, 1, 0},
		{0, 0, 1, 1},
		{0, 0, 1, 0},
		{0, 0, 0, 0}};
		
	/*
	 * ���� ��ȣ�� ���� ���� �� ����
	 */
	public Block(int startX, int startY, int blockType) {
		this.currentX = startX;
		this.currentY = startY;
		if(blockType == 1) {
			this.currentBlock = (int[][])block01.clone();
		} else if(blockType == 2) {
			this.currentBlock = (int[][])block02.clone();
		} else if(blockType == 3) {
			this.currentBlock = (int[][])block03.clone();
		} else if(blockType == 4) {
			this.currentBlock = (int[][])block04.clone();
		} else if(blockType == 5) {
			this.currentBlock = (int[][])block05.clone();
		} else if(blockType == 6) {
			this.currentBlock = (int[][])block06.clone();
		} else {
			return;
		}
	}
	
	/*
	 * block ��ĥ�ϱ�
	 */
	public void onDraw(Canvas canvas) {

		Paint paint = new Paint();
		paint.setARGB(255, 0, 0, 0);	//������
		
		/*
		 * 1�� ã�� �����鼭 ���ϻ����� ��ŭ ��ĥ�Ѵ�.
		 */
		for(int i = 0; i < currentBlock.length; i++) {
			for(int j =0; j < currentBlock[i].length; j++) {
				if(currentBlock[i][j] == 1) {
					canvas.drawRect(currentX + i * blockSize, currentY + j * blockSize,
									currentX + (1 + i) * blockSize, currentY + (1 + j) * blockSize, paint);
				}
			}
		}
	}
	
	
}