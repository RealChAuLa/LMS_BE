package com.lms.API.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore // Excludes this controller from Swagger documentation
public class HomeController {

    /**
     * Redirects the root URL to Swagger UI
     * @return redirect URL
     */
    @GetMapping("/")
    public String redirectToSwaggerUi() {
        return "redirect:/swagger-ui.html";
    }
}
