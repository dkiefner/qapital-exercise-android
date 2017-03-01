package de.dkiefner.qapital.exercise.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public final class DateTimeParser {

	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());

	static {
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	private DateTimeParser() {
	}

	public static long toMillis(String dateTime) throws ParseException {
		return formatter.parse(dateTime).getTime();
	}
}
