package com.example.tetrixtest;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ImageButton;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;
import android.os.Process;


public class TetrisTestMain extends Activity{

	private ImageButton introBtn;
	
	/*뒤로가기 버튼을 두번 눌러 종료 할때 사용한다.*/
	private long firstTime;
	private long secondTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		/* game_intro를 가장먼서 view하고 화면을 클릭할 시에 게임 view가 출력된다.*/
		setContentView(R.layout.game_intro);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);	
		
		introBtn = (ImageButton)findViewById(R.id.intro);		
		
		
		introBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(v.equals(introBtn)) {
					setContentView(R.layout.activity_tetris_test_main);
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);			
				}
			}
		});				
	}	
	
	@Override
	public void onBackPressed() {
		secondTime = System.currentTimeMillis();
		Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();

		if (secondTime - firstTime < 2000) {
			super.onBackPressed();
			Process.killProcess(Process.myPid());
		}
		firstTime = System.currentTimeMillis();
	}	
}
