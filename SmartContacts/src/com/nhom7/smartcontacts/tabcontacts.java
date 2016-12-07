package com.nhom7.smartcontacts;

import java.util.ArrayList;
import java.util.List;

import com.example.smartcontacts.R;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class tabcontacts extends Fragment implements OnItemClickListener{
	private ListView lvContacts;

	ArrayList<String> arrName = new ArrayList<String>();
	ArrayList<String> arrNuber = new ArrayList<String>();
	ArrayList<String> arrImage = new ArrayList<String>();
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tabcontacts, container, false);
		
		getData();
		
		ListView lv = (ListView)view.findViewById(R.id.listViewContacts);
		com.nhom7.adapter.ListContactsAdapter adapter = new com.nhom7.adapter.ListContactsAdapter(this, arrName, arrNuber);
		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(),"dcm", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getActivity(), InfoContactsActivity.class);
				startActivity(intent);
			}
		});
		return view;
	}
	
	public void getData(){
		ContentResolver cr = getActivity().getContentResolver();
		String name,number, image;
		Cursor phones = cr.query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
				null, null);
		while (phones.moveToNext()) {
			name = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			number = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			image = phones.getString(phones.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
			arrImage.add(image);
			System.out.println(".................." + number);
			arrName.add(name);
			arrNuber.add(number);
		}
		phones.close();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

}
