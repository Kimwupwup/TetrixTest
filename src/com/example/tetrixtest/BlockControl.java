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
import android.util.Log;




public class BlockControl extends View{
	
	private int score = 0;
	private int nextStage = 100;
	private int stage = 1;
	private TextView mTextScore;
	private TextView mTextStage;
	private Block block;
	private Block nextBlock;
	
	
	private final int blockSize = 50;
	
	/*블럭이 쌓이는 것을 확인 하기위해서 만든 현재의 맵*/
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
		
		/*처음에 나올 블럭은 random으로 해준다.*/
		block = new Block(1, 1, (int)(Math.random()*7) +1);
		nextBlock = new Block(15, 1, (int)(Math.random()*7) +1);
		
		/*타이머를 이용해서 일정 주기마다 한칸씩 내린다.*/
		timer = new Timer(true);
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {			
				
				/*한칸 내린다*/
				block.setCurrentY(block.getCurrentY() + 1);
				
				
				/*충돌이 일어날시에 이전의 상태로 복구한다.*/
				if(isCollide() == true) {
					block.setCurrentY(block.getCurrentY() - 1);
					
					/*다시 확인*/
					block.setCurrentY(block.getCurrentY() + 1);
					if(isCollide() == true) {
						block.setCurrentY(block.getCurrentY() - 1);
						
						/*바닥이라는 것을 확인했으니 맵에 표시해주고 삭제될 줄이 있는지 확인한다.*/
						setMap();
						deleteLine();
						
						/*현재 나올 블럭의 이전의 nextBlock를 넣어주고, random으로 nextBlock을 만들어준다.*/
						block = new Block(4, 1, nextBlock.getBlockType());
						nextBlock = new Block(15, 1, (int)(Math.random()*7) +1);
						
					}
				}
				/*Handler를 이용해서 Timer마다 화면을 초기화하여 블럭을 움직이는 것처럼 보여준다.*/
				mHandler.sendEmptyMessage(0);
			}
			
			/*1초후에 타이머를 시작하면 dropTimer만큼의 딜레이가 걸린다.*/
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

	/*onDraw 실제 화면에 출력해준다.*/
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();	//블럭나오는 곳
		Paint paintBackground = new Paint();	//뒷배경 색상
		
		paint.setARGB(255, 213, 213, 213);	//연한 회색
		paintBackground.setARGB(255, 235, 235, 235);
		
