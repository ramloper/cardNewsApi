package ac.yongson.artgram.domain.notice.controller;

import ac.yongson.artgram.domain.notice.dto.NoticeRequestDTO;
import ac.yongson.artgram.domain.notice.service.NoticeService;
import ac.yongson.artgram.global.dto.ResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping("notice/list")
    public ResponseEntity<?> getNoticeList() {
        return ResponseEntity.ok().body(new ResponseDTO<>(noticeService.getNotices()));
    }

    @PostMapping("admin/notice")
    public ResponseEntity<?> postNotice(@RequestBody NoticeRequestDTO.SaveNotice saveNotice) {
        noticeService.saveNotice(saveNotice);
        return ResponseEntity.ok().body(new ResponseDTO<>("공지사항 등록 성공"));
    }
}
