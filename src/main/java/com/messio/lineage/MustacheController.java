package com.messio.lineage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/site")
public class MustacheController {
    private final DataFacade facade;

    @Autowired
    public MustacheController(DataFacade facade) {
        this.facade = facade;
    }

    @GetMapping("/companies")
    public String getCompanies(final Model model){
        model.addAttribute("companyList", facade.findCompanies());
        return "companies";
    }
}
