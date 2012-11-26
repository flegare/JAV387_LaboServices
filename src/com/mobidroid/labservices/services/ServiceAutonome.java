package com.mobidroid.labservices.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/** 
 * 
 * Simple service autonome. 
 * @author Francois Legare (flegare@gmail.com)
 * 
 */
public class ServiceAutonome extends Service {
	
	private static final String TAG = ServiceAutonome.class.getName();
	
	public void onCreate() {	
		super.onCreate();				
		Log.d(TAG, "Test service created"); //initialiser les trucs ici
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "Executing logic now...."); // Execution de la logique ici
		return super.onStartCommand(intent, flags, startId);
	}
		
	/*
	 * Comme ce n'est pas un service lié on ne retour pas de binder
	 * 
	 * (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return null; 
	}
		
	
	/* 
	 * Est appeller quand le service est détruit
	 * 
	 * (non-Javadoc)
	 * @see android.app.Service#onDestroy()
	 */
	public void onDestroy() {
		Log.d(TAG, "Test service stopped"); //nettoyage ici
	}

}
