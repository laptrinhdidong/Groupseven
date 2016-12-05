package com.nhom7.smartcontacts;

import java.util.zip.Inflater;

import com.example.smartcontacts.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Dr.h3cker on 14/03/2015.
 */
public class tabkeypad extends Fragment implements OnClickListener, OnTouchListener {
	public Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btnthang,btnsao,btndel,btncall,btnadd;
	public TextView txtNumber;
	public String numberphone = "";
	public tabkeypad context;
	@Override
	public View onCreateView(final LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tabkeypad, container, false);
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
		btndel.setOnTouchListener(this);
		btnadd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
				View v = inflater.inflate(R.layout.add_dialog, null);
				mBuilder.setView(v);
				final AlertDialog dialog = mBuilder.create();			
				EditText txtPhonenumber = (EditText)v.findViewById(R.id.txtPhonenumber);
				txtPhonenumber.setText(numberphone);

				dialog.show();	
				Button btnCancel = (Button)v.findViewById(R.id.btnCancel);
				btnCancel.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
			}
		});
//		
		context = this;
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
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:"+numberphone));
			context.startActivity(callIntent);
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
	@Override
	public boolean onTouch(View v, MotionEvent arg1) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btndel:
			break;

		default:
			break;
		}
		return false;
	}
}
