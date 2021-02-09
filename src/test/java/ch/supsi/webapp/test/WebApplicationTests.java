package ch.supsi.webapp.test;


import ch.supsi.webapp.model.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WebApplicationTests {

	@Autowired
	private MockMvc mvc;

	private static int id;
	private static int id2;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	// GET /blogpost -> size == 0
	public void test01() throws Exception {
		this.mvc.perform(get("/items").accept("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$",  Matchers.hasSize(0)));
	}

	@Test
	// POST /blogpost
	public void test02() throws Exception {
		MvcResult mvcResult = this.mvc.perform(post("/items").header("Content-Type", "application/json").content("{\"title\":\"titolo\", \"description\":\"testo\", \"author\":\"autore\"}"))
				.andExpect(status().isCreated())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.title").value("titolo"))
				.andExpect(jsonPath("$.description").value("testo"))
				.andExpect(jsonPath("$.author").value("autore"))
				.andExpect(jsonPath("$.id").exists())
				.andReturn();
		id = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Item.class).getId(); System.out.println(id);
	}

	@Test
	// GET /blogpost -> size == 1
	public void test03() throws Exception {
		this.mvc.perform(get("/items").accept("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$",  Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].title").value("titolo"))
				.andExpect(jsonPath("$[0].author").value("autore"))
				.andExpect(jsonPath("$[0].description").value("testo"));
	}

	@Test
	// GET /blogpost/{id}
	public void test04() throws Exception {
		this.mvc.perform(get("/items/"+id).accept("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isMap())
				.andExpect(jsonPath("$.title").value("titolo"))
				.andExpect(jsonPath("$.description").value("testo"))
				.andExpect(jsonPath("$.author").value("autore"))
				.andExpect(jsonPath("$.id").value(id));
	}

	@Test
	// GET /blogpost/{id}+100 -> 404
	public void test05() throws Exception {
		this.mvc.perform(get("/blogpost/"+(id+100)).accept("application/json"))
				.andExpect(status().isNotFound());
	}

	@Test
	// PUT /blogpost/{id}
	public void test06() throws Exception {
		this.mvc.perform(put("/items/"+id).header("Content-Type", "application/json").content("{\"title\":\"titolo2\", \"description\":\"testo2\", \"author\":\"autore2\" }"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isMap())
				.andExpect(jsonPath("$.title").value("titolo2"))
				.andExpect(jsonPath("$.description").value("testo2"))
				.andExpect(jsonPath("$.author").value("autore2"))
				.andExpect(jsonPath("$.id").value(id));
	}

	@Test
	// PUT /blogpost/{id}+1 -> 404
	public void test07() throws Exception {
		this.mvc.perform(put("/items/"+(id+1)).header("Content-Type", "application/json").content("{\"title\":\"titolo2\", \"description\":\"testo2\", \"author\":\"autore2\"}"))
				.andExpect(status().isNotFound());
	}

	@Test
	// GET /blogpost -> size == 1
	public void test08() throws Exception {
		this.mvc.perform(get("/items").accept("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$",  Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].title").value("titolo2"))
				.andExpect(jsonPath("$[0].author").value("autore2"))
				.andExpect(jsonPath("$[0].description").value("testo2"));
	}

	@Test
	// POST /blogpost
	public void test09() throws Exception {
		MvcResult mvcResult = this.mvc.perform(post("/items").header("Content-Type", "application/json").content("{\"title\":\"titolo3\", \"description\":\"testo3\", \"author\":\"autore3\"}"))
				.andExpect(status().isCreated())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.title").value("titolo3"))
				.andExpect(jsonPath("$.description").value("testo3"))
				.andExpect(jsonPath("$.author").value("autore3"))
				.andExpect(jsonPath("$.id").exists())
				.andReturn();
		id2 = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Item.class).getId(); System.out.println(id);
	}

	@Test
	// DELETE /blogpost/{id}
	public void test10() throws Exception {
		this.mvc.perform(delete("/items/"+id))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success").value(true));
	}

	@Test
	// DELETE /blogpost/{id}+100 -> 404
	public void test11() throws Exception {
		this.mvc.perform(delete("/items/"+(id+100)))
				.andExpect(status().isNotFound());
	}

	@Test
	// GET /blogpost -> size == 1
	public void test12() throws Exception {
		this.mvc.perform(get("/items").accept("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$",  Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].title").value("titolo3"))
				.andExpect(jsonPath("$[0].author").value("autore3"))
				.andExpect(jsonPath("$[0].description").value("testo3"));
	}

	@Test
	// DELETE /blogpost/{id2}
	public void test13() throws Exception {
		this.mvc.perform(delete("/items/"+id2))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success").value(true));
	}

	@Test
	// GET /blogpost -> size == 0
	public void test14() throws Exception {
		this.mvc.perform(get("/items").accept("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$",  Matchers.hasSize(0)));
	}

}
