package ee.bitweb.wordcloud_core2.messaging;

import java.util.UUID;

public record TextChunkMessage(UUID jobId, String filename, String content) {
}
