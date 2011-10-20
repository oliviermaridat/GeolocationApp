package com.trialog.geolocation.accessor;

import java.util.Comparator;

import android.net.wifi.ScanResult;

public class WifiResult implements Comparable {
	protected String BSSID;
	protected String SSID;
	protected int level;
	protected int frequency;
	protected String capabilities;
	
	public WifiResult() {
		BSSID = "";
		SSID = "";
		level = 0;
		frequency = 0;
		capabilities = "";
	}
	public WifiResult(String BSSID, String SSID, int level) {
		this.BSSID = BSSID;
		this.SSID = SSID;
		this.level = level;
		this.frequency = 0;
		this.capabilities = "";
	}
	public WifiResult(ScanResult scanResult) {
		BSSID = scanResult.BSSID;
		SSID = scanResult.SSID;
		level = scanResult.level;
		frequency = scanResult.frequency;
		capabilities = scanResult.capabilities;
	}

	@Override
	/**
	 * /!\ Inverse of the attempted result to make a decroissant sort 
	 */
	public int compareTo(Object wifiResult) {
		// If object to compare is not a WifiResult, we saif : "this" is greater
		if (!WifiResult.class.equals(wifiResult.getClass())) {
			return -1;
		}
		// Else : If this<wifiResult => nb >0, else <0
		return ((WifiResult) wifiResult).getLevel() - getLevel();
	}
	
		@Override
	public String toString() {
		return SSID+" ["+BSSID+" ("+level+")]";
	}
	// get, set
	public String getBSSID() {
		return BSSID;
	}
	public void setBSSID(String bSSID) {
		BSSID = bSSID;
	}
	public String getSSID() {
		return SSID;
	}
	public void setSSID(String sSID) {
		SSID = sSID;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public String getCapabilities() {
		return capabilities;
	}
	public void setCapabilities(String capabilities) {
		this.capabilities = capabilities;
	}
}
