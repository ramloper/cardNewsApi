package ac.yongson.artgram.domain.image.controller;

import ac.yongson.artgram.domain.board.dto.BoardRequestDTO;
import ac.yongson.artgram.domain.image.service.ImageService;
import ac.yongson.artgram.global.dto.ResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    @GetMapping("image/list")
    public ResponseEntity<?> saveBoard(@RequestParam(name = "imageIds")List<Long> imageIds, HttpServletRequest request) {

        return ResponseEntity.ok().body(new ResponseDTO<>(imageService.getImages(imageIds)));
    }
}
