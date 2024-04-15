package ru.app.core.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.app.core.dtos.Country;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CountryRepository implements EntityRepository<Country> {

    private final JdbcTemplate jdbcTemplate;

    private final static String Q_INSERT_COUNTRY =
            "MERGE INTO countries as c1 " +
                    "USING (SELECT ? as code) AS c2 " +
                    "ON c1.code = c2.code " +
                    "WHEN MATCHED THEN " +
                    "  UPDATE SET created_at = current_timestamp " +
                    "WHEN NOT MATCHED THEN " +
                    "  INSERT (id, code, name, description) " +
                    "  VALUES (?, ?, ?, ?)";

    private final static String Q_DELETE_COUNTRIES =
            "delete from countries c where c.id not in " +
                    "(select o.country_id from prefixes p " +
                    "join regions r on r.id = p.region_id  " +
                    "join operators o on o.id = r.operator_id " +
                    "where p.manual_fl = 'Y')";

    private final static String Q_DELETE_OPERATORS =
            "delete from operators o where o.id not in " +
                    "(select r.operator_id from prefixes p " +
                    "join regions r on r.id = p.region_id  " +
                    "where p.manual_fl = 'Y')";

    private final static String Q_DELETE_REGIONS =
            "delete from regions r where r.id not in " +
                    "(select p.region_id from prefixes p where p.manual_fl = 'Y')";

    private final static String Q_DELETE_PREFIXES =
            "delete from prefixes p where p.manual_fl = 'N'";

    @Override
    public void insert(List<Country> countries) {
        jdbcTemplate.batchUpdate(Q_INSERT_COUNTRY, countries, countries.size(), (ps, country) -> {
            ps.setString(1, country.getCode());
            ps.setLong(2, country.getId());
            ps.setString(3, country.getCode());
            ps.setString(4, country.getName());
            ps.setString(5, country.getDescription());
        });
    }

    @Transactional
    public void deleteAll() {
        jdbcTemplate.update(Q_DELETE_COUNTRIES);
        jdbcTemplate.update(Q_DELETE_OPERATORS);
        jdbcTemplate.update(Q_DELETE_REGIONS);
        jdbcTemplate.update(Q_DELETE_PREFIXES);
    }
}
