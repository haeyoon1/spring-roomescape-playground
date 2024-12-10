package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.TimeRequestDto;
import roomescape.dto.TimeResponseDto;
import roomescape.entity.Time;
import roomescape.repository.TimeRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeService {

    private final TimeRepository timeRespository;

    public TimeService(TimeRepository timeRespository) {
        this.timeRespository = timeRespository;
    }

    public List<TimeResponseDto> findAllTimes() {
        return timeRespository.findAll()
                .stream()
                .map(time -> new TimeResponseDto(
                        time.getId(),
                        time.getTimeASLocalTime()))
                .collect(Collectors.toList());
    }

    public TimeResponseDto createTime(TimeRequestDto requestDto) {
        Time time = new Time(requestDto.getTime());
        Time newTime = timeRespository.save(time);

        //System.out.println("print newtimeid" + time.getId());
        //System.out.println("print newtimetime" + time.getTimeASLocalTime());

        return toResponseDTO(newTime.getId(), newTime.getTimeASLocalTime());
    }

    private TimeResponseDto toResponseDTO(Long id, LocalTime time) {
        return new TimeResponseDto(id, time);
    }


    public void deleteTime(Long id) {
        timeRespository.deleteById(id);
    }

}
