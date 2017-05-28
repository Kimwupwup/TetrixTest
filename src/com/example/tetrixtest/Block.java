package com.example.tetrixtest;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Block{
	
	private final int blockSize = 50; //블록의 한 도트의 크기
	
	/* 현재 좌표를 저장해줄
	 * currentX, currentY
	 * 블럭의 종류를 저장할
	 * blockType
	 * */
	private int currentX;
	private int currentY;
	private int blockType;
	
	private int[][] currentBlock;
	
	/* 블록 타입*/
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
	public Block(int currentX, int currentY, int blockType) {
		this.currentX = currentX;		
		this.currentY = currentY;
		this.blockType = blockType;
		
		/* 받아온 블럭 종류에 따라 분류하여 현재 블럭을 저장한다.
		 * clone()함수를 사용하면 쉽게 배열을 복사 할 수 있다.
		 * */
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
	
	/* 블럭을 색칠하는 함수
	 * 맵에서는 블럭의 좌표는 currentX, currentY이다. 하지만 이것은 대체적인 위치이며
	 * 정확한 블럭의 도트(박스 한개)의 좌표는 위의 좌표와 블럭의 index번호로 이루어진다.
	 * 즉, currentBlock에 들어있는 1의 index와 currentX, currentY를 더하면
	 * 전체 맵에서의 1의 위치가 되는 것이다.
	 * */
	public void onDraw(Canvas canvas) {

		Paint paint = new Paint();
		paint.setARGB(255, 159, 159, 159);	//회색

		/* Canvas.drawRect(left, top, right, bottom, color)로 이루어져 있다.
		 * 50*50크기의 박스를 그릴 것이므로, 
		 * blockSize를 곱해준다.
		 * 도트 한개씩 그리면서 블럭을 만들어준다.*/
		for(int i = 0; i < currentBlock.length; i++) {
			for(int j =0; j < currentBlock[i].length; j++) {
				if(currentBlock[i][j] == 1) {
					canvas.drawRect((currentX + j) * blockSize, (currentY + i) * blockSize,
									(currentX + 1 + j) * blockSize, (currentY + 1 + i) * blockSize, paint);
				}
			}
		}
	}
	
	/* 시계 반대방향으로 돌리는 함수
	 * k행에 있는 값들을 k열에 옮기면 된다.
	 * */
	public void rotate() {
		if(blockType != 4) {
			int[][] temp = new int[4][4];
			
			for(int i=0;i<temp.length;i++) {
				for(int j=0;j<temp[i].length;j++) {
					temp[3-j][i] = currentBlock[i][j];
				}
			}
			currentBlock = temp.clone();
		}
	}

	/* getter & setter*/
	public int getBlockType() {
		return blockType;
	}
	public void setBlockType(int blockType) {
		this.blockType = blockType;
	}

	public int getCurrentX() {
		return currentX;
	}
	public void setCurrentX(int currentX) {
		this.currentX = currentX;
	}
	
	public int getCurrentY() {
		return currentY;
	}
	public void setCurrentY(int currentY) {
		this.currentY = currentY;
	}
	
	public int[][] getCurrentBlock() {
		return currentBlock;
	}
	
	public void setCurrentBlock(int[][] block) {
		this.currentBlock = block.clone();
	}
}
