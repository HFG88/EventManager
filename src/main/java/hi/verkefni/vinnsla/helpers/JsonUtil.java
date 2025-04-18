package hi.verkefni.vinnsla.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;

public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper()
            .findAndRegisterModules() // support for Java 8+ types like LocalDate
            .enable(SerializationFeature.INDENT_OUTPUT); // pretty print

    /**
     * Writes any object to JSON file.
     *
     * @param file the file to write to
     * @param data the object to save
     * @throws IOException if writing fails
     */
    public static void writeToJson(File file, Object data) throws IOException {
        mapper.writeValue(file, data);
    }

    /**
     * Reads a JSON file and returns an object of the given class.
     *
     * @param file  the JSON file
     * @param clazz the class to deserialize into
     * @param <T>   the object type
     * @return deserialized object
     * @throws IOException if reading fails
     */
    public static <T> T readFromJson(File file, Class<T> clazz) throws IOException {
        return mapper.readValue(file, clazz);
    }
}
