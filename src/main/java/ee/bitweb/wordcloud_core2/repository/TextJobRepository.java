package ee.bitweb.wordcloud_core2.repository;

import ee.bitweb.wordcloud_core2.entity.TextJob;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TextJobRepository extends JpaRepository<TextJob, UUID> {
}
