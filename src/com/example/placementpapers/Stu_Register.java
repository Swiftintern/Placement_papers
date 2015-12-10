package com.example.placementpapers;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Stu_Register extends Activity implements OnClickListener{
	EditText Name, Email, Phone, City;
	Button Register;
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	private static String url_create_account = "http://swiftintern.com/app/student.json";
	private static final String TAG_SUCCESS = "success";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		Name = (EditText) findViewById(R.id.etname);
		Email = (EditText) findViewById(R.id.etemail);
		Phone = (EditText) findViewById(R.id.etphone);
		City = (EditText) findViewById(R.id.etcity);
		Register = (Button) findViewById(R.id.bregister);
		Register.setOnClickListener((android.view.View.OnClickListener) this);
	}
	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		if (Name.getText().toString().length() == 0) {
			Name.setError("Enter your name please.");
		}
		else if(Email.getText().toString().length() == 0) {
				Email.setError("Enter your mail id please.");
		}
		else {
			
			new CreateNewAccount().execute();
		}
	
	}
	class CreateNewAccount extends AsyncTask<String, String, String> {
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Stu_Register.this);
			pDialog.setMessage("Registering..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String name = Name.getText().toString();
			String email = Email.getText().toString();
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", name));
			params.add(new BasicNameValuePair("email", email));

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_create_account,
					"POST", params);

			// check log cat for response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				Boolean success = json.getBoolean(TAG_SUCCESS);

				if (success == true) {
					Stu_Register.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(getApplicationContext(),
									"Registration Successful",
									Toast.LENGTH_LONG).show();
						}
					});
				} else if (success == false) {

					Stu_Register.this.runOnUiThread(new Runnable() {
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
