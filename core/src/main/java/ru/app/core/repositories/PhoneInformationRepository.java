package ru.app.core.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.app.core.dtos.PhoneInformation;

@Repository
@RequiredArgsConstructor
public class PhoneInformationRepository {

    private final JdbcTemplate jdbcTemplate;

    private final static String Q_SELECT_PHONE_INFORMATION =
            "select " +
                    "c.code as country_code, " +
                    "c.name as country_name, " +
                    "o.code as operator_code, " +
                    "o.name as operator_name, " +
                    "r.code as region_code, " +
                    "r.name as region_name, " +
                    "r.time_zone " +
                    "from countries c " +
                    " join operators o on c.id = o.country_id " +
                    " join regions r on o.id = r.operator_id " +
                    " join prefixes p on r.id = p.region_id " +
                    " where p.prefix = ?";

    public PhoneInformation getPhoneInformation(String phone) {
        try {
            return jdbcTemplate.queryForObject(Q_SELECT_PHONE_INFORMATION, (rs, i) -> {
                PhoneInformation phoneInformation = new PhoneInformation();
                phoneInformation.setCountryCode(rs.getString("country_code"));
                phoneInformation.setCountryName(rs.getString("country_name"));
                phoneInformation.setOperatorCode(rs.getString("operator_code"));
                phoneInformation.setOperatorName(rs.getString("operator_name"));
                phoneInformation.setRegionCode(rs.getString("region_code"));
                phoneInformation.setRegionName(rs.getString("region_name"));
                phoneInformation.setTimezone(rs.getString("time_zone"));

                return phoneInformation;
            }, phone);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
