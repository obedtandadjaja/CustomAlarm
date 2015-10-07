package com.example.customalarm;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {

	ArrayList<String> list;
	Context context;
	
	public ListViewAdapter(ArrayList<String> list, Context context){
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size()/3;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position*3;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position*3;
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
		
	    if(list.get(position*3).equalsIgnoreCase(""))
	    {
	    	text.setText("Not Set");
	    }
	    else
	    {
	    	text.setText(list.get(position*3));
	    }
	    
	    if(list.get(position*3+1).equalsIgnoreCase(""))
	    {
	    	text2.setText("");
	    }
	    else
	    {
	    	text2.setText(list.get(position*3+1));
	    }
	    
	    if(list.get(position*3+2).equalsIgnoreCase(""))
	    {
	    	button.setText("Off");
	    }
	    else
	    {
	    	button.setText(list.get(position*3+2));
	    }	    
	    
		return v;
	}

}
