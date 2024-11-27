package roomescape.dto;

import java.time.LocalDateTime;

public class TimeRequestDto {
    public LocalDateTime time;

    public TimeRequestDto(LocalDateTime time) {
        this.time = time;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
