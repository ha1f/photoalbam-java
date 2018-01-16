package net.ha1f.photoalbam.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
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

import net.ha1f.photoalbam.model.Photo;
import net.ha1f.photoalbam.service.PhotoService;

import com.google.common.collect.ImmutableList;
import com.google.common.io.ByteStreams;

@Controller
@RequestMapping("photos")
public class PhotoController {

    @Autowired
    PhotoService photoService;

    @Autowired
    ResourceLoader resourceLoader;

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
        return photoService.addImage(multipartFile);
    }

    @RequestMapping(value = "/{photo_id}", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<byte[]> getById(@PathVariable("photo_id") String photoId) {
        Photo photo = photoService.findById(photoId);
        if (photo == null) {
            HttpHeaders headers = new HttpHeaders();
            byte[] bytes = new byte[0];
            return new HttpEntity<>(bytes, headers);
        }

        try (final InputStream is = Files.newInputStream(Paths.get(photo.getFilePath()))) {
            final InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            byte[] bytes = ByteStreams.toByteArray(is);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(bytes.length);
            return new HttpEntity<>(bytes, headers);
        } catch (IOException e) {
            System.err.println(e);
            HttpHeaders headers = new HttpHeaders();
            byte[] bytes = new byte[0];
            return new HttpEntity<>(bytes, headers);
        }
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
