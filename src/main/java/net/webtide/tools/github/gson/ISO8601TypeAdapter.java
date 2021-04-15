//
// ========================================================================
// Copyright (c) Webtide LLC and others.
//
// This program and the accompanying materials are made available under the
// terms of the Apache License, Version 2.0 which is available at
// https://www.apache.org/licenses/LICENSE-2.0.
//
// SPDX-License-Identifier: Apache-2.0
// ========================================================================
//

package net.webtide.tools.github.gson;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class ISO8601TypeAdapter extends TypeAdapter<ZonedDateTime>
{
    @Override
    public void write(JsonWriter out, ZonedDateTime value) throws IOException
    {
        if (value == null)
        {
            out.nullValue();
            return;
        }
        out.value(toISO8601(value));
    }

    public static String toISO8601(ZonedDateTime zonedDateTime)
    {
        return zonedDateTime.toOffsetDateTime().toString();
    }

    public static ZonedDateTime parseISO8601(String timestamp)
    {
        return ZonedDateTime.parse(timestamp, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    @Override
    public ZonedDateTime read(JsonReader in) throws IOException
    {
        if (in.peek() == JsonToken.NULL)
        {
            in.nextNull();
            return null;
        }
        return parseISO8601(in.nextString());
    }
}
