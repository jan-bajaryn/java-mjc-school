package com.epam.mjc.api.service.help;

import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.util.SearchParams;

import java.util.List;

/**
 * Service interface to work with {@link GiftCertificate}
 * Gives abilities for CRUD operations with {@link GiftCertificate}
 */
public interface GiftCertificateService {

    /**
     * @return List of all {@link GiftCertificate} from database
     */
    List<GiftCertificate> findAll();

    /**
     * @param id identifier of {@link GiftCertificate}
     * @return entity from database identified by id, or throws
     * {@link com.epam.mjc.api.service.exception.GiftCertificateNotFoundException} if
     * there no entity with so id
     * @throws com.epam.mjc.api.service.exception.GiftCertificateNotFoundException  if there no entity with so id
     * @throws com.epam.mjc.api.service.exception.GiftCertificateValidatorException if id is incorrect
     */
    GiftCertificate findById(Long id);


    /**
     * Create {@link GiftCertificate} in database and return created {@link GiftCertificate}
     *
     * @param giftCertificate {@link GiftCertificate} to create in the database
     * @throws com.epam.mjc.api.service.exception.GiftCertificateAlreadyExists      if
     *                                                                              it already exists in database
     * @throws com.epam.mjc.api.service.exception.GiftCertificateValidatorException if
     *                                                                              something wrong with parameter in input
     */
    GiftCertificate create(GiftCertificate giftCertificate);

    /**
     * @param id identifier of {@link GiftCertificate}
     * @throws com.epam.mjc.api.service.exception.GiftCertificateNotFoundException  if there not GiftCertificate with so id
     * @throws com.epam.mjc.api.service.exception.GiftCertificateValidatorException if id is incorrect
     */
    void deleteById(Long id);

    /**
     * @param toGiftCertificate {@link GiftCertificate} entity to update
     * @throws com.epam.mjc.api.service.exception.GiftCertificateNotFoundException  if there not GiftCertificate in base to update
     * @throws com.epam.mjc.api.service.exception.GiftCertificateValidatorException if field of giftCertificate is incorrect
     */
    void update(GiftCertificate toGiftCertificate);


    /**
     * Search {@link GiftCertificate} by params in database
     *
     * @param searchParams {@link SearchParams} parameters to search {@link GiftCertificate}
     *                     from database
     */
    List<GiftCertificate> search(SearchParams searchParams);
}
