package roomescape.repository;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.dto.Reservation;

import java.util.List;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //예약 조회
    public List<Reservation> findAll() {
        String sql = "SELECT id, name, date, time FROM reservation";

        return jdbcTemplate.query(
                sql, (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        resultSet.getString("time")
                ));
    }

    //예약 추가
    public Reservation insert(Reservation reservation) {
        try {
            String sql = "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql, reservation.getName(), reservation.getDate(), reservation.getTime());

            String query = "SELECT id FROM reservation ORDER BY id DESC LIMIT 1";
            Long id = jdbcTemplate.queryForObject(query, Long.class);

            return new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime());
        } catch (Exception ex) {
            throw new RuntimeException("오류가 발생하였습니다.");
        }
    }

    //예약 삭제
    public ResponseEntity<Object> delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, Long.valueOf(id));
        return ResponseEntity.noContent().build();
    }
}

