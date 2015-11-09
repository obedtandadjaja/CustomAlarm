package com.example.customalarm;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmReceiverActivity extends Activity {
    private MediaPlayer mMediaPlayer;
    int random1, random2;
    Context context = this;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.alarm);
 
        TextView text = (TextView) findViewById(R.id.textView1);
        
        random1 = (int) (Math.random()*100);
        random2 = (int) (Math.random()*10);
        
        text.setText(""+random1+" X "+random2+" =");
        
        SharedPreferences storage = getSharedPreferences("storage", Context.MODE_PRIVATE);
        int currVolume = storage.getInt("volume", 0);
        String music = storage.getString("song", "");
        
        Button stopAlarm = (Button) findViewById(R.id.stopAlarm);
        stopAlarm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try
				{
					EditText edit = (EditText) findViewById(R.id.editText1);
					int result = random1*random2;
					int userresult = Integer.parseInt(edit.getText().toString());
					if(userresult == result)
					{
						mMediaPlayer.stop();
		                finish();
					}
					else
					{
						Toast.makeText(context, "Wrong answer!", Toast.LENGTH_SHORT).show();
						edit.setText("");
					}
				}
				catch(Exception e)
				{
				}
			}
        });
        
        playSound(this, getAlarmUri(music), currVolume);
    }
 
    private void playSound(Context context, Uri alert, int volume) {
        mMediaPlayer = new MediaPlayer();
        
		float log1=(float)(Math.log(100-volume)/Math.log(100));
		
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null)
            vibrator.vibrate(400);
        
        try {
            mMediaPlayer.setDataSource(this, alert);
            final AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mMediaPlayer.prepare();
                mMediaPlayer.setVolume(1-log1, 1-log1);
                mMediaPlayer.start();
            }
        } catch (IOException e) {
            Toast.makeText(this, "No ringtone found!", Toast.LENGTH_LONG).show();
        }
    }
 
    //Get an alarm sound. Try for an alarm. If none set, try notification,
    //Otherwise, ring tone.
    private Uri getAlarmUri(String music) {
    	
    	StringBuilder sb = new StringBuilder("android.resource://com.example.customalarm/");
    	sb.append("R.raw.");
    	
    	Resources res = context.getResources();
    	
    	int id = res.getIdentifier(music, "raw", context.getPackageName());
    	Uri alert = Uri.parse("android.resource://com.example.customalarm/raw/"+id);
    	
//        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        if (alert == null) 
//        {
//            alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//            if (alert == null) 
//            {
//                alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
//            }
//        }
        return alert;
    }
}