package ac.yongson.artgram.domain.image.service;

import ac.yongson.artgram.domain.image.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image registerImage(MultipartFile file);
    List<Image> registerImages(List<MultipartFile> files);
    void deleteImage(Image image);
    void deleteImages(List<Image> images);
    List<Image> getImages(List<Long> imageIds);
}
