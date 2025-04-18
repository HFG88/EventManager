package hi.verkefni.vinnsla;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hi.verkefni.vinnsla.data.EventData;

import java.io.File;
import java.io.IOException;

public class EventStorage {
    private static final ObjectMapper mapper = new ObjectMapper()
            .findAndRegisterModules()
            .enable(SerializationFeature.INDENT_OUTPUT);

    public static void save(File file, EventData data) throws IOException {
        mapper.writeValue(file, data);
    }

    public static EventData load(File file) throws IOException {
        return mapper.readValue(file, EventData.class);
    }
}
