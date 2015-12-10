package com.example.placementpapers;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.placementpapers.Stu_Register.CreateNewAccount;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Experience extends Activity implements OnClickListener {
	Bundle data = getIntent().getExtras();
	String u_id = data.getString("user_id");
	String organisation_id = data.getString("organisation_id");
	Button Submit;
	EditText Details, Title;

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	private final String url = "http://swiftintern.com/organizations/saveExperience/"
			+ organisation_id + ".json";
	private static final String TAG_SUCCESS = "success";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.experience);
		Title = (EditText) findViewById(R.id.ettitle);
		Details = (EditText) findViewById(R.id.etdetails);
		Submit = (Button) findViewById(R.id.bsubmit);

	}

	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		if (Title.getText().toString().length() == 0) {
			Title.setError("Enter your name please.");
		} else if (Details.getText().toString().length() == 0) {
			Details.setError("Enter your mail id please.");
		} else {
			new AddExperience().execute();
		}

	}

	class AddExperience extends AsyncTask<String, String, String> {
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Experience.this);
			pDialog.setMessage("Feeding Your Details");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String title = Title.getText().toString();
			String details = Details.getText().toString();
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("title", title));
			params.add(new BasicNameValuePair("details", details));
			params.add(new BasicNameValuePair("user_id", u_id));

			JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);

			// check log cat for response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				Boolean success = json.getBoolean(TAG_SUCCESS);

				if (success == true) {
					Experience.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(getApplicationContext(),
									"Record Entered Successfully",
									Toast.LENGTH_LONG).show();
						}
					});
				} else if (success == false) {

					Experience.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(getApplicationContext(),
									"Some error occured, Please try again",
									Toast.LENGTH_LONG).show();
						}
					});
				}
			} catch (JSONException e) {
				e.printStackTrace();

			}
			return null;
		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
		}

	}

}
