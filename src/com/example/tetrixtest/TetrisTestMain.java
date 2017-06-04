package com.example.tetrixtest;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ImageButton;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;



public class TetrisTestMain extends Activity{

	private ImageButton introBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		/* game_intro를 가장먼서 view하고 화면을 클릭할 시에 게임 view가 출력된다.*/
		setContentView(R.layout.game_intro);
		introBtn = (ImageButton)findViewById(R.id.intro);		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
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
}
