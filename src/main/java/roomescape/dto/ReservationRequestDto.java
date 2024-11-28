package roomescape.dto;

import roomescape.entity.Time;

import java.time.LocalDateTime;

public class ReservationRequestDto {
    private String name;
    private String date;
    private String time;

    public ReservationRequestDto(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Time getTimeAsTime(){ //string -> time
        return new Time(time);
    }
}
