package songservice.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ResourceClient {

    private final RestTemplate restTemplate;

    @Value("${resource-service.baseUrl}")
    private String baseUrl;

    public Boolean resourceExists(Integer id) {
        String url = baseUrl + id + "/exists";
        return restTemplate.getForObject(url, Boolean.class);
    }
}
