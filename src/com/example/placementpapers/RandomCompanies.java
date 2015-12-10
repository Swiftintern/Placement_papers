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
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import android.widget.ListView;

import android.widget.Toast;

public class RandomCompanies extends Activity {
	private ProgressDialog pDialog;
	String name, limit, page;
	String organisation_id;

	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> ResultFetch;

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

		ResultFetch = new ArrayList<HashMap<String, String>>();
		new LoadResult().execute();

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
					HashMap<String, String> map = new HashMap<String, String>();
					map.put(TAG_ID, organisation_id);
					map.put(TAG_NAME, name);
					ResultFetch.add(map);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			runOnUiThread(new Runnable() {
				public void run() {
					ListAdapter adapter = new SimpleAdapter(ReadResult.this,
							ResultFetch, R.layout.list_item, new String[] {
									TAG_SEMESTER, TAG_COURSE, TAG_SM, TAG_MT,
									TAG_ET, TAG_TOTAL, TAG_GRADES }, new int[] {
									R.id.sem, R.id.sub, R.id.sm, R.id.mt,
									R.id.et, R.id.total, R.id.grade });
					list.setAdapter(adapter);

					// ArrayAdapter<String> arrayAdapter = new
					// ArrayAdapter(ReadResult.this,android.R.layout.simple_list_item_1,
					// ResultFetch);
					// list.setAdapter(arrayAdapter);

				}
			});

		}

	}

}