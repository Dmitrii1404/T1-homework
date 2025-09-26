package my.project.clientProcessing.mapper;

import my.project.clientProcessing.dto.ProductCreateDto;
import my.project.clientProcessing.dto.ProductResponseDto;
import my.project.clientProcessing.dto.ProductUpdateDto;
import my.project.clientProcessing.entity.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    Product toEntity(ProductCreateDto productCreateDto);

    void updateFromDto(ProductUpdateDto productUpdateDto, @MappingTarget Product product);

    ProductResponseDto toDto(Product product);

}
