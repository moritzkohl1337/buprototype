package org.example.buprototype;

import org.example.buprototype.model.ReturnDelivery;
import org.example.buprototype.service.ReturnDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class LambdaHandler {

    private final ReturnDeliveryService returnDeliveryService;

    @Autowired
    public LambdaHandler(ReturnDeliveryService returnDeliveryService) {
        this.returnDeliveryService = returnDeliveryService;
    }

    @Bean
    public Consumer<ReturnDelivery> handleReturnDelivery() {
        return returnDeliveryService::handleReturnDelivery;
    }
}
