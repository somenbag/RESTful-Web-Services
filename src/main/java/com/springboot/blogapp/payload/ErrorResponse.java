package com.springboot.blogapp.payload;

import lombok.*;

import java.util.Date;

@Data
@Builder
public class ErrorResponse {

        private Date timestamp;
        private String errorMessage;
        private String errorDetails;

}

