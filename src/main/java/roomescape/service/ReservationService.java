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

        //System.out.println("RequestDto form: " + requestDto);
        Long timeId = parseTimeId(requestDto.getTime());
        Time time = findTimeById(timeId);

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
//            throw new IllegalArgumentException("Invalid time format.");
//        }
//    }

    private Long parseTimeId(String time) {  // String -> long
        try {
            return Long.parseLong(time);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid time id format: " + time);
        }
    }

    private Time findTimeById(Long timeId) {
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
