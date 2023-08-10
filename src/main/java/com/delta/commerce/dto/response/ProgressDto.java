package com.delta.commerce.dto.response;

import lombok.Data;

@Data
public class ProgressDto {

    private long bytesRead;
    private long contentLength;
    private boolean done;

    public ProgressDto(long bytesRead, long contentLength, boolean done) {
        this.bytesRead = bytesRead;
        this.contentLength = contentLength;
        this.done = done;
    }


}
