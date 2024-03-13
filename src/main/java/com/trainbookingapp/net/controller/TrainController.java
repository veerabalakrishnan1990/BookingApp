package com.trainbookingapp.net.controller;

import com.trainbookingapp.net.model.Train;
import com.trainbookingapp.net.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trains")
public class TrainController {

    @Autowired
    private TrainService trainService;

    @PostMapping
    public Train createTrain(@RequestBody Train train) {
        return trainService.saveTrain(train);
    }

    @GetMapping("/{id}")
    public Train getTrainById(@PathVariable String id) {
        return trainService.getTrainById(id);
    }

    @GetMapping
    public Iterable<Train> getAllTrains() {
        return trainService.getAllTrains();
    }

    @DeleteMapping("/{id}")
    public void deleteTrainById(@PathVariable String id) {
        trainService.deleteTrainById(id);
    }
}

