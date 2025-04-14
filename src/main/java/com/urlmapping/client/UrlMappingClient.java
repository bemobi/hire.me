package com.urlmapping.client;


import com.urlmapping.dtos.ResponseURLDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "urlMappingClient", url = "${base-url-service}")
public interface UrlMappingClient  {

    @PostMapping("/create")
    ResponseURLDTO createShortenURL(@RequestParam("url") String url,
                                    @RequestParam(value = "CUSTOM_ALIAS", required = false) String customAlias);

    @GetMapping("/{alias}")
    void retrieveURL(@PathVariable("alias") String alias);
}
