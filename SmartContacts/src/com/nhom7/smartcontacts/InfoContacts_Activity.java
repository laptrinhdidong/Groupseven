package com.nhom7.smartcontacts;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.zip.Inflater;

import com.example.smartcontacts.R;
import com.nhom7.adapter.DataBlackContact_Adapter;
import com.nhom7.adapter.DataSmartContact_Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.RawContacts;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InfoContacts_Activity extends Activity implements OnClickListener {

	TextView txtname;
	EditText txtnumber, txtemail, txtaddPhonenumber, txtaddName, txtaddEmail;
	Button btnifcall, btnifmes, btnifedit, btnifdel, btnifaddsm, btnaddsave,
			btnifback, btnifaddblack, btnaddcancel;
	TextView txtifname;
	String id, name, number, email, type;
	DataSmartContact_Adapter dbsmcontact;
	DataBlackContact_Adapter dbblackcontact;
	AlertDialog dialog;
	ViewPager pager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.infocontact_layout);

		dbsmcontact = new DataSmartContact_Adapter(this);
		dbblackcontact = new DataBlackContact_Adapter(this);

		Intent intent = getIntent();
		id = intent.getStringExtra("ID");
		type = intent.getStringExtra("type");

		txtname = (TextView) findViewById(R.id.txtInfoName);
		txtnumber = (EditText) findViewById(R.id.txtInfoPhonenumber);
		txtemail = (EditText) findViewById(R.id.txtInfoEmail);
		btnifmes = (Button) findViewById(R.id.btnInfoMsg);
		btnifcall = (Button) findViewById(R.id.btnInfoCall);
		btnifedit = (Button) findViewById(R.id.btnifEditContact);
		btnifdel = (Button) findViewById(R.id.btnifDeleteContact);
		btnifaddsm = (Button) findViewById(R.id.btnifAddtosmart);
		btnifback = (Button) findViewById(R.id.btnback);
		btnifaddblack = (Button) findViewById(R.id.btnifaddblack);

		btnifmes.setOnClickListener(this);
		btnifcall.setOnClickListener(this);
		btnifedit.setOnClickListener(this);
		btnifdel.setOnClickListener(this);
		btnifaddsm.setOnClickListener(this);
		btnifback.setOnClickListener(this);
		btnifaddblack.setOnClickListener(this);
		if (type.equals("smart")) {
			getAData();
		}
		if (type.equals("nomal")) {
			getADataNomal(id);
			btnifaddsm.setVisibility(View.VISIBLE);
		}
		if (type.equals("black")) {
			getDataBlack();
			btnifedit.setVisibility(View.GONE);
			btnifaddblack.setVisibility(View.GONE);
		}

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
		pager = (ViewPager) view.findViewById(R.id.pager);

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
					if (type.equals("smart")) {
						UpdateData();
					} else {
						updateContactNomal(txtaddName.getText().toString(),
								txtaddPhonenumber.getText().toString(),
								txtaddEmail.getText().toString());
						reload();
					}

					dialog.cancel();
				}
			});
			dialog.show();
			break;
		case R.id.btnifDeleteContact:
			if (type.equals("smart")) {
				DeleteData();
				Intent go = new Intent(InfoContacts_Activity.this,
						MainLayout_Activity.class);
				go.putExtra("viewpager_position", 2);
				startActivity(go);
			}
			if (type.equals("nomal")) {
				deleteContactNomal(txtname.getText().toString());
				Intent go = new Intent(InfoContacts_Activity.this,
						MainLayout_Activity.class);
				go.putExtra("viewpager_position", 1);
				startActivity(go);
			}
			if (type.equals("black")) {
//				createContact(txtname.getText().toString(), txtnumber.getText()
//						.toString(), txtemail.getText().toString());
				DeleteDataBlack();
				Intent intent = new Intent(this, BlackContact_Activity.class);
				startActivity(intent);
			}
			break;
		case R.id.btnifAddtosmart:
			TabSmartContacts_Fragment tabsm = new TabSmartContacts_Fragment();
			tabsm.readFromFile(InfoContacts_Activity.this);
			if (tabsm.readFromFile(InfoContacts_Activity.this).equals("1")) {
				AddData(txtname.getText().toString(), txtnumber.getText()
						.toString(), txtemail.getText().toString());
				deleteContactNomal(txtname.getText().toString());
				Intent go = new Intent(InfoContacts_Activity.this,
						MainLayout_Activity.class);
				go.putExtra("viewpager_position", 2);
				startActivity(go);
			} else {
				Toast.makeText(InfoContacts_Activity.this, "Please Sign in!",
						Toast.LENGTH_SHORT).show();
				Intent go = new Intent(InfoContacts_Activity.this,
						MainLayout_Activity.class);
				go.putExtra("viewpager_position", 2);
				startActivity(go);
			}
			break;
		case R.id.btnback:
			if (type.equals("nomal")) {
				Intent go2 = new Intent(InfoContacts_Activity.this,
						MainLayout_Activity.class);
				go2.putExtra("viewpager_position", 1);
				startActivity(go2);
			} else {
				Intent go2 = new Intent(InfoContacts_Activity.this,
						MainLayout_Activity.class);
				go2.putExtra("viewpager_position", 2);
				startActivity(go2);
			}
			break;
		case R.id.btnifaddblack:
				dbblackcontact.insertDataBlack(txtname.getText().toString(),
						txtnumber.getText().toString(), txtemail.getText()
								.toString());
			if (type.equals("nomal")) {
				deleteContactNomal(txtname.getText().toString());
			}
			if (type.equals("smart")) {
				DeleteData();
			}
			Intent go = new Intent(InfoContacts_Activity.this,
					BlackContact_Activity.class);
			startActivity(go);

			break;
		default:
			break;
		}
	}

	public void AddData(String name, String phonenumber, String email) {
		boolean isInserted = dbsmcontact.insertData(name, phonenumber, email);
		if (isInserted = true) {
			Toast.makeText(InfoContacts_Activity.this,
					"Smart contact insert success!", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(InfoContacts_Activity.this,
					"Smart Contact insert fail!", Toast.LENGTH_SHORT).show();
		}
	}

	public void getAData() {

		Cursor res = dbsmcontact.getAData(id);
		if (res.getCount() == 0) {
			Toast.makeText(InfoContacts_Activity.this, "fail",
					Toast.LENGTH_SHORT).show();
			return;
		}
		while (res.moveToNext()) {
			txtname.setText(res.getString(1));
			txtnumber.setText(res.getString(2));
			txtemail.setText(res.getString(3));
		}
		res.close();
	}

	public void UpdateData() {
		boolean isUpdate = dbsmcontact.UpdateData(id, txtaddName.getText()
				.toString(), txtnumber.getText().toString(), txtaddEmail
				.getText().toString());
		if (isUpdate == true) {
			Toast.makeText(InfoContacts_Activity.this,
					"Contact update success", Toast.LENGTH_SHORT).show();
			reload();
		} else {
			Toast.makeText(InfoContacts_Activity.this, "Contact fail!",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void DeleteData() {
		boolean isDelete = dbsmcontact.DeleteData(id);
		if (isDelete = true) {
//			Toast.makeText(InfoContacts_Activity.this,
//					"Contact delete success!", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(InfoContacts_Activity.this, "Contact delete fail!",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void reload() {
		Intent intent = getIntent();
		finish();
		startActivity(intent);
	}

	private void writeToFile(String data, Context context) {
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					context.openFileOutput("config.txt", Context.MODE_PRIVATE));
			outputStreamWriter.write(data);
			outputStreamWriter.close();
		} catch (IOException e) {
			Log.e("Exception", "File write failed: " + e.toString());
		}
	}

	private String readFromFile(Context context) {

		String ret = "";

		try {
			InputStream inputStream = context.openFileInput("config.txt");

			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				String receiveString = "";
				StringBuilder stringBuilder = new StringBuilder();

				while ((receiveString = bufferedReader.readLine()) != null) {
					stringBuilder.append(receiveString);
				}

				inputStream.close();
				ret = stringBuilder.toString();
			}
		} catch (FileNotFoundException e) {
			Log.e("login activity", "File not found: " + e.toString());
		} catch (IOException e) {
			Log.e("login activity", "Can not read file: " + e.toString());
		}

		return ret;
	}

	// control nomal contact

	private void createContact(String name, String phone, String email) {
		ContentResolver cr = getContentResolver();

		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);

		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				String existName = cur
						.getString(cur
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				if (existName.contains(name)) {
					Toast.makeText(InfoContacts_Activity.this,
							"Phonenumber" + name + " already exists",
							Toast.LENGTH_SHORT).show();
					return;
				}
			}
		}

		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.RawContacts.CONTENT_URI)
				.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, email)
				.withValue(ContactsContract.RawContacts.ACCOUNT_NAME,
						"com.google").build());
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(
						ContactsContract.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
				.withValue(
						ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
						name).build());
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(
						ContactsContract.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
				.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
				.withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
						ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
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
	}

	private void updateContactNomal(String name, String phone, String email) {
		ContentResolver cr = getContentResolver();

		String where = ContactsContract.Data.DISPLAY_NAME + " = ? AND "
				+ ContactsContract.Data.MIMETYPE + " = ? AND "
				+ String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE)
				+ " = ? ";
		String[] params = new String[] {
				name,
				ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
				String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_HOME) };

		Cursor phoneCur = managedQuery(ContactsContract.Data.CONTENT_URI, null,
				where, params, null);

		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

		if ((null == phoneCur)) {
			createContact(name, phone, email);
		} else {
			ops.add(ContentProviderOperation
					.newUpdate(ContactsContract.Data.CONTENT_URI)
					.withSelection(where, params)
					.withValue(ContactsContract.CommonDataKinds.Phone.DATA,
							phone).build());
		}

		phoneCur.close();

		try {
			cr.applyBatch(ContactsContract.AUTHORITY, ops);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Toast.makeText(InfoContacts_Activity.this,
				"Updated contacts complete!" + phone, Toast.LENGTH_SHORT)
				.show();
	}

	private void deleteContactNomal(String name) {

		ContentResolver cr = getContentResolver();
		String where = ContactsContract.Data.DISPLAY_NAME + " = ? ";
		String[] params = new String[] { name };

		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		ops.add(ContentProviderOperation
				.newDelete(ContactsContract.RawContacts.CONTENT_URI)
				.withSelection(where, params).build());
		try {
			cr.applyBatch(ContactsContract.AUTHORITY, ops);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getADataNomal(String id) {
		String name = null, number = null, email = null;
		ContentResolver cr = getContentResolver();
		Cursor phone_cursor = cr.query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
				ContactsContract.CommonDataKinds.Phone._ID + " = ?",
				new String[] { id }, null);
		while (phone_cursor.moveToNext()) {

			name = phone_cursor
					.getString(phone_cursor
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			number = phone_cursor
					.getString(phone_cursor
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			email = phone_cursor
					.getString(phone_cursor
							.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
		}
		txtname.setText(name);
		txtnumber.setText(number);
		txtemail.setText(email);
	}

	private void getDataBlack() {
		Cursor res = dbblackcontact.getADataBlack(id);
		if (res.getCount() == 0) {
			Toast.makeText(InfoContacts_Activity.this, "fail",
					Toast.LENGTH_SHORT).show();
			return;
		}
		while (res.moveToNext()) {
			txtname.setText(res.getString(1));
			txtnumber.setText(res.getString(2));
			txtemail.setText(res.getString(3));
		}
		res.close();
	}

	public void DeleteDataBlack() {
		boolean isDelete = dbblackcontact.DeleteDataBlack(id);
		if (isDelete = true) {
			Toast.makeText(InfoContacts_Activity.this,
					"Contact delete success!", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(InfoContacts_Activity.this, "Contact delete fail!",
					Toast.LENGTH_SHORT).show();
		}
	}

}
