package org.example.payload.response;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.ByteArrayResource;

import java.sql.Date;
@Getter
@Setter
public class StatusResponse {
    private Integer id;
    private String text;
    private String ownerName;
    private Date date;
    private Integer emoteCount;
    private Integer commentCount;
    public StatusResponse() {
    }
}
