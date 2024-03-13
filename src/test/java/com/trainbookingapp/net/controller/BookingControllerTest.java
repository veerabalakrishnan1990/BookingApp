package com.trainbookingapp.net.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trainbookingapp.net.model.BookingInfoRequest;
import com.trainbookingapp.net.model.BookingRequest;
import com.trainbookingapp.net.model.CancelRequest;
import com.trainbookingapp.net.model.Seat;
import com.trainbookingapp.net.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BookingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
    }

    @Test
    public void testBookTicket() throws Exception {
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setTrainId("123");
        bookingRequest.setSeatNumber("A1");

        when(bookingService.bookTicket(any(BookingRequest.class))).thenReturn("Booking successful");

        mockMvc.perform(post("/api/booking/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookingRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Booking successful"));
    }

    @Test
    public void testCancelBooking() throws Exception {
        CancelRequest cancelRequest = new CancelRequest();
        cancelRequest.setTrainId("123");
        cancelRequest.setSeatNumber("A1");

        when(bookingService.cancelBooking(any(CancelRequest.class))).thenReturn("Cancellation successful");

        mockMvc.perform(post("/api/booking/cancel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(cancelRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Cancellation successful"));
    }

    @Test
    public void testGetSeatInfo() throws Exception {
        BookingInfoRequest bookingInfoRequest = new BookingInfoRequest();
        bookingInfoRequest.setTrainId("123");
        bookingInfoRequest.setUserId("456");

        List<Seat> seats = Collections.singletonList(new Seat());

        when(bookingService.getBookingInfo(any(BookingInfoRequest.class))).thenReturn(seats);

        mockMvc.perform(get("/api/booking/info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookingInfoRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists());
    }
}

