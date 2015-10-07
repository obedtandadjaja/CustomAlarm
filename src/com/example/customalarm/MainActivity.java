package com.example.customalarm;

import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ResourceAsColor")
public class MainActivity extends ActionBarActivity {

	ListView listview;
	SharedPreferences storage;
	Editor editor;
	int loadsize = 39;
	ArrayList<String> list = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		storage = getSharedPreferences("storage", Context.MODE_PRIVATE);
		editor = storage.edit();
		
		if(storage.getString("load", "").equalsIgnoreCase("yes"))
		{
			for(int i = 0; i < loadsize; i++)
			{
				list.add(storage.getString(""+i, ""));
			}
		}
		
		// if application is first used
		else
		{
			editor.putString("load", "no");
			for(int i = 0; i < loadsize; i++)
			{
				editor.putString(""+i, "");
			}
			editor.putString("load", "no");
			editor.commit();
			for(int i = 0; i < loadsize; i++)
			{
				list.add(storage.getString(""+i, ""));
			}
		}
		
		ListViewAdapter adapter = new ListViewAdapter(list, this);
		
		listview = (ListView) findViewById(R.id.listView1);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(getBaseContext(), "Redirecting...", Toast.LENGTH_SHORT).show();
				Intent myIntent = new Intent(v.getContext(), Edit.class);
				myIntent.putExtra("position", position);
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
