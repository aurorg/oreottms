package com.panda.common.controller;

import com.panda.common.file.FileUploadUtils;
import com.panda.common.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 封装图片上传请求
 */

/*
文件上传控制器FileUploadController，上传和删除文件的方法。

uploadUser()、uploadMovie()、uploadCinema()、uploadActor()方法：
分别用于上传用户头像、电影海报、影院图片和演员头像。
通过调用FileUploadUtils中的upload()方法来实现文件上传，并将上传后的文件名返回给前端。

deletePicture()方法：用于删除文件。
它接收一个filePath参数，指定要删除的文件路径。
在方法中，首先使用ResourceUtils.getURL()方法获取文件的绝对路径，
然后调用FileUploadUtils中的deleteFile()方法来实现文件删除。

@RequestParam和@PathVariable注解：用于接收前端传递的参数。
@RequestParam注解用于接收请求参数，例如上传的文件；
@PathVariable注解用于接收请求路径中的参数，例如删除文件的路径。

 */
@Slf4j
@RestController
@RequestMapping("/upload")
public class FileUploadController {

    @PostMapping("/user")
    public ResponseResult uploadUser(@RequestParam("file") MultipartFile file) throws IOException {
        FileUploadUtils.setDefaultBaseDir(FileUploadUtils.userPath);
        String filename = FileUploadUtils.upload(file);
        log.debug("上传文件名 : " + filename);
        return ResponseResult.success((Object) filename);
    }

    @PostMapping("/movie")
    public ResponseResult uploadMovie(@RequestParam("file") MultipartFile file) throws IOException {
        FileUploadUtils.setDefaultBaseDir(FileUploadUtils.moviePath);
        String filename = FileUploadUtils.upload(file);
        log.debug("上传文件名 : " + filename);
        return ResponseResult.success((Object) filename);
    }

    @PostMapping("/cinema")
    public ResponseResult uploadCinema(@RequestParam("file") MultipartFile file) throws IOException {
        FileUploadUtils.setDefaultBaseDir(FileUploadUtils.cinemaPath);
        String filename = FileUploadUtils.upload(file);
        log.debug("上传文件名 : " + filename);
        return ResponseResult.success((Object) filename);
    }

    @PostMapping("/actor")
    public ResponseResult uploadActor(@RequestParam("file") MultipartFile file) throws IOException {
        FileUploadUtils.setDefaultBaseDir(FileUploadUtils.actorPath);
        String filename = FileUploadUtils.upload(file);
        log.debug("上传文件名 : " + filename);
        return ResponseResult.success((Object) filename);
    }

    @RequestMapping("/delete")
    public ResponseResult deletePicture(String filePath) {
        try {
            String path = ResourceUtils.getURL("classpath:").getPath().substring(1) + "static" + filePath;
            log.debug("删除文件路径为：" + path);
            boolean flag = FileUploadUtils.deleteFile(path);
            log.debug(flag ? "删除成功" : "删除失败");
        } catch (FileNotFoundException e) {
            System.out.println("删除文件不存在");
        } finally {
            return ResponseResult.success();
        }
    }

}
