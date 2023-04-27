package com.hackaton.repositories;

import com.hackaton.models.Decision;
import com.hackaton.models.Test;
import com.hackaton.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DecisionsRepository extends JpaRepository<Decision, Integer> {
    List<Decision> findAllByPerson(User user);

    int countByPersonAndTest(User user, Test test);

    @Query(value = "SELECT max(d.count_of_right_answers) from decisions d where d.tests_id= ?1 and d.users_id= ?2"
            , nativeQuery = true)
    Integer findMaxCountOfRightAnswersByPersonAndTest(int tests_id , int users_id);

//    int findTopByCountOfRightAnswersAndPersonAndTest(User user, Test test);

}
