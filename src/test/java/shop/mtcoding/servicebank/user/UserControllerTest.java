package shop.mtcoding.servicebank.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import shop.mtcoding.servicebank._core.utils.DummyObjectUtils;

import javax.persistence.EntityManager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:db/teardown.sql")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserControllerTest extends DummyObjectUtils {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        userRepository.save(newUser("ssar", "쌀"));
        em.clear();
    }

    @Test
    public void join_success_test() throws Exception {
        // given
        UserRequest.JoinDTO joinDTO = new UserRequest.JoinDTO();
        joinDTO.setUsername("love");
        joinDTO.setPassword("1234");
        joinDTO.setEmail("love@nate.com");
        joinDTO.setFullName("love");

        String requestBody = om.writeValueAsString(joinDTO);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc
                .perform(post("/join").content(requestBody).contentType(MediaType.APPLICATION_JSON));
         String responseBody = resultActions.andReturn().getResponse().getContentAsString();
         System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().isOk());
    }
}
