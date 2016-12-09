package com.nhom7.smartcontacts;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.zip.Inflater;

import com.example.smartcontacts.R;
import com.nhom7.adapter.DataSmartContactAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InfoContactsActivity extends Activity implements OnClickListener {

	TextView txtname;
	EditText txtnumber, txtemail, txtaddPhonenumber, txtaddName, txtaddEmail;
	Button btnifcall, btnifmes, btnifedit, btnifdel, btnifaddsm, btnaddsave, btnifback,
			btnaddcancel;
	TextView txtifname;
	String id, name, number, email;
	DataSmartContactAdapter dbsmcontact;
	AlertDialog dialog;
	ViewPager pager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.infocontact_layout);

		dbsmcontact = new DataSmartContactAdapter(this);

		Intent intent = getIntent();
		id = intent.getStringExtra("ID");
//		name = intent.getStringExtra("Name");
//		number = intent.getStringExtra("Number");
//		email = intent.getStringExtra("Email");

		txtname = (TextView) findViewById(R.id.txtInfoName);
		txtnumber = (EditText) findViewById(R.id.txtInfoPhonenumber);
		txtemail = (EditText) findViewById(R.id.txtInfoEmail);
		btnifmes = (Button) findViewById(R.id.btnInfoMsg);
		btnifcall = (Button) findViewById(R.id.btnInfoCall);
		btnifedit = (Button) findViewById(R.id.btnifEditContact);
		btnifdel = (Button) findViewById(R.id.btnifDeleteContact);
		btnifaddsm = (Button) findViewById(R.id.btnifAddtosmart);
		btnifback= (Button)findViewById(R.id.btnback);

		btnifmes.setOnClickListener(this);
		btnifcall.setOnClickListener(this);
		btnifedit.setOnClickListener(this);
		btnifdel.setOnClickListener(this);
		btnifaddsm.setOnClickListener(this);
		btnifback.setOnClickListener(this);
		getAData();

		AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
		LayoutInflater inflater = this.getLayoutInflater();
		View view = inflater.inflate(R.layout.add_dialog, null);
		mBuilder.setView(view);
		dialog = mBuilder.create();
		txtaddPhonenumber = (EditText) view
				.findViewById(R.id.txtaddPhonenumber);
		txtaddName = (EditText) view.findViewById(R.id.txtaddName);
		txtaddEmail = (EditText) view.findViewById(R.id.txtaddEmail);
		btnaddsave = (Button) view.findViewById(R.id.btnaddSave);
		btnaddcancel = (Button) view.findViewById(R.id.btnaddCancel);
		pager = (ViewPager)view.findViewById(R.id.pager); 

		txtaddPhonenumber.setText(txtnumber.getText());
		txtaddName.setText(txtname.getText());
		txtaddEmail.setText(txtemail.getText());
		btnaddsave.setText("Update");

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnInfoMsg:
			Intent messIntent = new Intent(Intent.ACTION_VIEW);
			messIntent.setData(Uri.parse("sms:" + txtnumber.getText()));
			this.startActivity(messIntent);
			break;
		case R.id.btnInfoCall:
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:" + txtnumber.getText()));
			this.startActivity(callIntent);
			break;
		case R.id.btnifEditContact:

			btnaddcancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});
			btnaddsave.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					UpdateData();
					dialog.cancel();
				}
			});
			dialog.show();
			break;
		case R.id.btnifDeleteContact:
			DeleteData();
			Intent go = new Intent(InfoContactsActivity.this,MainLayoutActivity.class);
            go.putExtra("viewpager_position", 3);
//            go.putExtra("status", 1);
            startActivity(go);
			break;
		case R.id.btnifAddtosmart:

			break;
		case R.id.btnback:
			onBackPressed();
			break;

		default:
			break;
		}
	}
	
	public void getAData()
	{
        
		Cursor res = dbsmcontact.getAData(id);
		if(res.getCount() == 0){
            Toast.makeText(InfoContactsActivity.this, "fail", Toast.LENGTH_SHORT).show();
            return;
        }
        while (res.moveToNext())
        {
        	txtname.setText(res.getString(1));
        	txtnumber.setText(res.getString(2));
        	txtemail.setText(res.getString(3));
        }
        res.close();
	}
	
	public void UpdateData() {
		int num = Integer.valueOf(txtaddPhonenumber.getText().toString());
		boolean isUpdate = dbsmcontact.UpdateData(id, txtaddName.getText()
				.toString(), num, txtaddEmail.getText().toString());
		if (isUpdate == true) {
			Toast.makeText(InfoContactsActivity.this, "Contact update success",
					Toast.LENGTH_SHORT).show();
			reload();
		} else {
			Toast.makeText(InfoContactsActivity.this, "Contact fail!",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void DeleteData() {
		boolean isDelete = dbsmcontact.DeleteData(id);
		if (isDelete = true) {
			Toast.makeText(InfoContactsActivity.this, "Contact delete success!",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(InfoContactsActivity.this, "Contact delete fail!",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void reload() {
		Intent intent = getIntent();
		finish();
		startActivity(intent);
	}
	private void writeToFile(String data,Context context) {
	    try {
	        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
	        outputStreamWriter.write(data);
	        outputStreamWriter.close();
	    }
	    catch (IOException e) {
	        Log.e("Exception", "File write failed: " + e.toString());
	    } 
	}
	private String readFromFile(Context context) {

	    String ret = "";

	    try {
	        InputStream inputStream = context.openFileInput("config.txt");

	        if ( inputStream != null ) {
	            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
	            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	            String receiveString = "";
	            StringBuilder stringBuilder = new StringBuilder();

	            while ( (receiveString = bufferedReader.readLine()) != null ) {
	                stringBuilder.append(receiveString);
	            }

	            inputStream.close();
	            ret = stringBuilder.toString();
	        }
	    }
	    catch (FileNotFoundException e) {
	        Log.e("login activity", "File not found: " + e.toString());
	    } catch (IOException e) {
	        Log.e("login activity", "Can not read file: " + e.toString());
	    }

	    return ret;
	}

}
