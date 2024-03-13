package com.trainbookingapp.net.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class CancelRequest {
    private String userId;
    private String trainId;
    private String seatNumber;

    public CancelRequest(String userId, String trainId) {
        this.userId = userId;
        this.trainId = trainId;
    }
}
