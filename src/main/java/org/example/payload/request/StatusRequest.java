package org.example.payload.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
public class StatusRequest {
    private Integer id;
    private String content;
    private MultipartFile file;

    public StatusRequest() {
    }
}
