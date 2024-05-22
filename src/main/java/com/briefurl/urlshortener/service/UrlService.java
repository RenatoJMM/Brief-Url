package com.briefurl.urlshortener.service;

import com.briefurl.urlshortener.model.Url;
import com.briefurl.urlshortener.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UrlService {


    UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository){
        this.urlRepository = urlRepository;
    }

    public List<Url> findAllUrl(){
        return urlRepository.findAll();
    }


    public Url saveUrl(String longUrl){

        Url foundUrl = findByUrl(longUrl);

        if(foundUrl != null){

            return foundUrl;

        } else{

            String baseShortUrl = "http://localhost:8080/";
            String shortKey = "";

            while(true){
                shortKey = generateKey();

                if(findByShortKey(shortKey) == null){
                    break;
                }
            }

            String shortUrl = baseShortUrl.concat(shortKey);

            Url newUrl = new Url(longUrl,shortKey,shortUrl);

            return urlRepository.save(newUrl);
        }

    }

    public void deleteUrlByKey(String key){
        urlRepository.deleteByKey(key);
    }

    public Url findByUrl(String longUrl){
        Optional<Url> optUrl = urlRepository.findByUrl(longUrl);
        // Url url = optUrl.orElseThrow(() -> new RuntimeException("There is no such URL in our Database"));

        return optUrl.orElse(null);
    }

    public Url findByShortKey(String shortKey){
        Optional<Url> optUrl = urlRepository.findByShortKey(shortKey);


//        return optUrl.orElseThrow(() -> new RuntimeException("There is no such key in our Database"));

        return optUrl.orElse(null);
    }


    private String generateKey(){

        Random random = new Random();

        StringBuilder key = new StringBuilder();

        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        for(int i = 0; i < 6; i++){
            key.append(characters.charAt(random.nextInt(characters.length())));
        }

        String shortKey = key.toString();

        return shortKey;
    }
}
