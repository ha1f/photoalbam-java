package net.ha1f.photoalbam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.ha1f.photoalbam.service.AlbamService;

@Controller
@RequestMapping("albams")
public class AlbamController {

    AlbamService albamService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public String index() {
        return albamService.findAll().toString();
    }
}
