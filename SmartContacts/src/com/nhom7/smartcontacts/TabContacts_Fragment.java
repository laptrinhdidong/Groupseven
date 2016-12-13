package com.nhom7.smartcontacts;

import java.util.ArrayList;

import com.example.smartcontacts.R;
import com.nhom7.adapter.ListContacts_Adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class TabContacts_Fragment extends Fragment implements OnClickListener{
	
	Context context;
	ArrayList<String> arrName = new ArrayList<String>();
	ArrayList<String> arrNuber = new ArrayList<String>();
	ArrayList<String> arrID = new ArrayList<String>();
	ArrayList<String> arrEmail = new ArrayList<String>();
	String type;
	int i = 0;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tabcontacts, container, false);
		context = getActivity();
		i++;
		if(i==1){			
			getData();
		}
		
		ListView lv = (ListView)view.findViewById(R.id.listViewContacts);
		type = "nomal";
		ListContacts_Adapter adapter = new ListContacts_Adapter(context, arrName, arrNuber, arrID, arrEmail, type);
		lv.setAdapter(adapter);
		Button btnshowblack = (Button) view.findViewById(R.id.btnshowblack);
		btnshowblack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), BlackContact_Activity.class);
				context.startActivity(intent);
			}
		});
		return view;
	}
	
	public void getData(){
		ContentResolver cr = getActivity().getContentResolver();
		String name,number, image, id, email;
		Cursor phones = cr.query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
				null, null);
		while (phones.moveToNext()) {
			id = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
			name = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			number = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			email = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
			System.out.println(".................." + number);
			arrName.add(name);
			arrNuber.add(number);
			arrID.add(id);
			arrEmail.add(email);
		}
		phones.close();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
