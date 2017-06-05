package com.example.tetrixtest;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.app.AlertDialog;
import android.os.Process;


public class BlockControl extends View{
	
	private int over = 0;
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
	private TimerTask timerTask = new TimerTask() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
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
					if (over == 0) {
						block = new Block(4, 1, nextBlock.getBlockType());
						nextBlock = new Block(16, 2, (int)(Math.random()*7) +1);
					}
				}
			}
			/*Handler를 이용해서 Timer마다 화면을 초기화하여 블럭을 움직이는 것처럼 보여준다.*/
			mHandler.sendEmptyMessage(0);
		}		
	};

	public BlockControl(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		/*처음에 나올 블럭은 random으로 해준다.*/
		block = new Block(4, 1, (int)(Math.random()*7) +1);
		nextBlock = new Block(16, 2, (int)(Math.random()*7) +1);
		
		/*타이머를 이용해서 일정 주기마다 한칸씩 내린다.*/
		timer = new Timer(true);
		timer.schedule(timerTask, 1000, dropTimer);
	}
	
	/* Handler
	 * 안드로이드에서는 메인쓰레드만 UI를 제어할 수 있다고 한다.
	 * 그래서 개발자는 Handler를 사용하여 간접적으로 접근하여
	 * 그래픽을 처리할 수 있다고 한다.
	 * */
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if(msg.what == 0) {
				mTextScore.setText("Score : " + score);
				mTextStage.setText("Stage : " + stage);
				
				/*
				 *맵을 갱신한 후, 타이머를 종료하고 게임을 멈춘다.
				 *블럭이 새로생기는 것을 막고, 버튼이 작동하는 것을 막아야한다.
				 *지금은 타이어만 멈추고 나머지는 전부 작동하는 중...
				 */
				isOver();
				/* invalidate()
				 * 이 함수는 불려질때마다 onDraw함수를 호출한다고 한다.
				 * 바로 밑에 정의되어있는 onDraw함수를 호출하면서 그래픽이 움직이는 것처럼 보여지게 된다
				 * */
				invalidate();
			}
		}
	};

	/* onDraw 
	 * 실제 화면에 그래픽을 출력해준다.
	 * 출력순서
	 * 1.뒷배경
	 * 2.테트리스가 진행되는 레이어, 다음 블럭이 나오는 레이어
	 * 3.다음 블럭, 현재 블럭
	 * 4.쌓여있는 블럭
	 * */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();	//블럭나오는 곳
		Paint paintBackground = new Paint();	//뒷배경 색상
		
		paint.setARGB(255, 213, 213, 213);	//연한 회색
		paintBackground.setARGB(255, 235, 235, 235);
		
		canvas.drawRect(0, 0, 1080, 1920, paintBackground); //뒷배경
		canvas.drawRect(50,50,650,1300, paint);	//테트리스가 진행되는 곳		
		canvas.drawRect(750, 50, 1050, 350, paint); //다음 블럭이 나오는 곳
		
		nextBlock.onDraw(canvas);
		block.onDraw(canvas);
		onDrawMap(canvas);
	}
	
	/* 줄 완성시 지워주는 함수
	 * 한 줄에 블럭이 12개 이므로 그것을 카운트할 countBlock
	 * 스코어를 계산 해주기 위해서 countScore
	 * */
	public void deleteLine() {
		int countBlock = 0;
		int countScore = 0;
		
		/* 처음부터 끝까지 탐색하고, 블럭이 있을때마다 countBlock++해준다
		 * countBlock == 12(가로길이가 12)일 경우 , 모두 0으로 바꿔준다.
		 * countScore++를 해주면서 여러 줄을 삭제시 보너스를 주기위해서 계속 증가 시킨다.
		 * 삭제된 줄을 밑으로 당기기위해서 pullLine()함수를 사용한다. parameter는 해당 행번호를 넘겨준다.
		 * 줄을 당기고나면, countBlock를 초기화시키고 다음줄로 넘어간다.
		 * 모든 for문을 빠져나가면 스코를 계산한다.
		 * */
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
	
	/* 삭제된 줄을 밑으로 당기는 함수
	 * parameter로 해당라인의 행번호를 받고 행부터 맨위까지의 내용물을 전부
	 * 한칸아래로 전부 복사한다.
	 * */
	public void pullLine(int i) {
		for(;i > 1; i--) {
			for(int j = 1; j < currentMap[i].length-1; j++) {
				currentMap[i][j] = currentMap[i-1][j];
			}
		}
	}
	
	/* 블럭이 충돌하는지를 알아보는 함수.
	 * 블럭의 위치는 currentX, currentY로만 저장되어있다.
	 * 하지만 블럭의 모양은 다 달라서 이 두개의 값만으로는 확인이 불가능하다.
	 * 따라서 블럭의 모양과 currentX, currentY를 더해서
	 * 블럭의 도트(박스 한개)의 위치를 이용해서
	 * 블럭이 충돌 하는지 확인할 것 이다.
	 * */
	public boolean isCollide() {
		
		int[][] currentBlock = block.getCurrentBlock().clone();	//쓰기 쉽게 샘플을 만든다.
		
		/*확인해줄 좌표*/
		int chkX = 0;
		int chkY = 0;
		
		/* 블럭의 도트가 어디에 위치해있는지 찾는다.*/
		for(int i = 0; i < currentBlock.length; i++) {
			for(int j = 0; j < currentBlock[i].length; j++) {
				if(currentBlock[i][j] == 1) {
					
					/*블럭(4*4Matrix)의 좌표와 현재 블럭의 위치좌표를 더하면 맵에서의 블럭 한 도트의 좌표이다.*/
					chkX = j + block.getCurrentX();
					chkY = i + block.getCurrentY();
					
					/* 해당위치에 블럭이 있을 시에는 true를 return한다.
					 * 만약 true가 return되면 그 블럭으 이미 겹쳐져 있는 상태에 있다는 것이므로
					 * 블럭을 이전의 상태로 복구해야한다.
					 * */
					if(currentMap[chkY][chkX] == 1) return true;
				}
			}
		}
		return false;
	}
	
	/* 현재 쌓인 블럭을 새롭게 갱신해주는 함수
	 * 이 함수는 isCollide함수와 비슷한 알고리즘으로
	 * 현재 움직이는 블럭이 어디에 위치해있는 지를 알아야지 그 블럭을 맵에 저장할 수 있기에
	 * chkX, chkY를 똑같이 사용한다.
	 * */
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
	
	/* 현재 맵을 그려주는 함수
	 * 블럭을 그려주는 함수와 똑같은 알고리즘이다.
	 * Canvas.drawRect(left, top, right, bottom, color)로 이루어져 있다.
	 * 그래서 해당 좌표와 박스 하나의 크기를 곱해줘서 정사각형의 박스를 한개씩 그려나간다.
	 * */
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
	
	/* 버튼에 따른 이동
	 * 버튼의 호출은 GameControl 클래스에서 이루어 지고 
	 * 이 클래스에서는 처리만 해준다.
	 * 이 클래스에서 버튼 이벤트를 전부처리 하려했지만 layout을 사용하는 법을 몰라서
	 * 결국 다른 글을 참고하여 버튼 이벤트는 따른 클레스에 선언하게 되었다.
	 * */
	public void moveLeft() {
		
		/*블럭의 위치를 바꿔주고 작동했다면, LogCat에 나오도록 하였다.*/
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
				
				/* 바닥에 닿았다는 것이기 때문에 블럭을 새로 뽑아준다.*/
				if (over == 0) {
					block = new Block(4, 1, nextBlock.getBlockType());
					nextBlock = new Block(16, 2, (int)(Math.random()*7) +1);
				}
			}
		}
		/*Handler를 이용해서 화면을 초기화하여 블럭을 움직이는 것처럼 보여준다.*/
		mHandler.sendEmptyMessage(0);
	}
	
	/*Down키를 길게 누를경우 한번에 내려간다.*/
	public void moveQuickDown() {
		
		/* 아래를 확인하면서 한칸씩 옮긴다.
		 * 한칸씩 내려가게 하지만 화면상에는 출력되지 않는다.
		 * 그래서 모든 처리가 끝나고 위치를 잡게 된다음
		 * 화면에 출력한다.
		 * */
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
				
				if (over == 0) {
				/* 바닥에 닿았다는 것이기 때문에 블럭을 새롭게 뽑아준다.*/
					block = new Block(4, 1, nextBlock.getBlockType());
					nextBlock = new Block(16, 2, (int)(Math.random()*7) +1);
				}
				break;
			}
		}	
		
		/*Handler를 이용해서 화면을 초기화하여 블럭을 움직이는 것처럼 보여준다.*/
		mHandler.sendEmptyMessage(0);
	}
	
	/* rotate는 반시계방향이다.
	 * Block클래스에 있는 함수를 호출하여 처리하며
	 * 충돌이 일어나면 저장해둔 이전의 블럭을 사용한다.
	 * */
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
	
	/* TextView를 가져오기 위한 함수
	 * GameControl 클래스에서 설정된 TextView를 이 클래스의 값들을 넣어주려면 이렇게 해야된다고 한다.
	 * */
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
			dropTimer *= 0.1;	//확인용
			nextStage += 100;
			timer.cancel();
			timer.schedule(timerTask, 1000, dropTimer);
		}
	}
	
	/* 게임이 끝나는 경우, Toast메세지 또는 팝업창을 띄울 것이다.
	 * 이 함수는 굳이 boolean이 아니여도 될듯하다.
	 * */
	public void isOver() {
		
		/* 맨윗줄에 1이 한개로 있다면 게임은 끝난다.*/
		for(int i = 1; i < currentMap[1].length-1; i++) {
			if(currentMap[1][i] == 1) {
				
				/* over라는 변수를 사용해서, over가 0일 경우에만 블럭을 만들게 한다.
				 * 이렇게 하지 않을 경우, 게임이 끝나도 계속해서 블럭을 만들어낸다.
				 * */
				over = 1;
				timer.cancel();
				/* 스코어와 스테이지를 같이 출력해준다.*/
				DialogDefault();
				
			}
		}
	}	
	
	private void DialogDefault() {
		
		AlertDialog.Builder alter = new AlertDialog.Builder(getContext());
		alter.setTitle("GAME OVER!");
		alter.setMessage("Score : " + score + "\nStage : " + stage).setCancelable(false);
		
		/*
		 * 게임을 종료할 경우, 게임을 종료한다.
		 * 게임을 다시시작할 경우, 맵을 전부 0, score, stage, over를 초기화하고
		 * 타이머를 다시시작한다.
		 * */
		alter.setPositiveButton("나가기", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Log.e("Tag", "out");
				Process.killProcess(Process.myPid());
			}
		});
		
		alter.setNegativeButton("다시하기", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Log.e("Tag", "reset");
				for (int i = 1; i < currentMap.length - 1; i++) {
					for (int j = 1; j < currentMap[i].length - 1; j++) {
						currentMap[i][j] = 0;
					}
				}
				score = 0;
				stage = 1;
				over = 0;
				dropTimer = 1000;
				mHandler.sendEmptyMessage(0);
				
				timer = new Timer(true);
				timer.schedule(timerTask, 1000, dropTimer);
			}
		});
		alter.show();
	}	
}
