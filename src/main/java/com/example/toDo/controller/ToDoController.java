package com.example.toDo.controller;

import com.example.toDo.entity.ToDo;
import com.example.toDo.entity.UserInfo;
import com.example.toDo.services.ToDoService;
import com.example.toDo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@CrossOrigin(origins = "*")

public class ToDoController {

    @Autowired
    UserService userService;
    @Autowired
    ToDoService toDoService;
    @PostMapping("/saveUser")
    public UserInfo saveUser(@RequestBody UserInfo userInfo)
    {
        return userService.saveUser(userInfo);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('admin')")
    public ToDo createToDo(@RequestBody ToDo toDo)
    {
        return toDoService.create(toDo);
    }

    @PutMapping("/update/{id}")
    public ToDo updateToDo(@PathVariable Long id,@RequestBody ToDo toDo)
    {
        return toDoService.updateToDo(id,toDo);
    }

    @PutMapping("/closeToDo/{id}")
    public ToDo closeToDo(@PathVariable Long id)
    {
        return toDoService.closeToDo(id);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public String deleteToDo(@PathVariable Long id)
    {
        return toDoService.deleteById(id);
    }

    @GetMapping("/getAll")
    public List<ToDo> getAll()
    {
        return toDoService.getAll();
    }

    @PostMapping("/{id}/uploadPic")
    public String uploadPic(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            String picPath = toDoService.uploadAndResizePic(id, file);
            return picPath;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error uploading image.";
        }
    }


}
