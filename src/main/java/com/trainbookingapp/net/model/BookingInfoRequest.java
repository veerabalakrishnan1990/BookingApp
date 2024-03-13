package com.trainbookingapp.net.model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingInfoRequest {
    private String trainId;
    private String userId;
}
