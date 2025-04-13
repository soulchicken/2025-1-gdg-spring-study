package com.example.todo_api.hw;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyService {

    private final MyRepository myRepository;

    public void sayService() {
        System.out.println("service");
        myRepository.SayRepository();
    }
}
