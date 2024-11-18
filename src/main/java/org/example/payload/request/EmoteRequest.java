package org.example.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmoteRequest {
    private Integer id;

    private Integer statusId;

    private Integer commentId;

    public EmoteRequest() {
    }
}
