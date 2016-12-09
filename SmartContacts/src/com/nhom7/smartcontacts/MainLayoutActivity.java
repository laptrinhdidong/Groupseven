package com.nhom7.smartcontacts;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.example.smartcontacts.R;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainLayoutActivity extends FragmentActivity implements OnQueryTextListener {

	ViewPager pager;
	PagerTabStrip tab_strp;
	TextView txtnumber;
	Button btn1;
	int position = 0; String status = ""; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		Pager_Adapter mapager = new Pager_Adapter(getSupportFragmentManager());
		pager = (ViewPager) findViewById(R.id.pager);
		Bundle intent = getIntent().getExtras();
		
		if(intent != null) {
			position =  intent.getInt("viewpager_position");
//			status =  intent.getString("status");
	    }
//		Toast.makeText(MainLayoutActivity.this, readFromFile(this)+"abc", Toast.LENGTH_SHORT).show();  
		
		pager.setAdapter(mapager);
		tab_strp = (PagerTabStrip) findViewById(R.id.tab_strip);
		tab_strp.setTextColor(Color.WHITE);
		tab_strp.setPadding(20, 0, 20, 0);
		// tab_strp.setTextSize(14,14);
		tab_strp.setTabIndicatorColor(Color.WHITE);
		pager.setCurrentItem(position);
	}
	
//    public String getMyData() {
//    	writeToFile(status, this);
//    	status = readFromFile(this);
//        return status;}
    
//    public void writeToFile(String data,Context context) {
//	    try {
//	        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
//	        outputStreamWriter.write(data);
//	        outputStreamWriter.close();
//	    }
//	    catch (IOException e) {
//	        Log.e("Exception", "File write failed: " + e.toString());
//	    } 
//	}
//	public String readFromFile(Context context) {
//
//	    String ret = "";
//
//	    try {
//	        InputStream inputStream = context.openFileInput("config.txt");
//
//	        if ( inputStream != null ) {
//	            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//	            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//	            String receiveString = "";
//	            StringBuilder stringBuilder = new StringBuilder();
//
//	            while ( (receiveString = bufferedReader.readLine()) != null ) {
//	                stringBuilder.append(receiveString);
//	            }
//
//	            inputStream.close();
//	            ret = stringBuilder.toString();
//	        }
//	    }
//	    catch (FileNotFoundException e) {
//	        Log.e("login activity", "File not found: " + e.toString());
//	    } catch (IOException e) {
//	        Log.e("login activity", "Can not read file: " + e.toString());
//	    }
//
//	    return ret;
//	}
    
    
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.search_menu, menu);

	    SearchManager searchManager = (SearchManager)
	                            getSystemService(Context.SEARCH_SERVICE);
	    MenuItem searchMenuItem = menu.findItem(R.id.search);
	    SearchView searchView = (SearchView) searchMenuItem.getActionView();

	    searchView.setSearchableInfo(searchManager.
	                            getSearchableInfo(getComponentName()));
	    searchView.setSubmitButtonEnabled(true);
	    searchView.setOnQueryTextListener(this);

	    return true;
	}
	@Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
