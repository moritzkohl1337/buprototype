package org.example.buprototype.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotEmpty;

@Configuration
public class ForwardableServicesConfig {
    @NotEmpty
    @Value("buprototype.supplierChargeBackService.url")
    private String supplierChargeBackServiceUrl;
    @NotEmpty
    @Value("buprototype.customerCreditNoteService.url")
    private String customerCreditNoteServiceUrl;

    public String getSupplierChargeBackServiceUrl() {
        return supplierChargeBackServiceUrl;
    }

    public String getCustomerCreditNoteServiceUrl() {
        return customerCreditNoteServiceUrl;
    }
}
