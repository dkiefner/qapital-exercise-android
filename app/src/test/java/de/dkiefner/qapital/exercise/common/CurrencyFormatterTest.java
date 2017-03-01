package de.dkiefner.qapital.exercise.common;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class CurrencyFormatterTest {

	private CurrencyFormatter testee;

	@Before
	public void setup() {
		testee = new CurrencyFormatter(Locale.US);
	}

	@Test
	public void thatFormatWorks_whenTwoDecimalPlacesAreGiven() {
		// given
		float number = 1.23f;
		String expectedNumber = "$1.23";

		// when
		String result = testee.format(number);

		// then
		assertThat(result).isEqualTo(expectedNumber);
	}

	@Test
	public void thatFormatWorks_whenJustOneDecimalPlaceIsGiven() {
		// given
		float number = 1.2f;
		String expectedNumber = "$1.20";

		// when
		String result = testee.format(number);

		// then
		assertThat(result).isEqualTo(expectedNumber);
	}

	@Test
	public void thatFormatWorks_whenNoDecimalPlaceIsGiven() {
		// given
		float number = 1f;
		String expectedNumber = "$1.00";

		// when
		String result = testee.format(number);

		// then
		assertThat(result).isEqualTo(expectedNumber);
	}

	@Test
	public void thatFormatWorks_whenThreeDecimalPlacesAreGiven() {
		// given
		float number = 1.234f;
		String expectedNumber = "$1.23";

		// when
		String result = testee.format(number);

		// then
		assertThat(result).isEqualTo(expectedNumber);
	}

	@Test
	public void thatFormatWorks_whenZeroIsGiven() {
		// given
		float number = 0f;
		String expectedNumber = "$0.00";

		// when
		String result = testee.format(number);

		// then
		assertThat(result).isEqualTo(expectedNumber);
	}

	@Test
	public void thatFormatRoundsUp_whenThirdNumberIsFive() {
		// given
		float number = 1.235f;
		String expectedNumber = "$1.24";

		// when
		String result = testee.format(number);

		// then
		assertThat(result).isEqualTo(expectedNumber);
	}

}