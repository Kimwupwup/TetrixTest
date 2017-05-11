package com.example.tetrixtest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameControl extends RelativeLayout implements OnClickListener, OnLongClickListener{

	BlockControl blockControl;
	Button btnLeft, btnRight, btnDown, btnRotate;
	TextView mScore;
	TextView mStage;
	
	public GameControl(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.gamecontrol, this, true);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		blockControl = (BlockControl)findViewById(R.id.blockcontrol);		
		mScore = (TextView)findViewById(R.id.score);
		mStage = (TextView)findViewById(R.id.stage);
		blockControl.setTextView(mScore, mStage);
		
		btnLeft = (Button)findViewById(R.id.left);
		btnRight = (Button)findViewById(R.id.right);
		btnDown = (Button)findViewById(R.id.down);
		btnRotate = (Button)findViewById(R.id.rotate);
		
		btnLeft.setOnClickListener(this);
		btnLeft.setOnLongClickListener(this);
		btnRight.setOnClickListener(this);
		btnRight.setOnLongClickListener(this);
		btnDown.setOnClickListener(this);
		btnDown.setOnLongClickListener(this);
		
		btnRotate.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.equals(btnLeft)) blockControl.moveLeft();
		else if(v.equals(btnRight)) blockControl.moveRight();
		else if(v.equals(btnDown)) blockControl.moveDown();
		else if(v.equals(btnRotate)) blockControl.rotate();
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		if(v.equals(btnDown)) blockControl.moveQuickDown();
		return false;
	}

}
