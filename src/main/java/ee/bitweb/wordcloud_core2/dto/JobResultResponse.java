package ee.bitweb.wordcloud_core2.dto;

import java.util.List;
import java.util.UUID;

public record JobResultResponse(UUID id, String status, List<WordCountDto> words) {
}
