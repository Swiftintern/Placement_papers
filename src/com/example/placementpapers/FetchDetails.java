package com.example.placementpapers;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class FetchDetails extends Activity {
	String Fetch_Name, Fetch_Country, Fetch_About, Fetch_Fb;
	private ProgressDialog pDialog;
	Bundle gotData = getIntent().getExtras();
	String organisation_id = gotData.getString("organisation_id");
	TextView Name, Country, Website, About, fb;
	JSONParser jParser = new JSONParser();

	private final String url = "http://swiftintern.com/organization/detail/"
			+ organisation_id + ".json";

	private static final String TAG_NAME = "_name";
	private static final String TAG_COUNTRY = "_country";
	private static final String TAG_WEBSITE = "_website";
	private static final String TAG_ABOUT = "_about";
	private static final String TAG_FBPAGE = "_fbpage";
	private static final String TAG_ORGANISATION = "organisation";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fetchdetails);
		Name = (TextView) findViewById(R.id.tvname);
		Country = (TextView) findViewById(R.id.tvcountry);
		Website = (TextView) findViewById(R.id.tvwebsite);
		About = (TextView) findViewById(R.id.tvabout);
		fb = (TextView) findViewById(R.id.tvfb);
		new DownloadImageTask((ImageView) findViewById(R.id.ivImage)).execute();
		new LoadOutput().execute();

	}

	class LoadOutput extends AsyncTask<String, String, String> {
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(FetchDetails.this);
			pDialog.setMessage("Fetching Details. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			List<NameValuePair> paramss = new ArrayList<NameValuePair>();

			JSONObject json = jParser.makeHttpRequest(url, "GET", paramss);
			Log.d("Data: ", json.toString());
			try {

				JSONObject c = json.getJSONObject(TAG_ORGANISATION);
				Fetch_Name = c.getString(TAG_NAME);
				Fetch_Country = c.getString(TAG_COUNTRY);
				Fetch_About = c.getString(TAG_ABOUT);
				Fetch_Fb = c.getString(TAG_FBPAGE);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			runOnUiThread(new Runnable() {
				public void run() {
					Name.setText(Fetch_Name);
					Country.setText(Fetch_Country);
					About.setText(Fetch_About);
					fb.setText(Fetch_Fb);

				}
			});

		}

	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		@Override
		protected Bitmap doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String urlDisplay = "http://swiftintern.com/organizations/photo/"
					+ organisation_id;
			Bitmap mIcon = null;
			try {
				InputStream in = new java.net.URL(urlDisplay).openStream();
				mIcon = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error:", e.getMessage());
				e.printStackTrace();
			}
			return mIcon;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);

		}
	}

}
