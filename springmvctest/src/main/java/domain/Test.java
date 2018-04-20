package domain;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        HttpMessageConverter httpMessageConverter = new HttpMessageConverter() {
            public boolean canRead(Class aClass, MediaType mediaType) {
                return false;
            }

            public boolean canWrite(Class aClass, MediaType mediaType) {
                return false;
            }

            public List<MediaType> getSupportedMediaTypes() {
                return null;
            }

            public Object read(Class aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
                return null;
            }

            public void write(Object o, MediaType mediaType, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {

            }
        };
    }
}
