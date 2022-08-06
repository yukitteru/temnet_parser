package com.temnet.parser.controller;

import com.temnet.parser.dto.ListItemDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public abstract class AbstractController<T, R extends JpaRepository<T, ?>> {
    protected R repo;

    public AbstractController(R repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<T> getAll() {
        return repo.findAll();
    }

}
