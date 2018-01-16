package net.ha1f.photoalbam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.ha1f.photoalbam.model.Photo;
import net.ha1f.photoalbam.service.PhotoService;

import com.google.common.collect.ImmutableList;

@Controller
@RequestMapping("photos")
public class PhotoController {

    @Autowired
    PhotoService photoService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return "photos";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public String post(@RequestParam("image") MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            System.out.println("image is empty");
            return "emtpy";
        }
        System.out.println(multipartFile.getContentType());
        System.out.println(multipartFile.getName());
        return photoService.addImage(multipartFile);
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<Photo> list() {
        return ImmutableList.copyOf(photoService.findAll());
    }

    @RequestMapping("/sample")
    @ResponseBody
    public String addSample() {
        return "";
    }

}
