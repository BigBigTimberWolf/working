package com.glitter.working.util.date;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @program:
 * @author: Player
 * @create: 2019-08-28
 **/
public class DateFormatUtils {



    public static String dateFormat(@NotNull LocalDateTime localDateTime, String pattern){
        if(pattern==null||pattern.equals("")){
            pattern="yyyy-MM-dd HH:mm:ss";
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateTimeFormatter.format(localDateTime);
    }
}
