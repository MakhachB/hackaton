package com.hackaton.repositories;

import com.hackaton.models.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestsRepository extends JpaRepository<Test, Integer> {
    List<Test> findAllByOwnerId(int id);

    List<Test> findAllByTitleStartingWith(String startingWith);
}
