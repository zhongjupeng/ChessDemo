package com.demo.iryving.chess;
import static com.demo.iryving.chess.ViewConstant.*;

import java.util.HashMap;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Chess_DJB_Activity extends Activity implements View.OnClickListener{
	GameView gameView;
	Button btn_tip;
	SoundPool soundPool;//声音池
	HashMap<Integer, Integer> soundPoolMap; //声音池中声音ID与自定义声音ID的Map
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //设置全屏显示
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        getWindow().setFlags(
        		WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
      //设置横屏模式
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);//游戏过程中只允许多媒体音量,而不允许通化音量
        initPm();//调整屏幕分辨率
        initSound();
		setContentView(R.layout.main);
		gameView = (GameView)findViewById(R.id.gameView);
		btn_tip = (Button)findViewById(R.id.btn_tip);
		btn_tip.setOnClickListener(ocl);
    }

	View.OnClickListener ocl = new View.OnClickListener() {
		@Override
		public void onClick(View view) {

		}
	};


    public void initPm()
    {
    	//获取屏幕分辨率
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int tempHeight=(int) (height=dm.heightPixels);
        int tempWidth=(int) (width=dm.widthPixels); 
//        
        if(tempHeight>tempWidth)
        {
        	height=tempHeight;
        	width=tempWidth;
        }
        else
        {
        	height=tempWidth;
        	width=tempHeight;
        }
        float zoomx=width/480;
		float zoomy=height/800;
		if(zoomx>zoomy){
			xZoom=yZoom=zoomy;
			
		}else
		{
			xZoom=yZoom=zoomx;
		}
		sXtart=(width-480*xZoom)/2;
		sYtart=(height-800*yZoom)/2;
		initChessViewFinal();
    }

    public void initSound()
    {
		//声音池
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
	    soundPoolMap = new HashMap<Integer, Integer>();   
	    //吃东西音乐
	    soundPoolMap.put(1, soundPool.load(this, R.raw.noxiaqi, 1)); 
	    soundPoolMap.put(2, soundPool.load(this, R.raw.dong, 1)); //玩家走棋	    
	    soundPoolMap.put(4, soundPool.load(this, R.raw.win, 1)); //赢了
	    soundPoolMap.put(5, soundPool.load(this, R.raw.loss, 1)); //输了 
    }
    //播放声音
    public void playSound(int sound, int loop) 
    {
    	if(!isnoPlaySound)
    	{
    		return;
    	}
	    AudioManager mgr = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);   
	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
	    float volume = streamVolumeCurrent / streamVolumeMax;   
	    soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f);
	}


}