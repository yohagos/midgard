package com.midgard.files.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileAddRequest {

    private String filename;
    private String filetype;
    private byte[] data;
}
