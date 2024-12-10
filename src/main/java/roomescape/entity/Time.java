package roomescape.entity;

import java.time.DateTimeException;
import java.time.LocalTime;

public class Time {
    public Long id;
    public String time;

    public Time(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public Time(String time) {
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public LocalTime getTimeAsLocalTime(){ //string -> localtime
        try {
            return LocalTime.parse(time);
        } catch (DateTimeException e) {
        return LocalTime.parse(time + ":00"); // 초
        }
    }
}
