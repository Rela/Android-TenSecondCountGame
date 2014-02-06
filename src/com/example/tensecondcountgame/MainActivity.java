package com.example.tensecondcountgame;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView text;
    int number;
    int maxCount;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
	
	TextView sec;
	TextView mSecTen;
	TextView mSecOne;
	TextView maxcountText;
	boolean timerStarted;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		text = (TextView) findViewById(R.id.textView1);
        sec = (TextView)findViewById(R.id.sec);
        maxcountText = (TextView)findViewById(R.id.maxcount);
        mSecTen = (TextView)findViewById(R.id.msecten);
        mSecOne = (TextView)findViewById(R.id.msecone);
        
        timerStarted = false;
        number = 0;
        
        //sharedPreferences
        pref = getSharedPreferences("countGame",Context.MODE_PRIVATE);
        editor = pref.edit();
        
        maxCount = pref.getInt("maxcount",0);
        //editor.commit();
        maxcountText.setText(String.format("%d",maxCount));
	}
	
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
        editor.putInt("count",number);
        editor.commit();
        timerStarted = false;
	}



	public void plus(View v){
		if(timerStarted == false){
			tickStart();
			timerStarted = true;
		}else{
		number += 1;
		text.setText(String.format("%d",number));
		}
	}
	
	public void clear(View v){
		number = 0;
		text.setText(String.format("%d",number));
		timerFinish();
	}
	public void clear(){
		number = 0;
		text.setText(String.format("%d",number));
	}
	
    public void timerFinish(){
        sec.setText("0");
    	mSecTen.setText("0");
        mSecOne.setText("0");
        timerStarted = false;
        saveMaxCount(number);
        clear();
    }
    
	public void saveMaxCount(int number){
		maxCount = pref.getInt("maxcount",0);
		if( number > maxCount){
			maxCount = number;
			editor.putInt("maxcount",number);
			editor.commit();
		}
        maxcountText.setText(String.format("%d",maxCount));
		
	}
	
	public void tickStart(){
		// 60秒カウントダウンする
        new CountDownTimer(10000,10){
            
            // カウントダウン処理
            public void onTick(long millisUntilFinished){
                sec.setText(""+millisUntilFinished/1000);
                
                long milliSecTen = millisUntilFinished % 1000 / 100;
                long milliSecOne = millisUntilFinished % 100 / 10;                
                mSecTen.setText("" + milliSecTen);
                mSecOne.setText("" + milliSecOne);
              
            }
            
            // カウントが0になった時の処理
            public void onFinish(){
            	timerFinish();                
            }
        }.start();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
