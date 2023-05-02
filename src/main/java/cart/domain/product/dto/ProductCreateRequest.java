package cart.domain.product.dto;

import cart.domain.product.entity.Product;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ProductCreateRequest {

    @NotBlank
    @Max(value = 20, message = "상품의 이름은 20글자를 넘을 수 없습니다.")
    @Min(value = 1, message = "상품의 이름은 1글자를 넘어야합니다.")
    @Size(min = 1, max = 20, message = "상품의 이름은 1자 이상 20자 이하입니다.")
    private String name;

    @Max(value = 100_000_000, message = "가격은 1억 이하만 가능합니다.")
    @Min(value = 0, message = "가격은 음수가 될 수 없습니다.")
    private int price;

    @NotBlank
    private String imageUrl;

    public ProductCreateRequest() {
    }

    public ProductCreateRequest(final String name, final int price, final String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product makeProduct() {
        return new Product(null, name, price, imageUrl, null, null);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
