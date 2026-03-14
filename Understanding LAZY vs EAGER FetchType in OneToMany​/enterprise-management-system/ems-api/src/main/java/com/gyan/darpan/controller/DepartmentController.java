package com.gyan.darpan.controller;

import com.gyan.darpan.dto.DepartmentDto;
import com.gyan.darpan.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("department")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public DepartmentDto addDepartment(@RequestBody DepartmentDto departmentDto) {
        return departmentService.addDepartment(departmentDto);
    }

    @GetMapping("{departmentId}")
    public DepartmentDto getDepartmentDetail(@PathVariable long departmentId) {
        return departmentService.fetchDepartmentDetail(departmentId);
    }

}
