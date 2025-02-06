package resourceservice.service;

import org.springframework.web.multipart.MultipartFile;
import resourceservice.entity.ResourceEntity;

import java.util.List;

public interface ResourceService {

    ResourceEntity saveResource(byte[] file);

    ResourceEntity getResourceById(Integer id);

    List<Integer> deleteByIds(String ids);

    boolean resourceExists(String id);
}
