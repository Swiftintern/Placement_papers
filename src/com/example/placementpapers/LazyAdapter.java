package com.example.placementpapers;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {

	private Activity activity;
	private String[] data;
	private String[] orgname;
	private static LayoutInflater inflater = null;

	public ImageLoader imageLoader;

	public LazyAdapter(Activity a, String[] d, String[] o) {
		activity = a;
		data = d;
		orgname = o;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(activity.getApplicationContext());
	}

	public int getCount() {
		return data.length;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.list_item_layout, null);

		TextView text = (TextView) vi.findViewById(R.id.tvName);
		;
		ImageView image = (ImageView) vi.findViewById(R.id.ivImage);
		text.setText(orgname[position]);
		imageLoader.DisplayImage(data[position], image);
		return vi;
	}
}