package com.shortener;

import com.shortener.domain.repository.ShortenerRepository;
import com.shortener.infra.Base62Converter;
import com.shortener.infra.StatusError;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ApplicationTest {


    public static final String API_LOCATION = "/shortener/";
    private static final String URL_TO_TEST_UPDATED = "http://bemobi.com.br/";
    private static final String URL_TO_TEST_BAD_FORMAT = "Troll://zelda.com.br/";
    private static final String URL_TO_TEST = "http://zelda.com.br/";
    private static final String ALIAS_TO_TEST = "zelda";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private ShortenerRepository shortenerRepository;


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        shortenerRepository.deleteByKey(ALIAS_TO_TEST);
    }


    /*************************
     * *** Errors testes*****
     *************************/
    @Test
    public void shouldReturnErrorAlreadyExistsTest() throws Exception {
        mockMvc.perform(
                post(API_LOCATION)
                        .param("url", URL_TO_TEST)
                        .param("customName", ALIAS_TO_TEST)
        ).andExpect(status().isCreated());

        mockMvc.perform(
                post(API_LOCATION)
                        .param("url", URL_TO_TEST)
                        .param("customName", ALIAS_TO_TEST)
        ).andExpect(jsonPath("$.err_code", is(StatusError.ERROR_101)))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void shouldReturnErrorUrlNotFound() throws Exception {
        mockMvc.perform(
                get("/" + ALIAS_TO_TEST)
        ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.err_code", is(StatusError.ERROR_102)));
    }


    @Test
    public void shouldReturnErrorInvalidUrlTest() throws Exception {
        mockMvc.perform(
                post(API_LOCATION)
                        .param("url", URL_TO_TEST_BAD_FORMAT)
                        .param("customName", ALIAS_TO_TEST)
        ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.err_code", is(StatusError.ERROR_103)));
    }


    /*************************
     * *** Life cycle testes*****
     *************************/

    @Test
    public void shouldBeCreatedTest() throws Exception {
        mockMvc.perform(
                post("/shortener")
                        .param("url", URL_TO_TEST)
                        .param("customName", ALIAS_TO_TEST)
        ).andExpect(status().isCreated());

        Assert.assertNotNull(shortenerRepository.findById(ALIAS_TO_TEST));

    }

    @Test
    public void shouldBeUpdatedAndGetNewValueTest() throws Exception {
        // create new shortener object
        mockMvc.perform(
                post(API_LOCATION)
                        .param("url", URL_TO_TEST)
                        .param("customName", ALIAS_TO_TEST)

        ).andExpect(status().isCreated());

        //updated url from shortener by url
        mockMvc.perform(
                put(API_LOCATION)
                        .param("name", ALIAS_TO_TEST)
                        .param("url", URL_TO_TEST_UPDATED)
        ).andExpect(status().isOk());

        //get data from shortener, and check if is matcher
        mockMvc.perform(
                get(API_LOCATION).param("name", ALIAS_TO_TEST)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.targetUrl", is(URL_TO_TEST_UPDATED)));
    }

    @Test
    public void shouldBeDeletedAndTryToGetTest() throws Exception {
        // create new shortener object
        mockMvc.perform(
                post(API_LOCATION)
                        .param("url", URL_TO_TEST)
                        .param("customName", ALIAS_TO_TEST)

        ).andExpect(status().isCreated());

        //check if exist
        mockMvc.perform(
                get(API_LOCATION).param("name", ALIAS_TO_TEST)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.targetUrl", is(URL_TO_TEST)));

        //delete it
        mockMvc.perform(
                delete(API_LOCATION)
                        .param("name", ALIAS_TO_TEST)
        ).andExpect(status().isOk());

        //try get deleted object
        mockMvc.perform(
                get(API_LOCATION).param("name", ALIAS_TO_TEST)
        ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.err_code", is(StatusError.ERROR_102)));
    }


    @Test
    public void shouldBeRedirectedTest() throws Exception {

        mockMvc.perform(
                post(API_LOCATION)
                        .param("url", URL_TO_TEST)
                        .param("customName", ALIAS_TO_TEST)
        ).andExpect(status().isCreated());


        mockMvc.perform(
                get("/" + ALIAS_TO_TEST)
        ).andExpect(status().isMovedTemporarily());
    }

    @Test
    public void shouldReturnResponseOnCreatedTest() throws Exception {
        mockMvc.perform(
                post(API_LOCATION)
                        .param("url", URL_TO_TEST)
                        .param("customName", ALIAS_TO_TEST)
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.alias", is(ALIAS_TO_TEST)))
                .andExpect(jsonPath("$.url", is(Application.SHORTENER_DOMAIN + ALIAS_TO_TEST)));

    }

    @Test
    public void shouldReturnResponseOnCreatedAlreadyExistsTest() throws Exception {
        mockMvc.perform(
                post(API_LOCATION)
                        .param("url", URL_TO_TEST)
                        .param("customName", ALIAS_TO_TEST)
        ).andExpect(status().isCreated());

        mockMvc.perform(
                post(API_LOCATION)
                        .param("url", URL_TO_TEST)
                        .param("customName", ALIAS_TO_TEST)
        ).andExpect(jsonPath("$.err_code", is(StatusError.ERROR_101)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.alias", is(ALIAS_TO_TEST)))
                .andExpect(jsonPath("$.err_code", is(StatusError.ERROR_101)))
                .andExpect(jsonPath("$.description", is(StatusError.ERROR_101_DESCRIPTION)));
    }


    @Test
    public void shouldReturnResponseOnRedirectNotFoundTest() throws Exception {
        mockMvc.perform(
                get("/" + ALIAS_TO_TEST)
        ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.alias", is(ALIAS_TO_TEST)))
                .andExpect(jsonPath("$.err_code", is(StatusError.ERROR_102)))
                .andExpect(jsonPath("$.description", is(StatusError.ERROR_102_DESCRIPTION)));
    }


    @Test
    public void shouldGenerateUnicHashTest() {
        final List<String> hashs = new ArrayList<>();

        for (int i = 1; i < 1000; i++) {
            String hash = Base62Converter.converter(i);

            if (hashs.contains(hash))
                Assert.fail();

            hashs.add(hash);
        }
    }


}
