package com.urlmapping;

import com.urlmapping.dtos.CreateURLDTO;
import com.urlmapping.repository.UrlMappingRepository;
import com.urlmapping.services.UrlMappingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class MappingApplicationTests {

    @Mock
    private UrlMappingRepository repository;

    @InjectMocks
    private UrlMappingService service;

    @Test
    void testCreateShortenURLWithoutCustomAlias() {
        CreateURLDTO dto = new CreateURLDTO("https://example.com", null);


        Mockito.when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var response = service.createShortenURL(dto);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.url());
    }

    @Test
    void testCreateShortenURLWithCustomAlias() {
        CreateURLDTO dto = new CreateURLDTO("https://example.com", "customAlias");

        Mockito.when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        Mockito.when(repository.findByAlias("customAlias")).thenReturn(Optional.empty());

        var response = service.createShortenURL(dto);

        Assertions.assertEquals("customAlias", response.alias());
    }
}
