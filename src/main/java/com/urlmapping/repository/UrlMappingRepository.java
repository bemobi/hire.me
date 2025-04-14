package com.urlmapping.repository;


import com.urlmapping.entities.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Integer> {

    Optional<UrlMapping> findByAlias(String alias);

    @Query(value = "SELECT original_url AS originalURL, SUM(clicks) AS totalClicks " +
            "FROM url_mapping " +
            "GROUP BY original_url " +
            "ORDER BY totalClicks DESC " +
            "LIMIT 10", nativeQuery = true)
    List<Object[]> findTop10MostClickedGroupedByOriginalURL();









}
