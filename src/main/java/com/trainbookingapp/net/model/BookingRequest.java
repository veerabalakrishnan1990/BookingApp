package com.trainbookingapp.net.model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    private String userId;
    private String trainId;
    private String seatNumber;
    private String departureStation;
    private String arrivalStation;
}
