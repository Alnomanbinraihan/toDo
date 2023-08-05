package com.example.toDo.services;

import com.example.toDo.entity.ToDo;
import com.example.toDo.entity.UserInfo;
import com.example.toDo.repo.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
