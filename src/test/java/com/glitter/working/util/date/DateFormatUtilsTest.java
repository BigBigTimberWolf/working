package com.glitter.working.util.date;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.glitter.working.util.date.DateFormatUtils.dateFormat;
import static com.glitter.working.util.date.DateUtils.getNowTime;

public class DateFormatUtilsTest {

    @Test
    public void dateFormatTest(){
        dateFormat(getNowTime(),"");
    }

}
