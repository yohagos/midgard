package com.midgard.files;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class FilesEntity  implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String filename;
    private String filetype;
    private Long ticket_id;

    @Lob
    private byte[] data;

}
