package by.epam.reactrunner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TemplateFile {

    @RequestMapping(value = "/{p:^(?!index.html).*$}")
    public String HomePage() {
        return "index.html";
    }
}