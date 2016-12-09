package com.nhom7.smartcontacts;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;

public class Pager_Adapter extends FragmentPagerAdapter {
	public Pager_Adapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int i) {

		switch (i) {
		case 0:
			tabkeypad t1 = new tabkeypad();
			return t1;
		case 1:
			tabhistory t2 = new tabhistory();
			return t2;
		case 2:
			tabcontacts t3 = new tabcontacts();
			return t3;
		case 3:
			tabsmartcontacts t4 = new tabsmartcontacts();
			return t4;
		case 4:
			tababout t5 = new tababout();
			return t5;
		}
		return null;
	}

	@Override
	public int getCount() {
		return 5;
	}// set the number of tabs

	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case 0:
			return "Keypad";
		case 1:

			return "History";
		case 2:

			return "Contacts";
		case 3:

			return "SmartContacts";
		case 4:

			return "About";
		}
		return null;
	}

}
