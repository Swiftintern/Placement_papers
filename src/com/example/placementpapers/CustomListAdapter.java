package com.example.placementpapers;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter<String> {

	private final Activity context;
	private String[] imgurl;
	private String[] orgname;
	
	public CustomListAdapter(Activity context, String[] imgurl, String[] orgname) {
		super(context, R.layout.list_item_layout);
		
		this.context=context;
		this.imgurl=imgurl;
		this.orgname=orgname;
	}
	
	public View getView(int position,View view,ViewGroup parent) {
		LayoutInflater inflater=context.getLayoutInflater();
		View rowView=inflater.inflate(R.layout.list_item_layout, null,true);
		
		TextView Name = (TextView) rowView.findViewById(R.id.tvName);
		ImageView Image = (ImageView) rowView.findViewById(R.id.ivImage);
		
		Name.setText(orgname[position]);
		Image.setImageResource(imgurl[position]);
		return rowView;
		
	};
}
