package com.itechart.esm.utilities.converter;

import org.postgresql.util.PGInterval;

import java.time.Period;

public class PostgresIntervalConverter {
	public static PGInterval convertTimePeriodToPGInterval(Period period) {
		int defaultHours = 0;
		int defaultMinutes = 0;
		double defaultSeconds = 0;
		return new PGInterval(period.getYears(), period.getMonths(), period.getDays(),
				defaultHours, defaultMinutes, defaultSeconds);
	}

	public static Period convertPGIntervalToPeriod(PGInterval pgInterval) {
		return Period.of(pgInterval.getYears(), pgInterval.getMonths(), pgInterval.getDays());
	}
}
