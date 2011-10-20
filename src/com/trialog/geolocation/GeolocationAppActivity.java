package com.trialog.geolocation;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.trialog.geolocation.accessor.CellIDAccessor;
import com.trialog.geolocation.accessor.WifiAccessor;
import com.trialog.geolocation.accessor.WifiResult;

public class GeolocationAppActivity extends Activity implements OnClickListener, LocationListener {
	public String TAG = "GeolocationAppActivity";
	protected Button btnGps;
	protected Button btnCellId;
	protected Button btnWifi;
	protected Button btnNetwork;
	protected Button btnCancel;
	protected ProgressBar loadingBar;
	protected TextView txtLoadingBar;
	protected TextView txtDataResult;

	private LocationManager locationManager;
	private WifiManager wifiManager;
	private TelephonyManager telephonyManager;
	private Map<String, Object> values;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
	     // --- IHM
		loadingBar = (ProgressBar) findViewById(R.id.loadingBar);
		txtLoadingBar = (TextView) findViewById(R.id.txtLoadingBar);
		btnCancel = (Button) findViewById(R.id.btnCancel);
			btnCancel.setOnClickListener(this);
			txtLoadingBar.setVisibility(View.INVISIBLE);
			loadingBar.setVisibility(View.INVISIBLE);
			btnCancel.setVisibility(View.INVISIBLE);
		txtDataResult = (TextView) findViewById(R.id.txtResultData);
		btnGps = (Button) findViewById(R.id.gps);
			btnGps.setOnClickListener(this);
		btnCellId = (Button) findViewById(R.id.cellid);
			btnCellId.setOnClickListener(this);
		btnWifi = (Button) findViewById(R.id.wifi);
			btnWifi.setOnClickListener(this);
		btnNetwork = (Button) findViewById(R.id.network);
			btnNetwork.setOnClickListener(this);
 	}

 	public void onDestroy() {
 		super.onDestroy();
 	}

 	@Override
 	public void onClick(View v) {
 		// Actions à effectuer selon le bouton
 		switch (v.getId()) {
 			// GPS
 			case R.id.gps:
 				Log.i(TAG, "GPS");
 				locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
 				// Si un capteur GPS est dispo et activé
 				if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
 					// Activation du loader
 					txtLoadingBar.setVisibility(View.VISIBLE);
 					loadingBar.setVisibility(View.VISIBLE);
 					btnCancel.setVisibility(View.VISIBLE);
 					// Lancement de la géolocalisation. On récupère le résultat avec onLocationChanged
 					locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
 		        }
 				// Si pas de capteur, ou GPS désactivé
 				else {
 					txtDataResult.setText("No GPS data");
 				}
 				break;
 				
 			// CELL-ID
 			case R.id.cellid:
 				Log.i(TAG, "Cell-ID");
 				telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
 				// Lancement de la géolocalisation
 				values = CellIDAccessor.getCIDAnLac(telephonyManager);
 				// Affichage selon le résultat
 				if (null == values || values.size() <= 0) {
 					txtDataResult.setText("No Cell-ID data");
 				}
 				else {
 					txtDataResult.setText("CID: "+values.get("cid")+"\n LAC: "+values.get("lac"));
 				}
 				break;
 				
 			// WIFI
 			case R.id.wifi:
 				Log.i(TAG, "Wi-Fi");
 				wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
 				// Lancement de la géolocalisation
 				List<WifiResult> wifiResults = WifiAccessor.getWifiResultList(wifiManager);
 				// Affichage selon le résultat
 				if (null == wifiResults || wifiResults.size() <= 0) {
 					txtDataResult.setText("No Wi-Fi data");
 				}
 				// S'il y a des résultats, on tri par puissance du signal et on en affiche 3 
 				else {
 					// Sort by level
 					Collections.sort(wifiResults);
 					// Get the  "limit"th first
 					StringBuffer sbToPrint = new StringBuffer();
 					StringBuffer sbToSave = new StringBuffer();
 					int limit = 3; // Max number of mac adress to print
 					int lenght = wifiResults.size();
 					for (WifiResult wifiResult : wifiResults) {
 						sbToPrint.append("\n"+wifiResult.toString());
 						// If it's the last
 						if (limit == 1 || lenght == 1) {
 							sbToSave.append(wifiResult.getBSSID());
 							break;
 						}
 						sbToSave.append(wifiResult.getBSSID()+",");
 						limit--; lenght--;
 					}
 					txtDataResult.setText("MAC Adresses : "+sbToPrint.toString());
 				}
 				break;
 				
 			// NETWORK
 			case R.id.network:
 				Log.i(TAG, "Network");
 				locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
 				if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
 					txtLoadingBar.setVisibility(View.VISIBLE);
 					loadingBar.setVisibility(View.VISIBLE);
 					btnCancel.setVisibility(View.VISIBLE);
 					locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
 		        }
 				else {
 					txtDataResult.setText("No Network data");
 				}
 				break;
 				
 				
 			// ANNULATION D'UNE ACTION
 			case R.id.btnCancel:
 				// Disabled the loader
 	 			txtLoadingBar.setVisibility(View.INVISIBLE);
 	 			loadingBar.setVisibility(View.INVISIBLE);
 	 			btnCancel.setVisibility(View.INVISIBLE);
 	 			// Stop listening for location updates
 	 			if (null != locationManager) {
 	 				locationManager.removeUpdates(this);
 	 			}
 				break;
 		}
 	}

 	@Override
 	public void onLocationChanged(Location location) {
 		if (null != location) {
 			// Getting location data
 			values.put("altitude", location.getAltitude());
 			values.put("accuracy", location.getAccuracy());
 			values.put("latitude", location.getLatitude());
 			values.put("longitude", location.getLongitude());
 			values.put("provider", location.getProvider());
 			// print data
 			txtDataResult.setText("Latitude: "+values.get("latitude")+"\nLongitude: "+values.get("longitude")+"\nAltitude: "+values.get("altitude")+"\nAccuracy: "+values.get("accuracy"));
 			// Disabled the loader
 			txtLoadingBar.setVisibility(View.INVISIBLE);
 			loadingBar.setVisibility(View.INVISIBLE);
 			btnCancel.setVisibility(View.INVISIBLE);
 			// Stop listening for location updates
 			locationManager.removeUpdates(this);
 		}
 	}

 	@Override
 	public void onProviderDisabled(String provider) {
 	}

 	@Override
 	public void onProviderEnabled(String provider) {
 	}

 	@Override
 	public void onStatusChanged(String provider, int status, Bundle extras) {
 	}
}