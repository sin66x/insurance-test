package com.behnam.insurancetest.datareader;

import com.behnam.insurancetest.exceptions.ReadJsonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is for reading JSON File and returning List of Events
 */
@Component
public class ReadJson {

    private final String jsonPath;

    @Autowired
    public ReadJson(@Value("${data.file}") String jsonPath) {
        this.jsonPath = jsonPath;
    }

    /**
     * we assumed that objects in the file are in one line
     * @return List of events read from JSON File
     */
    public List<Event> read() {
        List<Event> events = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            File jsonFile = ResourceUtils.getFile(jsonPath);
            FileInputStream inputStream = new FileInputStream(jsonFile);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line;
            while ((line = br.readLine()) != null) {
                Event event = mapper.readValue(line, Event.class);
                events.add(event);
            }
        } catch (IOException e) {
            throw new ReadJsonException(e.getMessage());
        }

        return events;
    }
}
