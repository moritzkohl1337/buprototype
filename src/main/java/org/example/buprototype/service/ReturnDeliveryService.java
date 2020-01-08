package org.example.buprototype.service;

import org.example.buprototype.Response;
import org.example.buprototype.model.ReturnDelivery;
import org.example.buprototype.repository.ReturnDeliveryRepository;
import org.example.buprototype.validation.BeanValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class ReturnDeliveryService {

    private BeanValidator beanValidator;
    private ReturnDeliveryRepository returnDeliveryRepository;
    private ReturnDeliveryHttpClient returnDeliveryHttpClient;

    @Autowired
    public ReturnDeliveryService(BeanValidator beanValidator, ReturnDeliveryRepository returnDeliveryRepository, ReturnDeliveryHttpClient returnDeliveryHttpClient) {
        this.beanValidator = beanValidator;
        this.returnDeliveryRepository = returnDeliveryRepository;
        this.returnDeliveryHttpClient = returnDeliveryHttpClient;
    }

    public Response handleReturnDelivery(ReturnDelivery returnDelivery) {
        requireNonNull(returnDelivery, "returnDelivery may not be null");

        try {
            beanValidator.validateBean(returnDelivery);

            returnDeliveryRepository.save(returnDelivery);

            returnDeliveryHttpClient.forwardReturnDelivery(returnDelivery);
        } catch(Exception ex) {
            return new Response(false, ex.getMessage());
        }

        return new Response(true, "success");
    }
}
