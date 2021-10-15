package ch03_solid.lsp.exam1;

public class DiscountedBag extends Bag {
    private double discountedRate = 0;

    public void setDisounted(double discountedRate) {
        this.discountedRate = discountedRate;
    }

    public void applyDiscount(int price) {
        super.setPrice(price - (int) (discountedRate * price));
    }
}