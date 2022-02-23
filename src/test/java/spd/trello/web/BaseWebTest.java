package spd.trello.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import spd.trello.domain.Resource;
import spd.trello.start.Main;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest(classes = {
        Main.class})
@EnableAutoConfiguration
@AutoConfigureMockMvc
public class BaseWebTest<T> {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

    public MvcResult showAll(String url) throws Exception {
        return mockMvc.perform(get(url))
                .andReturn();
    }

    public MvcResult create(String url, T entity) throws Exception {
        return mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(entity)))
                .andReturn();
    }

    public MvcResult read(String url) throws Exception {
        return mockMvc.perform(get(url))
                .andReturn();
    }

    public MvcResult update(String url, T entity) throws Exception {
        return mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(entity)))
                .andReturn();
    }

    public MvcResult delete(String url) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.delete(url))
                .andReturn();
    }


    Object getValue(MvcResult mvcResult, String jsonPath) throws UnsupportedEncodingException {
        return JsonPath.read(mvcResult.getResponse().getContentAsString(), jsonPath);
    }

    String Date(LocalDateTime t)
    {
        if (t.toString().length()>26)
            return t.toString().substring(0,27);
        else
            return t.toString();
    }
}
