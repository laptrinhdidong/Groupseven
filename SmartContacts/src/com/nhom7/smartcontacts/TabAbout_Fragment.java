package com.nhom7.smartcontacts;

import com.example.smartcontacts.R;
import com.nhom7.adapter.DataSmartContact_Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

public class TabAbout_Fragment extends Fragment {
	DataSmartContact_Adapter dbsmcontact;
	Context context;
    AutoCompleteTextView txtAddress;
    ListView lvTinhThanh;
    String []arrTinhThanh;
    ArrayAdapter<String>adapterTinhThanh;
    LayoutInflater inflater;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		context = getActivity();
		View view = inflater.inflate(R.layout.tababout, container, false);
	
		dbsmcontact = new DataSmartContact_Adapter(context);
		return view;
	}
}
