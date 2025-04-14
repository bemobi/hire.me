package com.urlmapping.services;


import com.urlmapping.dtos.CreateURLDTO;
import com.urlmapping.dtos.ResponseURLDTO;
import com.urlmapping.dtos.Statistics;
import com.urlmapping.dtos.TopVisitedURLDTO;
import com.urlmapping.entities.UrlMapping;
import com.urlmapping.exceptions.AliasAlreadyExistsException;
import com.urlmapping.exceptions.ShortenedURLNotFoundException;
import com.urlmapping.exceptions.URLNotProvidedException;
import com.urlmapping.repository.UrlMappingRepository;
import com.urlmapping.utils.URLUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UrlMappingService {

    private final UrlMappingRepository repository;
    private final Integer TOTAL_CHARACTERS = 6;
    private final int DECIMAL_PLACES = 2;

    @Value("${base-url-service}")
    private String baseURL;


    public  UrlMappingService(UrlMappingRepository urlMappingRepository) {
        this.repository = urlMappingRepository;
    }

    @Transactional
    public ResponseURLDTO createShortenURL(CreateURLDTO dto) {
        long startTime = System.currentTimeMillis();
        this.validateURLDTO(dto);
        var url = new UrlMapping();
        var alias = dto.customAlias();

        if (alias == null || (alias.isEmpty() || alias.isBlank()))
            alias = URLUtils.generateShortUrlCode(TOTAL_CHARACTERS);

        url.setAlias(alias);
        url.setClicks(0);
        url.setOriginalURL(dto.url());

        this.repository.save(url);

        String timeTaken = URLUtils.formatTimeTaken(startTime, DECIMAL_PLACES);

        return new ResponseURLDTO(
                alias,
                baseURL + alias,
                dto.url(),
                new Statistics(timeTaken)
        );
    }

    public List<TopVisitedURLDTO> getTopTen() {
        List<Object[]> results = repository.findTop10MostClickedGroupedByOriginalURL();
        System.out.println(results);
        return results.stream()
                .map(r -> new TopVisitedURLDTO((String) r[0], ((Number) r[1]).intValue()))
                .toList();
    }

    public String retrieveURL(String alias) {
        var urlEntity = this.getURLEntityByAlias(alias);

        urlEntity.setClicks(urlEntity.getClicks() + 1);

        this.repository.save(urlEntity);

        return urlEntity.getOriginalURL();
    }

    private UrlMapping getURLEntityByAlias(String alias) {
        var urlEntity = this.repository.findByAlias(alias);

        if (urlEntity.isEmpty())
            throw new ShortenedURLNotFoundException(alias);

        return urlEntity.get();
    }

    private void validateURLDTO(CreateURLDTO dto) {

        var originalURL = dto.url();
        var customAlias = dto.customAlias();

        if (originalURL == null || originalURL.isEmpty() || originalURL.isBlank())
            throw new URLNotProvidedException(customAlias);

        if (customAlias != null && (customAlias.isEmpty() || customAlias.isBlank()))
            return;

        var urlWithSameAlias = this.repository.findByAlias(customAlias);

        if (urlWithSameAlias.isPresent())
            throw new AliasAlreadyExistsException(customAlias);
    }


}
