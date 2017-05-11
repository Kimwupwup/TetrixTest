package com.example.tetrixtest;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;




public class BlockControl extends View{
	
	private int score = 0;
	private int nextStage = 100;
	private int stage = 1;
	private TextView mTextScore;
	private TextView mTextStage;
	Block block;
	Block nextBlock;
	
	
	private final int blockSize = 50;
	
	/*���� ���̴� ���� Ȯ�� �ϱ����ؼ� ���� ������ ��*/
	private int[][] currentMap = {
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
		};
	
	private long dropTimer = 1000;
	
	private Timer timer;
	
	public BlockControl(Context context, AttributeSet attrs) {
		super(context, attrs);
		block = new Block(3, 0, (int)(Math.random()*7) +1);
		nextBlock = new Block(15, 1, (int)(Math.random()*7) +1);
		
		/*Ÿ�̸Ӹ� �̿��ؼ� ���� �ֱ⸶�� ��ĭ�� ������.*/
		timer = new Timer(true);
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {			
				
				
				/*�浹�ÿ� �����ϱ� ���ؼ� ���� recover*/
				int reX = block.getCurrentX();
				int reY = block.getCurrentY();
				int[][] recovBlock = block.getCurrentBlock().clone();
				
				/*��ĭ ������*/
				block.setCurrentY(block.getCurrentY() + 1);
				
				
				/*�浹�� �Ͼ�ÿ� ������ ���·� �����Ѵ�.*/
				if(isCollide() == true) {
					block.setCurrentX(reX);
					block.setCurrentY(reY);
					block.setCurrentBlock(recovBlock);
					
					block.setCurrentY(block.getCurrentY() + 1);
					if(isCollide() == true) {
						block.setCurrentY(reY);
						setMap();
						deleteLine();
						
						block = new Block(3, 0, nextBlock.getBlockType());
						nextBlock = new Block(15, 1, (int)(Math.random()*7) +1);
						
					}
				}
				/*Handler�� �̿��ؼ� Timer���� ȭ���� �ʱ�ȭ�Ͽ� ���� �����̴� ��ó�� �����ش�.*/
				mHandler.sendEmptyMessage(0);
			}
			
		}, 1000, dropTimer);
	}
	
	/*Handler*/
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if(msg.what == 0) {
				mTextScore.setText("Score : " + score);
				mTextStage.setText("Stage : " + stage);
				invalidate();
			}
		}
	};

	/*onDraw ���� ȭ�鿡 ������ش�.*/
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();
		
		paint.setARGB(255, 200, 200, 200);	//���� ȸ��
		
		canvas.drawRect(50,50,650,1300, paint);	//��Ʈ������ ����Ǵ� ��		
		canvas.drawRect(730, 50, 1030, 350, paint); //���� ���� ������ ��
		
		
		nextBlock.onDraw(canvas);
		block.onDraw(canvas);
		onDrawMap(canvas);
	}
	
	/*�� �ϼ��� */
	public void deleteLine() {
		int countBlock = 0;
		int countScore = 0;
		
		for(int i = 1; i < currentMap.length-1; i++) {
			for(int j = 1;j < currentMap[i].length-1; j++) {
				if(currentMap[i][j] == 1) {
					countBlock++;
					if(countBlock == 12) {
						for(int k = 1; k < currentMap[i].length-1; k++) {
							currentMap[i][k] = 0;
						}
						countScore++;
						pullLine(i);
					}
				}
			}
			countBlock = 0;
		}
		calScore(countScore);
	}
	
	public void pullLine(int i) {
		for(;i > 1; i--) {
			for(int j = 1; j < currentMap[i].length-1; j++) {
				currentMap[i][j] = currentMap[i-1][j];
			}
		}
	}
	
	/*���� �浹�ϴ����� �˾ƺ��� �Լ�.*/
	public boolean isCollide() {
		int[][] currentBlock = block.getCurrentBlock();
		int chkX = 0;
		int chkY = 0;

		for(int i = 0; i < currentBlock.length; i++) {
			for(int j = 0;j < currentBlock[i].length; j++) {
				if(currentBlock[i][j] == 1) {
					chkX = j + block.getCurrentX();
					chkY = i + block.getCurrentY();
										
					if(currentMap[chkY][chkX] == 1) return true;
				}
			}
		}
		return false;
	}
	
	/*���� ���� ���� ���Ӱ� �������ִ� �Լ�*/
	public void setMap() {
		int[][] currentBlock = block.getCurrentBlock();
		int chkX = 0;
		int chkY = 0;

		for(int i = 0; i < currentBlock.length; i++) {
			for(int j = 0;j < currentBlock[i].length; j++) {
				if(currentBlock[i][j] == 1) {
					chkX = j + block.getCurrentX();
					chkY = i + block.getCurrentY();

					currentMap[chkY][chkX] = 1;					
				}
			}
		}
	}
	
	/*���� ���� �׷��ش�*/
	public void onDrawMap(Canvas canvas) {
		
		Paint paint = new Paint();
		paint.setARGB(255, 50, 50, 50);
		for(int i = 1; i < currentMap.length-1; i++) {
			for(int j = 1;j < currentMap[i].length-1; j++) {
				if(currentMap[i][j] == 1) {
					canvas.drawRect(j*blockSize, i*blockSize, (j+1)*blockSize, (i+1)*blockSize, paint);
				}
			}
		}
	}
	
	/*��ư�� ���� �̵�*/
	public void moveLeft() {
		block.setCurrentX(block.getCurrentX() - 1);
		/*�浹�� �Ͼ�ÿ� ������ ���·� �����Ѵ�.*/
		if(isCollide() == true) {
			block.setCurrentX(block.getCurrentX() + 1);
		}
		/*Handler�� �̿��ؼ� Timer���� ȭ���� �ʱ�ȭ�Ͽ� ���� �����̴� ��ó�� �����ش�.*/
		mHandler.sendEmptyMessage(0);
		
	}
	public void moveRight() {
		block.setCurrentX(block.getCurrentX() + 1);
		
		/*�浹�� �Ͼ�ÿ� ������ ���·� �����Ѵ�.*/
		if(isCollide() == true) {
			block.setCurrentX(block.getCurrentX() - 1);
			
		}
		/*Handler�� �̿��ؼ� Timer���� ȭ���� �ʱ�ȭ�Ͽ� ���� �����̴� ��ó�� �����ش�.*/
		mHandler.sendEmptyMessage(0);
	}
	
	public void moveDown() {
		block.setCurrentY(block.getCurrentY() + 1);
		
		/*�浹�� �Ͼ�ÿ� ������ ���·� �����Ѵ�.*/
		if(isCollide() == true) {
			
			block.setCurrentY(block.getCurrentY() - 1);
			
			block.setCurrentY(block.getCurrentY() + 1);
			if(isCollide() == true) {
				block.setCurrentY(block.getCurrentY() - 1);
				setMap();
				deleteLine();
				
				block = new Block(3, 0, nextBlock.getBlockType());
				nextBlock = new Block(15, 1, (int)(Math.random()*7) +1);
			}
		}
		/*Handler�� �̿��ؼ� Timer���� ȭ���� �ʱ�ȭ�Ͽ� ���� �����̴� ��ó�� �����ش�.*/
		mHandler.sendEmptyMessage(0);
	}
	
	/*DownŰ�� ��� ������� �ѹ��� ��������.*/
	public void moveQuickDown() {
		block.setCurrentY((currentMap.length - 3));
		
		/*�浹�� �Ͼ�ÿ� ������ ���·� �����Ѵ�.*/
		while(isCollide()) {
			block.setCurrentY(block.getCurrentY() - 1);
		}

		block.setCurrentY(block.getCurrentY() + 1);
		if(isCollide() == true) {
			block.setCurrentY(block.getCurrentY() - 1);
			setMap();
			deleteLine();
			
			block = new Block(3, 0, nextBlock.getBlockType());
			nextBlock = new Block(15, 1, (int)(Math.random()*7) +1);
		}
		/*Handler�� �̿��ؼ� Timer���� ȭ���� �ʱ�ȭ�Ͽ� ���� �����̴� ��ó�� �����ش�.*/
		mHandler.sendEmptyMessage(0);
	}
	
	public void rotate() {
		int[][] recovBlock = block.getCurrentBlock();
		block.rotate();
		/*�浹�� �Ͼ�ÿ� ������ ���·� �����Ѵ�.*/
		if(isCollide() == true) {
			block.setCurrentBlock(recovBlock);
		}
		/*Handler�� �̿��ؼ� Timer���� ȭ���� �ʱ�ȭ�Ͽ� ���� �����̴� ��ó�� �����ش�.*/
		mHandler.sendEmptyMessage(0);
	}
	
	/*TextView�� �������� ���� �Լ�*/
	public void setTextView(TextView score, TextView stage) {
		mTextScore = score;
		mTextStage = stage;
	}

	/*������ ��� �ϱ� ���� �Լ�*/
	public void calScore(int num) {
		/*���� ���� ����ŭ ������ �޸��Ѵ�.*/
		switch(num) {
		case 0:
			break;
		case 1:
			score += 10; break;
		case 2:
			score += 30; break;
		case 3:
			score += 50; break;
		case 4: 
			score += 70; break;			
		}
		calStage();
	}
	
	/*���������� �ø������� �Լ�*/
	public void calStage() {
		if(nextStage < score) {
			stage++;
			dropTimer *= 0.9;
			nextStage += 100;
		}
	}
	
}
