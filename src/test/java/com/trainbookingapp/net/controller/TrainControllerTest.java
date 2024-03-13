package com.trainbookingapp.net.controller;

import com.trainbookingapp.net.model.Train;
import com.trainbookingapp.net.service.TrainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TrainControllerTest {

    @Mock
    private TrainService trainService;

    @InjectMocks
    private TrainController trainController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTrain() {
        Train train = new Train();
        train.setId("1");
        train.setName("Express");

        when(trainService.saveTrain(train)).thenReturn(train);

        Train createdTrain = trainController.createTrain(train);

        assertEquals(train, createdTrain);
        verify(trainService, times(1)).saveTrain(train);
    }

    @Test
    public void testGetTrainById() {
        String trainId = "1";
        Train train = new Train();
        train.setId(trainId);
        train.setName("Express");

        when(trainService.getTrainById(trainId)).thenReturn(train);

        Train foundTrain = trainController.getTrainById(trainId);

        assertEquals(train, foundTrain);
        verify(trainService, times(1)).getTrainById(trainId);
    }

    @Test
    public void testGetAllTrains() {
        List<Train> trainList = new ArrayList<>();
        trainList.add(new Train("1", "Express"));
        trainList.add(new Train("2", "Local"));

        when(trainService.getAllTrains()).thenReturn(trainList);

        Iterable<Train> trains = trainController.getAllTrains();

        assertEquals(trainList, (List<Train>) trains);
        verify(trainService, times(1)).getAllTrains();
    }

    @Test
    public void testDeleteTrainById() {
        String trainId = "1";

        trainController.deleteTrainById(trainId);

        verify(trainService, times(1)).deleteTrainById(trainId);
    }
}

