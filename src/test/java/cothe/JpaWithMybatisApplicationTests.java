package cothe;

import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.transaction.TransactionManager;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaWithMybatisApplicationTests {
    @Autowired
    private EntityManagerFactory emf;

    @Autowired
    private SqlSession sqlSession;

    @Autowired
    private JpaTransactionManager transactionManager;

    @Test
    public void contextLoads() {
        // transaction 단위
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute( transactionStatus -> {
            EntityManager em = EntityManagerFactoryUtils.getTransactionalEntityManager(emf);
            UserInfo userInfo = new UserInfo();
            userInfo.setId(1L);
            userInfo.setName("새로운 이름");
            em.persist(userInfo);
            //em.flush();

            List<Object> objects = sqlSession.selectList("h2.selectUserInfo", null);
            System.out.println("In transaction : " + objects.size());
            return userInfo;
        });

        List<Object> objects = sqlSession.selectList("h2.selectUserInfo", null);
        System.out.println("In transaction : " + objects.size());

    }

    @After
    public void after() {
        emf.close();
    }

}
