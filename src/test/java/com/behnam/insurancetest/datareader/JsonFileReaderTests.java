package com.behnam.insurancetest.datareader;

import static org.junit.jupiter.api.Assertions.*;

import com.behnam.insurancetest.exceptions.ReadJsonException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class JsonFileReaderTests {


    @Value("${data.file.main}")
    String mainDataFile;

    @Value("${data.file.bad-format}")
    String badFormatDataFile;

    @Value("${data.file.wrong-event}")
    String wrongEventDataFile;

    @Value("${data.file.records.main}")
    int MAIN_FILE_RECORDS;

    @Test
    void shouldReadMainFile() {
        ReadJson readJson = new ReadJson(mainDataFile);
        List<Event> events = readJson.read();
        assertNotNull(events);
        assertEquals(events.size(), MAIN_FILE_RECORDS);
    }

    @Test
    void shouldThrowReadJsonException_wrongFileName() {
        assertThrows(ReadJsonException.class, () -> {
            ReadJson readJson = new ReadJson("wrongFileName");
            List<Event> events = readJson.read();
        });
    }

    @Test
    void shouldThrowReadJsonException_badFormat() {
        assertThrows(ReadJsonException.class, () -> {
            ReadJson readJson = new ReadJson(badFormatDataFile);
            readJson.read();
        });
    }

    @Test
    void shouldThrowReadJsonException_wrongEventName() {
        assertThrows(ReadJsonException.class, () -> {
            ReadJson readJson = new ReadJson(wrongEventDataFile);
            List<Event> events = readJson.read();
        });
    }
}
