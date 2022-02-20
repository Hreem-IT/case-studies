package io.hreem.casestudies.bff;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public record OrderRequest(
        @NotEmpty String customerName,
        @NotEmpty String sku,
        @Min(1) int quantity) {
}
