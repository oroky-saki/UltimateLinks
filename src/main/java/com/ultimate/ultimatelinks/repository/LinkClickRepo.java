package com.ultimate.ultimatelinks.repository;

import com.ultimate.ultimatelinks.dto.ClickStatsDto;
import com.ultimate.ultimatelinks.entities.LinkClickEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LinkClickRepo extends CrudRepository<LinkClickEntity, Long> {


    Long countByLinkId(Long linkID);

    @Query(value = """
                   SELECT new com.ultimate.ultimatelinks.dto.ClickStatsDto(to_char(lc.clickTimestamp, 'DD-Mon-YYYY'), COUNT (*))
                   FROM LinkClickEntity as lc
                   WHERE lc.link.id=:link_id
                   GROUP BY to_char(lc.clickTimestamp, 'DD-Mon-YYYY')
                   ORDER BY to_char(lc.clickTimestamp, 'DD-Mon-YYYY')
                   """
    )
    List<ClickStatsDto> statsByDays(@Param("link_id") Long linkID);

    @Query(value = """
                   SELECT new com.ultimate.ultimatelinks.dto.ClickStatsDto(to_char(lc.clickTimestamp, 'DD-Mon-YYYY : HH24'), COUNT (*))
                   FROM LinkClickEntity as lc
                   WHERE lc.link.id=:link_id AND to_char(lc.clickTimestamp, 'DD-mm-YYYY') = :date
                   GROUP BY to_char(lc.clickTimestamp, 'DD-Mon-YYYY : HH24')
                   ORDER BY to_char(lc.clickTimestamp, 'DD-Mon-YYYY : HH24')
                   """
    )
    List<ClickStatsDto> statsByHourOnDate(@Param("link_id") Long linkID, @Param("date") String date);

    @Query(value = """
                   SELECT new com.ultimate.ultimatelinks.dto.ClickStatsDto(to_char(lc.clickTimestamp, 'DD-Mon-YYYY : HH24-mm'), COUNT (*))
                   FROM LinkClickEntity as lc
                   WHERE lc.link.id=:link_id AND to_char(lc.clickTimestamp, 'DD-mm-YYYY') = :date
                   GROUP BY to_char(lc.clickTimestamp, 'DD-Mon-YYYY : HH24-mm')
                   ORDER BY to_char(lc.clickTimestamp, 'DD-Mon-YYYY : HH24-mm')
                   """
    )
    List<ClickStatsDto> statsByMinuteOnDate(@Param("link_id") Long linkID, @Param("date") String date);

    @Query(value = """
                   SELECT new com.ultimate.ultimatelinks.dto.ClickStatsDto(subquery.site, SUM(subquery.clicks))
                   FROM ( SELECT COUNT(*) as clicks, lc.link.site as site, lc.link.id AS link_id
                          FROM LinkClickEntity as lc
                          INNER JOIN LinkEntity as l ON lc.link.id = l.id
                          GROUP BY site, link_id
                   ) as subquery
                   GROUP BY subquery.site
                   ORDER BY SUM(subquery.clicks) DESC, subquery.site
                   LIMIT 20
                   """
    )
    List<ClickStatsDto> getPopularLinks();
}
