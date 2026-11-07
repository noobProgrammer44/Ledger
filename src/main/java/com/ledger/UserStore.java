package com.ledger;

public interface  UserStore {
    User findByEmail(String email);
    User save(String email, String passwordHash);
}
