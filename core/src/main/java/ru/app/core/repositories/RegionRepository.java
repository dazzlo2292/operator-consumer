package ru.app.core.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.app.core.dtos.Region;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RegionRepository implements EntityRepository<Region> {

    private final JdbcTemplate jdbcTemplate;

    private final static String Q_INSERT_REGION =
            "MERGE INTO regions as r1 " +
                    "USING (SELECT ? as code) as r2 " +
                    "ON r1.code = r2.code " +
                    "WHEN MATCHED THEN " +
                    "  UPDATE SET created_at = current_timestamp " +
                    "WHEN NOT MATCHED THEN " +
                    "  INSERT (id, operator_id, code, name, description, time_zone) " +
                    "  VALUES (?, ?, ?, ?, ?, ?)";

    private final static String Q_SELECT_REGION =
            "select " +
                    " r.id, " +
                    " r.operator_id, " +
                    " r.code, " +
                    " r.name, " +
                    " r.description, " +
                    " r.time_zone " +
                    " from regions r " +
                    " where code = ?";

    public Region getByCode(String code) {
        try {
            return jdbcTemplate.queryForObject(Q_SELECT_REGION, (rs, i) -> {
                Region region = new Region();
                region.setId(rs.getLong("id"));
                region.setOperatorId(rs.getLong("operator_id"));
                region.setCode(rs.getString("code"));
                region.setName(rs.getString("name"));
                region.setDescription(rs.getString("description"));
                region.setTimeZone(rs.getString("time_zone"));

                return region;
            }, code);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void insert(List<Region> regions) {
        jdbcTemplate.batchUpdate(Q_INSERT_REGION, regions, regions.size(), (ps, region) -> {
            ps.setString(1, region.getCode());
            ps.setLong(2, region.getId());
            ps.setLong(3, region.getOperatorId());
            ps.setString(4, region.getCode());
            ps.setString(5, region.getName());
            ps.setString(6, region.getDescription());
            ps.setString(7, region.getTimeZone());
        });
    }
}
