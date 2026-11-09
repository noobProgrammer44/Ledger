package com.ledger;
import jakarta.validation.constraints.NotBlank;

public record LoginResponse(@NotBlank String token){}