package org.example.buprototype.service;

import org.example.buprototype.Response;
import org.example.buprototype.model.ReturnDelivery;
import org.example.buprototype.repository.ReturnDeliveryRepository;
import org.example.buprototype.validation.BeanValidator;
import org.example.buprototype.validation.ValidationException;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ReturnDeliveryServiceTest {

    @Mock
    private BeanValidator beanValidatorMock;

    @Mock
    private ReturnDeliveryHttpClient returnDeliveryHttpClientMock;

    @Mock
    private ReturnDeliveryRepository returnDeliveryRepositoryMock;

    private ReturnDeliveryService testee;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    void setUp() {
        this.testee = new ReturnDeliveryService(
                beanValidatorMock,
                returnDeliveryRepositoryMock,
                returnDeliveryHttpClientMock
        );
    }

    @Test
    void handleReturnDelivery_shouldReturnSuccess_ifNoComponentThrew() {
        ReturnDelivery returnDelivery = new ReturnDelivery();

        doNothing().when(beanValidatorMock).validateBean(returnDelivery);
        when(returnDeliveryRepositoryMock.save(returnDelivery)).thenReturn(returnDelivery);
        doNothing().when(returnDeliveryHttpClientMock).forwardReturnDelivery(returnDelivery);

        Response expectedResult = new Response(true, "success");

        assertThat(testee.handleReturnDelivery(returnDelivery)).isEqualTo(expectedResult);
    }

    @Test
    void handleReturnDelivery_shouldReturnFailure_ifHttpClientThrew() {
        ReturnDelivery returnDelivery = new ReturnDelivery();

        doNothing().when(beanValidatorMock).validateBean(returnDelivery);
        when(returnDeliveryRepositoryMock.save(returnDelivery)).thenReturn(returnDelivery);
        doThrow(new RuntimeException("something went wrong")).when(returnDeliveryHttpClientMock).forwardReturnDelivery(returnDelivery);

        Response expectedResult = new Response(false, "something went wrong");

        assertThat(testee.handleReturnDelivery(returnDelivery)).isEqualTo(expectedResult);
    }

    @Test
    void handleReturnDelivery_shouldReturnFailure_ifRepositoryThrew() {
        ReturnDelivery returnDelivery = new ReturnDelivery();

        doNothing().when(beanValidatorMock).validateBean(returnDelivery);
        when(returnDeliveryRepositoryMock.save(returnDelivery)).thenThrow(new RuntimeException("something went wrong"));
        doNothing().when(returnDeliveryHttpClientMock).forwardReturnDelivery(returnDelivery);

        Response expectedResult = new Response(false, "something went wrong");

        assertThat(testee.handleReturnDelivery(returnDelivery)).isEqualTo(expectedResult);
    }

    @Test
    void handleReturnDelivery_shouldReturnFailure_ifValidatorThrew() {
        ReturnDelivery returnDelivery = new ReturnDelivery();

        doThrow(new ValidationException("something went wrong")).when(beanValidatorMock).validateBean(returnDelivery);
        when(returnDeliveryRepositoryMock.save(returnDelivery)).thenReturn(returnDelivery);
        doNothing().when(returnDeliveryHttpClientMock).forwardReturnDelivery(returnDelivery);

        Response expectedResult = new Response(false, "something went wrong");

        assertThat(testee.handleReturnDelivery(returnDelivery)).isEqualTo(expectedResult);
    }
}
