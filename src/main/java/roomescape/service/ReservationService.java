package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.entity.Reservation;
import roomescape.entity.Time;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<ReservationResponseDto> getAllReservations() {
        List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
                .map(reservation -> new ReservationResponseDto(
                        reservation.getId(),
                        reservation.getName(),
                        reservation.getDate(),
                        reservation.getTimeAsLocalTime()))
                .collect(Collectors.toList());
    }

    public ReservationResponseDto createReservation(ReservationRequestDto newReservationDto) {
        Time time = newReservationDto.getTimeAsTime();
        Reservation reservation = new Reservation(newReservationDto.getName(), newReservationDto.getDate(), time);
        reservation = reservationDao.insert(reservation);

        return new ReservationResponseDto(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime().getTimeASALocalTime());
    }

    public void deleteReservation(Long id) {
        reservationDao.delete(id);
    }
}
