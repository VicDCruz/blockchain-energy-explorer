package mx.unam.cruz.victor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class JsonParseUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T parse(String js, Class<T> tClass) {
        if (isBlank(js)) return null;
        try {
            return objectMapper.readValue(js, tClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String stringify(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
