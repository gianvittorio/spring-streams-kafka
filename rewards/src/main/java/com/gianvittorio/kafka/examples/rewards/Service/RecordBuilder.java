package com.gianvittorio.kafka.examples.rewards.Service;

import com.gianvittorio.kafka.examples.model.Notification;
import com.gianvittorio.kafka.examples.model.PosInvoice;
import org.springframework.stereotype.Service;

@Service
public class RecordBuilder {

    public Notification getNotification(PosInvoice invoice) {
        Notification notification = new Notification();
        notification.setInvoiceNumber(invoice.getInvoiceNumber());
        notification.setCustomerCardNo(invoice.getCustomerCardNo());
        notification.setTotalAmount(invoice.getTotalAmount());
        notification.setEarnedLoyaltyPoints(invoice.getTotalAmount() * 0.02f);
        notification.setTotalLoyaltyPoints(notification.getEarnedLoyaltyPoints());

        return notification;
    }
}
