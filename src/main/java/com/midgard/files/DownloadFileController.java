package com.midgard.files;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/download")
@RequiredArgsConstructor
public class DownloadFileController {

    private final FilesService filesService;

    private static final Logger log = LoggerFactory.getLogger(DownloadFileController.class);

    @GetMapping("/{file_id}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable("file_id") Long id,
            HttpServletRequest request
    ) {
        var file = filesService.getDownLoadFile(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getFiletype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(new ByteArrayResource(file.getData()));
    }
}
