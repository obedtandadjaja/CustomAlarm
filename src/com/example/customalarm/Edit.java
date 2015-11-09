package com.example.customalarm;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Edit extends Activity {

	Button save, music;
	TimePicker time;
	ToggleButton sun, mon, tue, wed, thu, fri, sat, play;
	TextView volumevalue;
	SeekBar volume;
	StringBuilder editdays = new StringBuilder();
	StringBuilder editdaystext = new StringBuilder();
	StringBuilder edittime = new StringBuilder();
	ArrayList<Integer> days = new ArrayList<Integer>();
	String editswitch;
	Context context = this;
	Switch onoff;
	Resources res;
	SharedPreferences storage;
	Editor editor;
	String alarm_name;
	ArrayList<String> alarms = new ArrayList<String>();
	ArrayList<String> alarm = new ArrayList<String>();
	MediaPlayer mMediaPlayer;
	int song_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit);
		
		storage = getSharedPreferences("storage", Context.MODE_PRIVATE);
		editor = storage.edit();
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) 
		{
			alarm_name = extras.getString("alarm");
		    if(alarm_name.equals(""))
		    {
		    	alarm_name = generateRandomString();
		    }
		    if(storage.getStringSet("alarms", null) == null)
		    {
		    	alarms = new ArrayList<String>();
		    }
		    else
		    {
		    	alarms = new ArrayList<String>(storage.getStringSet("alarms", null));
		    }
			alarms.add(alarm_name);
		    for(int i = 0; i < 12; i++)
		    {
		    	alarm.add(storage.getString(alarm_name+"_"+i, ""));
		    }
		}
		
		save = (Button) findViewById(R.id.button1);
		time = (TimePicker) findViewById(R.id.timePicker1);
		sun = (ToggleButton) findViewById(R.id.radioButton1);
		mon = (ToggleButton) findViewById(R.id.radioButton2);
		tue = (ToggleButton) findViewById(R.id.radioButton3);
		wed = (ToggleButton) findViewById(R.id.radioButton4);
		thu = (ToggleButton) findViewById(R.id.radioButton5);
		fri = (ToggleButton) findViewById(R.id.radioButton6);
		sat = (ToggleButton) findViewById(R.id.radioButton7);
		onoff = (Switch) findViewById(R.id.switch1);
		volume = (SeekBar) findViewById(R.id.seekBar1);
		volumevalue = (TextView) findViewById(R.id.textView1);
		music = (Button) findViewById(R.id.button2);
		play = (ToggleButton) findViewById(R.id.toggleButton1);
		mMediaPlayer = new MediaPlayer();
		
		play.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				
				if(play.isChecked())
				{
					if(song_id == 0)
					{
						mMediaPlayer = MediaPlayer.create(Edit.this, R.raw.beautifulsms);
				        mMediaPlayer.start();
					}
					else
					{
						mMediaPlayer = MediaPlayer.create(Edit.this, song_id);
				        mMediaPlayer.start();
					}
				}
				else
				{
					mMediaPlayer.pause();
				}
			}
			
		});
			
		music.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				registerForContextMenu(arg0); 
				openContextMenu(arg0);
				unregisterForContextMenu(arg0);
				play.setChecked(false);
				mMediaPlayer.pause();
			}
		});
		
		if(!alarm.get(10).equals(""))
		{
			music.setText(alarm.get(10));
			changemusic(alarm.get(10).toLowerCase());
		}
			
		try
		{
			if(!alarm.get(1).equals("") || !alarm.get(2).equals(""))
			{
				int hour = Integer.parseInt(alarm.get(1));
				int minute = Integer.parseInt(alarm.get(2));
				time.setCurrentHour(hour);
				time.setCurrentMinute(minute);
			}
		}
		catch(Exception e)
		{
			Toast.makeText(this, "An Error Occurred", Toast.LENGTH_SHORT).show();
		}
		
		if(!alarm.get(3).equals(""))
		{
			sun.setChecked(true);
		}
		if(!alarm.get(4).equals(""))
		{
			mon.setChecked(true);
		}
		if(!alarm.get(5).equals(""))
		{
			tue.setChecked(true);
		}
		if(!alarm.get(6).equals(""))
		{
			wed.setChecked(true);
		}
		if(!alarm.get(7).equals(""))
		{
			thu.setChecked(true);
		}
		if(!alarm.get(8).equals(""))
		{
			fri.setChecked(true);
		}
		if(!alarm.get(9).equals(""))
		{
			sat.setChecked(true);
		}
		
		if(alarm.get(0).equalsIgnoreCase("On"))
		{
			onoff.setChecked(true);
		}
		else
		{
			onoff.setChecked(false);
		}
		
		if(!alarm.get(11).equals(""))
		{
			int vol = Integer.parseInt(alarm.get(11));
			volume.setProgress(vol);
			volumevalue.setText(""+vol);
		}
		else
		{
			volume.setProgress(80);
			volumevalue.setText("80");
		}
		
		volume.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				volumevalue.setText(""+arg1);
				alarm.set(11, ""+arg1);
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
			}
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
			}
			
		});
		
		save.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(onoff.isChecked())
				{
					alarm.set(0, "ON");
				}
				else
				{
					alarm.set(0, "OFF");
				}
				if(sun.isChecked())
				{
					alarm.set(3, "Y");
					days.add(0);
				}
				if(mon.isChecked())
				{
					alarm.set(4, "Y");
					days.add(1);
				}
				if(tue.isChecked())
				{
					alarm.set(5, "Y");
					days.add(2);
				}
				if(wed.isChecked())
				{
					alarm.set(6, "Y");
					days.add(3);
				}
				if(thu.isChecked())
				{
					alarm.set(7, "Y");
					days.add(4);
				}
				if(fri.isChecked())
				{
					alarm.set(8, "Y");
					days.add(5);
				}
				if(sat.isChecked())
				{
					alarm.set(9, "Y");
					days.add(6);
				}
				
				if(days.size() == 0)
				{
					Toast.makeText(Edit.this, "Please pick at least 1 day", Toast.LENGTH_SHORT).show();
				}
				else
				{
					Intent intent = new Intent(Edit.this, MainActivity.class);
					Editor editor = storage.edit();
					
					if(time.getCurrentHour() < 10)
					{
						edittime.append("0");
						edittime.append(time.getCurrentHour());
						alarm.set(1, edittime.toString());
					}
					else
					{
						edittime.append(time.getCurrentHour());
						alarm.set(1, edittime.toString());
					}
					
					if(time.getCurrentMinute() < 10)
					{
						alarm.set(2, "0"+time.getCurrentMinute());
					}
					else
					{
						alarm.set(2, ""+time.getCurrentMinute());
					}
					
					if(onoff.isChecked())
					{
				        setAlarm(days);
					}
					
					for(int i = 0; i < 12; i++)
					{
						editor.putString(alarm_name+"_"+i, alarm.get(i));
					}
					editor.putStringSet("alarms", new HashSet<String>(alarms));
					editor.commit();
					
					startActivity(intent);
				}
			}
			
		});
	}
	
	public void setAlarm(ArrayList<Integer> days)
	{
		Intent intent1 = new Intent(Edit.this, AlarmReceiverActivity.class);
		Calendar cal = Calendar.getInstance();
		
		for(int i = 0; i < days.size(); i++)
		{
			if(time.getCurrentHour()>12)
			{
				cal.set(Calendar.HOUR, (time.getCurrentHour()-12));
				cal.set(Calendar.AM_PM, 1);
			}
			else
			{
				cal.set(Calendar.HOUR, (time.getCurrentHour()));
				cal.set(Calendar.AM_PM, 0);
			}
			
			cal.set(Calendar.DAY_OF_WEEK, days.get(i));
	        cal.set(Calendar.MINUTE, time.getCurrentMinute());
	        cal.set(Calendar.SECOND, 0);
	        
	        int id = (int) System.currentTimeMillis();
	        
			PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), id, intent1, PendingIntent.FLAG_ONE_SHOT);
	        AlarmManager am = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
	        am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 24*60*60*1000, pendingIntent);
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Pick A Ringtone");
		menu.add(0, v.getId(), 0, "BeatifulSMS");
		menu.add(0, v.getId(), 0, "BestWakeUpSound");
		menu.add(0, v.getId(), 0, "CoolestAlarmClock");
		menu.add(0, v.getId(), 0, "ExtremeClockAlarm");
		menu.add(0, v.getId(), 0, "ICantStopDubstep");
		menu.add(0, v.getId(), 0, "NuclearAlarm");
		menu.add(0, v.getId(), 0, "RockClassic");
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		music = (Button) findViewById(R.id.button2);
		if (item.getTitle() == "BeatifulSMS") 
		{
			music.setText(item.getTitle());
			alarm.set(10, (String) item.getTitle());
		} 
		else if (item.getTitle() == "BestWakeUpSound") 
		{
			music.setText(item.getTitle());
			alarm.set(10, (String) item.getTitle());
		} 
		else if (item.getTitle() == "CoolestAlarmClock") 
		{
			music.setText(item.getTitle());
			alarm.set(10, (String) item.getTitle());
		}
		else if (item.getTitle() == "ExtremeClockAlarm") 
		{
			music.setText(item.getTitle());
			alarm.set(10, (String) item.getTitle());
		}
		else if (item.getTitle() == "ICantStopDubstep") 
		{
			music.setText(item.getTitle());
			alarm.set(10, (String) item.getTitle());
		}
		else if (item.getTitle() == "NuclearAlarm") 
		{
			music.setText(item.getTitle());
			alarm.set(10, (String) item.getTitle());
		}
		else if (item.getTitle() == "RockClassic") 
		{
			music.setText(item.getTitle());
			alarm.set(10, (String) item.getTitle());
		}
		else 
		{
			return false;
		}
		changemusic((String) music.getText());
		return true;
	}
	
	public void changemusic(String song)
	{
		mMediaPlayer.reset();
		Resources res = context.getResources();
		song_id = res.getIdentifier(song.toLowerCase(), "raw", context.getPackageName());
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("You have not save any changes. Are you sure you want to quit?");
        builder1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	Intent intent = new Intent(Edit.this, MainActivity.class);
            	startActivity(intent);
            }
        });
        builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
        
        AlertDialog alert11 = builder1.create();
        alert11.show();
	}
	
	public String generateRandomString()
	{
		SecureRandom random = new SecureRandom();
		String str = new BigInteger(130, random).toString(32);
		return str;
	}

}
