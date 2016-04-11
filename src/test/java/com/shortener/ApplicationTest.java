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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ApplicationTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Autowired
	private ShortenerRepository shortenerRepository;


	public String URL_TO_TEST = "http://zelda.com.br";
	public String ALIAS_TO_TEST = "zelda";

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		shortenerRepository.deleteByKey(ALIAS_TO_TEST);
	}


	@Test
	public void shouldBeRedirectedTest() throws Exception {

		mockMvc.perform(
                get("/api/create?url=" + URL_TO_TEST + "&customName=" + ALIAS_TO_TEST)
        ).andExpect(status().isCreated());


		mockMvc.perform(
				get("/" + ALIAS_TO_TEST)
		).andExpect(status().isMovedTemporarily());
	}

	@Test
	public void shouldBeCreatedTest() throws Exception {
		mockMvc.perform(
				get("/api/create?url=" + URL_TO_TEST + "&customName=" + ALIAS_TO_TEST)
		).andExpect(status().isCreated());

		Assert.assertNotNull(shortenerRepository.findById(ALIAS_TO_TEST));

	}



	@Test
	public void shouldReturnError101Test() throws Exception {
		mockMvc.perform(
				get("/api/create?url=" + URL_TO_TEST + "&customName=" + ALIAS_TO_TEST)
		).andExpect(status().isCreated());

		mockMvc.perform(
				get("/api/create?url=" + URL_TO_TEST + "&customName=" + ALIAS_TO_TEST)
		).andExpect(jsonPath("$.err_code", is(StatusError.ERROR_101)))
				.andExpect(status().isBadRequest());
	}


	@Test
	public void shouldReturnError102Test() throws Exception {
		mockMvc.perform(
				get("/" + ALIAS_TO_TEST)
		).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.err_code", is(StatusError.ERROR_102)));
	}

	@Test
	public void shouldReturnResponseOnCreated() throws Exception {
		mockMvc.perform(
				get("/api/create?url=" + URL_TO_TEST + "&customName=" + ALIAS_TO_TEST)
		).andExpect(status().isCreated())
				.andExpect(jsonPath("$.alias", is(ALIAS_TO_TEST)))
				.andExpect(jsonPath("$.url", is(Application.SHORTENER_DOMAIN + ALIAS_TO_TEST)));

	}

	@Test
	public void shouldReturnResponseOnCreatedAlreadyExists() throws Exception {
		mockMvc.perform(
				get("/api/create?url=" + URL_TO_TEST + "&customName=" + ALIAS_TO_TEST)
		).andExpect(status().isCreated());

		mockMvc.perform(
				get("/api/create?url=" + URL_TO_TEST + "&customName=" + ALIAS_TO_TEST)
		).andExpect(jsonPath("$.err_code", is(StatusError.ERROR_101)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.alias", is(ALIAS_TO_TEST)))
				.andExpect(jsonPath("$.err_code", is(StatusError.ERROR_101)))
				.andExpect(jsonPath("$.description", is(StatusError.ERROR_101_DESCRIPTION)));
	}


	@Test
	public void shouldReturnResponseOnRedirectNotFound() throws Exception {
		mockMvc.perform(
				get("/" + ALIAS_TO_TEST)
		).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.alias", is(ALIAS_TO_TEST)))
				.andExpect(jsonPath("$.err_code", is(StatusError.ERROR_102)))
				.andExpect(jsonPath("$.description", is(StatusError.ERROR_102_DESCRIPTION)));
	}

	@Test
	public void shouldGenerateUnicHash(){
		final List<String> hashs = new ArrayList<>();

		for (int i = 1; i < 1000 ; i++) {
			String hash = Base62Converter.converter(i);

			if (hashs.contains(hash))
				Assert.fail();

			hashs.add(hash);
		}
	}


}
