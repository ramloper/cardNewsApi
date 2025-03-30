package ac.yongson.artgram.domain.title.controller;

import ac.yongson.artgram.domain.title.service.TitleService;
import ac.yongson.artgram.global.dto.ResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class TitleController {
    private final TitleService titleService;
    @GetMapping("auth/title")
    public ResponseEntity<?> getTitle() {
        return ResponseEntity.ok().body(new ResponseDTO<>(titleService.getTitle()));
    }
    @PatchMapping("admin/title")
    public ResponseEntity<?> putTitle(@RequestParam String title) {
        titleService.patchTitle(title);
        return ResponseEntity.ok().body(new ResponseDTO<>("안내 멘트 수정 성공"));
    }
}
