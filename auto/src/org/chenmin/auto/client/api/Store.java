package org.chenmin.auto.client.api;

import com.google.gwt.storage.client.Storage;

public class Store {
	private static boolean isLocalStorageSupported = Storage.isLocalStorageSupported();
	
	public static void setItem(String keys,String vals){
		if (isLocalStorageSupported) {
			Storage s = Storage.getLocalStorageIfSupported();
			s.setItem(keys, vals);
		}
	}

	public static String getItem(String keys){
		if (isLocalStorageSupported) {
			Storage s = Storage.getLocalStorageIfSupported();
			return s.getItem(keys);
		}
		return null;
	}
}
