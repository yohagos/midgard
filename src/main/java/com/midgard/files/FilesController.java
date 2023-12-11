package com.midgard.files;


import com.midgard.files.request.FileAddRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("api/v1/files")
@RequiredArgsConstructor
public class FilesController {

    private final FilesService filesService;

    private static final Logger log = LoggerFactory.getLogger(FilesController.class);

    @GetMapping
    public List<FilesEntity> getAllFiles() {
        return filesService.getAllFiles();
    }

    @PostMapping(path = "/add/{ticket_id}")
    public ResponseEntity<FilesEntity> addFileToTicket(
            @PathVariable("ticket_id") Long ticket_id,
            @RequestParam("file") MultipartFile file
            ) {
        log.warn(ticket_id.toString());
        log.warn(file.toString());
        /*var fileEntity = filesService.storeFile(file, ticket_id);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/downloadFile/")*/

        return ResponseEntity.ok(filesService.storeFile(file, ticket_id));
    }
}
