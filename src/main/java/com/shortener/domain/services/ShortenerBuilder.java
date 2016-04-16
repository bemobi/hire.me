package com.shortener.domain.services;

import com.shortener.domain.entities.Shortener;
import com.shortener.domain.repository.ShortenerRepository;
import com.shortener.infra.Base62Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by isvaldo on 26/03/16.
 */


@Service
public class ShortenerBuilder {

    @Autowired
    private ShortenerRepository shortenerRepository;
    public String targetUrl;
    public String id;
    public String customName;


    public void save(){
        if (customName == null) {

            Integer lastId = shortenerRepository.countAll();
            String strindId = getHashName(lastId);

            while(shortenerRepository.findById(strindId) != null) {
                lastId++;
                strindId = getHashName(lastId);
            }

            this.id = strindId;
            shortenerRepository.save(new Shortener(this.id, this.targetUrl));

        }else {

            if (shortenerRepository.findById(customName) == null){
                this.id = customName;

                shortenerRepository.save(new Shortener(this.id, this.targetUrl));

            }else {
                throw new IllegalArgumentException();
            }
        }
    }

    private String getHashName(final Integer id){
       return  Base62Converter.converter(id);
    }

    public ShortenerBuilder withTargetUrl(final String targetUrl){
        this.targetUrl = targetUrl;
        return this;
    }

    public ShortenerBuilder withCustomName(final String customName) {
        this.customName = customName;
        return this;
    }

    public ShortenerBuilder withId(final String id) {
        this.id = id;
        return this;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public String getId() {
        return id;
    }

    public String getCustomName() {
        return customName;
    }
}
