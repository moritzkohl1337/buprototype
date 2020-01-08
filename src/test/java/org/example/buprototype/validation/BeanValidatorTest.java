package org.example.buprototype.validation;

import org.example.buprototype.model.Product;
import org.example.buprototype.model.ReturnDelivery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigInteger;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class BeanValidatorTest {

    private BeanValidator testee;

    @BeforeEach
    void setUp() {
        testee = new BeanValidator();
    }

    static Stream<Arguments> violationBeans() {
        Product product = new Product();
        product.setName("Hose");
        product.setPrice(123);
        product.setProductId(BigInteger.valueOf(42L));

        List<Product> productList = new LinkedList<>();
        productList.add(product);

        ReturnDelivery firstNonOkReturnDelivery = new ReturnDelivery();
        firstNonOkReturnDelivery.setCustomerId(null);

        ReturnDelivery secondNonOkReturnDelivery = new ReturnDelivery();
        secondNonOkReturnDelivery.setCustomerId(BigInteger.valueOf(1L));
        secondNonOkReturnDelivery.setReturnedProducts(Collections.emptyList());

        ReturnDelivery thirdNonOkReturnDelivery = new ReturnDelivery();
        thirdNonOkReturnDelivery.setCustomerId(BigInteger.valueOf(-1L));
        thirdNonOkReturnDelivery.setReturnedProducts(productList);

        return Stream.of(
                Arguments.of(firstNonOkReturnDelivery, "must not be null,must not be empty"),
                Arguments.of(secondNonOkReturnDelivery, "must not be empty"),
                Arguments.of(thirdNonOkReturnDelivery, "must be greater than or equal to 0")
        );
    }

    @ParameterizedTest
    @MethodSource("violationBeans")
    void validateBean_shouldThrow_ifBeanViolatesConstraints(Object bean, String message) {
        assertThatExceptionOfType(ValidationException.class)
                .isThrownBy(() -> testee.validateBean(bean))
                .withMessage(message);
    }

    static Stream<Arguments> nonViolationBeans() {
        Product product = new Product();
        product.setName("Hose");
        product.setPrice(123);
        product.setProductId(BigInteger.valueOf(42L));

        List<Product> productList = new LinkedList<>();
        productList.add(product);

        ReturnDelivery firstOKReturnDelivery = new ReturnDelivery();
        firstOKReturnDelivery.setReturnedProducts(productList);
        firstOKReturnDelivery.setCustomerId(BigInteger.valueOf(1L));

        ReturnDelivery secondOKReturnDelivery = new ReturnDelivery();
        secondOKReturnDelivery.setCustomerId(BigInteger.valueOf(2000000L));
        secondOKReturnDelivery.setReturnedProducts(productList);

        return Stream.of(
                Arguments.of(firstOKReturnDelivery),
                Arguments.of(secondOKReturnDelivery)
        );
    }

    @ParameterizedTest
    @MethodSource("nonViolationBeans")
    void validateBean_shouldNotThrow_ifBeanOk(Object bean) {
        testee.validateBean(bean);
    }

    @Test
    void validateBean_shouldThrow_ifPreconditionsFail() {
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> testee.validateBean(null));
    }
}
