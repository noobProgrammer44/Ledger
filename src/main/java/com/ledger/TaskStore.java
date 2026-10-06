package com.ledger;

import java.util.List;

public interface  TaskStore {
    List<Task>findAll();
    Task findById(int id);
    Task save(CreateTaskRecord task);
    boolean delete(int id);
    Task update(int id,CreateTaskRecord request);
}
