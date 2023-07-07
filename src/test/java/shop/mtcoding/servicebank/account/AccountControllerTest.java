package shop.mtcoding.servicebank.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import shop.mtcoding.servicebank._core.utils.DummyObjectUtils;
import shop.mtcoding.servicebank.user.User;
import shop.mtcoding.servicebank.user.UserRepository;

import javax.persistence.EntityManager;

@Sql("classpath:db/teardown.sql")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class AccountControllerTest extends DummyObjectUtils {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;


    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        dataSetting();
        em.clear();
    }

    private void dataSetting() {
        User ssar = userRepository.save(newUser("ssar", "쌀"));
        User cos = userRepository.save(newUser("cos", "코스,"));
        User love = userRepository.save(newUser("love", "러브"));
        User admin = userRepository.save(newUser("admin", "관리자"));

        Account ssarAccount1 = accountRepository.save(newAccount(1111, ssar));
        Account cosAccount = accountRepository.save(newAccount(2222, cos));
        Account loveAccount = accountRepository.save(newAccount(3333, love));
        Account ssarAccount2 = accountRepository.save(newAccount(4444, ssar));
    }

}
