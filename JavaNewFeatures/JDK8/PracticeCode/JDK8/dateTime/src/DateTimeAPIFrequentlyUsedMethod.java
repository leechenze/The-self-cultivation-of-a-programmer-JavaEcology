import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

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
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter isoDateTime = DateTimeFormatter.ISO_DATE_TIME;
        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("yyyy年MM月dd HH时mm分ss秒");

        // 时间格式化
        String format = now.format(isoDateTime);
        String format1 = now.format(customFormat);
        System.out.println("format = " + format);
        System.out.println("format1 = " + format1);

        // 解析
        LocalDateTime parse = LocalDateTime.parse("2024年12月12 21时13分04秒", customFormat);
        System.out.println("parse = " + parse);

    }

    @Test
    public void testInstant() {
        Instant now = Instant.now();
        System.out.println("now = " + now);

        Instant add = now.plusSeconds(20);
        System.out.println("add = " + add);

        Instant minus = now.minusSeconds(20);
        System.out.println("minus = " + minus);

        System.out.println("now.getEpochSecond() = " + now.getEpochSecond());
        System.out.println("now.getNano() = " + now.getNano());

    }

    /**
     * 计算时间差
     */
    @Test
    public void testDuration() {
        LocalTime now = LocalTime.now();
        LocalTime time = LocalTime.of(14, 15, 20);
        // 本质上是第二个参数减去第一个参数的结果
        Duration duration = Duration.between(time, now);
        System.out.println("相差的天数 = " + duration.toDays());
        System.out.println("相差的小时 = " + duration.toHours());
        System.out.println("相差的分钟 = " + duration.toMinutes());
        System.out.println("相差的秒数 = " + duration.toSeconds());
    }

    /**
     * 计算日期差
     */
    @Test
    public void testPeriod() {
        LocalDate now = LocalDate.now();
        LocalDate date = LocalDate.of(2000, 8, 8);
        // 本质上是第二个参数减去第一个参数的结果
        Period period = Period.between(date, now);
        System.out.println("相差的年数 = " + period.getYears());
        System.out.println("相差的月数 = " + period.getMonths());
        System.out.println("相差的天数 = " + period.getDays());
    }

    /**
     * 时间校正器
     */
    @Test
    public void testTemporalAdjuster() {

        LocalDateTime now = LocalDateTime.now();

        // 自定义时间调整器
        TemporalAdjuster firstDayOfMonth = new TemporalAdjuster() {
            @Override
            public Temporal adjustInto(Temporal temporal) {
                LocalDateTime dateTime = (LocalDateTime) temporal;
                LocalDateTime dateTime1 = dateTime.plusMonths(1).withDayOfMonth(1);
                return dateTime1;
            }
        };


        // 将日期调整到下个月的第一天
        LocalDateTime newDateTime = now.with(firstDayOfMonth);
        System.out.println("newDateTime = " + newDateTime);
        // 使用JDK自带的 TemporalAdjuster 中的方法, 和自定义的 TemporalAdjuster 是一样的
        LocalDateTime newDateTime1 = now.with(TemporalAdjusters.firstDayOfNextMonth());
        System.out.println("newDateTime1 = " + newDateTime1);
    }


    @Test
    public void testZoned() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("now = " + now);
        System.out.println();

        // 获取所有的时区ID
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
        // availableZoneIds.forEach(System.out::println);

        // 操作带时区的类
        // 中国是东八区时区, Clock.systemUTC()是世界标准时间(零时区), 东八时区比零时区早8小时
        ZonedDateTime now1 = ZonedDateTime.now(Clock.systemUTC());
        System.out.println("now1 = " + now1);
        ZonedDateTime now2 = ZonedDateTime.now();
        System.out.println("now2 = " + now2);
        // 输出: now2 = 2024-12-12T21:56:51.590265+08:00[Asia/Shanghai]

        // 使用制定的时区创建日期时间
        ZonedDateTime now3 = ZonedDateTime.now(ZoneId.of("America/Managua"));
        System.out.println("now3 = " + now3);

        // 修改时区(withZoneSameInstant 即更改时区也更改时间)
        ZonedDateTime zonedDateTimeInstant = now3.withZoneSameInstant(ZoneId.of("Asia/Shanghai"));
        System.out.println("zonedDateTimeInstant = " + zonedDateTimeInstant);
        // 修改时间(withZoneSameLocal 只更改时间不更改时区)
        ZonedDateTime zonedDateTimeLocal = now3.withZoneSameLocal(ZoneId.of("Asia/Shanghai"));
        System.out.println("zonedDateTimeLocal = " + zonedDateTimeLocal);

    }


}
