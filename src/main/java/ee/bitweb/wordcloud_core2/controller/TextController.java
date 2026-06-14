package ee.bitweb.wordcloud_core2.controller;

import ee.bitweb.wordcloud_core2.dto.JobResultResponse;
import ee.bitweb.wordcloud_core2.service.TextService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/texts")
public class TextController {

    private final TextService textService;

    public TextController(TextService textService) {
        this.textService = textService;
    }

    @PostMapping
    public ResponseEntity<Map<String, UUID>> submit(@RequestParam("file") MultipartFile file) throws IOException {
        UUID id = textService.submit(file);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("id", id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResultResponse> getResult(@PathVariable UUID id) {
        return ResponseEntity.ok(textService.getResult(id));
    }
}
