package com.ledger;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class InMemoryTaskStoreTest {

    // @Test marks a method as a test
    @Test 
    void saveAssignsNextId(){
        InMemoryTaskStore store=new InMemoryTaskStore();

        Task saved = store.save(new CreateTaskRecord("Test", false));

        // assertEquals(expected, actual) is like expect(actual).toBe(expected). The
        // expected value goes first.
        assertEquals(3, saved.id());
        assertEquals("Test", saved.title());
    }
    
}
