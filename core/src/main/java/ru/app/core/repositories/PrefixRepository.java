package ru.app.core.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.app.core.dtos.Prefix;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PrefixRepository implements EntityRepository<Prefix> {

    private final JdbcTemplate jdbcTemplate;

    private final static String Q_INSERT_PREFIX =
            "MERGE INTO prefixes as p1 " +
                    "USING (SELECT ? as prefix) as p2 " +
                    "ON p1.prefix = p2.prefix " +
                    "WHEN MATCHED THEN " +
                    "  UPDATE SET created_at = current_timestamp " +
                    "WHEN NOT MATCHED THEN " +
                    "  INSERT (id, region_id, prefix, mnp_fl, manual_fl) " +
                    "  VALUES (?, ?, ?, ?, ?)";

    @Override
    public void insert(List<Prefix> prefixes) {
        jdbcTemplate.batchUpdate(Q_INSERT_PREFIX, prefixes, prefixes.size(), (ps, prefix) -> {
            ps.setString(1, prefix.getPrefix());
            ps.setLong(2, prefix.getId());
            ps.setLong(3, prefix.getRegionId());
            ps.setString(4, prefix.getPrefix());
            ps.setString(5, prefix.isMnp() ? "Y": "N");
            ps.setString(6, prefix.isManualInsert() ? "Y": "N");
        });
    }
}
