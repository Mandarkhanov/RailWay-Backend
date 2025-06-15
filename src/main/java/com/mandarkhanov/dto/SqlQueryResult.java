package com.mandarkhanov.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class SqlQueryResult {
    private List<String> columns;
    private List<Map<String, Object>> rows;
    private Integer updateCount;
    private boolean isUpdate;
    private String error;
}