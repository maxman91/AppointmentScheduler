package utilities;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.ZoneOffset.UTC;
/** timeConvert class converts time*/
public class timeConvert {

    /** converts a given string to LocalTime
     @param time
     @return lcTime
     */
    public static LocalTime stringToLocalTime(String time){
        LocalTime lcTime = null;
        CharSequence finalTime = null;


        String timeParse[] = time.split(" ");
        String hm[] = timeParse[0].split(":");

        if (timeParse[1].equals("PM") && Integer.parseInt(hm[0]) != 12){
            //String hm[] = timeParse[0].split(":");
            int hours = Integer.parseInt(hm[0]) + 12;
            lcTime = LocalTime.parse(hours + ":" + hm[1] +":00");
        }
        else {
            //String hm[] = timeParse[0].split(":");
            if (Integer.parseInt(hm[0]) < 10){
                String hours = "0"+ hm[0];
                lcTime = LocalTime.parse(hours + ":" + hm[1] +":00");
            }
            else {
                int hours = Integer.parseInt(hm[0]);
                lcTime = LocalTime.parse(hours + ":" + hm[1] +":00");
            }


        }



        //lcTime = LocalTime.parse(finalTime);
        
        return lcTime;
    }
    /** converts timestamp to a more read able string for a user to know what time a appointment starts and ends.
     @param time
     @return timeOfDay
     */
    public static String timeOfDay(Timestamp time) {
        String timeOfDay = null;
        String fullTime = time.toString();
        String split[] = fullTime.split(" ");
        String exacttime = split[1];

        String split2[] = exacttime.split(":");
         String hours = split2[0];
        String min = split2[1];

        if (Integer.parseInt(hours) >= 12){
            int hour = Integer.parseInt(hours);
            if (hour != 12){
            hour = hour - 12;
            }
            timeOfDay = hour + ":" + min + " PM";
        }
        else {
            int hour = Integer.parseInt(hours);
            timeOfDay = hour + ":" + min + " AM";
        }


        return timeOfDay;
    }
    /** converts timestamp format string to a more read able string for a user to know what time a appointment starts and ends.
     @param time
     @return timeOfDay
     */
    public static String timeOfDay(String time) {
        String timeOfDay = null;
        String fullTime = time.toString();
        String split[] = fullTime.split(" ");
        String exacttime = split[1];

        String split2[] = exacttime.split(":");
        String hours = split2[0];
        String min = split2[1];

        if (Integer.parseInt(hours) >= 12 && Integer.parseInt(hours) != 24){
            int hour = Integer.parseInt(hours);
            if (hour != 12){
                hour = hour - 12;
            }
            timeOfDay = hour + ":" + min + " PM";
        }

        else if (Integer.parseInt(hours) == 24){
            int hour = Integer.parseInt(hours);
            if (hour != 12){
                hour = hour - 12;
            }
            timeOfDay = hour + ":" + min + " AM";
        }
        else {
            int hour = Integer.parseInt(hours);
            timeOfDay = hour + ":" + min + " AM";
        }


        return timeOfDay;
    }
    /** Converts Timestamp utc to local time string.
     @param utc
     @return currentTime
     */
    public static String toLocalTime(Timestamp utc){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime utcLocalTime = utc.toLocalDateTime();

        ZonedDateTime utcZone = utcLocalTime.atZone(UTC);
        ZonedDateTime lcZone = utcZone.withZoneSameInstant(ZoneId.systemDefault());

        LocalDateTime localtimeLocalTime = lcZone.toLocalDateTime();

        String currentTime = localtimeLocalTime.format(format);



        return currentTime;
    }
    /** Converts Military time to standard time.
     @param military
     @return AMPMtime.
     */
    public static String toLocalTimeAMPM(String military){
        String AMPMtime;

        String timeMilitary[] = military.split(" ");
        String hoursMinutes[] = timeMilitary[1].split(":");

        int hours = Integer.parseInt(hoursMinutes[0]);

        if (hours >= 12) {
            if (hours >12){
            hours = hours - 12;
            }
            if (hours > 9){
            AMPMtime = timeMilitary[0] + " " + hours + ":" + hoursMinutes[1] + ":" + hoursMinutes[2] + " PM";
            }
            else {
                AMPMtime = timeMilitary[0] + " 0" + hours + ":" + hoursMinutes[1] + ":" + hoursMinutes[2] + " PM";
            }
        }

        else {
            AMPMtime = military + " AM";
        }



        return AMPMtime;
    }



}
