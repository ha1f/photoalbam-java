package net.ha1f.photoalbam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("photos")
public class PhotosController {

    @RequestMapping("/")
    @ResponseBody
    public String index() {
        return "Hello";
    }

}
