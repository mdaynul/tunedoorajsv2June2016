package com.tdt.musicapp.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongDTO {
    protected String id;
    protected String recordedDateTime;
    protected String title;
    protected String url;
}
