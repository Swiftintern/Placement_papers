package com.example.placementpapers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import android.widget.ListView;

import android.widget.Toast;

public class RandomCompanies extends Activity {
	public String[] imgurl;
	public String[] orgname;
	public String[] orga_id;
	private ProgressDialog pDialog;
	String name, limit, page;
	String organisation_id;
	LazyAdapter adapter;
	int orgid;
	JSONParser jParser = new JSONParser();

	// ArrayList<HashMap<String, String>> ResultFetch;

	private static String url = "http://swiftintern.com/organizations/placementpapers.json";

	private static final String TAG_ORGANIZATIONS = "organizations";
	private static final String TAG_NAME = "_name";
	private static final String TAG_ID = "_id";
	ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.random_companies);
		list = (ListView) findViewById(android.R.id.list);
		// ResultFetch = new ArrayList<HashMap<String, String>>();
		new LoadResult().execute();
		adapter = new LazyAdapter(this, imgurl, orgname);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String id = (String) arg0.getItemAtPosition(arg2);
				for (int i = 1; i <= orgname.length; i++) {
					if (orgname[i] == id) {
						orgid = i;
					}
				}
				Bundle senddata = new Bundle();
				senddata.putString("organisation_id", orga_id[orgid]);
				Intent j = new Intent("android.intent.action.FETCHDETAILS");
				j.putExtras(senddata);
				startActivity(j);
				finish();
			}

		});

	}

	@Override
	public void onDestroy() {
		list.setAdapter(null);
		super.onDestroy();
	}

	class LoadResult extends AsyncTask<String, String, String> {
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(RandomCompanies.this);
			pDialog.setMessage("Crunching the latest Data.");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			List<NameValuePair> paramss = new ArrayList<NameValuePair>();
			paramss.add(new BasicNameValuePair("limit", limit));
			paramss.add(new BasicNameValuePair("page", page));

			JSONObject json = jParser.makeHttpRequest(url, "GET", paramss);
			Log.d("Data: ", json.toString());
			try {
				JSONArray organizations = json.getJSONArray(TAG_ORGANIZATIONS);
				for (int i = 0; i < organizations.length(); i++) {
					JSONObject c = organizations.getJSONObject(i);
					organisation_id = c.getString(TAG_ID);
					name = c.getString(TAG_NAME);
					orgname[i] = name;
					orga_id[i] = organisation_id;
					imgurl[i] = "http://swiftintern.com/organizations/photo/"
							+ organisation_id;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();

		}

	}

}