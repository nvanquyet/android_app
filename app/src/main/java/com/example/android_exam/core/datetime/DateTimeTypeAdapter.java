package com.example.android_exam.core.datetime;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Date;

/**
 * Unified Gson TypeAdapter for Date serialization/deserialization
 * Uses DateTimeManager for all date operations
 */
public class DateTimeTypeAdapter extends TypeAdapter<Date> {
    private final DateTimeManager dateTimeManager;

    public DateTimeTypeAdapter() {
        this.dateTimeManager = DateTimeManager.getInstance();
    }

    @Override
    public void write(JsonWriter out, Date value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        String isoString = dateTimeManager.formatDateTimeToIsoUTC(value);
        out.value(isoString);
    }

    @Override
    public Date read(JsonReader in) throws IOException {
        if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        String dateString = in.nextString();
        return dateTimeManager.parseIsoDateToDate(dateString);
    }
}

