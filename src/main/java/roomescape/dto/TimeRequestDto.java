package roomescape.dto;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeRequestDto {
    private String time;

    public TimeRequestDto() {}

    public TimeRequestDto(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

}
