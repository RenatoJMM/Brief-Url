package com.briefurl.urlshortener.controller;

import com.briefurl.urlshortener.model.Url;
import com.briefurl.urlshortener.service.UrlService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("")
public class UrlController {

    private UrlService urlService;

    public UrlController(UrlService urlService){
        this.urlService = urlService;
    }

    @PostMapping("")
    public ResponseEntity<?> saveUrl(@RequestBody Url url){

        try {
            return new ResponseEntity<>(urlService.saveUrl(url.getUrl()), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>("Invalid body!!", HttpStatus.BAD_REQUEST);
        }

    }


    @RequestMapping(value = "/{key}", method = RequestMethod.GET)
    public ResponseEntity<?> redirectKeyToUrl(@PathVariable("key") String shortKey){

        Url url = urlService.findByShortKey(shortKey);

        if(url != null){
            UriComponents uriComponents = UriComponentsBuilder
                    .fromUriString(url.getUrl())
                    .build();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(uriComponents.toUri());

            return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


//        try {
//
//            String url = urlService.findByShortKey(shortKey).getUrl();
//
//            UriComponents uriComponents = UriComponentsBuilder
//                    .fromUriString(url)
//                    .build();
//
//            HttpHeaders httpHeaders = new HttpHeaders();
//            httpHeaders.setLocation(uriComponents.toUri());
//
////            return ResponseEntity.status(302).location(uriComponents.toUri()).build();
////            return ResponseEntity.created(uriComponents.toUri()).build();
//
//            return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
//
//
//        } catch(Exception e){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUrlByKey(@PathVariable("key") String shortKey){
        Url url = urlService.findByShortKey(shortKey);

        if(url == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else{
            urlService.deleteUrlByKey(shortKey);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

}
