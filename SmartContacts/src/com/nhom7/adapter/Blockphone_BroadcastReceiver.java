package com.nhom7.adapter;

import java.lang.reflect.Method;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.telephony.TelephonyManager;
public class Blockphone_BroadcastReceiver extends BroadcastReceiver {
	Context context;
	TelephonyManager telephonyManager;
	DataBlackContact_Adapter dbblack;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		this.context = context;
		telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		dbblack = new DataBlackContact_Adapter(context);
		Cursor cur = dbblack.getAllDataBlack();
		//myNumber = tMgr.getLine1Number();
		if(cur.getCount()<=0)
		{
			
		}
		else{
			while (cur.moveToNext()) {
//				String blocknum = cur.getString(3);
				Bundle extras = intent.getExtras();
				if (extras != null) {
					String state = extras.getString(TelephonyManager.EXTRA_STATE);
					// Log.v("test", state);
					if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
						String phoneNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
						//if (phoneNumber.equalsIgnoreCase("15555215554")) {
						if (phoneNumber.equalsIgnoreCase(cur.getString(2))) {
						// Log.v("test", "This phone is not allowed!");
						disconnectCall();
						// Toast.makeText(context, "This phone is not allowed!",
						// Toast.LENGTH_LONG).show();
					}
						// Log.v("test", phoneNumber);
					}
				}
			}
			}
	}

	public void disconnectCall() {
		try {

			// Get the getITelephony() method
			Class<?> classTelephony = Class.forName(telephonyManager.getClass().getName());
			Method method = classTelephony.getDeclaredMethod("getITelephony");
			// Disable access check
			method.setAccessible(true);
			// Invoke getITelephony() to get the ITelephony interface
			Object telephonyInterface = method.invoke(telephonyManager);
			// Get the endCall method from ITelephony
			Class<?> telephonyInterfaceClass = Class.forName(telephonyInterface.getClass().getName());
			Method methodEndCall = telephonyInterfaceClass.getDeclaredMethod("endCall");
			// Invoke endCall()
			methodEndCall.invoke(telephonyInterface);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}