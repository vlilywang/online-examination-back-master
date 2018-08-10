package com.init.online_examination.utilty;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URLEncoder;

public class ResultData {
    public static ResponseEntity success(Object data, HttpHeaders httpHeaders) {
        return new ResponseEntity<>(data, httpHeaders, HttpStatus.OK);
    }

    public static ResponseEntity success(Object data) {
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public static ResponseEntity success() {
        return new ResponseEntity<>("{\"message\":\"操作成功\"}", HttpStatus.OK);
    }

    public static ResponseEntity error(String message) {
        return error(message, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity error(String message, HttpStatus httpStatus) {
        return new ResponseEntity<>("{\"message\":\"" + message + "\"}", httpStatus);
    }

    public static ResponseEntity forbidden() {
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    public static String getDownloadResponseContentDispositionHeader(String filename) {
        String contentDisposition;
        try {
            contentDisposition = "attachment; fileName=" + filename + "; filename*=utf-8''" + URLEncoder.encode(filename, "UTF-8");
        } catch (Exception ex) {
            contentDisposition = "attachment; fileName=" + filename;
        }
        return contentDisposition;
    }

    public static String getDownloadResponseContentType() {
        return "application/octet-stream; charset=utf-8";
    }

}
