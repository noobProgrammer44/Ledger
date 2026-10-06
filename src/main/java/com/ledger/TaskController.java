package com.ledger;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;





@RestController 
public class TaskController {
    TaskStore store;
    TaskController(TaskStore store){
        this.store=store;
    }

    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return store.findAll();
    }

    @GetMapping("/tasks/{id}")
    public Task getTask(@PathVariable int id) { // this will take the params id from the GET URL
        Task t= store.findById(id);
        if(t==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Tasks not found");
        }
        return t;
    }

 
    
    @PostMapping("/tasks")
    public Task createTask(@Valid @RequestBody CreateTaskRecord task){
        Task t =store.save(task);
        return t;
    }

    @DeleteMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable int id){
        boolean status=store.delete(id);
        if(!status){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Task not found");
        }
    }

    // @Valid tells Spring: "before you call my method, check the rules written on
    // this object. If any rule fails, stop and return a 400."
    @PutMapping("tasks/{id}")
    public Task updateTask(@Valid @PathVariable int id, @RequestBody CreateTaskRecord request) {
        Task t=store.update(id, request);
        if(t==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Task not found");
        }
        return t;
    }
    
    
    
}
