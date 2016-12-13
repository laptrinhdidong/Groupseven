package com.nhom7.adapter;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;

import com.nhom7.smartcontacts.TabAbout_Fragment;
import com.nhom7.smartcontacts.TabContacts_Fragment;
import com.nhom7.smartcontacts.TabKeypad_Fragment;
import com.nhom7.smartcontacts.TabSmartContacts_Fragment;

public class Pager_Adapter extends FragmentPagerAdapter {
	public Pager_Adapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int i) {

		switch (i) {
		case 0:
			TabKeypad_Fragment t1 = new TabKeypad_Fragment();
			return t1;
		case 1:
			TabContacts_Fragment t3 = new TabContacts_Fragment();
			return t3;
		case 2:
			TabSmartContacts_Fragment t4 = new TabSmartContacts_Fragment();
			return t4;
		case 3:
			TabAbout_Fragment t5 = new TabAbout_Fragment();
			return t5;
		}
		return null;
	}

	@Override
	public int getCount() {
		return 4;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case 0:
			return "Keypad";
		case 1:

			return "Contacts";
		case 2:

			return "SmartContacts";
		case 3:

			return "About";
		}
		return null;
	}

}
