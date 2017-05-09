package com.example.tetrixtest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;


public class TetrisTestMain extends Activity {

	BlockControl blockControl;
	Button button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		blockControl = new BlockControl(this);
		setContentView(blockControl);
				
	}
}
