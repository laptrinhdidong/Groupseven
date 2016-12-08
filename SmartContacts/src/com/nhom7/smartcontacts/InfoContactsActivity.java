package com.nhom7.smartcontacts;

import com.example.smartcontacts.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InfoContactsActivity extends Activity{

	TextView txtname;
	EditText txtnumber, txtemail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.infocontact_layout);
		Intent intent = getIntent();
		String id = intent.getStringExtra("ID");
		String name = intent.getStringExtra("Name");
		String number = intent.getStringExtra("Number");
		String email = intent.getStringExtra("Email");
		
		txtname = (TextView)findViewById(R.id.txtInfoName);
		txtnumber = (EditText)findViewById(R.id.txtInfoPhonenumber);
		txtemail = (EditText)findViewById(R.id.txtInfoEmail);
		
		txtname.setText(name);
		txtnumber.setText(number);
		txtemail.setText(email);
		
		Toast.makeText(InfoContactsActivity.this, id, Toast.LENGTH_SHORT).show();
	}
		
}
