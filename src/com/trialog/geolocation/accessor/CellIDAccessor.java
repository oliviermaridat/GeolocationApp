/**
 * 
 */
package com.trialog.geolocation.accessor;

import java.util.HashMap;
import java.util.Map;

import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;

/**
 * @author olivierm
 *
 */
public class CellIDAccessor {

	/**
	 * 
	 */
	public CellIDAccessor() {
	}
	
	/**
	 * Récup_re CID et le LAC de la cellule la plus proche
	 * @pre Le TelephonyManager a été initialisé avec getSystemService dans une Activity
	 * @param telephonyManager Le telephony manager
	 * @return Map contenant un champ "cid" et un champ "lac"
	 */
	public static Map<String, Object> getCIDAnLac(TelephonyManager telephonyManager) {
		if (null == telephonyManager) {
			return null;
		}
		GsmCellLocation gsmCellLocation = (GsmCellLocation) telephonyManager.getCellLocation();
		if (null == gsmCellLocation) {
			return null;
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("cid", gsmCellLocation.getCid());
		result.put("lac", gsmCellLocation.getLac());
		return result;
	}

}
