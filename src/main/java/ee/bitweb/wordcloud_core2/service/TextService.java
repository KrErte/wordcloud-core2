package ee.bitweb.wordcloud_core2.service;

import ee.bitweb.wordcloud_core2.dto.JobResultResponse;
import ee.bitweb.wordcloud_core2.dto.WordCountDto;
import ee.bitweb.wordcloud_core2.entity.JobStatus;
import ee.bitweb.wordcloud_core2.entity.TextJob;
import ee.bitweb.wordcloud_core2.messaging.TextChunkMessage;
import ee.bitweb.wordcloud_core2.repository.TextJobRepository;
import ee.bitweb.wordcloud_core2.repository.WordCountRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TextService {

    private final TextJobRepository textJobRepository;
    private final WordCountRepository wordCountRepository;
    private final RabbitTemplate rabbitTemplate;

    @Value("${wordcloud.rabbitmq.exchange}")
    private String exchange;

    @Value("${wordcloud.rabbitmq.routing-key}")
    private String routingKey;

    public TextService(TextJobRepository textJobRepository,
                       WordCountRepository wordCountRepository,
                       RabbitTemplate rabbitTemplate) {
        this.textJobRepository = textJobRepository;
        this.wordCountRepository = wordCountRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public UUID submit(MultipartFile file) throws IOException {
        TextJob job = new TextJob();
        job.setId(UUID.randomUUID());
        job.setStatus(JobStatus.PENDING);
        job.setFilename(file.getOriginalFilename());
        job.setCreatedAt(LocalDateTime.now());
        textJobRepository.save(job);

        String content = new String(file.getBytes());
        rabbitTemplate.convertAndSend(exchange, routingKey,
                new TextChunkMessage(job.getId(), job.getFilename(), content));

        return job.getId();
    }

    public JobResultResponse getResult(UUID id) {
        TextJob job = textJobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found: " + id));

        List<WordCountDto> words = wordCountRepository
                .findByJobIdOrderByCountDesc(id)
                .stream()
                .map(wc -> new WordCountDto(wc.getWord(), wc.getCount()))
                .toList();

        return new JobResultResponse(job.getId(), job.getStatus().name(), words);
    }
}
