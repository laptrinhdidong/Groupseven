package com.nhom7.smartcontacts;

import java.util.ArrayList;

import com.example.smartcontacts.R;
import com.nhom7.adapter.DataSmartContact_Adapter;

import android.app.AlertDialog;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Dr.h3cker on 14/03/2015.
 */
public class TabKeypad_Fragment extends Fragment implements OnClickListener {
	public Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btnthang,btnsao,btndel,btncall,btnadd;
	public TextView txtNumber;
	public String numberphone = "";
	public Context context;
	DataSmartContact_Adapter dbsmcontact;
	@Override
	public View onCreateView(final LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tabkeypad, container, false);
		
		dbsmcontact = new DataSmartContact_Adapter(getActivity());
		
		btn0 = (Button) view.findViewById(R.id.btn0);
		btn1 = (Button) view.findViewById(R.id.btn1);
		btn2 = (Button) view.findViewById(R.id.btn2);
		btn3 = (Button) view.findViewById(R.id.btn3);
		btn4 = (Button) view.findViewById(R.id.btn4);
		btn5 = (Button) view.findViewById(R.id.btn5);
		btn6 = (Button) view.findViewById(R.id.btn6);
		btn7 = (Button) view.findViewById(R.id.btn7);
		btn8 = (Button) view.findViewById(R.id.btn8);
		btn9 = (Button) view.findViewById(R.id.btn9);
		btnthang = (Button) view.findViewById(R.id.btnthang);
		btnsao = (Button) view.findViewById(R.id.btnsao);
		txtNumber =(TextView) view.findViewById(R.id.txtNumberKey);
		btncall = (Button) view.findViewById(R.id.btncall);
		btndel = (Button) view.findViewById(R.id.btndel);
		btnadd = (Button) view.findViewById(R.id.btnadd);
		
		
		btn0.setOnClickListener(this);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);
		btn7.setOnClickListener(this);
		btn8.setOnClickListener(this);
		btn9.setOnClickListener(this);
		btnthang.setOnClickListener(this);
		btnsao.setOnClickListener(this);
		btncall.setOnClickListener(this);
		btndel.setOnClickListener(this);
		btnadd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
				View v = inflater.inflate(R.layout.add_dialog, null);
				mBuilder.setView(v);
				final AlertDialog dialog = mBuilder.create();			
				EditText txtPhonenumber = (EditText)v.findViewById(R.id.txtaddPhonenumber);
				txtPhonenumber.setText(numberphone);

				dialog.show();	
				Button btnCancel = (Button)v.findViewById(R.id.btnaddCancel);
				btnCancel.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
				Button btnsave = (Button)v.findViewById(R.id.btnaddSave);
				final EditText txtaddname = (EditText)v.findViewById(R.id.txtaddName);
				final EditText txtaddphone = (EditText)v.findViewById(R.id.txtaddPhonenumber);
				final EditText txtaddemail = (EditText)v.findViewById(R.id.txtaddEmail);
				final CheckBox cksm = (CheckBox)v.findViewById(R.id.ckaddsavesm);
				btnsave.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(txtaddname.length() == 0 || txtaddphone.length()==0 || txtaddemail.length()==0)
						{
							Toast.makeText(context, "Please enter the full information", Toast.LENGTH_SHORT).show();
						}
						else{
						if(cksm.isChecked()){
							TabSmartContacts_Fragment tabsm = new TabSmartContacts_Fragment();
							tabsm.readFromFile(getActivity());
							if(tabsm.readFromFile(getActivity()).equals("1"))
							{
							AddData(txtaddname.getText().toString(), txtaddphone.getText().toString(), txtaddemail.getText().toString());
							dialog.cancel();
							Intent go = new Intent(getActivity(),
									MainLayout_Activity.class);
							go.putExtra("viewpager_position", 2);
							startActivity(go);
							}
							else{
								Intent go = new Intent(getActivity(),
										MainLayout_Activity.class);
								go.putExtra("viewpager_position", 2);
								startActivity(go);		
								Toast.makeText(getActivity(),"Please Sign in", Toast.LENGTH_SHORT).show();
							}
						}
						else {
							createContact(txtaddname.getText().toString(), txtaddphone.getText().toString(), txtaddemail.getText().toString());
							dialog.cancel();
							Intent go = new Intent(getActivity(),
									MainLayout_Activity.class);
							go.putExtra("viewpager_position", 1);
							startActivity(go);
						}
						}
					}
				});				
			}
		});
