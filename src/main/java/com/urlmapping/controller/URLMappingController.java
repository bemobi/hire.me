package com.urlmapping.controller;


import com.urlmapping.dtos.CreateURLDTO;
import com.urlmapping.dtos.ResponseURLDTO;
import com.urlmapping.dtos.TopVisitedURLDTO;
import com.urlmapping.exceptions.ApplicationException;
import com.urlmapping.services.UrlMappingService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/u")
public class URLMappingController {

    private final UrlMappingService service;

    public URLMappingController(UrlMappingService urlMappingService) {
        this.service = urlMappingService;
    }


    @PostMapping("/create")
    public ResponseEntity<ResponseURLDTO> createShortenURL(@RequestParam("url") String url,
                                                           @RequestParam(value = "CUSTOM_ALIAS",
                                                                   required = false) String customAlias) throws ApplicationException {
        var createDTO = new CreateURLDTO(url, customAlias);
        var response = service.createShortenURL(createDTO);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{alias}")
    public ResponseEntity<?> retrieveURL(@PathVariable(name = "alias") String alias, HttpServletResponse response) throws ApplicationException,
            IOException {
        var originalURL = service.retrieveURL(alias);
        response.sendRedirect(originalURL);
        return ResponseEntity.status(301).build();
    }

    @GetMapping("/top")
    public ResponseEntity<List<TopVisitedURLDTO>> findTopTen() {
        return ResponseEntity.status(200).body(service.getTopTen());
    }




}

