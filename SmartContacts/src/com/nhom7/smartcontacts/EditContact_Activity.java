package com.nhom7.smartcontacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartcontacts.R;
import com.nhom7.adapter.DataSmartContactAdapter;

public class EditContact_Activity extends Activity implements OnClickListener {

	DataSmartContactAdapter dbsmcontact;
	Button btnaddsm, btncancelsm;
	EditText txtsmname, txtsmphone, txtsmemail;
	String id, name, number, email;

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
		
		Intent intent = getIntent();
		id = intent.getStringExtra("ID");
		name = intent.getStringExtra("Name");
		number = intent.getStringExtra("Number");
		email = intent.getStringExtra("Email");
		
		txtsmname.setText(name);
		txtsmphone.setText(number);
		txtsmemail.setText(email);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnSavesm:
			UpdateData();
			break;
		case R.id.btnCancelsm:
			onBackPressed();
			break;

		default:
			break;
		}
	}

	public void UpdateData() {
		int num = Integer.valueOf(txtsmphone.getText().toString());
		boolean isUpdate = dbsmcontact.UpdateData(id, txtsmname.getText()
				.toString(), num, txtsmemail.getText().toString());
		if (isUpdate == true) {
			Toast.makeText(
					EditContact_Activity.this,"Data update as",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(EditContact_Activity.this, "Data update fail!",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void DeleteData() {
		boolean isDelete = dbsmcontact.DeleteData(id);
		if (isDelete = true) {
			Toast.makeText(EditContact_Activity.this, "Data delete success!",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(EditContact_Activity.this, "Data delete fail!",
					Toast.LENGTH_SHORT).show();
		}
	}


}
