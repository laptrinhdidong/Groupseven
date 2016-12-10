package com.nhom7.smartcontacts;

import com.example.smartcontacts.R;
import com.nhom7.adapter.DataSmartContactAdapter;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddSmartContact_Activity extends Activity implements
		OnClickListener {

	DataSmartContactAdapter dbsmcontact;
	Button btnaddsm, btncancelsm;
	EditText txtsmname, txtsmphone, txtsmemail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcontact_layout);

		dbsmcontact = new DataSmartContactAdapter(this);

		btnaddsm = (Button) findViewById(R.id.btnSavesm);
		btncancelsm = (Button) findViewById(R.id.btnCancelsm);

		txtsmname = (EditText) findViewById(R.id.txtsmName);
		txtsmphone = (EditText) findViewById(R.id.txtsmPhonenumber);
		txtsmemail = (EditText) findViewById(R.id.txtsmEmail);

		btnaddsm.setOnClickListener(this);
		btncancelsm.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnSavesm:
			AddData(txtsmemail.getText().toString(), txtsmphone.getText().toString(), txtsmemail.getText().toString());
			break;
		case R.id.btnCancelsm:
			onBackPressed();
			break;

		default:
			break;
		}
	}

	public void AddData(String name, String phonenumber, String email) {
		int num = Integer.valueOf(phonenumber.toString());
		
		Cursor res = dbsmcontact.checknull(num);
		if(res.getCount() == 0)
		{
			boolean isInserted = dbsmcontact.insertData(name, num, email);
			if (isInserted = true) {
				Toast.makeText(AddSmartContact_Activity.this,
						"Contact insert success!", Toast.LENGTH_SHORT).show();
				Intent go = new Intent(AddSmartContact_Activity.this,
						MainLayoutActivity.class);
				go.putExtra("viewpager_position", 3);
				startActivity(go);
			} else {
				Toast.makeText(AddSmartContact_Activity.this,
						"Contact insert fail!", Toast.LENGTH_SHORT).show();
			}
		}
		else {
			Toast.makeText(AddSmartContact_Activity.this,
					"Number already exists!", Toast.LENGTH_SHORT).show();
		}
		
	}

}