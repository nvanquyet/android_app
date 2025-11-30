package com.example.android_exam.core.datetime;

import android.util.Log;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Centralized Date/Time management utility
 * All date/time operations should use this class for consistency
 */
public class DateTimeManager {
    private static final String TAG = "DateTimeManager";

    // Timezone constants
    private static final ZoneId SYSTEM_TIMEZONE = ZoneId.systemDefault();
    private static final ZoneId UTC_TIMEZONE = ZoneOffset.UTC;

    // Standard formatters - thread-safe and reusable
    public static final DateTimeFormatter ISO_8601_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    public static final DateTimeFormatter ISO_8601_WITHOUT_MS = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    public static final DateTimeFormatter ISO_8601_LOCAL = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd", Locale.getDefault());
    public static final DateTimeFormatter DD_MM_YYYY_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Flexible ISO parsers for different formats
    private static final DateTimeFormatter[] ISO_PARSERS = {
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    };

    private static DateTimeManager instance;

    private DateTimeManager() {
    }

    public static synchronized DateTimeManager getInstance() {
        if (instance == null) {
            instance = new DateTimeManager();
        }
        return instance;
    }

    // ==================== Current Date/Time ====================

    /**
     * Get today's date as string in system timezone (yyyy-MM-dd)
     */
    public String getTodayString() {
        return LocalDate.now(SYSTEM_TIMEZONE).format(DATE_FORMAT);
    }

    /**
     * Get today's date as string in UTC timezone
     */
    public String getTodayStringUTC() {
        return LocalDate.now(UTC_TIMEZONE).format(DATE_FORMAT);
    }

    /**
     * Get current timestamp in UTC milliseconds
     */
    public long getCurrentTimestampUTC() {
        return Instant.now().toEpochMilli();
    }

    /**
     * Get current LocalDate in system timezone
     */
    public LocalDate getCurrentDate() {
        return LocalDate.now(SYSTEM_TIMEZONE);
    }

