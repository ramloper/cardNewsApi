package ac.yongson.artgram.global.service;

import ac.yongson.artgram.global.exception.Exception400;
import ac.yongson.artgram.global.exception.Exception500;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

@Service
@RequiredArgsConstructor
public class S3ImageService {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    /**
     * 단일 이미지 업로드
     */
    public String upload(MultipartFile image) {
        if(image.isEmpty() || Objects.isNull(image.getOriginalFilename())){
            throw new Exception400("images", "업로드할 이미지 없음");
        }
        return this.uploadImage(image);
    }
    /**
     * 다중 이미지 업로드
     */
    public List<String> uploadMultipleImages(List<MultipartFile> images) {
        if (images == null || images.isEmpty()) {
            throw new Exception400("images", "업로드할 이미지 없음");
        }

        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile image : images) {
            imageUrls.add(uploadImage(image));  // 개별 이미지 업로드 후 URL 리스트에 추가
        }
        return imageUrls;
    }
    private String uploadImage(MultipartFile image) {
        validateImageFileExtention(image.getOriginalFilename());
        try {
            return uploadImageToS3(image);
        } catch (IOException e) {
            throw new Exception500("images", "이미지 업로드 실패");
        }
    }


    private void validateImageFileExtention(String filename) {
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new Exception400("images", "파일 확장자 없음");
        }

        String extention = filename.substring(lastDotIndex + 1).toLowerCase();
        List<String> allowedExtentionList = Arrays.asList("jpg", "jpeg", "png", "gif");

        if (!allowedExtentionList.contains(extention)) {
            throw new Exception400("images", "업로드 가능한 이미지는 jpg,jpeg,png,gif만 가능합니다.");
        }
    }

    private String uploadImageToS3(MultipartFile image) throws IOException {
        String originalFilename = image.getOriginalFilename(); //원본 파일 명
        String extention = originalFilename.substring(originalFilename.lastIndexOf(".")); //확장자 명

        String s3FileName = UUID.randomUUID().toString().substring(0, 10) + originalFilename; //변경된 파일 명

        InputStream is = image.getInputStream();
        byte[] bytes = IOUtils.toByteArray(is);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/" + extention);
        metadata.setContentLength(bytes.length);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        try{
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(bucketName, s3FileName, byteArrayInputStream, metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(putObjectRequest); // put image to S3
        }catch (Exception e){
            throw new Exception500("images", "이미지 업로드 실패");
        }finally {
            byteArrayInputStream.close();
            is.close();
        }

        return amazonS3.getUrl(bucketName, s3FileName).toString();
    }

    public void deleteImageFromS3(String imageAddress){
        String key = getKeyFromImageAddress(imageAddress);
        try{
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
        }catch (Exception e){
            throw new Exception500("images", "이미지 삭제 실패");
        }
    }
    /**
     * 여러 개의 이미지 삭제
     */
    public void deleteMultipleImagesFromS3(List<String> imageAddresses) {
        for (String imageAddress : imageAddresses) {
            deleteImageFromS3(imageAddress);
        }
    }

    private String getKeyFromImageAddress(String imageAddress){
        try{
            URL url = new URL(imageAddress);
            String decodingKey = URLDecoder.decode(url.getPath(), "UTF-8");
            return decodingKey.substring(1); // 맨 앞의 '/' 제거
        }catch (Exception e){
            throw new Exception500("images", "이미지 삭제 실패");
        }
    }
}
