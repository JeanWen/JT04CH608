package com.zcbdqn.converter;


import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StringToDateConverter implements Converter<String,Date> {
    private List<SimpleDateFormat> sdfs;

    public void setSdfs(List<SimpleDateFormat> sdfs) {
        this.sdfs = sdfs;
    }

    @Override
    public Date convert(String s) {
        for (SimpleDateFormat sdf : sdfs) {
            try {
                return sdf.parse(s);
            } catch (ParseException e) {
//                e.printStackTrace();
                continue;
            }
        }
        return null;
    }
}
