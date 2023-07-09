package com.delta.commerce.service.impl;

import com.delta.commerce.repository.WorkOrderRepository;
import com.delta.commerce.service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkOrderServiceImpl implements WorkOrderService {

    @Autowired
    private WorkOrderRepository workOrderRepository;

}
