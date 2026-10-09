package com.ledger;

import java.util.ArrayList;
import java.util.List;


// @Service is another Spring label, like @RestController. It says "Spring, create one of these and manage it." @RestController is for classes that handle web requests, and @Service is for classes that hold data or logic.
// @Service
public class InMemoryTaskStore implements TaskStore {

    private final List<Task> tasks = new ArrayList<>(List.of(
            new Task(1, "Learn Spring", false),
            new Task(2, "Build Ledger", false)));
    private int nextId=3;

    @Override
    public List<Task> findAll() {
        return tasks;
    }

    @Override
    public Task findById(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).id() == id) {
                return tasks.get(i);
            }
        }
        return null;
    }

    @Override
    public Task save(CreateTaskRecord task) {
        Task newTask=new Task(nextId,task.title(),task.done());
        tasks.add(newTask);
        nextId++;
        return newTask;
    }

    @Override 
    public boolean delete(int id){
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).id() == id) {
                tasks.remove(i);
                return true;
            }
        }
        return false;
    }
    @Override
    public Task update(int id, CreateTaskRecord request) {
        Task t=new Task(id,request.title(),request.done());
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).id() == id) {
                tasks.set(i, t);
                return t;
            }
        }
        return null;
    }

}
