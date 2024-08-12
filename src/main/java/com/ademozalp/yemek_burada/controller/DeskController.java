package com.ademozalp.yemek_burada.controller;

import com.ademozalp.yemek_burada.dto.GenericResponse;
import com.ademozalp.yemek_burada.dto.desk.request.CreateDeskRequest;
import com.ademozalp.yemek_burada.service.DeskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/desks")
public class DeskController {
    private final DeskService deskService;


    public DeskController(DeskService deskService) {
        this.deskService = deskService;
    }

    @PostMapping
    public GenericResponse createDesk(@RequestBody @Valid CreateDeskRequest request) {
        return new GenericResponse("Masa oluşturuldu", 201, deskService.createDesk(request));
    }

    @GetMapping
    public GenericResponse getAllDesk() {
        return new GenericResponse("Masalar listelendi", 200, deskService.getAllDesk());
    }
}
