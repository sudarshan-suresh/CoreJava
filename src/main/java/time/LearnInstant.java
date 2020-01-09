package time;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
/**
 * This class is used for understanding java.time.Instant and java.time.OffsetDateTime .
 */
public class LearnInstant {

    public static void main(String[] args) {

        offsetDateTime("Australia/Darwin");
        offsetDateTime("Australia/Sydney");
        offsetDateTime("America/Argentina/Buenos_Aires");
        offsetDateTime("Africa/Cairo");
        offsetDateTime("America/Anchorage");
        offsetDateTime("America/Sao_Paulo");
        offsetDateTime("Asia/Dhaka");
        offsetDateTime("Africa/Harare");
        offsetDateTime("America/St_Johns");
        offsetDateTime("America/Chicago");
        offsetDateTime("Asia/Shanghai");
        offsetDateTime("Africa/Addis_Ababa");
        offsetDateTime("Europe/Paris");
        offsetDateTime("Asia/Kolkata");
        offsetDateTime("America/Indiana/Indianapolis");
        offsetDateTime("Asia/Tokyo");
        offsetDateTime("Pacific/Apia");
        offsetDateTime("Asia/Yerevan");
        offsetDateTime("Pacific/Auckland");
        offsetDateTime("Asia/Karachi");
        offsetDateTime("America/Phoenix");
        offsetDateTime("America/Puerto_Rico");
        offsetDateTime("America/Los_Angeles");
        offsetDateTime("Pacific/Guadalcanal");
        offsetDateTime("Asia/Ho_Chi_Minh");


    }

    public static void instant() {
        Instant instant = Instant.now();
        print(instant); // prints in this format yyyy-MM-dd'T'hh:mm:ss.sssZ
        print(instant.getEpochSecond()); // prints the second 1970
        print(instant.getNano()); // prints the nano
        print(instant.toEpochMilli()); // prints the milli seconds equivalent System.currentTimeMillis;
    }

    public static void offsetDateTime(String timeZone) {
        print(timeZone);
        OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(Instant.now(), ZoneId.of(timeZone));
        long offsetSeconds = offsetDateTime.getOffset().getLong(ChronoField.OFFSET_SECONDS);
        long val = 0L;
        if (offsetSeconds > 0){
            val = offsetSeconds;

        } else {
            val = 86400 + offsetSeconds;

        }
        print(val + " " + (val / 3600));
    }

    public static void print(Object obj){
        System.out.println(obj);
    }
}
