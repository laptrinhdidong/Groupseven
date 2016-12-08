package com.nhom7.smartcontacts;

import java.util.ArrayList;

import com.example.smartcontacts.R;
import com.nhom7.adapter.DataSmartContactAdapter;
import com.nhom7.adapter.ListContactsAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.ListView;
import android.widget.Toast;

public class tabsmartcontacts extends Fragment implements OnClickListener{
	
	private ListView lvsmContacts;

	ArrayList<String> arrsmName = new ArrayList<String>();
	ArrayList<String> arrsmNuber = new ArrayList<String>();
	ArrayList<String> arrsmID = new ArrayList<String>();
	ArrayList<String> arrsmEmail = new ArrayList<String>();
	
	Context context;
	DataSmartContactAdapter dbsmcontact;
	@Override
	public View onCreateView(final LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		context =getActivity();
		View view = inflater.inflate(R.layout.tabsmartcontacts, container,
				false);
		dbsmcontact = new DataSmartContactAdapter(context);
		getData();
		
		lvsmContacts = (ListView)view.findViewById(R.id.lvsmContacts);
		ListContactsAdapter adapter = new ListContactsAdapter(context, arrsmName, arrsmNuber, arrsmID, arrsmEmail);
		lvsmContacts.setAdapter(adapter);
		
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
		
		Button btnaddsm = (Button)view.findViewById(R.id.btnaddnew);
		btnaddsm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), AddSmartContact_Activity.class);	
				startActivity(intent);
			}
		});
		
		return view;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	
//	database
	public void getData()
	{
		Cursor res =  dbsmcontact.getAllData();
        if(res.getCount() == 0){
            Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
            return;
        }
        while (res.moveToNext())
        {
            arrsmID.add(res.getString(0));
            arrsmName.add(res.getString(1));
            arrsmNuber.add(res.getString(2));
            arrsmEmail.add(res.getString(3));
        }
        res.close();
	}
	
	
}