    /**
     * Get current LocalDateTime in system timezone
     */
    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now(SYSTEM_TIMEZONE);
    }

    // ==================== Date Range Operations ====================

    /**
     * Get last N days from today in system timezone
     */
    public List<String> getLastNDays(int n) {
        List<String> days = new ArrayList<>(n);
        LocalDate today = LocalDate.now(SYSTEM_TIMEZONE);
        for (int i = 0; i < n; i++) {
            LocalDate date = today.minusDays(i);
            days.add(date.format(DATE_FORMAT));
        }
        return days;
    }

    /**
     * Get last 5 days from today
     */
    public List<String> getLast5Days() {
        return getLastNDays(5);
    }

    /**
     * Get last 2 days from today
     */
    public List<String> getLast2Days() {
        return getLastNDays(2);
    }

    // ==================== Parsing Operations ====================

    /**
     * Parse ISO 8601 date string to LocalDateTime
     * Supports multiple formats with/without microseconds/milliseconds
     * Automatically handles UTC conversion if 'Z' suffix is present
     */
    public LocalDateTime parseIsoDateTime(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }

        String originalString = dateString.trim();
        boolean isUtc = originalString.endsWith("Z");
        String cleanDateString = originalString.replace("Z", "");

        try {
            LocalDateTime localDateTime = null;

            // Try with fractional seconds
            if (cleanDateString.contains(".")) {
                String[] parts = cleanDateString.split("\\.");
                String fractionalPart = parts[1];

                if (fractionalPart.length() >= 6) {
                    // Microseconds - truncate to 6 digits
                    cleanDateString = parts[0] + "." + fractionalPart.substring(0, 6);
                    localDateTime = LocalDateTime.parse(cleanDateString, ISO_PARSERS[0]);
                } else if (fractionalPart.length() >= 1) {
                    // Milliseconds - pad to 3 digits
                    String milliseconds = String.format("%-3s", fractionalPart).replace(' ', '0');
                    if (milliseconds.length() > 3) {
                        milliseconds = milliseconds.substring(0, 3);
                    }
                    cleanDateString = parts[0] + "." + milliseconds;
                    localDateTime = LocalDateTime.parse(cleanDateString, ISO_PARSERS[1]);
                } else {
                    cleanDateString = parts[0];
                    localDateTime = LocalDateTime.parse(cleanDateString, ISO_PARSERS[2]);
                }
            } else {
                // Try without fractional seconds
                localDateTime = LocalDateTime.parse(cleanDateString, ISO_PARSERS[2]);
            }

            // If the original string had Z suffix, convert from UTC to local time
            if (isUtc && localDateTime != null) {
                ZonedDateTime utcDateTime = localDateTime.atZone(UTC_TIMEZONE);
                ZonedDateTime localZonedDateTime = utcDateTime.withZoneSameInstant(SYSTEM_TIMEZONE);
                return localZonedDateTime.toLocalDateTime();
            }

            return localDateTime;

        } catch (DateTimeParseException e) {
            Log.e(TAG, "Failed to parse ISO date: " + dateString, e);
            return null;
        }
    }

    /**
     * Parse ISO date string to Date object
     */
    public Date parseIsoDateToDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }

        try {
            LocalDateTime dateTime = parseIsoDateTime(dateString);
            if (dateTime != null) {
                return Date.from(dateTime.atZone(SYSTEM_TIMEZONE).toInstant());
            }

            // Try dd/MM/yyyy format as fallback
            LocalDate localDate = LocalDate.parse(dateString, DD_MM_YYYY_FORMAT);
            return convertToDate(localDate);
        } catch (DateTimeParseException e) {
            Log.e(TAG, "Failed to parse date: " + dateString, e);
            return null;
        }
    }

    /**
     * Parse date string (yyyy-MM-dd) to LocalDate
     */
    public LocalDate parseDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(dateString, DATE_FORMAT);
        } catch (DateTimeParseException e) {
            Log.e(TAG, "Failed to parse date: " + dateString, e);
            return null;
        }
    }

    // ==================== Formatting Operations ====================

    /**
     * Format Date to ISO 8601 string in UTC (for API calls)
     */
    public String formatDateTimeToIsoUTC(Date dateTime) {
        if (dateTime == null) {
            dateTime = new Date();
        }
        ZonedDateTime utcDateTime = dateTime.toInstant().atZone(UTC_TIMEZONE);
        return utcDateTime.format(ISO_8601_FORMAT);
    }

    /**
     * Format LocalDateTime to ISO 8601 string in UTC (for API calls)
     */
    public String formatLocalDateTimeToIsoUTC(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            localDateTime = LocalDateTime.now(SYSTEM_TIMEZONE);
        }
        ZonedDateTime zonedDateTime = localDateTime.atZone(SYSTEM_TIMEZONE);
        ZonedDateTime utcDateTime = zonedDateTime.withZoneSameInstant(UTC_TIMEZONE);
        return utcDateTime.format(ISO_8601_FORMAT);
    }

    /**
     * Format LocalDate to ISO 8601 string (at start of day)
     */
    public String formatDateToIso(LocalDate date) {
        if (date == null) {
            return LocalDateTime.now(SYSTEM_TIMEZONE).format(ISO_8601_LOCAL);
        }
        return date.atStartOfDay().format(ISO_8601_LOCAL);
    }

    /**
     * Format Date to dd/MM/yyyy string
     */
    public String formatDate(Date date) {
        if (date == null) return "";
        LocalDate localDate = convertToLocalDate(date);
        return localDate != null ? localDate.format(DD_MM_YYYY_FORMAT) : "";
    }

    /**
     * Format date string for display (MMM dd format)
     */
    public String formatDateForDisplay(String dateString) {
        try {
            LocalDate date = LocalDate.parse(dateString, DATE_FORMAT);
            return date.format(DISPLAY_FORMAT);
        } catch (DateTimeParseException e) {
            Log.w(TAG, "Failed to parse date for display: " + dateString, e);
            return dateString;
        }
    }

    /**
     * Format LocalDate for display
     */
    public String formatDateForDisplay(LocalDate date) {
        if (date == null) return "";
        return date.format(DISPLAY_FORMAT);
    }

    // ==================== Conversion Operations ====================

    /**
     * Convert Date to LocalDate in system timezone
     */
    public LocalDate convertToLocalDate(Date date) {
        if (date == null) return null;
        return date.toInstant().atZone(SYSTEM_TIMEZONE).toLocalDate();
    }

    /**
     * Convert Date to LocalDateTime in system timezone
     */
    public LocalDateTime convertToLocalDateTime(Date date) {
        if (date == null) return null;
        return date.toInstant().atZone(SYSTEM_TIMEZONE).toLocalDateTime();
    }

    /**
     * Convert LocalDate to Date (at start of day in system timezone)
     */
    public Date convertToDate(LocalDate localDate) {
        if (localDate == null) return null;
        return Date.from(localDate.atStartOfDay(SYSTEM_TIMEZONE).toInstant());
    }

    /**
     * Convert LocalDateTime to Date
     */
    public Date convertToDate(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        return Date.from(localDateTime.atZone(SYSTEM_TIMEZONE).toInstant());
    }

    /**
     * Convert LocalDate to LocalDateTime at start of day
     */
    public LocalDateTime toLocalDateTime(LocalDate localDate) {
        if (localDate == null) return null;
        return localDate.atStartOfDay();
    }

    // ==================== Date Comparison Operations ====================

    /**
     * Get days until expiry (positive = future, negative = past)
     */
    public int getDaysUntilExpiry(String expiryDate) {
        try {
            LocalDate expiry = LocalDate.parse(expiryDate, DATE_FORMAT);
            LocalDate today = LocalDate.now(SYSTEM_TIMEZONE);
            return (int) ChronoUnit.DAYS.between(today, expiry);
        } catch (DateTimeParseException e) {
            Log.w(TAG, "Failed to parse expiry date: " + expiryDate, e);
            return 0;
        }
    }

    /**
     * Get days until expiry from Date object
     */
    public int getDaysUntilExpiry(Date expiryDate) {
        if (expiryDate == null) {
            return 0;
        }
        LocalDate localExpiry = convertToLocalDate(expiryDate);
        if (localExpiry == null) {
            return 0;
        }
        LocalDate today = LocalDate.now(SYSTEM_TIMEZONE);
        return (int) ChronoUnit.DAYS.between(today, localExpiry);
    }

    /**
     * Check if date is expiring soon (within daysAhead days)
     */
    public boolean isExpiringSoon(String expiryDate, int daysAhead) {
        int daysUntilExpiry = getDaysUntilExpiry(expiryDate);
        return daysUntilExpiry <= daysAhead && daysUntilExpiry >= 0;
    }

    /**
     * Check if date is expiring soon from Date object
     */
    public boolean isExpiringSoon(Date expiryDate, int daysAhead) {
        int daysUntilExpiry = getDaysUntilExpiry(expiryDate);
        return daysUntilExpiry <= daysAhead && daysUntilExpiry >= 0;
    }

    /**
     * Check if date is expired
     */
    public boolean isExpired(String expiryDate) {
        return getDaysUntilExpiry(expiryDate) < 0;
    }

    /**
     * Check if a date is today
     */
    public boolean isToday(LocalDate date) {
        if (date == null) return false;
        return date.isEqual(LocalDate.now(SYSTEM_TIMEZONE));
    }

    /**
     * Check if a date is yesterday
     */
    public boolean isYesterday(LocalDate date) {
        if (date == null) return false;
        return date.isEqual(LocalDate.now(SYSTEM_TIMEZONE).minusDays(1));
    }

    /**
     * Check if a date is tomorrow
     */
    public boolean isTomorrow(LocalDate date) {
        if (date == null) return false;
        return date.isEqual(LocalDate.now(SYSTEM_TIMEZONE).plusDays(1));
    }

    /**
     * Get relative date string (Today, Yesterday, Tomorrow, or formatted date)
     */
    public String getRelativeDateString(LocalDate date) {
        if (date == null) return "";

        if (isToday(date)) {
            return "Today";
        } else if (isYesterday(date)) {
            return "Yesterday";
        } else if (isTomorrow(date)) {
            return "Tomorrow";
        } else {
            return formatDateForDisplay(date);
        }
    }

    /**
     * Calculate age from birth date
     */
    public int calculateAge(LocalDate birthDate) {
        if (birthDate == null) return 0;
        return (int) ChronoUnit.YEARS.between(birthDate, LocalDate.now(SYSTEM_TIMEZONE));
    }

    /**
     * Create LocalDateTime from date/time components in system timezone
     */
    public LocalDateTime createLocalDateTime(int year, int month, int dayOfMonth, int hour, int minute) {
        return LocalDateTime.of(year, month + 1, dayOfMonth, hour, minute, 0);
    }
}

