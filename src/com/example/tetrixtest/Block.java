package com.example.tetrixtest;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Block{
	
	private final int blockSize = 50; //블록의 한 도트의 크기
	
	private int currentX;
	private int currentY;

	
	private int[][] currentBlock;
	
	/*
	 * 블록 타입
	 */
	private final int[][] block01 = {
		{0, 1, 0, 0},
		{0, 1, 0, 0},
		{0, 1, 0, 0},
		{0, 1, 0, 0}};
	
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
	private final int[][] block07 = {
			{0, 1, 0, 0},
			{0, 1, 1, 0},
			{0, 0, 1, 0},
			{0, 0, 0, 0}};
	
		
	/*
	 * 블록 번호에 따라서 형성 및 복사
	 */
	public Block(int startX, int startY, int blockType) {
		this.currentX = startX+1;
		this.currentY = startY+1;

		
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
		} else if(blockType == 7) {
			this.currentBlock = (int[][])block07.clone();
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
				if(currentBlock[i][j] == 1) {
					canvas.drawRect((currentX * blockSize) + j * blockSize, (currentY * blockSize) + i * blockSize,
									(currentX * blockSize) + (1 + j) * blockSize, (currentY * blockSize) + (1 + i) * blockSize, paint);
				}
			}
		}
	}
	
	/*
	 * setter & getter
	 */
	

	public int getCurrentX() {
		return currentX;
	}
	public void setCurrentX(int currentX) {
		this.currentX = currentX;
		if(this.currentX < 0) {
			this.currentX = 0;
		} else if(this.currentX > 10) {
			this.currentX = 10;
		}
	}
	
	public int getCurrentY() {
		return currentY;
	}
	public void setCurrentY(int currentY) {
		this.currentY = currentY;
		if(this.currentY < 0) {
			this.currentY = 0;
		} else if(this.currentY > 25) {
			this.currentY = 25;
		}
	}
	
	public int[][] getCurrentBlock() {
		return currentBlock;
	}
	
	public void setCurrentBlock(int[][] block) {
		this.currentBlock = block.clone();
	}


}
