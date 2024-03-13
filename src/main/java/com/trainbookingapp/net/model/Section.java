package com.trainbookingapp.net.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
@RedisHash("Section")
public class Section implements Serializable {
    @Id
    private String sectionId;
    private String name;
    private int seatCount;
    private List<Seat> seats;

    public Section(String sectionId, List<Seat> seats) {
        this.sectionId = sectionId;
        this.seats = seats;
    }
}

