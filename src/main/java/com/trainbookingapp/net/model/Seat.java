package com.trainbookingapp.net.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@Data
@RedisHash("Seat")
public class Seat implements Serializable {
    @Id
    private String seatNumber;
    private String state;
    private Booking booking;

    public Seat(String seatNumber, String state) {
        this.seatNumber = seatNumber;
        this.state = state;
    }
}

