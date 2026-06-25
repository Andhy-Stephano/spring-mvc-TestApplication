package test;

import annotation.Controller;
import annotation.GetMapping;

@Controller
public class EmpController {

    @GetMapping("/emp/list")
    public String liste() {
        return "liste";
    }

    @GetMapping("/emp/new")
    public String create() {
        return "create";
    }
}
