package com.midgard.files;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "files")
public class FilesEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String filename;
    private String filetype;
    private Long ticket_id;

    @Lob
    private byte[] data;

}
