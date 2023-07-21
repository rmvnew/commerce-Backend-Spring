package com.delta.commerce.service.impl;

import com.delta.commerce.dto.request.WorkOrderLineRequestDto;
import com.delta.commerce.entity.WorkOrderLine;
import com.delta.commerce.repository.ProductRepository;
import com.delta.commerce.repository.WorkOrderLineRepository;
import com.delta.commerce.service.WorkOrderLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkOrderLineServiceImpl implements WorkOrderLineService {


    @Autowired
    private WorkOrderLineRepository workOrderLineRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public WorkOrderLine createWorkOrderLine(WorkOrderLineRequestDto dto) {

        var workLine = new WorkOrderLine();
        workLine.setProduct(dto.getProduct_id() != null ?
                this.productRepository.findById(dto.getProduct_id()).get() : null);
        workLine.setUsed(dto.getUsed() != null ? dto.getUsed() : null);
        workLine.setQuantity(dto.getQuantity() != null ? dto.getQuantity() : null);
        workLine.setReturned(dto.getReturned() != null ? dto.getReturned() : null);

        return this.workOrderLineRepository.save(workLine);


    }
}
