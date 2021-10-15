package ch03_solid.lsp.exam2;

import ch03_solid.lsp.exam1.DiscountedBag;

public class MainDiscBag {
    public static void main(String[] args) {
        ch03_solid.lsp.exam1.DiscountedBag b1 = new ch03_solid.lsp.exam1.DiscountedBag();
        ch03_solid.lsp.exam1.DiscountedBag b2 = new DiscountedBag();

        b1.setPrice(50000);
        System.out.println(b1.getPrice());

        b2.setPrice(b1.getPrice());
        System.out.println(b2.getPrice());
    }
}