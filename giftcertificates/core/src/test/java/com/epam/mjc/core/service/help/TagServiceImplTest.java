package com.epam.mjc.core.service.help;

import com.epam.mjc.api.domain.Tag;
import com.epam.mjc.core.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
class TagServiceImplTest {

    private static final Logger log = LoggerFactory.getLogger(TagServiceImplTest.class);

    @Autowired
    private TagServiceImpl tagServiceImpl;

    @Test
    void findAll() {
        List<Tag> all = tagServiceImpl.findAll();
        log.debug("all = {}", all);
    }
}