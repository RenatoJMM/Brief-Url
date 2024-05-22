package com.briefurl.urlshortener.repository;

import com.briefurl.urlshortener.model.Url;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByUrl(String url);

    Optional<Url> findByShortKey(String shortKey);

    @Modifying
    @Query(value = "DELETE FROM url WHERE short_Key = :key", nativeQuery = true)
    void deleteByKey(String key);
}
