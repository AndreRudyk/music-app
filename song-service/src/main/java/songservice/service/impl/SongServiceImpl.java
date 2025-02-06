package songservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import request.song.SongMetadataRequest;
import songservice.client.ResourceClient;
import songservice.converter.SongConverter;
import songservice.entity.SongMetadataEntity;
import songservice.exception.SongAlreadyExists;
import songservice.exception.SongMatadataNotFound;
import songservice.repository.SongMetadataRepository;
import songservice.service.SongService;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SongServiceImpl implements SongService {

    private final SongMetadataRepository repository;

    private final SongConverter converter;

    private final ResourceClient client;

    @Override
    public SongMetadataEntity createSongMetadata(SongMetadataRequest request) {
        if (repository.existsById(Integer.valueOf(request.getId()))) {
            log.error("Song with id {} already exists", request.getId());
            throw new SongAlreadyExists(String.format("Resource with ID=%s already exists", request.getId()));
        }
        return repository.save(converter.convert(request));
    }

    @Override
    public SongMetadataEntity getSongMetadata(Integer id) {
        if (!client.resourceExists(id)) {
            log.error("Song not found, id: {}", id);
            repository.deleteAllByIdInBatch(List.of(id));
            throw new SongMatadataNotFound(String.format("Resource with ID=%s not found", id));
        }
        return repository.findById(id)
                .orElseThrow(() ->
                        new SongMatadataNotFound((String.format("Resource with ID=%s not found", id))));
    }

    @Override
    public List<Integer> deleteSongMetadata(List<Integer> ids) {
        List<Integer> existingIds = ids.stream()
                        .filter(repository::existsById)
                        .toList();
        repository.deleteAllByIdInBatch(existingIds);
        return existingIds;
    }
}
