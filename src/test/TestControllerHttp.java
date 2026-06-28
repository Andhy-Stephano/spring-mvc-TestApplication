package test;

import annotation.Controller;
import annotation.GetMapping;

@Controller
public class TestControllerHttp {

    // Repond uniquement au GET
    @GetMapping(value = "/test", method = {"GET"})
    public String afficher() {
        return "afficher";
    }

    // Repond uniquement au POST
    @GetMapping(value = "/test", method = {"POST"})
    public String traiter() {
        return "traiter";
    }

    // Repond aux deux GET et POST
    @GetMapping(value = "/test/all", method = {"GET", "POST"})
    public String surDefinir() {
        return "surDefinir";
    }
}