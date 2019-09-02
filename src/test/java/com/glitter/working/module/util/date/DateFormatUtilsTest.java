package com.glitter.working.module.util.date;

import org.junit.Test;

import static com.glitter.working.module.util.date.DateFormatUtils.dateFormat;
import static com.glitter.working.module.util.date.DateUtils.getNowTime;

public class DateFormatUtilsTest {

    @Test
    public void dateFormatTest(){
        dateFormat(getNowTime(),"");
    }

}
