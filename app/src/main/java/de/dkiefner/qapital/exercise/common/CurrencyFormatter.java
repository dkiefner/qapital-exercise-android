package de.dkiefner.qapital.exercise.common;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormatter {

	private final NumberFormat formatter;

	public CurrencyFormatter(Locale locale) {
		formatter = NumberFormat.getCurrencyInstance(locale);
	}

	public String format(Object number) {
		return formatter.format(number);
	}
}
