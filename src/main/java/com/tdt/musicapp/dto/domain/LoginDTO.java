package com.tdt.musicapp.dto.domain;

import com.tdt.musicapp.dto.response.GenericResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    protected String resetAuthToken;
    protected String authToken;
    protected GenericResponse genericResponse;
    protected String authCode;
}
