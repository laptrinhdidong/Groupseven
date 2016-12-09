package com.nhom7.smartcontacts;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import com.example.smartcontacts.R;
import com.nhom7.adapter.DataSmartContactAdapter;
import com.nhom7.adapter.ListContactsAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.LogWriter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class tabsmartcontacts extends Fragment implements OnClickListener {
	private ListView lvsmContacts;

	ArrayList<String> arrsmName = new ArrayList<String>();
	ArrayList<String> arrsmNuber = new ArrayList<String>();
	ArrayList<String> arrsmID = new ArrayList<String>();
	ArrayList<String> arrsmEmail = new ArrayList<String>();
	
	LinearLayout lnsmcontact;
	Boolean check  = false;
	EditText txtpassword, txtrepass;
	Button btnlogin;
	Button btnshow;
	Button btnsignout;
	Context context;
	DataSmartContactAdapter dbsmcontact;
	String Password, pass, repass, statuslogin;
	int i = 0;
	
	@Override
	public View onCreateView(final LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		context = getActivity();
		View view = inflater.inflate(R.layout.tabsmartcontacts, container,
				false);		
		Password =readPassFromFile(context);
		statuslogin =readFromFile(context);				
		dbsmcontact = new DataSmartContactAdapter(context);		
		i++;
		if (i == 1) {
			getData();
		}
		lvsmContacts = (ListView) view.findViewById(R.id.lvsmContacts);
		ListContactsAdapter adapter = new ListContactsAdapter(context,
				arrsmName, arrsmNuber, arrsmID, arrsmEmail);
		adapter.notifyDataSetChanged();
		lvsmContacts.setAdapter(adapter);		
		lnsmcontact = (LinearLayout)view.findViewById(R.id.layoutsmcontact);		
		btnshow = (Button) view.findViewById(R.id.btnShow);
		btnsignout = (Button) view.findViewById(R.id.btnsigout);
		btnshow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder mBuilder = new AlertDialog.Builder(
						getActivity());
				View v = inflater.inflate(R.layout.login_dialog, null);
				mBuilder.setView(v);
				
				final AlertDialog dialog = mBuilder.create();
				
				txtpassword = (EditText)v.findViewById(R.id.txtPass);
				txtrepass = (EditText)v.findViewById(R.id.txtRePass);
				btnlogin = (Button)v.findViewById(R.id.btnlogin);
				
				
				btnlogin.setOnClickListener(this);
				txtrepass.setOnClickListener(this);
				
				if(Password == "")
				{
					txtrepass.setVisibility(View.VISIBLE);
					btnlogin.setText("Sign up");
					btnlogin.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							pass = txtpassword.getText().toString();
							repass = txtrepass.getText().toString();
							if(pass.length()<6){
								txtpassword.setText(null);
								txtpassword.setHint("Password must contain at least six characters");
							}
							else {
								if(pass.equals(repass)){
									Password = pass;
									writePassToFile(Password, context);
									dialog.cancel();									
								}
								else{
									txtrepass.setText(null);
									txtrepass.setHint("Different Password");
								}
							}							
						}
					});
				}
				else {
					btnlogin.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if(txtpassword.getText().toString().equals(readPassFromFile(context))){
//								Toast.makeText(getActivity(), readFromFile(context)+"abc", Toast.LENGTH_SHORT).show();
								statuslogin="1";
								writeToFile(statuslogin, context);
								checklogin();
								dialog.cancel();
							}
						}
					});
				}
				dialog.show();
			}
		});
		btnsignout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				statuslogin="0";
				writeToFile(statuslogin, context);
//				Toast.makeText(getActivity(), readFromFile(context)+"abc", Toast.LENGTH_SHORT).show();
				checklogin();
			}
		});
		Button btnaddsm = (Button) view.findViewById(R.id.btnaddnew);
		btnaddsm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						AddSmartContact_Activity.class);
				startActivity(intent);
			}
		});
		checklogin();
		return view;
	}

	@Override
	public void onClick(View arg0) {
		
	}

	// database
	public void getData() {
		Cursor res = dbsmcontact.getAllData();
		if (res.getCount() == 0) {
			return;
		}
		while (res.moveToNext()) {
			arrsmID.add(res.getString(0));
			arrsmName.add(res.getString(1));
			arrsmNuber.add(res.getString(2));
			arrsmEmail.add(res.getString(3));
		}
		res.close();
	}
	public void checklogin()
	{
		MainLayoutActivity main = (MainLayoutActivity)getActivity();
		if(readFromFile(context).equals("1")){
			btnsignout.setVisibility(View.VISIBLE);
			btnshow.setVisibility(View.GONE);
			lnsmcontact.setVisibility(View.VISIBLE);
//			Toast.makeText(getActivity(), "true", Toast.LENGTH_SHORT).show();
		}
		else{
			btnsignout.setVisibility(View.GONE);
			btnshow.setVisibility(View.VISIBLE);
			lnsmcontact.setVisibility(View.INVISIBLE);
//			Toast.makeText(getActivity(), "false", Toast.LENGTH_SHORT).show();
		}
	}
    public void writeToFile(String data,Context context) {
	    try {
	        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
	        outputStreamWriter.write(data);
	        outputStreamWriter.close();
	    }
	    catch (IOException e) {
	        Log.e("Exception", "File write failed: " + e.toString());
	    } 
	}
	public String readFromFile(Context context) {

	    String ret = "";

	    try {
	        InputStream inputStream = context.openFileInput("config.txt");

	        if ( inputStream != null ) {
	            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
	            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	            String receiveString = "";
	            StringBuilder stringBuilder = new StringBuilder();

	            while ( (receiveString = bufferedReader.readLine()) != null ) {
	                stringBuilder.append(receiveString);
	            }

	            inputStream.close();
	            ret = stringBuilder.toString();
	        }
	    }
	    catch (FileNotFoundException e) {
	        Log.e("login activity", "File not found: " + e.toString());
	    } catch (IOException e) {
	        Log.e("login activity", "Can not read file: " + e.toString());
	    }

	    return ret;
	}
	 public void writePassToFile(String data,Context context) {
		    try {
		        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("configpass.txt", Context.MODE_PRIVATE));
		        outputStreamWriter.write(data);
		        outputStreamWriter.close();
		    }
		    catch (IOException e) {
		        Log.e("Exception", "File write failed: " + e.toString());
		    } 
		}
		public String readPassFromFile(Context context) {

		    String ret = "";

		    try {
		        InputStream inputStream = context.openFileInput("configpass.txt");

		        if ( inputStream != null ) {
		            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		            String receiveString = "";
		            StringBuilder stringBuilder = new StringBuilder();

		            while ( (receiveString = bufferedReader.readLine()) != null ) {
		                stringBuilder.append(receiveString);
		            }

		            inputStream.close();
		            ret = stringBuilder.toString();
		        }
		    }
		    catch (FileNotFoundException e) {
		        Log.e("login activity", "File not found: " + e.toString());
		    } catch (IOException e) {
		        Log.e("login activity", "Can not read file: " + e.toString());
		    }

		    return ret;
		}
}
