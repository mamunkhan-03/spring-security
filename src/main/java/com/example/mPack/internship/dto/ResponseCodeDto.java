package com.example.mPack.internship.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCodeDto {
    private int responseCode;
    private String responseMessage;

}
