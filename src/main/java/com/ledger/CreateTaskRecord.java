package com.ledger;

import jakarta.validation.constraints.NotBlank;

// @NotBlank means the title can't be null, empty, or only spaces.
public record CreateTaskRecord(@NotBlank String title,boolean done) {
    
}
