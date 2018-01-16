package net.ha1f.photoalbam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.ha1f.photoalbam.model.Photo;
import net.ha1f.photoalbam.service.PhotoService;

import com.google.common.collect.ImmutableList;

@Controller
@RequestMapping("photos")
public class PhotoController {

    @Autowired
    PhotoService photoService;

    @RequestMapping("")
    @ResponseBody
    public String index() {
        return "Hello";
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<Photo> list() {
        return ImmutableList.copyOf(photoService.findAll());
    }

    @RequestMapping("/sample")
    @ResponseBody
    public String addSample() {
        return photoService.addSamplePhoto();
    }

}
