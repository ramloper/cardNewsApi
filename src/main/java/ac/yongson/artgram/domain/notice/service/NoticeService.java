package ac.yongson.artgram.domain.notice.service;

import ac.yongson.artgram.domain.notice.dto.NoticeRequestDTO;
import ac.yongson.artgram.domain.notice.entity.Notice;

import java.util.List;

public interface NoticeService {
    List<Notice> getNotices();
    void saveNotice(NoticeRequestDTO.SaveNotice saveNotice);
}
