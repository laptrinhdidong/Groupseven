package com.nhom7.smartcontacts;

import com.example.smartcontacts.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WelcomeScreen_Activity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcomescreen);

		
		Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(2000);
                } catch (Exception e) {

                } finally {

                    Intent i = new Intent(WelcomeScreen_Activity.this,
                            MainLayout_Activity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        welcomeThread.start();
	}
	
}
