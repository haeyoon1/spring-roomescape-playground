package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.entity.Time;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TimeDao {
    public final JdbcTemplate jdbcTemplate;

    public TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Time> findAll() {
        String sql = "SELECT id, time FROM time";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Time(
                rs.getLong("id"),
                rs.getString("time")
        ));
    }

    public Time insert(Time time) {
        String sql = "INSERT INTO time(time) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, time.getTime());
                    return ps;
                }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        return new Time(id, time.getTime());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
