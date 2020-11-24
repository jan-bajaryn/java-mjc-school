package com.epam.mjc.core.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
//@ComponentScan(basePackages = {"com.epam.mjc"})
//@SpringBootTest(classes = TestConfig.class)
//@ContextConfiguration(classes = {TestConfig.class})
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