package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dto.Reservation;
import roomescape.exception.InvalidValueException;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.ReservationRepository;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository repository) {
        this.reservationRepository = repository;
    }

    // 홈화면
    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    //예약 조회
    @ResponseBody
    @GetMapping("/reservations")
    public List<Reservation> list() {
        return reservationRepository.findAll();
    }

    //예약 추가
    @ResponseBody
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation newReservation) {
        if (newReservation.getName() == null || newReservation.getName().isEmpty() ||
                newReservation.getDate() == null || newReservation.getDate().isEmpty() ||
                newReservation.getTime() == null || newReservation.getTime().isEmpty()) {
            throw new InvalidValueException("예약 추가에 필요한 인자값이 비어있습니다.");
        }

        Reservation reservation = reservationRepository.insert(newReservation);

        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId()))
                .body(reservation);
    }

    //예약 삭제
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
// 수정예정       Reservation reservation = reservationRepository.fin
//                .orElseThrow(() -> new NotFoundReservationException("예약을 찾을 수 없습니다."));

        reservationRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
