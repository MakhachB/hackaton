package com.hackaton.services;

import com.hackaton.models.Decision;
import com.hackaton.models.Test;
import com.hackaton.repositories.DecisionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hackaton.models.AnswersList;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class DecisionService {

    private final DecisionsRepository decisionsRepository;
    private final UserService userService;

    @Autowired
    public DecisionService(DecisionsRepository decisionsRepository, UserService userService) {
        this.decisionsRepository = decisionsRepository;
        this.userService = userService;
    }

    @Transactional
    public void save(AnswersList answersList, int rightAnswersCount) {
        Decision decision = enrichDecision(answersList, rightAnswersCount);
        decisionsRepository.save(decision);
    }

    private Decision enrichDecision(AnswersList answersList, int rightAnswersCount) {
        Decision decision = new Decision();
        decision.setPerson(userService.getCurrentUser());
        decision.setTest(answersList.getAnswers().get(0).getTest());
        decision.setCountOfRightAnswers(rightAnswersCount);
        decision.setCountOfAllAnswers(answersList.getAllQuestionsCount());
        decision.setTimeOfDecision(new Date());

        return decision;
    }

    public List<Decision> findAllByUserId() {
        return decisionsRepository.findAllByPerson(userService.getCurrentUser());
    }

    public int findCountOfDecisionsByUser(Test test) {
        return decisionsRepository.countByPersonAndTest(userService.getCurrentUser(), test);
    }

    public int findBestDecisionByUser(Test test) {

        Integer result = decisionsRepository.findMaxCountOfRightAnswersByPersonAndTest(
                test.getId(),
                userService.getCurrentUser().getId());

        return result == null ? 0 : result;

    }
}
