package cart.domain.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import cart.domain.product.dao.ProductDao;
import cart.dto.ProductCreateRequest;
import cart.dto.ProductResponse;
import cart.dto.ProductUpdateRequest;
import cart.domain.product.entity.Product;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("상품을 생성한다.")
    public void testCreate() {
        //given
        final LocalDateTime now = LocalDateTime.now();
        final Product savedProduct = new Product(1L, "name", 1000, "imgUrl", now, now);
        given(productDao.save(any()))
            .willReturn(savedProduct);

        //when
        final ProductResponse result = productService.create(
            new ProductCreateRequest("name", 1000, "imageUrl"));

        //then
        assertThat(result.getId()).isEqualTo(savedProduct.getId());
        assertThat(result.getName()).isEqualTo(savedProduct.getName());
        assertThat(result.getPrice()).isEqualTo(savedProduct.getPrice());
        assertThat(result.getImageUrl()).isEqualTo(savedProduct.getImageUrl());
        assertThat(result.getCreatedAt()).isEqualTo(savedProduct.getCreatedAt());
        assertThat(result.getUpdatedAt()).isEqualTo(savedProduct.getUpdatedAt());
    }

    @Test
    @DisplayName("모든 상품을 조회한다.")
    public void testFindAll() {
        //given
        final LocalDateTime now = LocalDateTime.now();
        final List<Product> products = List.of(
            new Product(1L, "name1", 1000, "imgUrl1", now, now),
            new Product(2L, "name2", 2000, "imgUrl2", now, now));
        given(productDao.findAll()).willReturn(products);

        //when
        final List<ProductResponse> result = productService.findAll();

        //then
        assertThat(result.size()).isEqualTo(products.size());
    }

    @Test
    @DisplayName("존재하지 않는 상품을 수정한다.")
    public void testUpdateNotExistProduct() {
        //given
        given(productDao.update(any())).willReturn(0);

        //when + then
        assertThatThrownBy(
            () -> productService.update(new ProductUpdateRequest(1L, "name", 2000, "image_url")))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품을 수정한다.")
    public void testUpdate() {
        //given
        given(productDao.update(any())).willReturn(1);

        //when + then
        assertDoesNotThrow(
            () -> productService.update(new ProductUpdateRequest(1L, "name", 2000, "image_url")));
    }

    @Test
    @DisplayName("존재하지 않는 상품을 삭제한다.")
    public void testDeleteNotExistProduct() {
        //given
        given(productDao.delete(anyLong())).willReturn(0);

        //when + then
        assertThatThrownBy(
            () -> productService.delete(anyLong()))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    public void testDelete() {
        //given
        given(productDao.delete(anyLong())).willReturn(1);

        //when + then
        assertDoesNotThrow(
            () -> productService.delete(anyLong()));
    }
}
