package test;

import annotation.Controller;
import annotation.GetMapping;
import annotation.HttpMethod;
import annotation.RestAPI;

@Controller
public class TestControllerHttp {

    // Repond uniquement au GET
    @GetMapping(value = "/test", method = {HttpMethod.GET})
    public String afficher() {
        return "afficher";
    }

    // Repond uniquement au POST
    @GetMapping(value = "/test", method = {HttpMethod.POST})
    public String traiter() {
        return "traiter";
    }

    // Repond aux deux GET et POST
    @GetMapping(value = "/test/all", method = {HttpMethod.GET, HttpMethod.POST})
    public String surDefinir() {
        return "surDefinir";
    }
}