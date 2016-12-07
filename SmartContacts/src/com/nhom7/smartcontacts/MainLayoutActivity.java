package com.nhom7.smartcontacts;

import com.example.smartcontacts.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

public class MainLayoutActivity extends FragmentActivity implements OnQueryTextListener {

	ViewPager pager;
	PagerTabStrip tab_strp;
	TextView txtnumber;
	Button btn1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		Pager_Adapter mapager = new Pager_Adapter(getSupportFragmentManager());
		pager = (ViewPager) findViewById(R.id.pager);

		pager.setAdapter(mapager);
		tab_strp = (PagerTabStrip) findViewById(R.id.tab_strip);
		tab_strp.setTextColor(Color.WHITE);
		tab_strp.setPadding(20, 0, 20, 0);
		// tab_strp.setTextSize(14,14);
		tab_strp.setTabIndicatorColor(Color.WHITE);
	}
	//
	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// getMenuInflater().inflate(R.menu.menu_main, menu);
	// return true;
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// // Handle action bar item clicks here. The action bar will
	// // automatically handle clicks on the Home/Up button, so long
	// // as you specify a parent activity in AndroidManifest.xml.
	// int id = item.getItemId();
	//
	// // noinspection SimplifiableIfStatement
	// if (id == R.id.share) {
	// return true;
	// }
	//
	// return super.onOptionsItemSelected(item);
	// }
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
