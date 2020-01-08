package org.example.buprototype.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

public class ReturnDelivery {
    @NotEmpty
    private List<Product> returnedProducts;
    @NotNull
    @Min(0)
    private BigInteger customerId;

    public List<Product> getReturnedProducts() {
        return returnedProducts;
    }

    public void setReturnedProducts(List<Product> returnedProducts) {
        this.returnedProducts = returnedProducts;
    }

    public BigInteger getCustomerId() {
        return customerId;
    }

    public void setCustomerId(BigInteger customerId) {
        this.customerId = customerId;
    }
}
