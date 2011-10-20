/**
 * 
 */
package com.trialog.geolocation.accessor;

import java.util.ArrayList;
import java.util.List;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

/**
 * @author olivierm
 *
 */
public class WifiAccessor {

	/**
	 * 
	 */
	public WifiAccessor() {
	}
	
	/**
	 * Récupère la liste des informations Wifi alentours
	 * @pre Le WifiManager a été initialisé avec getSystemService dans une Activity
	 * @param wifiManager Le wifi manager
	 * @return list des informations sur les Wifi
	 */
	public static List<WifiResult> getWifiResultList(WifiManager wifiManager) {
		// Scan
		if (null == wifiManager || !wifiManager.startScan()) {
			return null;
		}
		// Récupération du résultat du scan
		List<ScanResult> scanResults = wifiManager.getScanResults();
		if (null == scanResults || scanResults.size() <= 0) {
			return null;
		}
		// Récupération du BSSID
		List<WifiResult> result = new ArrayList<WifiResult>();
		for(ScanResult scanResult : scanResults) {
			result.add(new WifiResult(scanResult));
		}
		return result;
	}
}
