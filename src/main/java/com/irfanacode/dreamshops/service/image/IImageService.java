package com.irfanacode.dreamshops.service.image;

import com.irfanacode.dreamshops.dto.ImageDto;
import com.irfanacode.dreamshops.model.Image;
import com.irfanacode.dreamshops.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files, Long productId);
    void updateImage(Long imageId, MultipartFile image);

}
