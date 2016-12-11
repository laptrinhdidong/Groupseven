package com.nhom7.smartcontacts;

import java.util.ArrayList;

import com.example.smartcontacts.R;
import com.nhom7.adapter.DataBlackContact_Adapter;
import com.nhom7.adapter.DataSmartContactAdapter;
import com.nhom7.adapter.ListContactsAdapter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class BlackContact_Activity extends Activity implements OnClickListener{

	DataBlackContact_Adapter dbblackcontact;
	
	ListView lvblackContacts;
//	LinearLayout lnblackcontact;
	String type = "black";
	Button btnback;
	
	ArrayList<String> arrsmName = new ArrayList<String>();
	ArrayList<String> arrsmNuber = new ArrayList<String>();
	ArrayList<String> arrsmID = new ArrayList<String>();
	ArrayList<String> arrsmEmail = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blackcontacts_layout);
		dbblackcontact = new DataBlackContact_Adapter(this);
		getData();
//		lnblackcontact = (LinearLayout) findViewById(R.id.layoutsmcontact);
		lvblackContacts = (ListView) findViewById(R.id.lvBlackContacts);
		ListContactsAdapter adapter = new ListContactsAdapter(this,
				arrsmName, arrsmNuber, arrsmID, arrsmEmail, type);
		lvblackContacts.setAdapter(adapter);		
		btnback = (Button) findViewById(R.id.btnBackBlack);
		btnback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent go = new Intent(BlackContact_Activity.this,MainLayoutActivity.class);
	            go.putExtra("viewpager_position", 2);
	            startActivity(go);
			}
		});
	}
	public void getData() {
		Cursor res = dbblackcontact.getAllDataBlack();
		if (res.getCount() == 0) {
			Toast.makeText(this, "abc", Toast.LENGTH_SHORT).show();
			return;
		}
		while (res.moveToNext()) {
			arrsmID.add(res.getString(0));
			arrsmName.add(res.getString(1));
			arrsmNuber.add(res.getString(2));
			arrsmEmail.add(res.getString(3));
		}
		res.close();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	

}
