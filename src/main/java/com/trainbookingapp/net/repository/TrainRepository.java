package com.trainbookingapp.net.repository;

import com.trainbookingapp.net.model.Train;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainRepository extends CrudRepository<Train, String> {
}

