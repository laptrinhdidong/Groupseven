package com.nhom7.smartcontacts;

import com.example.smartcontacts.R;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class tabsmartcontacts extends Fragment implements OnClickListener{

	@Override
	public View onCreateView(final LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.tabsmartcontacts, container,
				false);
		Button btnshow = (Button)view.findViewById(R.id.btnShow);
		btnshow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
				View v = inflater.inflate(R.layout.login_dialog, null);
				mBuilder.setView(v);
				
				AlertDialog dialog = mBuilder.create();
				dialog.show();				
			}
		});
		
		return view;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
}
