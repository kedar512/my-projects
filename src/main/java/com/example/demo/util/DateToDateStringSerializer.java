package com.example.demo.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@Component
public class DateToDateStringSerializer extends JsonSerializer<Date> {
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat(
            "MM/dd/yyyy");
 
    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        if (value != null) {        
            String formatted = dateFormat.format(value);
            gen.writeString(formatted);
        }
    }
 
}
