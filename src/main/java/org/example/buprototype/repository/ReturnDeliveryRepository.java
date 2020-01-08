package org.example.buprototype.repository;

import org.example.buprototype.model.ReturnDelivery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnDeliveryRepository extends CrudRepository<ReturnDelivery, Long> {

}
