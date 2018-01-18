package net.ha1f.photoalbam.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import net.ha1f.photoalbam.model.Album;
import net.ha1f.photoalbam.model.Photo;
import net.ha1f.photoalbam.service.AlbumService;

@Controller
@RequestMapping("album")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("albums", albumService.findAll());
        mv.setViewName("albums");
        return mv;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public String createAlbum(@RequestParam("title") final String title,
                              @RequestParam(name = "password", required = false) final String password) {
        String albumId;
        if (password == null || password.isEmpty()) {
            albumId = albumService.create(title);
        } else {
            albumId = albumService.create(title, password);
        }
        return "Album created: <a href=\"./" + albumId + "\">link</a>";
    }

    @RequestMapping(value = "/{albumId}", method = RequestMethod.GET)
    public ModelAndView getById(@PathVariable("albumId") String albumId) {
        Album album = albumService.findById(albumId);

        // not found error
        if (album == null) {
            return new ModelAndView();
        }

        List<Photo> photos = albumService.getPhotos(album);

        ModelAndView mv = new ModelAndView();
        mv.addObject("album", album);
        mv.addObject("photos", photos);
        mv.setViewName("album");
        return mv;
    }

    @RequestMapping(value = "/{albumId}/photos", method = RequestMethod.POST)
    @ResponseBody
    public String appendPhoto(@PathVariable("albumId") final String albumId,
                              @RequestParam("images[]") final MultipartFile[] multipartFile) {
        List<Boolean> results = new ArrayList<>();
        // TODO: 権限確認
        Stream.of(multipartFile).forEach(file -> {
            boolean hasSucceeded = albumService.appendPhoto(albumId, file);
            results.add(hasSucceeded);
        });

        return results.toString();
    }
}
