package com.mobidroid.labservices.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/** 
 * 
 * Simple service lié. 
 * @author Francois Legare (flegare@gmail.com)
 * 
 */
public class ServiceLie extends Service {
	
	private int counter;

	private static final String TAG = ServiceLie.class.getName();

	private final ServiceLieBinder binderInstance = new ServiceLieBinder();
	
	@Override
	public void onCreate() {	
		Log.d(TAG, "ServiceLie created");
		super.onCreate();
	}
	
	@Override
	public void onDestroy() {
		Log.d(TAG, "ServiceLie created");
		super.onDestroy();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand");
		return super.onStartCommand(intent, flags, startId);		
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return binderInstance;
	}

	/**
	 * 
	 * Notre classe binder sera retourné aux client afin d'executer
	 * notre logique.
	 *
	 */
	public class ServiceLieBinder extends Binder {

		/**
		 * Retourne l'instance du binder
		 * @return unBinder
		 */
		public ServiceLieBinder getService() {
			return binderInstance;
		}

		/**
		 * Cette méthode n'est pas utilisé dans le laboratoire mais
		 * pourrait être une autre méthode qui exécute de la logique 
		 * complexe
		 */
		public void doMyStuff() {
			// TODO Faire des chose complique ici ou déléguer au service
			Log.d(TAG, "Doing stuff");
		}
		
		/**
		 * On exécute de la "grosse" logique d'affaire ici, par exemple
		 * augmenter incrémenter un compteur...
		 */
		public void incrementNumber() {
			Log.d(TAG, "Incrementing stuff");
			counter++;			
		}

		/**
		 * Retourne la valeur de notre fabuleux compteur
		 * @return int
		 */
		public int getNumber() {
			Log.d(TAG, "Showing stuff");
			return counter;
		}
		
	}
	

}