package com.example.clouds.catalogmovie.utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ext {
    public static String getDateFormat(String desiredDateFormat, String inputFormat,String inputStringDate) {

        try {
            Date date = (new SimpleDateFormat(inputFormat)).parse(inputStringDate);
            return (new SimpleDateFormat(desiredDateFormat)).format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
