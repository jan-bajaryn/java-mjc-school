package com.epam.mjc.core.service.help;

import com.epam.mjc.api.domain.Tag;
import com.epam.mjc.api.service.exception.TagValidatorException;
import com.epam.mjc.api.service.help.TagService;
import com.epam.mjc.core.config.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Assertions;

import java.util.Collections;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@SqlGroup({
        @Sql(scripts = "/drop_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
        @Sql(scripts = "/create_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
        @Sql(scripts = "/fill_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class TagServiceImplTestWithoutMock {

    private static final String CREATE_NAME_1 = "AAA";
    private static final String CREATE_NAME_2 = "BBB";
    @Autowired
    private TagService tagService;

    private Tag tag1;
    private Tag tag2;
    private Tag tag3;
    private Tag tag4;
    private Tag tag5;


    private Tag tagCreate1;
    private Tag tagCreate2;

    @BeforeEach
    void setUp() {
        tag1 = Tag.builder().id(1L).name("Antoshka").build();
        tag2 = Tag.builder().id(2L).name("Vasia").build();
        tag3 = Tag.builder().id(3L).name("Minsk").build();
        tag4 = Tag.builder().id(4L).name("Vitsebsk").build();
        tag5 = Tag.builder().id(5L).name("Vasniatsova").build();

        tagCreate1 = Tag.builder().name(CREATE_NAME_1).build();
        tagCreate2 = Tag.builder().name(CREATE_NAME_2).build();
    }

    @Test
    public void testCreateByName() {
        Tag byName = tagService.createByName(CREATE_NAME_1);
        Assertions.assertNotNull(byName.getId());
    }

    @Test
    public void testCreateByNameWrongNameValidatorException() {
        String longName = String.join("", Collections.nCopies(256, "A"));
        Assertions.assertThrows(TagValidatorException.class, () -> tagService.createByName(longName));
    }

    @Test
    public void testCreateByNameNullNameValidatorException() {
        Assertions.assertThrows(TagValidatorException.class, () -> tagService.createByName(null));
    }


}
