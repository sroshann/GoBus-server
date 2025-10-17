package com.group3.goBus.Repository;

import com.group3.goBus.Model.Bus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRepository extends MongoRepository<Bus, String> {

    Bus findByBusNumber(String busNumber);

}
