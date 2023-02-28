package com.ansv.taskmanagement.controllers;

import com.ansv.taskmanagement.dto.request.UploadFileDTO;
import com.ansv.taskmanagement.dto.response.ResponseDataObject;
import com.ansv.taskmanagement.service.StorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController()
@RequestMapping("/api/uploadFile")
public class UploadController extends BaseController {

    @Autowired
    private StorageService storageService;

    @PostMapping("/task")
    public ResponseEntity<ResponseDataObject<String>> uploadFileInTask(@RequestParam("files") List<MultipartFile> files, @RequestParam("data") String data) throws IOException {
        ResponseDataObject<String> response = new ResponseDataObject<>();
        ObjectMapper mapper = new ObjectMapper();
        UploadFileDTO item = mapper.readValue(data, UploadFileDTO.class);
        Boolean storage = storageService.storageFile(files, item);
        if (storage) {
            response.success();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/getFileById")
    public ResponseEntity<InputStreamResource> getFileInTaskById(@RequestBody UploadFileDTO item) throws IOException {
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + item.getName());
        Resource resource = storageService.getFileById(item);
        File file = new File(resource.getURI());
        InputStreamResource inpuStream = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .headers(respHeaders)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .contentLength(file.length())
                .body(inpuStream);
    }

    @PostMapping("/getNameFileInTask")
    public ResponseEntity<ResponseDataObject<List<UploadFileDTO>>> getFileNameInTaskById(@RequestBody Long id) {
        ResponseDataObject<List<UploadFileDTO>> response = new ResponseDataObject<>();
        List<UploadFileDTO> listData = storageService.getNameFileOfTask(id);
        response.data = listData;
        response.success();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/getNameFileInProject")
    public ResponseEntity<ResponseDataObject<List<UploadFileDTO>>> getFileNameInProjectById(@RequestBody Long id) {
        ResponseDataObject<List<UploadFileDTO>> response = new ResponseDataObject<>();
        List<UploadFileDTO> listData = storageService.getNameFileOfProject(id);
        response.data = listData;
        response.success();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/project")
    public ResponseEntity<ResponseDataObject<String>> uploadFileInProject(@RequestParam("files") List<MultipartFile> files, @RequestParam("data") String data) throws IOException {
        ResponseDataObject<String> response = new ResponseDataObject<>();
        ObjectMapper mapper = new ObjectMapper();
        UploadFileDTO item = mapper.readValue(data, UploadFileDTO.class);
        Boolean storage = storageService.storageFile(files, item);
        if (storage) {
            response.success();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/deleteFile")
    public ResponseEntity<ResponseDataObject<String>> deleteFileInTask(@RequestBody UploadFileDTO data) throws IOException {
        ResponseDataObject<String> response = new ResponseDataObject<>();
        Boolean delete = storageService.deleteFile(data);
        if (delete) {
            response.success();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




}
