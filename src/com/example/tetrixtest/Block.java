package com.example.tetrixtest;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Block {
	
	private final int blockSize = 50; //블록의 한 도트의 크기
	
	private int currentX;
	private int currentY;
	
	private int[][] currentBlock;
	
	/*
	 * 블록 타입
	 */
	private final int[][] block01 = {
		{0, 0, 1, 0},
		{0, 0, 1, 0},
		{0, 0, 1, 0},
		{0, 0, 1, 0}};
	
	private final int[][] block02 = {
		{0, 1, 0, 0},
		{0, 1, 0, 0},
		{0, 1, 1, 0},
		{0, 0, 0, 0}};
	
	private final int[][] block03 = {
		{0, 0, 1, 0},
		{0, 1, 1, 0},
		{0, 1, 0, 0},
		{0, 0, 0, 0}};
	
	private final int[][] block04 = {
		{0, 1, 1, 0},
		{0, 1, 1, 0},
		{0, 0, 0, 0},
		{0, 0, 0, 0}};
	
	private final int[][] block05 = {
		{0, 0, 1, 0},
		{0, 0, 1, 0},
		{0, 1, 1, 0},
		{0, 0, 0, 0}};
	
	private final int[][] block06 = {
		{0, 1, 0, 0},
		{0, 1, 1, 0},
		{0, 1, 0, 0},
		{0, 0, 0, 0}};
		
	/*
	 * 블록 번호에 따라서 형성 및 복사
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
	 * block 색칠하기
	 */
	public void onDraw(Canvas canvas) {

		Paint paint = new Paint();
		paint.setARGB(255, 100, 100, 100);	//회색
		
		
		/*
		 * 1을 찾아 읽으면서 블록사이즈 만큼 색칠한다.
		 */
		for(int i = 0; i < currentBlock.length; i++) {
			for(int j =0; j < currentBlock[i].length; j++) {
				if(currentBlock[j][i] == 1) {
					canvas.drawRect(currentX + i * blockSize, currentY + j * blockSize,
									currentX + (1 + i) * blockSize, currentY + (1 + j) * blockSize, paint);
				}
			}
		}
	}
	
	public int getCurrentX() {
		return currentX;
	}
	public void setCurrentX(int currentX) {
		this.currentX = currentX;
		if(this.currentX < 0) {
			this.currentX = 0;
		} else if(this.currentX > 800) {
			this.currentX = 800;
		}
	}
	
	public int getCurrentY() {
		return currentY;
	}
	public void setCurrentY(int currentY) {
		this.currentY = currentY;
		if(this.currentY < 0) {
			this.currentY = 0;
		} else if(this.currentY > 1500) {
			this.currentY = 1500;
		}
	}
}
