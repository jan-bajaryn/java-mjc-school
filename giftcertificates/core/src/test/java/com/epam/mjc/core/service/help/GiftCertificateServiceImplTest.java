package com.epam.mjc.core.service.help;

import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.domain.Tag;
import com.epam.mjc.api.service.exception.GiftCertificateNotFoundException;
import com.epam.mjc.api.service.exception.GiftCertificateValidatorException;
import com.epam.mjc.core.config.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@SqlGroup({
        @Sql(scripts = "/drop_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
        @Sql(scripts = "/create_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
        @Sql(scripts = "/fill_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class GiftCertificateServiceImplTest {

    private static final Logger log = LoggerFactory.getLogger(GiftCertificateServiceImplTest.class);
    private static final long FIRST_ID = 1L;
    private static final String FIRST_NAME = "First";
    private static final String FIRST_DESCRIPTION = "First certificate";
    private static final String FIRST_PRICE = "2.3";
    private static final String FIRST_CREATE_DATE = "2020-09-11T14:42:21";
    private static final String FIRST_UPDATE_DATE = "2020-09-11T14:42:21";
    private static final int FIRST_DURATION = 3;
    private static final long SEC_ID = 2L;
    private static final String SEC_NAME = "Second";
    private static final String SEC_DESCRIPTION = "Second certificate";
    private static final String SEC_PRICE = "5.34";
    private static final String SEC_CREATE_DATE = "2020-01-12T14:42:11";
    private static final String SEC_UPDATE_DATE = "2020-01-12T14:42:11";
    private static final int SEC_DURATION = 10;
    private static final String CREATE_NAME = "Create";
    private static final String CREATE_DESCRIPTION = "Created";
    private static final String CREATE_PRICE = "5.34";
    private static final int CREATE_DURATION = 10;
    private static final long WRONG_ID = -1L;
    private static final long LARGE_ID = 20L;
    private static final String UPDATE_DESCRIPTION = "newDescription";

    @Autowired
    private GiftCertificateServiceImpl giftCertificateService;


    private GiftCertificate giftCertificate1;
    private GiftCertificate giftCertificate2;
    private GiftCertificate giftCertificateCreate;

    private Tag tag1;
    private Tag tag2;
    private Tag tag3;


    private Tag tagCreate1;
    private Tag tagCreate2;


    @BeforeEach
    void setUp() {
        giftCertificate1 = new GiftCertificate();

        giftCertificate1.setId(FIRST_ID);
        giftCertificate1.setName(FIRST_NAME);
        giftCertificate1.setDescription(FIRST_DESCRIPTION);
        giftCertificate1.setPrice(new BigDecimal(FIRST_PRICE));
        giftCertificate1.setCreateDate(LocalDateTime.parse(FIRST_CREATE_DATE));
        giftCertificate1.setLastUpdateDate(LocalDateTime.parse(FIRST_UPDATE_DATE));
        giftCertificate1.setDuration(FIRST_DURATION);

        tag1 = Tag.builder().id(1L).name("Antoshka").build();
        tag2 = Tag.builder().id(2L).name("Vasia").build();
        tag3 = Tag.builder().id(3L).name("Minsk").build();

        giftCertificate1.setTags(new ArrayList<>(
                Arrays.asList(tag1,
                        tag2)
        ));

        giftCertificate2 = new GiftCertificate();

        giftCertificate2.setId(SEC_ID);
        giftCertificate2.setName(SEC_NAME);
        giftCertificate2.setDescription(SEC_DESCRIPTION);
        giftCertificate2.setPrice(new BigDecimal(SEC_PRICE));
        giftCertificate2.setCreateDate(LocalDateTime.parse(SEC_CREATE_DATE));
        giftCertificate2.setLastUpdateDate(LocalDateTime.parse(SEC_UPDATE_DATE));
        giftCertificate2.setDuration(SEC_DURATION);

        giftCertificateCreate = new GiftCertificate();

        giftCertificateCreate.setName(CREATE_NAME);
        giftCertificateCreate.setDescription(CREATE_DESCRIPTION);
        giftCertificateCreate.setPrice(new BigDecimal(CREATE_PRICE));
        giftCertificateCreate.setDuration(CREATE_DURATION);


        tagCreate1 = Tag.builder().name("AAA").build();
        tagCreate2 = Tag.builder().name("BBB").build();
    }


    @Test
    public void testFindById() {
        GiftCertificate actual = giftCertificateService.findById(FIRST_ID);
        Assertions.assertEquals(giftCertificate1, actual);
    }

    @Test
    public void testFindByIdWrongId() {
        Assertions.assertThrows(GiftCertificateValidatorException.class, () -> giftCertificateService.findById(WRONG_ID));
    }

    @Test
    public void testFindByIdNotFound() {
        Assertions.assertThrows(GiftCertificateNotFoundException.class, () -> giftCertificateService.findById(LARGE_ID));
    }

    @Test
    public void testCreate() {
        GiftCertificate giftCertificate = giftCertificateService.create(giftCertificateCreate);
        Assertions.assertNotNull(giftCertificate.getId());
    }

    @Test
    public void testCreateNullValidatorException() {
        Assertions.assertThrows(GiftCertificateValidatorException.class,
                () -> giftCertificateService.create(null));
    }

    @Test
    public void testCreateWithLongNameValidatorException() {
        String longName = String.join("", Collections.nCopies(256, "A"));
        giftCertificateCreate.setName(longName);
        Assertions.assertThrows(
                GiftCertificateValidatorException.class,
                () -> giftCertificateService.create(giftCertificateCreate)
        );
    }

    @Test
    public void testCreateWithTags() {
        giftCertificateCreate.setTags(
                Arrays.asList(
                        tagCreate1, tagCreate2
                )
        );

        GiftCertificate giftCertificate = giftCertificateService.create(giftCertificateCreate);
        Assertions.assertNotNull(giftCertificate.getTags().get(0).getId());
        Assertions.assertNotNull(giftCertificate.getTags().get(1).getId());
    }

    @Test
    public void testDeleteByIdThanNotFoundInDatabase() {
        giftCertificateService.deleteById(FIRST_ID);
        Assertions.assertThrows(
                GiftCertificateNotFoundException.class,
                () -> giftCertificateService.findById(FIRST_ID)
        );
    }

    @Test
    public void testDeleteByIdNotExistingNofFoundException() {
        Assertions.assertThrows(
                GiftCertificateNotFoundException.class,
                () -> giftCertificateService.deleteById(LARGE_ID)
        );
    }

    @Test
    public void testDeleteByIdWrongIdValidatorException() {
        Assertions.assertThrows(
                GiftCertificateValidatorException.class,
                () -> giftCertificateService.deleteById(WRONG_ID)
        );
    }

    @Test
    public void testUpdateThanNewDescription() {
        giftCertificate1.setDescription(UPDATE_DESCRIPTION);
        giftCertificateService.update(giftCertificate1);
        GiftCertificate byId = giftCertificateService.findById(FIRST_ID);
        Assertions.assertEquals(UPDATE_DESCRIPTION, byId.getDescription());

    }

    @Test
    public void testUpdateThanNewTagsCheck() {
        log.debug("giftCertificate1.getTags() = {}", giftCertificate1.getTags());
        Tag remove = giftCertificate1.getTags().remove(0);
        giftCertificate1.getTags().add(tag3);
        log.debug("remove = {}", remove);
        giftCertificateService.update(giftCertificate1);
        GiftCertificate byId = giftCertificateService.findById(FIRST_ID);
        Assertions.assertEquals(2, byId.getTags().size());
        log.debug("byId.getTags() = {}", byId.getTags());
    }


}