//		
		context = getActivity();
		checknull();
		return view;
	}
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.btn0:
			numberphone += (btn0.getText().toString());
			break;
		case R.id.btn1:
			numberphone += (btn1.getText().toString());
			break;
		case R.id.btn2:
			numberphone += (btn2.getText().toString());
			txtNumber.setText(numberphone);
			break;
		case R.id.btn3:
			numberphone += (btn3.getText().toString());
			txtNumber.setText(numberphone);
			break;
		case R.id.btn4:
			numberphone += (btn4.getText().toString());
			break;
		case R.id.btn5:
			numberphone += (btn5.getText().toString());
			break;
		case R.id.btn6:
			numberphone += (btn6.getText().toString());
			break;
		case R.id.btn7:
			numberphone += (btn7.getText().toString());
			break;
		case R.id.btn8:
			numberphone += (btn8.getText().toString());
			break;
		case R.id.btn9:
			numberphone += (btn9.getText().toString());
			break;
		case R.id.btnsao:
			numberphone += (btnsao.getText().toString());
			break;
		case R.id.btnthang:
			numberphone += (btnthang.getText().toString());			
			break;
		case R.id.btndel:
		     numberphone = (numberphone.substring(0, numberphone.length() - 1));
			break;
		case R.id.btncall:			
			Cursor res = dbsmcontact.checknull(numberphone);
			TabSmartContacts_Fragment tabsm = new TabSmartContacts_Fragment();
			tabsm.readFromFile(getActivity());
			if(tabsm.readFromFile(getActivity()).equals("1"))
			{
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:"+numberphone));
				context.startActivity(callIntent);
			}
			else {
				if(res.getCount() == 0){
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:"+numberphone));
					context.startActivity(callIntent);
		        }
				else {
					Intent go = new Intent(getActivity(),
							MainLayout_Activity.class);
					go.putExtra("viewpager_position", 2);
					startActivity(go);		
					Toast.makeText(getActivity(),"Please Sign in", Toast.LENGTH_SHORT).show();
				}	   
			}			     
	        res.close();			
			break;

		default:
			break;
		}
		txtNumber.setText(numberphone);
		checknull();
		
	}
	
	public void checknull()
	{
		if(numberphone.length() <1){
			btndel.setVisibility(View.INVISIBLE);
			btnadd.setVisibility(View.INVISIBLE);
			btncall.setClickable(false);
		}
		else{
			btndel.setVisibility(View.VISIBLE);
			btnadd.setVisibility(View.VISIBLE);
			btncall.setClickable(true);
		}
	}
	private void createContact(String name, String phone, String email) {
    	ContentResolver cr = getActivity().getContentResolver();
    	
    	Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        
    	if (cur.getCount() > 0) {
        	while (cur.moveToNext()) {
        		String existName = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        		if (existName.contains(name)) {
                	Toast.makeText(getActivity(),"Phonenumber" + name + " already exists", Toast.LENGTH_SHORT).show();
                	return;        			
        		}
        	}
    	}
    	
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, email)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, "com.google")
                .build());
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
                .build());
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                .build());

        
        try {
			cr.applyBatch(ContactsContract.AUTHORITY, ops);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Toast.makeText(getActivity(),
				"Contact insert success!", Toast.LENGTH_SHORT).show();	
    }
	public void AddData(String name, String phonenumber, String email) {
		boolean isInserted = dbsmcontact.insertData(name, phonenumber, email);
		if (isInserted = true) {
			Toast.makeText(getActivity(),
					"Contact insert success!", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getActivity(),
					"Contact insert fail!", Toast.LENGTH_SHORT).show();
		}
	}
	private void checkinsert()
	{
		
	}
	}
