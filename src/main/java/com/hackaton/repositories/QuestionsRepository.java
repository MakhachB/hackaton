package com.hackaton.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hackaton.models.Question;

import java.util.List;

@Repository
public interface QuestionsRepository extends JpaRepository<Question, Integer> {
    List<Question> findByTestId(int id, Sort numberOfQuestion);

    void deleteAllByTestId(int id);

}
