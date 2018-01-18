package net.ha1f.photoalbam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import net.ha1f.photoalbam.service.PhotoService;

import com.google.common.collect.ImmutableList;

@Controller
@RequestMapping("photos")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return "upload";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public String post(@RequestParam("image") MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return "error";
        }
        return photoService.addImage(multipartFile).orElse("error");
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteAll() {
        photoService.deleteAll();
        return "Deleted all images";
    }

    @RequestMapping(value = "/{photo_id}", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<byte[]> getById(@PathVariable("photo_id") String photoId) {
        return photoService.findById(photoId)
                           .map(photo -> {
                               byte[] bytes = photoService.fetchImageData(photo);
                               HttpHeaders headers = new HttpHeaders();
                               headers.setContentType(
                                       photoService.getMediaType(photo).orElse(MediaType.IMAGE_JPEG));
                               headers.setContentLength(bytes.length);
                               return new HttpEntity<>(bytes, headers);
                           })
                           .orElse(new HttpEntity<>(new byte[0], new HttpHeaders()));
    }

    @RequestMapping("/list")
    @ResponseBody
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("photos");
        mv.addObject("photos", ImmutableList.copyOf(photoService.findAll()));
        return mv;
    }

}
