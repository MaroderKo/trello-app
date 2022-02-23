package spd.trello.web;

import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ParentBasedWebTest extends BaseWebTest{
    public ResultActions getParrent(String url)  throws Exception
    {
        return mockMvc.perform(get(url)).andExpect(status().isOk());
    }
}
