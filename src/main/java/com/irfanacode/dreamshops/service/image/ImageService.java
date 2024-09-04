package com.irfanacode.dreamshops.service.image;

import com.irfanacode.dreamshops.dto.ImageDto;
import com.irfanacode.dreamshops.exceptions.ResourceNotFoundException;
import com.irfanacode.dreamshops.model.Image;
import com.irfanacode.dreamshops.model.Product;
import com.irfanacode.dreamshops.repository.ImageRepository;
import com.irfanacode.dreamshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{
    private final ImageRepository imageRepository;
    private final IProductService  productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No  image not found with "+id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,() ->{
            throw new ResourceNotFoundException("No  image not found with "+id);});

    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImageDtos =  new ArrayList<>();
        for(MultipartFile file: files){
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);
                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage = imageRepository.save(image);

                savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
                imageRepository.save(savedImage);

                ImageDto  imageDto = new ImageDto();
                imageDto.setId(savedImage.getId());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                imageDto.setFileName(savedImage.getFileName());
                savedImageDtos.add(imageDto);

            } catch (SQLException | IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDtos;
    }

    @Override
    public void updateImage(Long imageId, MultipartFile file) {
        Image image = getImageById(imageId);
        try {
            image.setImage(new SerialBlob(file.getBytes()));
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
