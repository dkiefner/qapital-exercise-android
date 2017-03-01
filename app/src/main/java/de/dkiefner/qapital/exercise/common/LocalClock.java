package de.dkiefner.qapital.exercise.common;

import org.threeten.bp.ZonedDateTime;

public class LocalClock {

	public ZonedDateTime nowAsZonedDateTime() {
		return ZonedDateTime.now();
	}
}
