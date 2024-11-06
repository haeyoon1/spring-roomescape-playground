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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);
// 이단계
//    public ReservationController() {
//        reservations.add(new Reservation(1L, "브라운1", "2023-01-01", "10:00"));
//        reservations.add(new Reservation(2L, "브라운2", "2023-01-02", "11:00"));
//        reservations.add(new Reservation(3L, "브라운3", "2023-01-03", "12:00"));
//    }

    // 홈화면
    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    //예약 조회
    @ResponseBody
    @GetMapping("/reservations")
    public List<Reservation> list() {
        return reservations;
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

        Reservation reservation = new Reservation(index.getAndIncrement(), newReservation.getName(), newReservation.getDate(), newReservation.getTime());
        reservations.add(reservation);

        return ResponseEntity.created(URI.create("/reservations/" + reservation.getId()))
                .body(reservation);
    }

    //예약 삭제
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> it.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundReservationException("예약을 찾을 수 없습니다."));

        reservations.remove(reservation);
        return ResponseEntity.noContent().build();
    }
}
