package ac.yongson.artgram.domain.notice.service.impl;

import ac.yongson.artgram.domain.notice.dto.NoticeRequestDTO;
import ac.yongson.artgram.domain.notice.entity.Notice;
import ac.yongson.artgram.domain.notice.repository.NoticeRepository;
import ac.yongson.artgram.domain.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;

    @Override
    public List<Notice> getNotices(){
        return noticeRepository.findAll();
    }

    @Override
    public void saveNotice(NoticeRequestDTO.SaveNotice saveNotice){
        noticeRepository.save(saveNotice.toEntity());
    }

    @Override
    public void deleteNotice(Long noticeId){
        noticeRepository.deleteById(noticeId);
    }
}
