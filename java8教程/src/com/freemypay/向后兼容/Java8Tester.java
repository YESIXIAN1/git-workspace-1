package com.freemypay.向后兼容;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * toInstant()方法被添加到可用于将它们转换到新的日期时间的API原始日期和日历对象。
 * 使用ofInstant(Insant,ZoneId)方法得到一个LocalDateTime或ZonedDateTime对象。
 */
public class Java8Tester {
    public static void main(String args[]){
        Java8Tester java8tester = new Java8Tester();
        java8tester.testBackwardCompatability();
    }

    public void testBackwardCompatability(){
        //Get the current date
        Date currentDate = new Date();
        System.out.println("Current date: " + currentDate);

        //Get the instant of current date in terms of milliseconds
        Instant now = currentDate.toInstant();
        ZoneId currentZone = ZoneId.systemDefault();

        LocalDateTime localDateTime = LocalDateTime.ofInstant(now, currentZone);
        System.out.println("Local date: " + localDateTime);

        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(now, currentZone);
        System.out.println("Zoned date: " + zonedDateTime);
    }
}
