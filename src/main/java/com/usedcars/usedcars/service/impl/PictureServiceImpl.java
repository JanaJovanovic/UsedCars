package com.usedcars.usedcars.service.impl;

import com.usedcars.usedcars.repository.PictureRepository;
import com.usedcars.usedcars.service.PictureService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;

    @Transactional
    public void deleteByCar(UUID id){
        List<String> paths = pictureRepository.findByCar(id.toString());

        paths.stream()
                .map(path -> {
                    try {
                        Files.delete(Path.of(path));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return path;
                })
                .collect(Collectors.toSet());

        pictureRepository.deleteByCar(id.toString());

    }


}
