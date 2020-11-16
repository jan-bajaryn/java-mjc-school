package com.epam.mjc.api.service.help;

import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.domain.Tag;

import java.util.List;
import java.util.Optional;

/**
 * Service interface to work with {@link Tag}
 * Gives abilities for CRD operations with {@link Tag}
 */
public interface TagService {
    /**
     * @return List of all {@link Tag} from database
     */
    List<Tag> findAll();

    /**
     * Create {@link GiftCertificate} by name in database and return created {@link GiftCertificate}
     *
     * @param name name of{@link GiftCertificate} to create in the database
     * @throws com.epam.mjc.api.service.exception.TagAlreadyExistsException if
     *                                                                      it already exists in database
     * @throws com.epam.mjc.api.service.exception.TagValidatorException     if
     *                                                                      something wrong with parameter in input
     */
    Tag createByName(String name);

    /**
     * @param id identifier of {@link Tag}
     * @throws com.epam.mjc.api.service.exception.TagNotFoundException  if there not GiftCertificate with so id
     * @throws com.epam.mjc.api.service.exception.TagValidatorException if id is incorrect
     */
    boolean deleteById(Long id);

    /**
     * @param id identifier of {@link Tag}
     * @return entity from database identified by id, or throws
     * @throws com.epam.mjc.api.service.exception.TagNotFoundException  if there no entity with so id
     * @throws com.epam.mjc.api.service.exception.TagValidatorException if id is incorrect
     */
    Tag findById(Long id);

    /**
     * Find tags with names, or create them in database
     *
     * @param tags list of {@link GiftCertificate} to find or create
     * @return list of {@link Tag} all created in the database with ids
     */
    List<Tag> findOrCreateAll(List<Tag> tags);

    /**
     * @param name of {@link Tag}
     * @return Optional with entity from database with so name or empty {@link Optional#empty()}
     * if there not so entity in database
     * @throws com.epam.mjc.api.service.exception.TagValidatorException if name is incorrect
     */
    Optional<Tag> findByTagName(String name);
}
