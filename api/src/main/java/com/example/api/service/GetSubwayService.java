package com.example.api.service;

import com.example.storage.SubwayInfo;
import com.example.storage.SubwayJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetSubwayService {
    private final SubwayJpaRepository repository;

    public List<SubwayInfo> execute(final String param1, final String param2) {
        return repository.findByDongNameContainingAndDongEval(param1, param2);
    }
}