		canvas.drawRect(0, 0, 1080, 1920, paintBackground); //뒷배경
		canvas.drawRect(50,50,650,1300, paint);	//테트리스가 진행되는 곳		
		canvas.drawRect(730, 50, 1030, 350, paint); //다음 블럭이 나오는 곳
		
		
		nextBlock.onDraw(canvas);
		block.onDraw(canvas);
		onDrawMap(canvas);
	}
	
	/*줄 완성시 */
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
	
	/*블럭이 충돌하는지를 알아보는 함수.*/
	public boolean isCollide() {
		
		int[][] currentBlock = block.getCurrentBlock().clone();	//쓰기 쉽게 샘플을 만든다.
		
		/*확인해줄 좌표*/
		int chkX = 0;
		int chkY = 0;

		for(int i = 0; i < currentBlock.length; i++) {
			for(int j = 0;j < currentBlock[i].length; j++) {
				if(currentBlock[i][j] == 1) {
					
					/*블럭(4*4Matrix)의 좌표와 현재 블럭의 위치좌표를 더하면 맵에서의 블럭 한 도트의 좌표이다.  */
					chkX = j + block.getCurrentX();
					chkY = i + block.getCurrentY();
										
					if(currentMap[chkY][chkX] == 1) return true;
				}
			}
		}
		return false;
	}
	
	/*현재 쌓인 블럭을 새롭게 갱신해주는 함수*/
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
	
	/*현재 맵을 그려준다*/
	public void onDrawMap(Canvas canvas) {
		
		Paint paint = new Paint();
		paint.setARGB(255, 100, 100, 100);
		for(int i = 1; i < currentMap.length-1; i++) {
			for(int j = 1;j < currentMap[i].length-1; j++) {
				if(currentMap[i][j] == 1) {
					canvas.drawRect(j*blockSize, i*blockSize, (j+1)*blockSize, (i+1)*blockSize, paint);
				}
			}
		}
	}
	
	/*버튼에 따른 이동*/
	public void moveLeft() {
		block.setCurrentX(block.getCurrentX() - 1);
		Log.e("Tag", "LMove");

		/*충돌이 일어날시에 이전의 상태로 복구한다.*/
		if(isCollide() == true) {
			Log.e("Tag", "LCollide");
			block.setCurrentX(block.getCurrentX() + 1);
		}
		/*Handler를 이용해서 화면을 초기화하여 블럭을 움직이는 것처럼 보여준다.*/
		mHandler.sendEmptyMessage(0);
	}
	
	public void moveRight() {
		block.setCurrentX(block.getCurrentX() + 1);
		Log.e("Tag", "RMove");

		/*충돌이 일어날시에 이전의 상태로 복구한다.*/
		if(isCollide() == true) {
			block.setCurrentX(block.getCurrentX() - 1);
			Log.e("Tag", "RCollide");
		}
		/*Handler를 이용해서 화면을 초기화하여 블럭을 움직이는 것처럼 보여준다.*/
		mHandler.sendEmptyMessage(0);
	}
	
	public void moveDown() {
		block.setCurrentY(block.getCurrentY() + 1);
		
		/*충돌이 일어날시에 이전의 상태로 복구한다.*/
		if(isCollide() == true) {
			Log.e("Tag", "DCollide");
			block.setCurrentY(block.getCurrentY() - 1);
			
			block.setCurrentY(block.getCurrentY() + 1);
			if(isCollide() == true) {
				block.setCurrentY(block.getCurrentY() - 1);
				setMap();
				deleteLine();
				
				block = new Block(4, 1, nextBlock.getBlockType());
				nextBlock = new Block(15, 1, (int)(Math.random()*7) +1);
			}
		}
		/*Handler를 이용해서 화면을 초기화하여 블럭을 움직이는 것처럼 보여준다.*/
		mHandler.sendEmptyMessage(0);
	}
	
	/*Down키를 길게 누를경우 한번에 내려간다.*/
	public void moveQuickDown() {
		
		/*아래를 확인하면서 한칸씩 옮긴다.*/
		for(int i = 1; i < currentMap.length-1; i++) {
			block.setCurrentY(block.getCurrentY() + 1);
			
			/*충돌이 일어날시, 한칸위로 복구한다.*/
			if(isCollide() == true) {
				Log.e("Tag", "DCollide");
				block.setCurrentY(block.getCurrentY() - 1);
				
				/*한번더 확인해준다.*/
				if(isCollide() == true) {
					block.setCurrentY(block.getCurrentY() - 1);
				}
				setMap();
				deleteLine();
				
				block = new Block(4, 1, nextBlock.getBlockType());
				nextBlock = new Block(15, 1, (int)(Math.random()*7) +1);
				break;
			}
		}	
		
		/*Handler를 이용해서 화면을 초기화하여 블럭을 움직이는 것처럼 보여준다.*/
		mHandler.sendEmptyMessage(0);
	}
	
	public void rotate() {
		int[][] recovBlock = block.getCurrentBlock();
		block.rotate();
		/*충돌이 일어날시에 이전의 상태로 복구한다.*/
		if(isCollide() == true) {
			block.setCurrentBlock(recovBlock);
		}
		/*Handler를 이용해서 Timer마다 화면을 초기화하여 블럭을 움직이는 것처럼 보여준다.*/
		mHandler.sendEmptyMessage(0);
	}
	
	/*TextView를 가져오기 위한 함수*/
	public void setTextView(TextView score, TextView stage) {
		mTextScore = score;
		mTextStage = stage;
	}

	/*점수를 계산 하기 위한 함수*/
	public void calScore(int num) {
		/*줄을 없앤 수많큼 점수를 달리한다.*/
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
	
	/*스테이지를 올리기위한 함수*/
	public void calStage() {
		if(nextStage < score) {
			stage++;
			dropTimer *= 0.8;
			nextStage += 100;
		}
	}
	
	/*게임이 끝나는 경우, Toast메세지 또는 팝업창을 띄울 것이다.*/
	public boolean isOver() {
		for(int i = 1; i < currentMap[1].length-1; i++) {
			if(currentMap[1][i] == 1) {
				return true;
			}
		}
		return false;
	}
	
}
