package com.epam.mjc.api.dao;

import com.epam.mjc.api.domain.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepo extends PagingAndSortingRepository<Tag, Long> {

    @Query("select tt from tag tt where tt.name in :names")
    List<Tag> findAllExistingByNames(@Param("names") List<String> names);

    Optional<Tag> findByName(String name);

    @Query(value =
            "with rich_user(id, price) as\n" +
            "         (select usr.id, sum(o.price) as sm\n" +
            "          from usr\n" +
            "                   inner join orders o on usr.id = o.user_id\n" +
            "          group by usr.id\n" +
            "          order by sm desc\n" +
            "          limit 1)\n" +
            "   ,\n" +
            "     tag_count_usages(id, cnt) as\n" +
            "    (select tag.id, sum(gco.count)\n" +
            "     from tag\n" +
            "              inner join gift_certificate_tag gct on tag.id = gct.tag_id\n" +
            "              inner join gift_certificate gc on gct.gift_certificate_id = gc.id\n" +
            "              inner join gift_certificate_order gco on gc.id = gco.gift_certificate_id\n" +
            "              inner join orders o2 on gco.order_id = o2.id\n" +
            "              inner join usr u on o2.user_id = u.id\n" +
            "     where u.id = (select id from rich_user)\n" +
            "     group by tag.id\n" +
            "     order by sum(gco.count) desc\n" +
            "     limit 1)\n" +
            " select tag.id, tag.name from tag where tag.id = (select tag_count_usages.id from tag_count_usages)",
            nativeQuery = true
    )
    Tag findMostPopularTagIdOfUserHigherCostOrders();
}
