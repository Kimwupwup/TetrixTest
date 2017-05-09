package com.example.tetrixtest;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.os.Handler;
import android.os.Message;

public class BlockControl extends View{
	
	Block block;
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
	
	public BlockControl(Context context) {
		super(context);
		
		block = new Block(3, 0, (int)(Math.random()*7) +1);
		
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
				block.setCurrentX(block.getCurrentX() + 1);
				
				/*�浹�� �Ͼ�ÿ� ������ ���·� �����Ѵ�.*/
				if(isCollide() == true) {
					block.setCurrentX(reX);
					block.setCurrentY(reY);
					block.setCurrentBlock(recovBlock);
					
					block.setCurrentY(block.getCurrentY() + 1);
					if(isCollide() == true) {
						block.setCurrentY(reY);
						setMap();
						block = new Block(3, 0, (int)(Math.random()*7) +1);
					}
				}
				/*Handler�� �̿��ؼ� Timer���� ȭ���� �ʱ�ȭ�Ͽ� ���� �����̴� ��ó�� �����ش�.*/
				mHandler.sendEmptyMessage(0);
			}
			
		}, 1000, 300);
	}
	
	/*Handler*/
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if(msg.what == 0) {
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
		
		block.onDraw(canvas);
		onDrawMap(canvas);
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
	
	
}
