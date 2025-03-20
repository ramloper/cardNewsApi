package ac.yongson.artgram.domain.image.service;

import ac.yongson.artgram.domain.image.entity.Image;
import ac.yongson.artgram.domain.image.repository.ImageRepository;
import ac.yongson.artgram.global.service.S3ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final S3ImageService s3ImageService;

    @Override
    public Image registerImage(MultipartFile file){
        String imageUrl = s3ImageService.upload(file);
        return imageRepository.save(new Image(imageUrl));
    }
    @Override
    public List<Image> registerImages(List<MultipartFile> files){
        List<String> imageUrls = s3ImageService.uploadMultipleImages(files);
        List<Image> images = imageUrls.stream().map(Image::new).toList();
        return imageRepository.saveAll(images);
    }
    @Override
    @Transactional
    public void deleteImage(Image image){
        imageRepository.delete(image);
        s3ImageService.deleteImageFromS3(image.getImageUrl());
    }
    @Override
    public void deleteImages(List<Image> images){
        imageRepository.deleteAll(images);
        s3ImageService.deleteMultipleImagesFromS3(images.stream().map(Image::getImageUrl).toList());
    }

    @Override
    public List<Image> getImages(List<Long> imageIds){
        return imageRepository.findAllById(imageIds);
    }
}
