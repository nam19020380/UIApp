package org.example.payload.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.sql.Date;
@Getter
@Setter
public class CommentResponse {
    private Integer id;
    private String ownername;
    private String text;
    private Date date;
    private Integer emoteCount;
    private Integer commentCount;

    public CommentResponse(Integer id, String ownername, String text, Date date, Integer emoteCount, Integer commentCount) {
        this.id = id;
        this.ownername = ownername;
        this.text = text;
        this.date = date;
        this.emoteCount = emoteCount;
        this.commentCount = commentCount;
    }

    public CommentResponse() {

    }
}
