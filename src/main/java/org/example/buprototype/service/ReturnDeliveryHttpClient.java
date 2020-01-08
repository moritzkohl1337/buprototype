package org.example.buprototype.service;

import org.example.buprototype.configuration.ForwardableServicesConfig;
import org.example.buprototype.model.ReturnDelivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ReturnDeliveryHttpClient {

    private ForwardableServicesConfig forwardableServicesConfig;
    private RestTemplate restTemplate;

    @Autowired
    public ReturnDeliveryHttpClient(ForwardableServicesConfig forwardToConfig, RestTemplate restTemplate) {
        this.forwardableServicesConfig = forwardToConfig;
        this.restTemplate = restTemplate;
    }

    public void forwardReturnDelivery(ReturnDelivery returnDelivery) {
        postReturnDelivery(forwardableServicesConfig.getSupplierChargeBackServiceUrl(), returnDelivery);
        postReturnDelivery(forwardableServicesConfig.getCustomerCreditNoteServiceUrl(), returnDelivery);
    }

    private ResponseEntity postReturnDelivery(String url, ReturnDelivery returnDelivery) {
        return this.restTemplate.postForEntity(
                url,
                returnDelivery,
                Void.class
        );
    }
}
