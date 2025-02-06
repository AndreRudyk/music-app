package resourceservice.client;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import request.song.SongMetadataRequest;
import response.song.SongMetadataResponse;
import java.util.List;

import static org.apache.commons.collections4.CollectionUtils.isEmpty;

@Slf4j
@Service
@RequiredArgsConstructor
public class SongServiceClient {

    private final RestTemplate restTemplate;

    @Value("${song-service.baseUrl}")
    private String baseUrl;

    public void saveSongMetadata(SongMetadataRequest request) {
        if (request == null) {
            log.warn("Skipping create song call as request is null");
            return;
        }
        restTemplate.postForEntity(baseUrl, request, SongMetadataResponse.class);
    }

    public void deleteSongMetadata(List<Integer> ids) {
        if (isEmpty(ids)) {
            log.warn("Skipping removing song metadata, ids list is empty");
            return;
        }
        StringBuilder url = new StringBuilder(baseUrl);
        url.append("?id=");
        ids.forEach(id -> url.append(id).append("&"));
        restTemplate.delete(url.toString());
    }
}
