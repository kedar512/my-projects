package com.example.demo.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, LocalDate> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    @Override
    public String marshal(LocalDate localDate) {
    	return localDate.format(FORMATTER);
    }
    
    public static String marshalDate(LocalDate localDate) {
        return localDate.format(FORMATTER);
    }

    @Override
    public LocalDate unmarshal(String date) {
    	return LocalDate.parse(date, FORMATTER);
    }
    
    public static LocalDate unmarshalDate(String date) {
    	return LocalDate.parse(date, FORMATTER);
    }

}
