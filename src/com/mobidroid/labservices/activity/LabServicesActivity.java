package com.mobidroid.labservices.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.mobidroid.labservices.R;
import com.mobidroid.labservices.services.ServiceAutonome;
import com.mobidroid.labservices.services.ServiceLie;
import com.mobidroid.labservices.services.ServiceLie.ServiceLieBinder;

/** 
 * 
 * Simple activité afin de tester les services sous Android.
 * 
 * 
 * @author Francois Legare (flegare@gmail.com)
 * 
 */
public class LabServicesActivity extends Activity {

	private static final String TAG = LabServicesActivity.class.getName();

	private ServiceLieBinder mService;
	private boolean mBound;
	private boolean bindingPaused;

	private ServiceConnection mConnection =

	/**
	 * Cet objet servira pour la gestion de la connection
	 */
	new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder service) {
			Log.d(TAG, "Activity is now connected from the binded service");
			ServiceLieBinder binder = (ServiceLieBinder) service;
			mService = binder.getService();
			mBound = true;
		}

		public void onServiceDisconnected(ComponentName cn) {
			Log.d(TAG, "Activity is now disconnected from the binded service "
					+ cn.toString());
			mBound = false;
		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		
		//Ajoute les onClick events.
		bindButtons();
	}

	/**
	 * Cette méthode fait ajoute les différentes actions pour nos bouttons
	 * de l'activité.
	 */
	private void bindButtons() {


		findViewById(R.id.btStartService).setOnClickListener(
				new OnClickListener() {
					public void onClick(View arg0) {
						doStartService();
					}
				});

		findViewById(R.id.btStopService).setOnClickListener(
				new OnClickListener() {
					public void onClick(View arg0) {
						doStopService();
					}
				});

		findViewById(R.id.btBindService).setOnClickListener(
				new OnClickListener() {
					public void onClick(View arg0) {
						doBindService();
					}
				});

		findViewById(R.id.btCheckStatus).setOnClickListener(
				new OnClickListener() {
					public void onClick(View arg0) {
						doCheckStatus();
					}
				});

		findViewById(R.id.btIncrement).setOnClickListener(
				new OnClickListener() {
					public void onClick(View arg0) {
						doIncrement();
					}
				});

		findViewById(R.id.btUnbindService).setOnClickListener(
				new OnClickListener() {
					public void onClick(View arg0) {
						doUnbindService();
					}
				});		
	}

	/**
	 * Démarre un service autonome
	 */
	private void doStartService() {
		startService(new Intent(this, ServiceAutonome.class));
	}

	/**
	 * Stop un service autonome
	 */
	private void doStopService() {
		stopService(new Intent(this, ServiceAutonome.class));
	}

	/**
	 * Execute la liaison, on peut voir ceci comme une connection a a un client
	 * ver un seveur.
	 */
	private void doBindService() {
		// startService(new Intent(this, ServiceLie.class));
		boolean boundSuccess = bindService(new Intent(this, ServiceLie.class),
				mConnection, BIND_AUTO_CREATE);

		showToastAndLog("Binding service success : " + boundSuccess);
	}

	/**
	 * Si le service est lié on brise la liaison.
	 */
	private void doUnbindService() {

		String msg = "Not binded...";

		if (mBound) {
			Log.d(TAG, "Unbinding service (" + mBound + ")");
			unbindService(mConnection);
			stopService(new Intent(this, ServiceLie.class));
			mBound = false;
		} else {
			showToastAndLog(msg);
		}
	}

	/**
	 * Retourne le status du service. Est-ce notre méthode est lié ou non?
	 */
	private void doCheckStatus() {

		String msg = "Service is not binded";

		if (mBound) {
			msg = "My number is " + mService.getNumber();
		}

		showToastAndLog(msg);
	}

	/**
	 * Simple méthode pour afficher une "toast" à l'utilisateur et inscrit le
	 * message dans les logs.
	 * 
	 * @param msg
	 */
	private void showToastAndLog(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		Log.d(TAG, msg);
	}

	/**
	 * Une méthode qui permet d'incérmenter un service bidon. Normalement on
	 * appliquerais notre logique applicative dans une méthode plus utile.
	 */
	private void doIncrement() {

		String msg = "Service is not binded";
		if (mBound) {
			msg = "Executing the logic";
			mService.incrementNumber();
		}

		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	/* 
	 * Important on pause la lisaison quand l'activité perd la vue (voir cycle de vies des activités)
	 * elle pourrait être tuée d'un moment à l'autre donc on se déconnect du
	 * service pour éviter des "leak" de liaison.
	 * 
	 * (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();

		if (mBound) {
			Log.d(TAG, "Pause binding");
			doUnbindService();
			bindingPaused = true;
		}
	}

	/* 
	 * On restore la liaison avec le service.
	 * 
	 * (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();

		if (!mBound && bindingPaused) {
			Log.d(TAG, "Restore binding");
			doBindService();
			bindingPaused = false;
		}

	}

}