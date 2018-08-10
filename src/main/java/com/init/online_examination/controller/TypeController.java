package com.init.online_examination.controller;

import com.init.online_examination.service.TypeService;
import com.init.online_examination.utilty.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/types")
public class TypeController {
    private TypeService typeService;

    @Autowired
    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

//    @RequestMapping(name = "", method = RequestMethod.GET)
//    public ResponseEntity all() {
//        return ResultData.success(typeService.findAll());
//    }
//    @RequestMapping(name = "/{id}", method = RequestMethod.GET)
//    public ResponseEntity findById(@PathVariable Long id) {
//        return ResultData.success(typeService.findById(id));
//    }
}
