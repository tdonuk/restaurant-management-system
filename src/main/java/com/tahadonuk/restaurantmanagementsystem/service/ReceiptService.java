package com.tahadonuk.restaurantmanagementsystem.service;

import com.tahadonuk.restaurantmanagementsystem.data.entity.Receipt;
import com.tahadonuk.restaurantmanagementsystem.data.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceiptService {
    @Autowired
    ReceiptRepository receiptRepository;

    public Receipt getByOrderId(final long orderId) {
        return receiptRepository.findReceiptByOrderId(orderId).get();
    }
}
