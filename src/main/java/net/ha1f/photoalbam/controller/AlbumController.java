package net.ha1f.photoalbam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.ha1f.photoalbam.model.Album;
import net.ha1f.photoalbam.model.Photo;
import net.ha1f.photoalbam.service.AlbumService;

@Controller
@RequestMapping("albums")
public class AlbumController {

    @Autowired
    AlbumService albamService;

    private AlbumService albumService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody

    public String index() {
        return albumService.findAll().toString();
    }

    @RequestMapping(value = "/{albamId}", method = RequestMethod.GET)
    public ModelAndView getById(@PathVariable("albamId") String albamId) {
        Album albam = albamService.findById(albamId);
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
