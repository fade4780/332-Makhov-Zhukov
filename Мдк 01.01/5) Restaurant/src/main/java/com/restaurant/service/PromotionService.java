package com.restaurant.service;

import com.restaurant.model.Dish;
import com.restaurant.model.OrderItem;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class PromotionService {
    private final double discountRate = 0.2;
    private final int promotionStartHour = 15;
    private final int promotionEndHour = 18;

    public boolean isPromotionActive(LocalDateTime currentTime) {
        LocalTime time = currentTime.toLocalTime();
        int hour = time.getHour();
        boolean isWeekday = currentTime.getDayOfWeek().getValue() < 6;
        
        return isWeekday && hour >= promotionStartHour && hour < promotionEndHour;
    }

    public double calculateDiscount(List<OrderItem> items, LocalDateTime currentTime) {
        if (!isPromotionActive(currentTime)) {
            return 0.0;
        }

        return items.stream()
                .filter(OrderItem::isDish)
                .mapToDouble(item -> item.getTotal() * discountRate)
                .sum();
    }
}

