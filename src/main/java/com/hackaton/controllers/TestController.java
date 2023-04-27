package com.hackaton.controllers;

import com.hackaton.models.Test;
import com.hackaton.services.DecisionService;
import com.hackaton.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.hackaton.models.AnswersList;
import com.hackaton.models.Question;
import com.hackaton.services.TestService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    private final TestService testService;

    private final QuestionService questionService;

    private final DecisionService decisionService;

    @Autowired
    public TestController(TestService testService, QuestionService questionService, DecisionService decisionService) {
        this.testService = testService;
        this.questionService = questionService;
        this.decisionService = decisionService;
    }

    @GetMapping()
    public String index(Model model) {
        List<Test> tests = testService.findAll();

        tests.forEach(test -> {
            test.setCountOfCurrentUserDecisions(decisionService.findCountOfDecisionsByUser(test));
            test.setBestDecisionOfCurrentUser(decisionService.findBestDecisionByUser(test));
        });

        model.addAttribute("tests", tests);
        return "tests/index";
    }

    @PostMapping()
    public String indexByName(@RequestParam("query") String query, Model model) {
        List<Test> tests = testService.getTestsByTitleStartingWith(query);

        tests.forEach(test -> {
            test.setCountOfCurrentUserDecisions(decisionService.findCountOfDecisionsByUser(test));
            test.setBestDecisionOfCurrentUser(decisionService.findBestDecisionByUser(test));
        });

        model.addAttribute("tests", tests);
        model.addAttribute("query", query);
        return "tests/index";
    }

    @GetMapping("/{id}")
    public String info(@PathVariable("id") int id,
                       Model model) {
        model.addAttribute("test", testService.findById(id));
        return "tests/info";
    }

    @GetMapping("/{id}/show")
    public String show(@PathVariable("id") int id,
                       Model model) {

        AnswersList answersList = new AnswersList((ArrayList<Question>) questionService.findAllByTestId(id));
        answersList.setAllQuestionsCount(answersList.getAnswers().size());

        model.addAttribute("answersList", answersList);
        return "tests/show";
    }

    @PostMapping("/{id}/show")
    public String check(@ModelAttribute AnswersList answersList ,
                        Model model, @PathVariable int id) {

        int rightAnswersCount = (int) answersList.getAnswers().stream().filter(Question::isRight).count();
        String resultInfo = rightAnswersCount + "/" + answersList.getAllQuestionsCount();


        decisionService.save(answersList, rightAnswersCount);
        testService.countUp(id);
        model.addAttribute("resultInfo", resultInfo);
        return "/tests/result";
    }



}
