package com.nhom7.smartcontacts;

import java.util.ArrayList;

import com.example.smartcontacts.R;
import com.nhom7.adapter.DataSmartContact_Adapter;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddSmartContact_Activity extends Activity implements
		OnClickListener {

	DataSmartContact_Adapter dbsmcontact;
	Button btnaddsm, btncancelsm;
	EditText txtsmname, txtsmphone, txtsmemail;
	String type = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcontact_layout);
		
		dbsmcontact = new DataSmartContact_Adapter(this);

		btnaddsm = (Button) findViewById(R.id.btnSavesm);
		btncancelsm = (Button) findViewById(R.id.btnCancelsm);

		txtsmname = (EditText) findViewById(R.id.txtsmName);
		txtsmphone = (EditText) findViewById(R.id.txtsmPhonenumber);
		txtsmemail = (EditText) findViewById(R.id.txtsmEmail);

		btnaddsm.setOnClickListener(this);
		btncancelsm.setOnClickListener(this);
		
		Intent intent = getIntent();
		type = intent.getStringExtra("type");

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnSavesm:
			if(type.equals("smart"))
			{
				if(txtsmname.length() == 0)
				{
					Toast.makeText(AddSmartContact_Activity.this,
							"Name null", Toast.LENGTH_SHORT).show();
				}
				if(txtsmphone.length()==0)
				{
					Toast.makeText(AddSmartContact_Activity.this,
							"Phonenumber null", Toast.LENGTH_SHORT).show();
				}
				if(txtsmname.length()!=0 && txtsmphone.length() !=0) {
					AddData(txtsmname.getText().toString(), txtsmphone.getText().toString(), txtsmemail.getText().toString());
					this.finish();
					Intent go = new Intent(AddSmartContact_Activity.this,
							MainLayout_Activity.class);
					go.putExtra("viewpager_position", 1);
				}
			}
			if(type.equals("nomal"))
			{
				if(txtsmname.length() ==0)
				{
					Toast.makeText(AddSmartContact_Activity.this,
							"Name null", Toast.LENGTH_SHORT).show();
				}
				if(txtsmphone.length()==0)
				{
					Toast.makeText(AddSmartContact_Activity.this,
							"Phonenumber null", Toast.LENGTH_SHORT).show();
				}
				if(txtsmname.length()!=0 && txtsmphone.length() !=0) {
					createContact(txtsmname.getText().toString(), txtsmphone.getText().toString(), txtsmemail.getText().toString());
				}
			}
			
			break;
		case R.id.btnCancelsm:
			onBackPressed();
			break;
		default:
			break;
		}
	}
	private void createContact(String name, String phone, String email) {
    	ContentResolver cr = getContentResolver();
    	
    	Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        
    	if (cur.getCount() > 0) {
        	while (cur.moveToNext()) {
        		String existName = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        		if (existName.contains(name)) {
                	Toast.makeText(AddSmartContact_Activity.this,"Phonenumber" + name + " already exists", Toast.LENGTH_SHORT).show();
                	return;        			
        		}
        	}
    	}
    	
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, email)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, "com.google")
                .build());
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
                .build());
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                .build());

        
        try {
			cr.applyBatch(ContactsContract.AUTHORITY, ops);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Toast.makeText(AddSmartContact_Activity.this,
				"Contact insert success!", Toast.LENGTH_SHORT).show();
        this.finish();
        Intent go = new Intent(AddSmartContact_Activity.this,
				MainLayout_Activity.class);
		go.putExtra("viewpager_position", 1);
		startActivity(go);
    }
	public void AddData(String name, String phonenumber, String email) {		
		Cursor res = dbsmcontact.checknull(phonenumber);
		if(res.getCount() == 0)
		{
			boolean isInserted = dbsmcontact.insertData(name, phonenumber, email);
			if (isInserted = true) {
				Toast.makeText(AddSmartContact_Activity.this,
						"Contact insert success!", Toast.LENGTH_SHORT).show();
				this.finish();
				Intent go = new Intent(AddSmartContact_Activity.this,
						MainLayout_Activity.class);
				go.putExtra("viewpager_position", 2);
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