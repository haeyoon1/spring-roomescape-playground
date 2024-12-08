package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.entity.Reservation;
import roomescape.entity.Time;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public List<ReservationResponseDto> getAllReservations(){
        return reservationRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public ReservationResponseDto createReservation(ReservationRequestDto requestDto){

        System.out.println("!!!!!!!!!!!!!!!!!!!" + requestDto);
        Long timeId = parseTimeId(requestDto.getTime());
        Time time = findTimeById(timeId);
        System.out.println("timeInfo" + time.toString());
//
//        validateTimeFormat(requestDto.getTime());
//        Time time = requestDto.toTime();

        Reservation reservation = new Reservation(
                requestDto.getName(),
                requestDto.getDate(),
                time
        );
        Reservation newReservation = reservationRepository.save(reservation);
        System.out.println("new Reservation" + newReservation.toString());

        return toResponseDto(newReservation);
    }

    public void deleteReservation(Long id){
        reservationRepository.delete(id);
    }

    private ReservationResponseDto toResponseDto(Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTimeAsLocalTime()
        );
    }

//    private void validateTimeFormat (String time) {
//        try {
//            LocalTime.parse(time);
//        } catch (DateTimeException e){
//            throw new IllegalArgumentException("Invalid time format!!!!!");
//        }
//    }

    private void validateTimeFormat(String time) {
        System.out.println("Received time: " + time);  // 클라이언트에서 받은 시간 출력

        // Time 객체로 변환
        Time timeinfo = new Time(time);
        System.out.println("Time object1: " + timeinfo);  // Time 객체 출력
        System.out.println("Time object1: " + timeinfo.toString());  // Time 객체 출력

        try {
            // 시간을 파싱 시도
            LocalTime.parse(time);
        } catch (DateTimeException e) {
            // 예외 발생 시 오류 메시지 출력
            System.out.println("Error with time: " + timeinfo.toString());

            System.err.println("Invalid time format received: " + time);
            throw new IllegalArgumentException("Invalid time format! Please use HH:mm or HH:mm:ss.");
        }
    }

    private Long parseTimeId(String time) {
        try {
            return Long.parseLong(time);  // String을 Long으로 변환
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid time id format: " + time);
        }
    }

    // requestDto.getTime()으로 받은 id를 통해 Time 객체를 찾는 메서드
    private Time findTimeById(Long timeId) {
        // TimeRepository에서 id에 맞는 Time 객체를 찾음
        List<Time> times = timeRepository.findAll();
        Optional<Time> time = times.stream()
                .filter(t -> t.getId().equals(timeId))
                .findFirst();

        if (time.isEmpty()) {
            throw new IllegalArgumentException("Invalid time id received: " + timeId);
        }

        return time.get();
    }
}
