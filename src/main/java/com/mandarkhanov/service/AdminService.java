package com.mandarkhanov.service;

import com.mandarkhanov.dto.SqlQueryResult;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final JdbcTemplate jdbcTemplate;

    public SqlQueryResult executeSql(String sql) {
        String trimmedSql = sql.trim().toLowerCase();

        try {
            if (trimmedSql.startsWith("select")) {
                List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
                List<String> columns = rows.isEmpty() ? Collections.emptyList() : List.copyOf(rows.get(0).keySet());
                return SqlQueryResult.builder()
                        .isUpdate(false)
                        .columns(columns)
                        .rows(rows)
                        .build();
            } else {
                int updateCount = jdbcTemplate.update(sql);
                return SqlQueryResult.builder()
                        .isUpdate(true)
                        .updateCount(updateCount)
                        .build();
            }
        } catch (DataAccessException e) {
            return SqlQueryResult.builder()
                    .error(e.getMostSpecificCause().getMessage())
                    .build();
        }
    }
}