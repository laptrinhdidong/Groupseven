package com.nhom7.adapter;

import java.util.ArrayList;

import com.example.smartcontacts.R;
import com.nhom7.smartcontacts.InfoContacts_Activity;
import com.nhom7.smartcontacts.TabContacts_Fragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListContacts_Adapter extends BaseAdapter {
	ArrayList<String> name, number, id, email;
	String adtype;
	Context context;
	int[] imageId;
	private static LayoutInflater inflater = null;

	public ListContacts_Adapter(Context tabcontacts, ArrayList<String> arrName,
			ArrayList<String> arrNumber, ArrayList<String> arrID, ArrayList<String> arremail, String type){
		// TODO Auto-generated constructor stub
		name = arrName;
		number = arrNumber;
		context = tabcontacts;
		id = arrID;
		email = arremail;
		adtype = type;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return name.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public class Holder {
		TextView tvn;
		TextView tvp;
		TextView img;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder = new Holder();
		final View rowView;
		rowView = inflater.inflate(R.layout.listcontacts_list, null);
		holder.tvn = (TextView) rowView.findViewById(R.id.textName);
		holder.tvp = (TextView) rowView.findViewById(R.id.textNumber);
		holder.tvn.setText(name.get(position));
		holder.tvp.setText(number.get(position));
		rowView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(rowView.getContext(), InfoContacts_Activity.class);
				intent.putExtra("ID", id.get(position));
				intent.putExtra("type", adtype);
				context.startActivity(intent);
			}

		});
		return rowView;
	}

}