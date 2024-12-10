import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeAPIFrequentlyUsedMethod {
    public static void main(String[] args) {

    }

    @Test
    public void testLocalDate() {
        LocalDate now = LocalDate.now();
        System.out.println("now = " + now);

        LocalDate date = LocalDate.of(2018, 8, 8);
        System.out.println("date = " + date);

        System.out.println("now.getYear() = " + now.getYear());
        System.out.println("now.getMonthValue() = " + now.getMonthValue());
        System.out.println("now.getDayOfYear() = " + now.getDayOfYear());
        System.out.println("now.getDayOfMonth() = " + now.getDayOfMonth());
        System.out.println("now.getDayOfWeek() = " + now.getDayOfWeek());

    }

    @Test
    public void testLocalTime() {
        LocalTime now = LocalTime.now();
        System.out.println("now = " + now);

        LocalTime time = LocalTime.of(13, 23, 20);
        System.out.println("time = " + time);

        System.out.println("now.getHour() = " + now.getHour());
        System.out.println("now.getMinute() = " + now.getMinute());
        System.out.println("now.getSecond() = " + now.getSecond());
        System.out.println("now.getNano() = " + now.getNano());

    }

    @Test
    public void testLocalDateTime() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("now = " + now);

        LocalDateTime dateTime = LocalDateTime.of(1999, 3, 16, 8, 28, 11);
        System.out.println("dateTime = " + dateTime);

        System.out.println();
        System.out.println("now.getYear() = " + now.getYear());
        System.out.println("now.getMonthValue() = " + now.getMonthValue());
        System.out.println("now.getDayOfYear() = " + now.getDayOfYear());
        System.out.println("now.getDayOfMonth() = " + now.getDayOfMonth());
        System.out.println("now.getDayOfWeek() = " + now.getDayOfWeek());
        System.out.println("now.getHour() = " + now.getHour());
        System.out.println("now.getMinute() = " + now.getMinute());
        System.out.println("now.getSecond() = " + now.getSecond());
        System.out.println("now.getNano() = " + now.getNano());

    }

    /**
     * 修改日期时间方法
     */
    @Test
    public void testModification() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("now = " + now);
        LocalDateTime newDateTime = now.withYear(1999);
        System.out.println("newDateTime = " + newDateTime);

        System.out.println("now.plusYears(10) = " + now.plusYears(10));
        System.out.println("now.minusYears(10) = " + now.minusYears(10));

    }

    /**
     * 比较日期时间方法
     */
    @Test
    public void testCompare() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("now = " + now);
        LocalDateTime dateTime = LocalDateTime.of(1999, 3, 16, 8, 28, 11);
        System.out.println("dateTime = " + dateTime);

        System.out.println("now.isAfter(dateTime) = " + now.isAfter(dateTime));
        System.out.println("now.isBefore(dateTime) = " + now.isBefore(dateTime));
        System.out.println("now.isEqual(dateTime) = " + now.isEqual(dateTime));

    }

    /**
     * 时间日期格式化方法
     */
    @Test
    public void testFormat() {
        // TODO
    }
}
