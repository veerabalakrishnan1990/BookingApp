package com.trainbookingapp.net.service;

import com.trainbookingapp.net.model.Train;
import com.trainbookingapp.net.repository.TrainRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TrainServiceTest {

    @Mock
    private TrainRepository trainRepository;

    @InjectMocks
    private TrainService trainService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveTrain() {
        Train train = new Train();
        train.setId("1");
        train.setName("Express");

        when(trainRepository.save(train)).thenReturn(train);

        Train savedTrain = trainService.saveTrain(train);

        assertEquals(train.getId(), savedTrain.getId());
        assertEquals(train.getName(), savedTrain.getName());
        verify(trainRepository, times(1)).save(train);
    }

    @Test
    public void testGetTrainById() {
        Train train = new Train();
        train.setId("1");
        train.setName("Express");

        when(trainRepository.findById("1")).thenReturn(Optional.of(train));

        Train foundTrain = trainService.getTrainById("1");

        assertEquals(train.getId(), foundTrain.getId());
        assertEquals(train.getName(), foundTrain.getName());
        verify(trainRepository, times(1)).findById("1");
    }

    @Test
    public void testGetAllTrains() {
        List<Train> trains = new ArrayList<>();
        trains.add(new Train("1", "Express1"));
        trains.add(new Train("2", "Express2"));
        when(trainRepository.findAll()).thenReturn(trains);

        Iterable<Train> foundTrains = trainService.getAllTrains();

        assertEquals(trains.size(), ((List<Train>) foundTrains).size());
        verify(trainRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteTrainById() {
        trainService.deleteTrainById("1");

        verify(trainRepository, times(1)).deleteById("1");
    }
}
