package com.epam.mjc.core.dao;

import com.epam.mjc.TestApplication;
import com.epam.mjc.core.config.PersistenceConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@ComponentScan(basePackages = {"com.epam.mjc"})
//@SpringBootTest(classes = TestApplication.class)
@ContextConfiguration(classes = {TestApplication.class, PersistenceConfig.class})
class TagDaoImplTest {

    //
    @Autowired
    private TagDaoImpl tagDaoImpl;

    //
    @Test
    void findAll() {
//        tagDaoImpl.findAll(1, 10);
        assertTrue(true);
    }
//
//    @Test
//    void create() {
//    }
//
//    @Test
//    void delete() {
//    }
//
//    @Test
//    void findById() {
//    }
//
//    @Test
//    void findByTagName() {
//    }
//
//    @Test
//    void findMostPopularTagIdOfUserHigherCostOrders() {
//    }
}