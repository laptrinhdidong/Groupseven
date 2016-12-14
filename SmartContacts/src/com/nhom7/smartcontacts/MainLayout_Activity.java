package com.nhom7.smartcontacts;

import java.util.ArrayList;

import com.example.smartcontacts.R;
import com.nhom7.adapter.DataSmartContact_Adapter;
import com.nhom7.adapter.Pager_Adapter;
import android.app.ActionBar;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

public class MainLayout_Activity extends FragmentActivity implements OnQueryTextListener{

	ViewPager pager;
	PagerTabStrip tab_strp;
	TextView txtnumber;
	int position = 0; String status = "", number = ""; 
	DataSmartContact_Adapter dbsm;
	AutoCompleteTextView txtAutoSearch;
	ArrayList<String> arrName = new ArrayList<String>();
	ArrayList<String> arrNumber = new ArrayList<String>();
	ArrayList<String> arrID = new ArrayList<String>();
	ArrayList<String> arrEmail = new ArrayList<String>();
	String type = "search";
	ArrayAdapter<String> searchAdapter;
	Context context;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.main_activity);
		dbsm = new DataSmartContact_Adapter(this);
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.tabbackground)));
		actionBar.setDisplayShowTitleEnabled(false);
	    actionBar.setDisplayShowCustomEnabled(true);
	    
				
		Pager_Adapter mapager = new Pager_Adapter(getSupportFragmentManager());
		pager = (ViewPager) findViewById(R.id.pager);
		Bundle intent = getIntent().getExtras();
		
		if(intent != null) {
			position =  intent.getInt("viewpager_position");
	    }
		
		pager.setAdapter(mapager);
		tab_strp = (PagerTabStrip) findViewById(R.id.tab_strip);
		tab_strp.setTextColor(Color.WHITE);
		tab_strp.setPadding(0, 0, 0, 0);
		tab_strp.setTabIndicatorColor(Color.WHITE);
		pager.setCurrentItem(position);
		
		getData();
	    LayoutInflater inflator = (LayoutInflater) this
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View v = inflator.inflate(R.layout.searchview, null);
	    actionBar.setCustomView(v);
	    
	    txtAutoSearch = (AutoCompleteTextView)findViewById(R.id.autosearch);
	    searchAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrName);
	    txtAutoSearch.setAdapter(searchAdapter);
	    txtAutoSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Cursor cur = dbsm.searchsm(txtAutoSearch.getText().toString());
				String ID = null;
				String mid = null;
				if(cur.getCount()==0)
				{
					String name = null, number = null, email = null;
			    	ContentResolver cr = getContentResolver();
			    	Cursor phone_cursor = cr.query( 
			                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
			                null,
			                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = ?", 
			                new String[]{txtAutoSearch.getText().toString()}, null); 
			        while (phone_cursor.moveToNext()) 
			        { 

			        	mid = phone_cursor.getString(phone_cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
			        }
					Intent intent = new Intent(MainLayout_Activity.this, InfoContacts_Activity.class);
					intent.putExtra("ID", mid);
					intent.putExtra("type", "nomal");
					startActivity(intent);
				}
				else{
				while (cur.moveToNext()) {
					number = (cur.getString(2));
					ID = cur.getString(0);
				}
				Intent intent = new Intent(MainLayout_Activity.this, InfoContacts_Activity.class);
				intent.putExtra("ID", ID);
				intent.putExtra("type", "smart");
				startActivity(intent);}
			}
		});
	    getsearch();
	    
	}
	private void getData()
	{
		
		TabSmartContacts_Fragment tabsm = new TabSmartContacts_Fragment();
		tabsm.readFromFile(this);
		if(tabsm.readFromFile(this).equals("1"))
		{
		ContentResolver cr = getContentResolver();
		Cursor phones = cr.query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
				null, null);
		while (phones.moveToNext()) {
			arrName.add(phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
		}
		phones.close();
		Cursor cur = dbsm.getAllData();
		if(cur.getCount()==0)
		{}
		while (cur.moveToNext()) {
			arrName.add(cur.getString(1));
		}
		}
		else
		{
			ContentResolver cr = getContentResolver();
			Cursor phones = cr.query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
					null, null);
			while (phones.moveToNext()) {
				arrName.add(phones
						.getString(phones
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
			}
			phones.close();
			Cursor cur = dbsm.getAllData();
			if(cur.getCount()==0)
			{}
			while (cur.moveToNext()) {
				arrName.add(cur.getString(1));
			}
		}
	}
	
	private void getsearch(){
		txtAutoSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                searchAdapter.getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
	}
	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		
		return false;
	}
}
