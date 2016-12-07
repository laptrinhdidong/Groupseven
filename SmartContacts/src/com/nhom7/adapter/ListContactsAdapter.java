package com.nhom7.adapter;

import java.util.ArrayList;

import com.example.smartcontacts.R;
import com.nhom7.smartcontacts.InfoContactsActivity;
import com.nhom7.smartcontacts.tabcontacts;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.sax.StartElementListener;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ListContactsAdapter extends BaseAdapter {
	ArrayList<String> name, number, image;
	tabcontacts context;
	int[] imageId;
	private static LayoutInflater inflater = null;

	public ListContactsAdapter(tabcontacts tabcontacts, ArrayList<String> arrName,
			ArrayList<String> arrNumber){
		// TODO Auto-generated constructor stub
		name = arrName;
		number = arrNumber;
		context = tabcontacts;
//		image = arrImage;
		inflater = (LayoutInflater) context
				.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		rowView = inflater.inflate(R.layout.listcontacts, null);
		holder.tvn = (TextView) rowView.findViewById(R.id.textName);
		holder.tvp = (TextView) rowView.findViewById(R.id.textNumber);
//		holder.img = (TextView) rowView.findViewById(R.id.textimg);
		holder.tvn.setText(name.get(position));
		holder.tvp.setText(number.get(position));
//		holder.img.setText(image.get(position));
		rowView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent callIntent = new Intent(Intent.ACTION_CALL);
//				callIntent.setData(Uri.parse("tel:"+number.get(position)));
//				context.startActivity(callIntent);
				context.startActivity(new Intent(rowView.getContext(), InfoContactsActivity.class));
			}

		});
		return rowView;
	}

}