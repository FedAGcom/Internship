package com.fedag.internship.domain.util;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import java.util.List;

import static com.google.api.services.calendar.CalendarScopes.CALENDAR;
import static java.util.Collections.singletonList;

/**
 * interface ApiConstants for constants in CalendarService
 *
 * @author damir.iusupov
 * @since 2022-06-10
 */
public interface GoogleCalendarConstants {
    /**
     * Application name.
     */
    String APPLICATION_NAME = "Internship";

    /**
     * Global instance of the JSON factory.
     */
    JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    /**
     * Directory to store authorization tokens for this application.
     */
    String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by start of calendar.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    List<String> SCOPES = singletonList(CALENDAR);
    String CREDENTIALS_FILE_PATH = "/credentials.json";
}
