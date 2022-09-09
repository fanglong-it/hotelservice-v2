package fiveman.hotelservice.utils;

public class Utilities {

	public static boolean isEmptyString(String result) {
		if(result == null || result.trim().isEmpty() || result.isEmpty()) {
			return true;
		}
		return false;
	}
}
