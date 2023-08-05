package com.example.toDo.services;

import com.example.toDo.entity.ToDo;
import com.example.toDo.entity.UserInfo;
import com.example.toDo.repo.ToDoRepository;
import com.example.toDo.utils.ImageResizer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ToDoService {

    @Autowired
    ToDoRepository toDoRepository;

    public ToDo create(ToDo toDo) {
        return toDoRepository.save(toDo);
    }

    public ToDo updateToDo(Long id, ToDo toDo) {

        Optional<ToDo> existingToDo = toDoRepository.findById(id);

        if (existingToDo.isPresent()) {
            existingToDo.get().setOpen(toDo.getOpen());
            return toDoRepository.save(existingToDo.get());
        }
        return new ToDo();
    }
    public ToDo closeToDo(Long id) {

        Optional<ToDo> existingToDo = toDoRepository.findById(id);

        if (existingToDo.isPresent()) {
            existingToDo.get().setOpen(false);
            return toDoRepository.save(existingToDo.get());
        }
        return new ToDo();
    }

    public String deleteById(Long id) {

        try {
            toDoRepository.deleteById(id);
            return "Successfully deleted!";
        } catch (Exception e) {
            e.printStackTrace();
            return "failed to deleted!";
        }
    }
    public List<ToDo> getAll() {
        return toDoRepository.findAll();
    }

    private static final String UPLOAD_DIR = "path/upload/directory/";

    @Transactional
    public String uploadAndResizePic(Long toDoId, MultipartFile file) throws IOException {
        // Save the original image
        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String originalFilePath = UPLOAD_DIR + toDoId + "_original" + extension;
        File originalFile = new File(originalFilePath);
        file.transferTo(originalFile);

        // Resize the image and save the thumbnail
        String thumbnailFilePath = UPLOAD_DIR + toDoId + "_thumbnail" + extension;
        ImageResizer.resize(originalFilePath, thumbnailFilePath, 200, 200);

        // Save the file paths to the database
        ToDo toDo = toDoRepository.findById(toDoId).orElseThrow(() -> new IllegalArgumentException("ToDo not found."));
        toDo.setOriginalPicPath(originalFilePath);
        toDo.setThumbnailPicPath(thumbnailFilePath);
        toDoRepository.save(toDo);

        return thumbnailFilePath;
    }
}
