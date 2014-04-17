package edu.berkeley.kaleongkong.proj2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class BWIndex extends Activity implements OnClickListener{
	ImageButton lion;
	ImageButton eagle;
	ImageButton dolphine;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bwindex_main);
		ImageButton lion = (ImageButton)findViewById(R.id.imageButtonlion);
		ImageButton eagle = (ImageButton)findViewById(R.id.imageButtoneagle);
		ImageButton dolphine = (ImageButton)findViewById(R.id.imageButtondolphin);
		
		lion.setOnClickListener(this);
		eagle.setOnClickListener(this);
		dolphine.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i = new Intent(this, MainActivity.class);
		switch(v.getId()){
		case R.id.imageButtonlion:
			i.putExtra("picture", "lion");
			startActivityForResult(i,1);
			break;
		case R.id.imageButtoneagle:
			i.putExtra("picture", "eagle");
			startActivityForResult(i,1);
			break;
		case R.id.imageButtondolphin:
			i.putExtra("picture", "dolphine");
			startActivityForResult(i,1);
			break;
		}
	}
}
