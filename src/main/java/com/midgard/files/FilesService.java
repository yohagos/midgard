package com.midgard.files;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilesService {

    private final FilesRepository filesRepository;

    private static final Logger log = LoggerFactory.getLogger(FilesService.class);

    public List<FilesEntity> getAllFiles() {
        return filesRepository.findAll();
    }

    public FilesEntity findFileById(Long id) {
        return filesRepository.findById(id).orElseThrow();
    }

    public FilesEntity storeFile(
            MultipartFile file,
            Long ticket_id
    ) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (filename.contains(".."))
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + filename);

            var fileEntity = new FilesEntity();
            fileEntity.setFilename(filename);
            fileEntity.setData(file.getBytes());
            fileEntity.setFiletype(file.getContentType());
            fileEntity.setTicket_id(ticket_id);

            return filesRepository.save(fileEntity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
