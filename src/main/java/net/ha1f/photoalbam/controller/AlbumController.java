package net.ha1f.photoalbam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.ha1f.photoalbam.service.AlbumService;

@Controller
@RequestMapping("albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public String index() {
        return albumService.findAll().toString();
    }
}
