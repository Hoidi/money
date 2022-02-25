package com.money.money.controller;

import com.money.money.MoneyFacade;
import com.money.money.Tag;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TagController {

    private final MoneyFacade moneyFacade;

    TagController(MoneyFacade moneyFacade) {
        this.moneyFacade = moneyFacade;
    }

    public List<Tag> getAllTags() {
        return moneyFacade.getAllTags();
    }
}
