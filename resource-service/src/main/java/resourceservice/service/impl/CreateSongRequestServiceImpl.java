package resourceservice.service.impl;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import request.song.SongMetadataRequest;
import resourceservice.service.CreateSongRequestService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class CreateSongRequestServiceImpl implements CreateSongRequestService {

    @Override
    public SongMetadataRequest extractMetadata(byte[] songData, Integer id) throws IOException, TikaException, SAXException {
        try (InputStream inputstream = new ByteArrayInputStream(songData)) {
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            ParseContext pcontext = new ParseContext();

            Mp3Parser mp3Parser = new Mp3Parser();
            mp3Parser.parse(inputstream, handler, metadata, pcontext);

            SongMetadataRequest songMetadata = new SongMetadataRequest();
            songMetadata.setId(String.valueOf(id));
            songMetadata.setName(metadata.get("title") != null ? metadata.get("title") : "Unknown Title");
            songMetadata.setArtist(metadata.get("xmpDM:artist") != null ? metadata.get("xmpDM:artist") : "Unknown Artist");
            songMetadata.setAlbum(metadata.get("xmpDM:album") != null ? metadata.get("xmpDM:album") : "Unknown Album");
            songMetadata.setDuration(formatDuration(metadata.get("xmpDM:duration")));
            songMetadata.setYear(metadata.get("xmpDM:releaseDate"));

            return songMetadata;
        }
    }

    private String formatDuration(String rawDuration) {
        if (rawDuration != null && !rawDuration.isEmpty()) {
            try {
                double durationSec = Double.parseDouble(rawDuration);
                int minutes = (int) (durationSec / 60);
                int seconds = (int) (durationSec % 60);
                return String.format("%02d:%02d", minutes, seconds);
            } catch (NumberFormatException e) {
                System.err.println("Invalid duration format");
            }
        }
        return "00:00";
    }
}
