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

import java.util.Arrays;
import java.util.List;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/api/v1/songs")
public class SongController {

    private final SongService songService;

    private final SongConverter songConverter;

    @PostMapping
    public SongMetadataResponse saveSongMetadata(@Valid @RequestBody SongMetadataRequest request) {
        SongMetadataEntity songMetadata = songService.createSongMetadata(request);
        return songConverter.convert(songMetadata);
    }

    @GetMapping("/{id}")
    public SongMetadataResponse getSongMetadata(@PathVariable Integer id) {
        return songConverter.convert(songService.getSongMetadata(id));
    }

    @DeleteMapping
    public DeleteSongResponse deleteSongMetadata(@RequestParam("id") @ValidIdsCsv(maxLength = 100) String ids) {
        List<Integer> parsedIds = Arrays.stream(ids.split(",")).map(Integer::parseInt).toList();
        List<Integer> deletedIds = songService.deleteSongMetadata(parsedIds);
        return DeleteSongResponse.builder()
                .ids(deletedIds)
                .build();
    }
}
