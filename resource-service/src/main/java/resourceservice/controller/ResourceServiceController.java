package resourceservice.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import resourceservice.entity.ResourceEntity;
import resourceservice.service.ResourceService;
import resourceservice.validation.PositiveInteger;
import response.resource.DeleteResourceResponse;
import response.resource.ResourceResponse;
import validation.ValidIdsCsv;
import java.util.List;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ResourceServiceController {

    private final ResourceService resourceService;

    @PostMapping(path =  "/resources", consumes = "audio/mpeg")
    public ResourceResponse uploadResource(@RequestBody byte[] file) {
        ResourceEntity uploadedEntity = resourceService.saveResource(file);
        return new ResourceResponse(uploadedEntity.getId());
    }

    @GetMapping(path = "/resources/{id}", produces = "audio/mpeg")
    public ResponseEntity<byte[]> getResource(@PathVariable @Valid @PositiveInteger String id) {
        ResourceEntity entity = resourceService.getResourceById(Integer.parseInt(id));
        String fileName = entity.getId() + ".mp3";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ fileName+ "\"");
        httpHeaders.setContentLength(entity.getBytes().length);
        httpHeaders.setContentType(MediaType.parseMediaType("audio/mpeg"));
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(entity.getBytes());
    }

    @DeleteMapping(path = "/resources", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeleteResourceResponse deleteResource(@RequestParam("id") @ValidIdsCsv(maxLength = 100) String ids) {
        List<Integer> deletedIds = resourceService.deleteByIds(ids);
        return DeleteResourceResponse.builder()
                .ids(deletedIds)
                .build();
    }

    @GetMapping(path = "/resources/{id}/exists")
    public boolean existsResource(@PathVariable @Valid @PositiveInteger String id) {
        return resourceService.resourceExists(id);
    }
}
