package com.example.customalarm;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {

	ArrayList<String[]> list;
	Context context;
	String[] day_names = {"S", "M", "T", "W", "TR", "F", "ST"};
	
	public ListViewAdapter(ArrayList<String[]> list, Context context){
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = convertView;
		
		if (v == null) {
	        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        v = mInflater.inflate(R.layout.list_items, null);
	    }
		
		final TextView text = (TextView) v.findViewById(R.id.textView1);
		final TextView text2 = (TextView) v.findViewById(R.id.textView2);
	    final Button button = (Button) v.findViewById(R.id.button1);
		
	    String[] array = list.get(position);
	    if(array[0].equalsIgnoreCase("on"))
	    	button.setText("ON");
	    else
	    	button.setText("OFF");
	    
	    String time = array[1]+":"+array[2];
	    text.setText(time);
	    
	    String days = "";
	    for(int i = 3; i < 10; i++)
	    {
	    	if(array[i] != "")
	    	{
	    		days += day_names[i-3]+" ";
	    	}
	    }
	    text2.setText(days);
	    
		return v;
	}

}
