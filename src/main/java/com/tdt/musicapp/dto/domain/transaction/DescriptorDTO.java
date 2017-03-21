package com.tdt.musicapp.dto.domain.transaction;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DescriptorDTO {
    protected String name;
    protected String phone;
    protected String url;
}
