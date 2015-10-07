package com.example.customalarm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

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
import android.media.AudioManager;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Switch;
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
	String editswitch;
	Context context = this;
	Switch onoff;
	Resources res;
	SharedPreferences storage;
	ArrayList<Integer> days = new ArrayList<Integer>();
	int position;
	MediaPlayer mMediaPlayer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.edit);
		
		storage = getSharedPreferences("storage", Context.MODE_PRIVATE);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    position = extras.getInt("position");
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
					mMediaPlayer.seekTo(0);
					mMediaPlayer.start();
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
			}
		});
		
		String s = storage.getString(""+(position*3), "");
		String s1 = storage.getString("days", "");
		String s2 = storage.getString(""+(position*3+2), "");
		
		if(s2.equalsIgnoreCase("on"))
		{
			music.setText(storage.getString("song", ""));
		}
			
		try
		{
			int t = Integer.parseInt(s.substring(0, 2));
			int u = Integer.parseInt(s.substring(3, 5));
			time.setCurrentHour(t);
			time.setCurrentMinute(u);
		}
		catch(Exception e)
		{
		}
		
		if(s1.contains("1"))
		{
			sun.setChecked(true);
		}
		if(s1.contains("2"))
		{
			mon.setChecked(true);
		}
		if(s1.contains("3"))
		{
			tue.setChecked(true);
		}
		if(s1.contains("4"))
		{
			wed.setChecked(true);
		}
		if(s1.contains("5"))
		{
			thu.setChecked(true);
		}
		if(s1.contains("6"))
		{
			fri.setChecked(true);
		}
		if(s1.contains("7"))
		{
			sat.setChecked(true);
		}
		
		if(s2.equalsIgnoreCase("On"))
		{
			onoff.setChecked(true);
		}
		else
		{
			onoff.setChecked(false);
		}
		
		volume.setProgress(80);
		volumevalue.setText("80");
		
		volume.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				volumevalue.setText(""+arg1);
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
					editswitch = "On";
				}
				else
				{
					editswitch = "Off";
				}
				
				if(sun.isChecked())
				{
					editdaystext.append("S ");
					days.add(1);
				}
				if(mon.isChecked())
				{
					editdaystext.append("M ");
					days.add(2);
				}
				if(tue.isChecked())
				{
					editdaystext.append("T ");
					days.add(3);
				}
				if(wed.isChecked())
				{
					editdaystext.append("W ");
					days.add(4);
				}
				if(thu.isChecked())
				{
					editdaystext.append("T ");
					days.add(5);
				}
				if(fri.isChecked())
				{
					editdaystext.append("F ");
					days.add(6);
				}
				if(sat.isChecked())
				{
					editdaystext.append("S ");
					days.add(7);
				}
				
				if(days.size() == 0)
				{
					AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
		            builder1.setMessage("Please select at least a day");
		            builder1.setPositiveButton("Ok",
		                    new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int id) {
		                    dialog.cancel();
		                }
		            });

		            AlertDialog alert11 = builder1.create();
		            alert11.show();
				}
				else
				{
					Intent intent = new Intent(Edit.this, MainActivity.class);
					Editor editor = storage.edit();
					
					if(time.getCurrentHour() < 10)
					{
						edittime.append("0");
						edittime.append(time.getCurrentHour());
					}
					else
					{
						edittime.append(time.getCurrentHour());
					}
					
					if(time.getCurrentMinute() < 10)
					{
						edittime.append(":0");
						edittime.append(time.getCurrentMinute());
					}
					else
					{
						edittime.append(":");
						edittime.append(time.getCurrentMinute());
					}
					
					if(onoff.isChecked())
					{
				        setAlarm(days);
					}
					
					for(int i = 0; i<days.size();i++)
					{
						editdays.append(""+days.get(i));
					}
					editor.putString("days", editdays.toString());
					editor.putString(""+(position*3), edittime.toString());
					editor.putString(""+(position*3+1), editdaystext.toString());
					editor.putString(""+(position*3+2), editswitch);
					editor.putString("load", "yes");
					editor.putInt("volume", volume.getProgress());
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
		storage = getSharedPreferences("storage", Context.MODE_PRIVATE);
		Editor editor = storage.edit();
		music = (Button) findViewById(R.id.button2);
		
		if (item.getTitle() == "BeatifulSMS") 
		{
			editor.putString("song", "beautifulsms");
			music.setText(item.getTitle());
		} 
		else if (item.getTitle() == "BestWakeUpSound") 
		{
			editor.putString("song", "bestwakeupsound");
			music.setText(item.getTitle());
		} 
		else if (item.getTitle() == "CoolestAlarmClock") 
		{
			editor.putString("song", "coolestalarmclock");
			music.setText(item.getTitle());
		}
		else if (item.getTitle() == "ExtremeClockAlarm") 
		{
			editor.putString("song", "extremeclockalarm");
			music.setText(item.getTitle());
		}
		else if (item.getTitle() == "ICantStopDubstep") 
		{
			editor.putString("song", "icantstopdubstep");
			music.setText(item.getTitle());
		}
		else if (item.getTitle() == "NuclearAlarm") 
		{
			editor.putString("song", "nuclearalarm");
			music.setText(item.getTitle());
		}
		else if (item.getTitle() == "RockClassic") 
		{
			editor.putString("song", "rockclassic");
			music.setText(item.getTitle());
		}
		else 
		{
			return false;
		}
		editor.commit();
		changemusic();
		return true;
	}
	
	public void changemusic()
	{
		mMediaPlayer.reset();
		Resources res = context.getResources();
		storage = getSharedPreferences("storage", Context.MODE_PRIVATE);
		int id = res.getIdentifier(storage.getString("song", ""), "raw", context.getPackageName());
		try 
		{
			mMediaPlayer.setDataSource(context, Uri.parse("android.resource://com.example.customalarm/"+id));
			mMediaPlayer.prepareAsync();
		}
		catch(IOException e)
		{
			Toast.makeText(this, "Ringtone not found!", Toast.LENGTH_LONG).show();
		}
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

}
