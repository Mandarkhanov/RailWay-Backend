package com.mandarkhanov.controller;

import com.mandarkhanov.dto.SqlQueryRequest;
import com.mandarkhanov.dto.SqlQueryResult;
import com.mandarkhanov.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/sql")
    public ResponseEntity<SqlQueryResult> executeSql(@RequestBody SqlQueryRequest request) {
        SqlQueryResult result = adminService.executeSql(request.getSql());
        if (result.getError() != null) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }
}