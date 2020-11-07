package com.colliers.mongodb.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class Message {
    private String timestamp;
    private int status;
    private String exception;
    private String message;
}