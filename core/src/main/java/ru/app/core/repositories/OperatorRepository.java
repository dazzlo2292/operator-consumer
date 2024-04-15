package ru.app.core.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.app.core.dtos.Operator;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OperatorRepository implements EntityRepository<Operator> {

    private final JdbcTemplate jdbcTemplate;

    private final static String Q_INSERT_OPERATOR =
            "MERGE INTO operators as o1 " +
                    "USING (SELECT ? as code) as o2 " +
                    "ON o1.code = o2.code " +
                    "WHEN MATCHED THEN " +
                    "  UPDATE SET created_at = current_timestamp " +
                    "WHEN NOT MATCHED THEN " +
                    "  INSERT (id, country_id, code, name, description) " +
                    "  VALUES (?, ?, ?, ?, ?)";

    @Override
    public void insert(List<Operator> operators) {
        jdbcTemplate.batchUpdate(Q_INSERT_OPERATOR, operators, operators.size(), (ps, operator) -> {
            ps.setString(1, operator.getCode());
            ps.setLong(2, operator.getId());
            ps.setLong(3, operator.getCountryId());
            ps.setString(4, operator.getCode());
            ps.setString(5, operator.getName());
            ps.setString(6, operator.getDescription());
        });
    }
}
