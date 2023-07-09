package com.delta.commerce.controller;

import com.delta.commerce.dto.request.WorkOrderRequestDto;
import com.delta.commerce.service.WorkOrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "work-order")
@Tag(name = "WorkOrder")
public class WorkOrderController {

    @Autowired
    private WorkOrderService workOrderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN_WRITE','USER_WRITE')")
    public void createWorkOrder(@RequestBody @Valid WorkOrderRequestDto dto) {
        this.workOrderService.createWorkOrder(dto);
    }

}
