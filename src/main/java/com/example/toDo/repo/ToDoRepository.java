package com.example.toDo.repo;


import com.example.toDo.entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ToDoRepository extends JpaRepository<ToDo,Long> {
    @Query(value = "SELECT * FROM to_do ORDER BY CASE WHEN open = true THEN 0 ELSE 1 END;",nativeQuery = true)
    List<ToDo> findAllBySorting();

}
