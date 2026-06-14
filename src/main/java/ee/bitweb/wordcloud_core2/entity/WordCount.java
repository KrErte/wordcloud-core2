package ee.bitweb.wordcloud_core2.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "word_count")
public class WordCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_id", nullable = false)
    private UUID jobId;

    @Column(nullable = false)
    private String word;

    @Column(nullable = false)
    private Integer count;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UUID getJobId() { return jobId; }
    public void setJobId(UUID jobId) { this.jobId = jobId; }

    public String getWord() { return word; }
    public void setWord(String word) { this.word = word; }

    public Integer getCount() { return count; }
    public void setCount(Integer count) { this.count = count; }
}
