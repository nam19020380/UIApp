package org.example.payload.request;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
public class CommentRequest {
    private Integer id;

    private Integer statusId;

    private Integer commentId;

    private String text;

    private MultipartFile file;

    public CommentRequest() {
    }
}
