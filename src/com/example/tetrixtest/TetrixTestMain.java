package com.example.tetrixtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class TetrixTestMain extends Activity {

	DrawSquare ds;
	Button button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ds = new DrawSquare(this);
		setContentView(ds);
		
		
	}
}
