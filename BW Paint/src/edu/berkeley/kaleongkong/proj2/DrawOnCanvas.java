package edu.berkeley.kaleongkong.proj2;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

@SuppressLint("DrawAllocation")
public class DrawOnCanvas extends View{
	Bitmap pic;
	Bitmap graypic;
	Bitmap origin;
	Bitmap assist;
	int cursorx;
	int cursory;
	int cursoradjust;
	int radius;
	int ORIGIN = 0;
	
	
	boolean grayscale_ischosen;
	public DrawOnCanvas(Context context, int brushRadius, Bitmap image, boolean grayscale) {
		super(context);
		pic = Bitmap.createBitmap(image.getWidth(),image.getHeight(), Config.ARGB_8888);
		Canvas piccanvas = new Canvas(pic);
		piccanvas.drawBitmap(image, ORIGIN,ORIGIN,null);
		
		origin = pic;
		graypic = Bitmap.createBitmap(pic.getWidth(),pic.getHeight(), Config.ARGB_8888);
		Canvas graycanvas = new Canvas(graypic);
		graycanvas.drawBitmap(toGrayscale(origin), ORIGIN,ORIGIN,null);
		assist = graypic;
		grayscale_ischosen = grayscale;
		cursorx = 0;
		cursory = 0;
		radius = brushRadius; // should be input from outside
		cursoradjust = 0; //changing this would affect the gray dot from the cursor
		
	}
	
	public void setBrushSize(int progress){
		radius = progress;
	}
	public void grayScaleSelector(boolean hasChosenGrayscale){
		if(hasChosenGrayscale){
			assist = graypic;
		}else{
			assist = pic;
		}
	}
	
	public void setCursorCorr(int x, int y){
		cursorx = x;
		cursory = y;
	}
	
	public void reset(){
		origin = pic;
	}
	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Paint circlepaint = new Paint();
		
		Log.v("grayscale:",new Boolean(grayscale_ischosen).toString());
		circlepaint.setColor(Color.TRANSPARENT);
		
		//grayScaleSelector(grayscale_ischosen);//control between grayscale or origin
		
		
		canvas.drawCircle(cursorx, cursory, radius, circlepaint);
		
		Bitmap chopped = getCroppedBitmap(origin, assist,cursorx, cursory, radius);
		
		
		Bitmap combine = Bitmap.createBitmap(origin.getWidth(),origin.getHeight(), Config.ARGB_8888);
		Canvas comCanvas = new Canvas(combine);
		comCanvas.drawBitmap(origin, ORIGIN, ORIGIN, null);
		comCanvas.drawBitmap(chopped, ORIGIN, ORIGIN, null);
		canvas.drawBitmap(combine, ORIGIN, ORIGIN+cursoradjust, null);
		origin = combine;
		//Log.v("cursor x in canvas:", new Integer(cursorx).toString());
		//Log.v("cursor y in canvas:", new Integer(cursory).toString());
		//invalidate();
	}
	
	
	public Bitmap getCroppedBitmap(Bitmap origin, Bitmap bitmap, int x, int y, int r) {
	    Bitmap output = Bitmap.createBitmap(origin.getWidth(),origin.getHeight(), Config.ARGB_8888);
	    Canvas canvas = new Canvas(output);

	    final int color = 0xff424242;
	    final Paint paint = new Paint();
	    final Rect rect = new Rect(ORIGIN, ORIGIN, bitmap.getWidth(), bitmap.getHeight());

	    paint.setAntiAlias(true);
	    canvas.drawARGB(ORIGIN, ORIGIN, ORIGIN, ORIGIN);
	    paint.setColor(color);
	    canvas.drawCircle(x, y-cursoradjust,r, paint);
	    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	    canvas.drawBitmap(bitmap, rect, rect, paint);
	    return output;
	}
	public Bitmap toGrayscale(Bitmap bmpOriginal)
	{        
	    int width, height;
	    height = bmpOriginal.getHeight();
	    width = bmpOriginal.getWidth();    

	    Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
	    Canvas c = new Canvas(bmpGrayscale);
	    Paint paint = new Paint();
	    ColorMatrix cm = new ColorMatrix();
	    cm.setSaturation(0);
	    ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
	    paint.setColorFilter(f);
	    c.drawBitmap(bmpOriginal, 0, 0, paint);
	    return bmpGrayscale;
	}

	
	
	
	
	
	
	
	

}
