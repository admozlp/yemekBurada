package com.ademozalp.yemek_burada.service;


import com.ademozalp.yemek_burada.dto.desk.request.CreateDeskRequest;
import com.ademozalp.yemek_burada.dto.desk.response.DeskResponse;
import com.ademozalp.yemek_burada.exception.YemekBuradaException;
import com.ademozalp.yemek_burada.model.Desk;
import com.ademozalp.yemek_burada.repository.DeskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeskService {
    private final DeskRepository repository;

    public DeskService(DeskRepository repository) {
        this.repository = repository;
    }

    public DeskResponse createDesk(CreateDeskRequest request) {
        Desk desk = CreateDeskRequest.convert(request);
        Desk savedDesk = repository.save(desk);
        return DeskResponse.convert(savedDesk);
    }

    public List<DeskResponse> getAllDesk() {
        List<Desk> desks = repository.findAll();
        return desks.stream().map(DeskResponse::convert).toList();
    }


    protected Desk getDeskByIdForOtherService(Long id) {
        return repository.findById(id).orElseThrow(() -> new YemekBuradaException("Masa bulunamadı!"));
    }
}
