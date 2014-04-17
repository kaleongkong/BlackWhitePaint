package edu.berkeley.kaleongkong.proj2;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

public class MainActivity extends Activity implements SeekBar.OnSeekBarChangeListener{
	RelativeLayout r;
	DrawOnCanvas d;
	RadioGroup button_group;
	RadioButton gray_scalerb;
	RadioButton color_scalerb;
	SeekBar brushSeekBar;
	Button reset;
	int screenWidth;
	int screenHeight;
	int cursorx;
	int cursory;
	int brushRadius;
	int chosen_img;
	final int LION =1;
	final int EAGLE = 1;
	final int DOLPHINE =1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent i = getIntent();
		Bitmap image = null;
		String choosen = i.getStringExtra("picture");
		if(choosen.contentEquals("lion")){
			image = BitmapFactory.decodeResource(getResources(),R.drawable.lion_600x450);
		}else if (choosen.contentEquals("eagle")){
			image = BitmapFactory.decodeResource(getResources(),R.drawable.baldeagle600x450);
			Log.v("ealge", new Integer(1).toString());
		}else{
			image = BitmapFactory.decodeResource(getResources(),R.drawable.dolphins);
		}
		
		brushRadius = 0;
		Point screenSize = new Point();
		Display screenDisplay = getWindowManager().getDefaultDisplay();
		
		
		button_group =(RadioGroup) findViewById(R.id.radiogroup);
		button_group.check(R.id.radiogray);
		gray_scalerb = (RadioButton)findViewById(R.id.radiogray);
		color_scalerb= (RadioButton)findViewById(R.id.radiocolor);
		
		
		brushSeekBar = (SeekBar)findViewById(R.id.seekBar1);
		brushSeekBar.setOnSeekBarChangeListener(this);
		
		reset = (Button) findViewById(R.id.button1);
		screenDisplay.getSize(screenSize);
		screenWidth = screenSize.x;
		screenHeight = screenSize.y;
		Log.v("screenWidth", new Integer(screenWidth).toString());
		Log.v("screenHeight", new Integer(screenHeight).toString());
		r = (RelativeLayout) findViewById(R.id.layout);
		
		d = new DrawOnCanvas(this, brushRadius, image, true);
		RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(screenWidth,(int)(screenHeight*0.7));
		r.addView(d,p);
		d.setOnTouchListener(DrawOnCanvasOnTouchListener);
		d.setY((int)(screenHeight*0.15));
		
		reset.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				d.reset();
				
			}
			
		});
		
		button_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(RadioGroup button_group, int checkedId) {
				if(checkedId == R.id.radiogray){
					d.grayScaleSelector(true);
				}else{
					d.grayScaleSelector(false);
				}
			}
			
		});
	}
	private OnTouchListener DrawOnCanvasOnTouchListener = new OnTouchListener(){

		@Override
		public boolean onTouch(View v, MotionEvent e) {
			switch(e.getAction()){
				case MotionEvent.ACTION_DOWN:{
					cursorx = (int)e.getX();
					cursory = (int)e.getY();
					d.setCursorCorr(cursorx, cursory);
					//Log.v("action down x", new Float(e.getX()).toString());
					//Log.v("action down y", new Float(e.getY()).toString());
				}
				case MotionEvent.ACTION_MOVE:{
					cursorx = (int)e.getX();
					cursory = (int)e.getY();
					d.setCursorCorr(cursorx, cursory);
					//Log.v("action move x", new Float(e.getX()).toString());
					//Log.v("action move y", new Float(e.getY()).toString());
				}
				case MotionEvent.ACTION_UP:{
					cursorx = (int)e.getX();
					cursory = (int)e.getY();
					d.setCursorCorr(cursorx, cursory);
					
				}
				case MotionEvent.ACTION_CANCEL:{
					
				}
			}
			return true;
		}};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
		// TODO Auto-generated method stub
		//Log.v("SeekBar", new Integer(progress).toString());
		d.setBrushSize(progress);
	}
	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

}
