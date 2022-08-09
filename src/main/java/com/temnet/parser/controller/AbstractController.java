package com.temnet.parser.controller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Abstract controller class using REST API to present data preprocessed by the corresponding service class*
 *
 * @author Temnet
 */
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
