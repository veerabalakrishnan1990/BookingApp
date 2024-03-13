package com.trainbookingapp.net.model;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

@Setter
@Getter
@Data
@RedisHash("Booking")
public class Booking {
    @Id
    private String id;
    private String user;
    private String departureStation;
    private String trainId;
    private String arrivalStation;
    private String travelDate;
    private int pricePaid;
    private String seatNumber;

    public Booking(String id, String user, String departureStation, String trainId, String arrivalStation) {
        this.id = id;
        this.user = user;
        this.departureStation = departureStation;
        this.trainId = trainId;
        this.arrivalStation = arrivalStation;
    }

    public Booking() {

    }
}
