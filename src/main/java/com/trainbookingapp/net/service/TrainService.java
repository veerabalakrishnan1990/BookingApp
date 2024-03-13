package com.trainbookingapp.net.service;

import com.trainbookingapp.net.model.Train;
import com.trainbookingapp.net.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainService {

    @Autowired
    private TrainRepository trainRepository;

    public Train saveTrain(Train train) {
        return trainRepository.save(train);
    }
    public Train getTrainById(String id) {
        return trainRepository.findById(id).orElse(null);
    }
    public Iterable<Train> getAllTrains() {
        return trainRepository.findAll();
    }
    public void deleteTrainById(String id) {
        trainRepository.deleteById(id);
    }
}

