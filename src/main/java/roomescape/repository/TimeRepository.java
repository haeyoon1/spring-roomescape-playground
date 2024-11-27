package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.dao.TimeDao;
import roomescape.entity.Time;

import java.util.List;

@Repository
public class TimeRespository {

    private final TimeDao timeDao;

    public TimeRespository(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public List<Time> findTime() {
        return timeDao.findAll();
    }

    public Time insertTime(Time time) {
        return timeDao.insert(time);
    }

    public void deleteTime(Long id) {
        timeDao.delete(id);
    }
}
