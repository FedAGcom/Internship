package com.fedag.internship.service.impl;

import com.fedag.internship.domain.entity.TraineePositionEntity;
import com.fedag.internship.domain.util.GoogleCalendarConstants;
import com.fedag.internship.service.GoogleCalendarService;
import com.fedag.internship.service.TraineePositionService;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;

import static com.fedag.internship.domain.util.GoogleCalendarConstants.APPLICATION_NAME;
import static com.fedag.internship.domain.util.GoogleCalendarConstants.CREDENTIALS_FILE_PATH;
import static com.fedag.internship.domain.util.GoogleCalendarConstants.JSON_FACTORY;
import static com.fedag.internship.domain.util.GoogleCalendarConstants.SCOPES;
import static com.fedag.internship.domain.util.GoogleCalendarConstants.TOKENS_DIRECTORY_PATH;

/**
 * class GoogleCalendarServiceImpl is implementation of {@link GoogleCalendarService} interface.
 *
 * @author damir.iusupov
 * @since 2022-06-10
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleCalendarServiceImpl implements GoogleCalendarService {
    private final TraineePositionService traineePositionService;

    @SneakyThrows
    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) {
        log.info("Получение credentials");
        log.info("Загрузка данных из {}", CREDENTIALS_FILE_PATH);
        InputStream in = GoogleCalendarConstants.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            log.error("Данные из {} не найдены", CREDENTIALS_FILE_PATH);
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        log.info("Загрузка данных из {} прошла успешно", CREDENTIALS_FILE_PATH);
        log.info("Создание потока для авторизации Google");
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow
                .Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        log.info("Поток для авторизацию Google содан");
        log.info("Получение ссылки для авторизации Google и токена пользователя от Google");
        LocalServerReceiver receiver = new LocalServerReceiver.Builder()
                .setCallbackPath("/intership/api/v1.0/callback")
                .setPort(8000)
                .build();
        // result link for front
//        String resultLink = flow.getAuthorizationServerEncodedUrl() + "?" +
//                "access_type=" + flow.getAccessType() +
//                "&client_id=" + flow.getClientId() +
//                "&redirect_uri=http://" + receiver.getHost() +
//                ":" + receiver.getPort() +
//                receiver.getCallbackPath() +
//                "&response_type=code&scope=" +
//                flow.getScopesAsString();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        log.info("Credentials в виде токена получены");
        return credential;
    }

    @Override
    @SneakyThrows
    public void addEventToCalendar(Long traineePositionId) {
        log.info("Добавление стажировки в календарь пользователю");
        final TraineePositionEntity traineePosition = traineePositionService.findById(traineePositionId);
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, this.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        log.info("Создание нового календаря от пользователя");
        var calendar = new com.google.api.services.calendar.model.Calendar();
        calendar.setSummary(traineePosition.getName());
        var executedCalendar = service.calendars().insert(calendar).execute();
        log.info("Календарь создан");
        log.info("Создание евента в виде стажировки и добавление в календарь");
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        Event event = new Event();
        event.setSummary(traineePosition.getEmployeePosition())
                .setStart(new EventDateTime()
                        .setDateTime(new DateTime(traineePosition.getDate().format(formatterDate) +
                                "T" +
                                traineePosition.getDate().minusHours(3).format(formatterTime))))
                .setEnd(event.getStart());
        service.events().insert(executedCalendar.getId(), event).execute();
        log.info("Евент создан и добавлен в календарь");
        try {
            log.info("Удаление токена пользователя");
            new File(TOKENS_DIRECTORY_PATH + "/StoredCredential").delete();
        } finally {
            log.info("Токен пользователя удален");
        }
    }
}
