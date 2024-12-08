package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.dao.ReservationDao;
import roomescape.entity.Reservation;

import java.util.List;

@Repository
public class ReservationRepository {

    private final ReservationDao reservationDao;

    public ReservationRepository(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public Reservation save(Reservation reservation) {
        return reservationDao.insert(reservation);
    }

    public void delete(Long id) {
        reservationDao.delete(id);
    }
}

