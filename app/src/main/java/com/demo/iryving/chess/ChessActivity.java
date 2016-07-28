package com.demo.iryving.chess;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.iryving.util.JsonParser;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.GrammarListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import java.util.HashMap;
import static com.demo.iryving.chess.ViewConstant.height;
import static com.demo.iryving.chess.ViewConstant.initChessViewFinal;
import static com.demo.iryving.chess.ViewConstant.isnoPlaySound;
import static com.demo.iryving.chess.ViewConstant.sXtart;
import static com.demo.iryving.chess.ViewConstant.sYtart;
import static com.demo.iryving.chess.ViewConstant.width;
import static com.demo.iryving.chess.ViewConstant.xZoom;
import static com.demo.iryving.chess.ViewConstant.yZoom;

public class ChessActivity extends Activity {
	GameView gameView;
	Button btn_tip;
	TextView tv_tip;
	SoundPool soundPool;//声音池
	HashMap<Integer, Integer> soundPoolMap; //声音池中声音ID与自定义声音ID的Map
	SpeechRecognizer mAsr = null;


	private static final String TAG = "ChessActivity";
	private Toast mToast;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置全屏显示
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN
		);
		//设置横屏模式
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);//游戏过程中只允许多媒体音量,而不允许通化音量
		initPm();//调整屏幕分辨率
		initSound();
		setContentView(R.layout.main);
		initView();
		initAsr();
	}


	private void initView(){
		gameView = (GameView) findViewById(R.id.gameView);
		btn_tip = (Button) findViewById(R.id.btn_tip);
		tv_tip = (TextView)findViewById(R.id.tv_tip);
		btn_tip.setOnClickListener(ocl);
		mToast = Toast.makeText(this,"", Toast.LENGTH_SHORT);
	}

	private void initAsr() {
		//1.创建SpeechRecognizer对象
		mAsr = SpeechRecognizer.createRecognizer(getApplicationContext(), null);
		mAsr.setParameter(SpeechConstant.ENGINE_TYPE, "clound");
		mAsr.setParameter(SpeechConstant.SUBJECT, "asr");
	}

	View.OnClickListener ocl = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
            //开始识别
			int ret = mAsr.startListening(mRecognizerListener);
			if (ret != ErrorCode.SUCCESS){
				Log.d(TAG, "识别失败，错误码: " + ret);
			}
			btn_tip.setEnabled(false);
		}
	};

	//构建语法监听器
	private GrammarListener grammarListener = new GrammarListener() {
		@Override
		public void onBuildFinish(String grammarId, SpeechError error) {
			if (error == null) {
				if (!TextUtils.isEmpty(grammarId)) {
					//构建语法成功，请保存grammarId用于识别
				} else {
					Log.d(TAG, "语法构建失败,错误码：" + error.getErrorCode());
				}
			}
		}
	};

	/**
	 * 识别监听器。
	 */
	private RecognizerListener mRecognizerListener = new RecognizerListener() {

		@Override
		public void onVolumeChanged(int volume, byte[] data) {
			Log.i(TAG, "===============onVolemeChanged volume================ : " + volume);
			showTip("当前正在说话，音量大小：" + volume);
			Log.d(TAG, "返回音频数据："+data.length);
		}

		@Override
		public void onResult(final RecognizerResult result, boolean isLast) {
			Log.i(TAG, "===============onResult================");
			if (null != result) {
				Log.d(TAG, "recognizer result：" + result.getResultString());
				String text ;
				if("cloud".equalsIgnoreCase(SpeechConstant.TYPE_CLOUD)){
					text = JsonParser.parseGrammarResult(result.getResultString());
				}else {
					text = JsonParser.parseLocalGrammarResult(result.getResultString());
				}

				// 显示
				tv_tip.setText(text);
			} else {
				Log.d(TAG, "recognizer result : null");
			}
		}

		@Override
		public void onEndOfSpeech() {
			Log.i(TAG, "===============onEndOfSpeech================");
			// 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
			showTip("结束说话");
			btn_tip.setText("开始");
			btn_tip.setEnabled(true);
		}

		@Override
		public void onBeginOfSpeech() {
			Log.i(TAG, "===============onBeginOfSpeech================");
			// 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
			showTip("开始说话");
			btn_tip.setText("识别中。。。");
		}

		@Override
		public void onError(SpeechError error) {
			Log.i(TAG, "===============onError================");
			showTip("onError Code："	+ error.getErrorCode());
			btn_tip.setEnabled(true);
		}

		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
			Log.i(TAG, "===============onEvent================");
			// 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
			// 若使用本地能力，会话id为null
			//	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
			//		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
			//		Log.d(TAG, "session id =" + sid);
			//	}
		}

	};

	private void showTip(final String str) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mToast.setText(str);
				mToast.show();
			}
		});
	}


	public void initPm() {
		//获取屏幕分辨率
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int tempHeight = (int) (height = dm.heightPixels);
		int tempWidth = (int) (width = dm.widthPixels);
//        
		if (tempHeight > tempWidth) {
			height = tempHeight;
			width = tempWidth;
		} else {
			height = tempWidth;
			width = tempHeight;
		}
		float zoomx = width / 480;
		float zoomy = height / 800;
		if (zoomx > zoomy) {
			xZoom = yZoom = zoomy;

		} else {
			xZoom = yZoom = zoomx;
		}
		sXtart = (width - 480 * xZoom) / 2;
		sYtart = (height - 800 * yZoom) / 2;
		initChessViewFinal();
	}

	public void initSound() {
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
	public void playSound(int sound, int loop) {
		if (!isnoPlaySound) {
			return;
		}
		AudioManager mgr = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
		float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
		float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = streamVolumeCurrent / streamVolumeMax;
		soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f);
	}

}