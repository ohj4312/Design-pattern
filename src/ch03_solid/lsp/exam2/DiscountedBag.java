package ch03_solid.lsp.exam2;

public class DiscountedBag extends Bag {
    private double discountedRate = 0;

    public void setDisounted(double discountedRate) {
        this.discountedRate = discountedRate;
    }

    @Override
    public void setPrice(int price) {
        super.setPrice(price - (int) (discountedRate * price));
    }
}