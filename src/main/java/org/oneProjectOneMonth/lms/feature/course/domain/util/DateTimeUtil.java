package org.oneProjectOneMonth.lms.feature.course.domain.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Author : Min Myat Thu Kha
 * Created At : 24/02/2025, Feb
 * Project Name : lms-java
 **/
public class DateTimeUtil {

//    For Today
    public LocalDateTime Today(){
        return LocalDateTime.now();
    }

//    For 7 Days
    public LocalDateTime Last7Days() {
        return LocalDateTime.now().minusDays(7);
    }

//    For 1 Month
    public LocalDateTime Last30Days() {
        return LocalDateTime.now().minusDays(30);
    }

//    For 3 Months
    public LocalDateTime Last100Days() {
        return LocalDateTime.now().minusDays(100);
    }
}
