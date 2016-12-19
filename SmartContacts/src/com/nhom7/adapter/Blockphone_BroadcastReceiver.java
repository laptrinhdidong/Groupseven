package com.nhom7.adapter;

import java.lang.reflect.Method;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;
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
				Bundle extras = intent.getExtras();
				if (extras != null) {
					String state = extras.getString(TelephonyManager.EXTRA_STATE);
					if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
						String phoneNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
						if (phoneNumber.equalsIgnoreCase(cur.getString(2))) {
						disconnectCall();
						DeleteCallLogByNumber(cur.getString(2));
					}
					}
				}
			}
			}
	}

	public void disconnectCall() {
		try {
			Class<?> classTelephony = Class.forName(telephonyManager.getClass().getName());
			Method method = classTelephony.getDeclaredMethod("getITelephony");
			method.setAccessible(true);
			Object telephonyInterface = method.invoke(telephonyManager);
			Class<?> telephonyInterfaceClass = Class.forName(telephonyInterface.getClass().getName());
			Method methodEndCall = telephonyInterfaceClass.getDeclaredMethod("endCall");
			methodEndCall.invoke(telephonyInterface);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void DeleteCallLogByNumber(String numberp) { 
		try {
	        Thread.sleep(4000);
	        String strNumberOne[] = { numberp };
	        Cursor cursor = context.getContentResolver().query(
	                CallLog.Calls.CONTENT_URI, null,
	                CallLog.Calls.NUMBER + " = ? ", strNumberOne, "");
	        boolean bol = cursor.moveToFirst();
	        if (bol) {
	            do {
	                int idOfRowToDelete = cursor.getInt(cursor
	                        .getColumnIndex(CallLog.Calls._ID));
	                context.getContentResolver().delete(
	                        CallLog.Calls.CONTENT_URI,
	                        CallLog.Calls._ID + "= ? ",
	                        new String[] { String.valueOf(idOfRowToDelete) });
	            } while (cursor.moveToNext());
	        }
	    } catch (Exception ex) {
	        Log.v("deleteNumber",
	                "Exception, unable to remove # from call log: "
	                        + ex.toString());
	    }

	}
}