package org.example.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportResponse {
    private Integer statusNum;
    private Integer friendNum;
    private Integer emoteNum;
    private Integer commentNum;

    public ReportResponse() {
    }
}
