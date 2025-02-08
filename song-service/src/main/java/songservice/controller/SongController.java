package songservice.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import request.song.SongMetadataRequest;
import response.song.SongMetadataResponse;
import songservice.converter.SongConverter;
import songservice.entity.SongMetadataEntity;
import songservice.response.DeleteSongResponse;
import songservice.service.SongService;
import validation.ValidIdsCsv;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/api/v1/songs")
public class SongController {

    private final SongService songService;

    private final SongConverter songConverter;

    @ResponseStatus(OK)
    @PostMapping
    public SongMetadataResponse saveSongMetadata(@Valid @RequestBody SongMetadataRequest request) {
        SongMetadataEntity songMetadata = songService.createSongMetadata(request);
        return songConverter.convert(songMetadata);
    }

    @ResponseStatus(OK)
    @GetMapping("/{id}")
    public SongMetadataResponse getSongMetadata(@PathVariable Integer id) {
        return songConverter.convert(songService.getSongMetadata(id));
    }

    @ResponseStatus(OK)
    @DeleteMapping
    public DeleteSongResponse deleteSongMetadata(@RequestParam("id") @ValidIdsCsv(maxLength = 100) String ids) {
        List<Integer> deletedIds = songService.deleteSongMetadata(ids);
        return DeleteSongResponse.builder()
                .ids(deletedIds)
                .build();
    }
}
