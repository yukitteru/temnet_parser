package com.temnet.parser.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


public abstract class AbstractController<T, R extends JpaRepository<T, ?>> {
    protected R repo;

    public AbstractController(R repo) {
        this.repo = repo;
    }

    public Page<T> list(@PageableDefault Pageable pageable) {
        return repo.findAll(pageable);
    }

    @PostMapping
    public T add(@RequestBody T obj) {
        return repo.save(obj);
    }
}
