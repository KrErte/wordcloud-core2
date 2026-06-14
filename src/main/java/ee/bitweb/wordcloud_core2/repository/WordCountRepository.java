package ee.bitweb.wordcloud_core2.repository;

import ee.bitweb.wordcloud_core2.entity.WordCount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface WordCountRepository extends JpaRepository<WordCount, Long> {
    List<WordCount> findByJobIdOrderByCountDesc(UUID jobId);
}
