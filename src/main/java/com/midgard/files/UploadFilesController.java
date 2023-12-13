package com.midgard.files;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/files")
@RequiredArgsConstructor
public class UploadFilesController {

    private final FilesService filesService;

    private static final Logger log = LoggerFactory.getLogger(UploadFilesController.class);

    @GetMapping(path = "/all")
    public List<FilesEntity> getAllFiles() {
        return filesService.getAllFiles();
    }

    @GetMapping(path = "{ticket_id}")
    public ResponseEntity<List<FilesEntity>> getFilesForTicket(
            @PathVariable("ticket_id") Long ticket_id
    ) {
        return ResponseEntity.ok(filesService.getFilesForTicket(ticket_id));
    }

    @PostMapping(path = "/add/{ticket_id}")
    public ResponseEntity<FilesEntity> addFileToTicket(
            @PathVariable("ticket_id") Long ticket_id,
            @RequestParam("file") MultipartFile file
            ) {
        return ResponseEntity.ok(filesService.storeFile(file, ticket_id));
    }

    @PostMapping(path = "/add/multiple/{ticket_id}")
    public ResponseEntity<List<FilesEntity>> uploadMultipleFiles(
            @PathVariable("ticket_id") Long ticket_id,
            @RequestParam("files") MultipartFile[] files
    ) {
        var result = Arrays.asList(files)
                .stream()
                .map(file -> filesService.storeFile(file, ticket_id))
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}
