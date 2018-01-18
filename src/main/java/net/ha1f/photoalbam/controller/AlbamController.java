package net.ha1f.photoalbam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.ha1f.photoalbam.model.Albam;
import net.ha1f.photoalbam.model.Photo;
import net.ha1f.photoalbam.service.AlbamService;

@Controller
@RequestMapping("albams")
public class AlbamController {

    @Autowired
    AlbamService albamService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public String index() {
        return albamService.findAll().toString();
    }

    @RequestMapping(value = "/{albamId}", method = RequestMethod.GET)
    public ModelAndView getById(@PathVariable("albamId") String albamId) {
        Albam albam = albamService.findById(albamId);
        if (albam == null) {
            // error
            return new ModelAndView();
        }
        List<Photo> photos = albamService.getPhotos(albam);

        ModelAndView mv = new ModelAndView();
        mv.addObject("albam", albam);
        mv.addObject("photos", photos);
        mv.setViewName("albam");
        return mv;
    }
}
