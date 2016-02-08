package com.example.customalarm;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ResourceAsColor")
public class MainActivity extends ActionBarActivity {

	ListView listview;
	Button add;
	TextView text;
	SharedPreferences storage;
	Editor editor;
	ArrayList<String[]> list = new ArrayList<String[]>();
	ArrayList<String> alarms;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.noalarm);
		
		storage = getSharedPreferences("storage", Context.MODE_PRIVATE);
		editor = storage.edit();
		
		listview = (ListView) findViewById(R.id.listView1);
		text = (TextView) findViewById(R.id.textView1);
		add = (Button) findViewById(R.id.button1);
		
		if(storage.getStringSet("alarms", null) == null)
		{
			listview.setVisibility(View.INVISIBLE);
			text.setVisibility(View.VISIBLE);
			alarms = new ArrayList<String>();
		}
		else
		{
			listview.setVisibility(View.VISIBLE);
			text.setVisibility(View.INVISIBLE);
			alarms = new ArrayList<String>(storage.getStringSet("alarms", null));
			for(int i = 0; i < alarms.size(); i++)
			{
				String[] string_array = new String[11];
				for(int k = 0; k < string_array.length; k++)
				{
					string_array[k] = storage.getString(alarms.get(i)+"_"+k, "");
				}
				list.add(string_array);
			}
			final ListViewAdapter adapter = new ListViewAdapter(list, this);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
					// TODO Auto-generated method stub
					Intent myIntent = new Intent(v.getContext(), Edit.class);
					myIntent.putExtra("alarm", alarms.get(position));
					startActivity(myIntent);
				}
			});
			listview.setOnItemLongClickListener(new OnItemLongClickListener(){
				@Override
				public boolean onItemLongClick(AdapterView<?> parent,
						View view, int position, long id) {
					// TODO Auto-generated method stub
					final int pos = position;
					
					// alert build
					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
			        builder.setMessage("Are you sure you want to delete this alarm?");
			        builder.setPositiveButton("Yes",
			                new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int id) {
			            	// get the alarm name
							String alarm_name = alarms.remove(pos);
							// remove the alarm from the list
							list.remove(pos);
							// remove every instances of the alarm in SP
							for(int i = 0; i < 12; i++)
							{
								editor.remove(alarm_name+"_"+i);
							}
							// set new alarms
							editor.putStringSet("alarms", new HashSet<String>(alarms));
							editor.commit();
							
							Toast.makeText(getBaseContext(), "Alarm successfully deleted", Toast.LENGTH_SHORT).show();
							adapter.notifyDataSetChanged();
			            }
			        });
			        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
			        AlertDialog alert = builder.create();
			        alert.show();

					return true;
				}
			});
		}
		
		add.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(v.getContext(), Edit.class);
				myIntent.putExtra("alarm", "");
				startActivity(myIntent);
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
