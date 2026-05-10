package com.example.backend.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class DateFormatterUtil {

	private static final List<String> DATE_PATTERNS = Arrays.asList(
			"yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd",
			"MM-dd-yyyy", "MM/dd/yyyy", "MM.dd.yyyy",
			"dd-MM-yyyy", "dd/MM/yyyy", "dd.MM.yyyy", 
			"dd MMM yyyy");

	public static LocalDate parseDate(String text) {
		for (String pattern : DATE_PATTERNS) {

			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

				return LocalDate.parse(text, formatter);

			} catch (DateTimeParseException ignored) {
			}
		}

		throw new RuntimeException("Unsupported date format: " + text);
	}

}
