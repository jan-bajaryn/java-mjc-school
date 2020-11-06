package com.epam.mjc.core.dao.builder;

import com.epam.mjc.api.util.sort.SortParam;
import com.epam.mjc.api.util.sort.SortParams;
import com.epam.mjc.api.util.SearchParams;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class SearchQueryBuilder {

    // language=SQL
    private static final String DEFAULT_WITH_INNER_JOIN = "SELECT gift_certificate.id as id,gift_certificate.name as name,description,price,createDate,lastUpdateDate,duration FROM gift_certificate INNER JOIN gift_certificate_tag ON gift_certificate.id = gift_certificate_tag.gift_certificate_id INNER JOIN tag ON gift_certificate_tag.tag_id = tag.id ";
    // language=SQL
    private static final String DEFAULT_WITHOUT_INNER_JOIN = "SELECT gift_certificate.id as id,gift_certificate.name as name,description,price,createDate,lastUpdateDate,duration FROM gift_certificate ";

    private String tagName;
    private String partName;
    private String partDescription;
    private SortParams sortParams;

    private StringBuilder sb;

    boolean whereInserted;

    private SearchQueryBuilder() {
    }

    public SearchQueryBuilder searchParams(SearchParams searchParams) {
        this.tagName = searchParams.getTagName();
        this.partName = searchParams.getPartName();
        this.partDescription = searchParams.getPartDescription();
        this.sortParams = searchParams.getSortParams();
        return this;
    }

    public String build() {

        tagName();
        partName();
        partDescription();
        sortParams();

        return sb.toString();
    }

    private void tagName() {
        if (tagName != null) {
            sb = new StringBuilder(DEFAULT_WITH_INNER_JOIN);
            insertWhereOrAnd();
            sb.append(" tag.name = ").append(QueryParser.escape(this.tagName));
        }
        sb = new StringBuilder(DEFAULT_WITHOUT_INNER_JOIN);
    }

    private void insertWhereOrAnd() {
        if (whereInserted) {
            sb.append(" AND ");
        } else {
            sb.append(" WHERE ");
        }
    }

    private void partName() {
        if (this.partName != null) {
            insertWhereOrAnd();
            sb.append(" gift_certificate.name LIKE '%").append(QueryParser.escape(this.partName)).append("%' ");
        }
    }

    private void partDescription() {
        if (this.partDescription != null) {
            insertWhereOrAnd();
            sb.append(" gift_certificate.description LIKE '%").append(QueryParser.escape(this.partDescription)).append("%' ");
        }
    }

    private void sortParams() {
        if (this.sortParams != null) {
            sb.append(" ORDER BY ");
            for (SortParam sortParam : sortParams.getSortParams()) {
                sb.append(sortParam.getFieldName().getColumnName()).append(" ");
                if (sortParam.isAsc()) {
                    sb.append(" ASC ");
                } else {
                    sb.append(" DESC ");
                }
            }

        }
    }


    public static SearchQueryBuilder builder() {
        return new SearchQueryBuilder();
    }
}
