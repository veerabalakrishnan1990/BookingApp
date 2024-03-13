package com.trainbookingapp.net.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@RedisHash("Train")
public class Train implements Serializable {
    @Id
    private String id;
    private String name;
    private List<Section> sections;

    public Train(String id, String name) {
        this.id = id;
        this.name = name;
    }
}

