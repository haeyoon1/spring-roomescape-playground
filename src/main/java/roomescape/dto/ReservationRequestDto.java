package roomescape.dto;

import roomescape.entity.Time;

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

    public Time toTime(){ //string -> time
        return new Time(time);
    }

    @Override
    public String toString() {
        return "ReservationRequestDto{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
