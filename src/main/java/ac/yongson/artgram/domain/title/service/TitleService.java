package ac.yongson.artgram.domain.title.service;

import ac.yongson.artgram.domain.title.entity.Title;

public interface TitleService {
    Title getTitle();
    void patchTitle(String title);
}
