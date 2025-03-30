package ac.yongson.artgram.domain.title.service.impl;

import ac.yongson.artgram.domain.title.entity.Title;
import ac.yongson.artgram.domain.title.repository.TitleRepository;
import ac.yongson.artgram.domain.title.service.TitleService;
import ac.yongson.artgram.global.exception.Exception500;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TitleServiceImpl implements TitleService {

    private final TitleRepository titleRepository;

    @Override
    public Title getTitle() {
        return titleRepository.findById(1L).get();
    }

    @Override
    @Transactional
    public void patchTitle(String title){
        Title titlePS = getTitle();
        try{
            titlePS.patchTitle(title);
        }catch (Exception e){
            throw new Exception500("title", "타이틀 수정 실패");
        }

    }
}
