package com.example.tetrixtest;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class TetrisTestMain extends Activity{

	
	BlockControl blockControl;
	View mainView;
	ImageButton btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		btn = (ImageButton)findViewById(R.id.imageButton1);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//blockControl.moveLeft();
				Toast.makeText(getApplicationContext(), "Left", Toast.LENGTH_SHORT).show();
			}
			
		});
		setContentView(R.layout.activity_tetris_test_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		//initView();
					
	}
	
}
