package ch03_solid.lsp.exam2;

import ch03_solid.lsp.exam1.Bag;

public class MainBag {
    public static void main(String[] args) {
        ch03_solid.lsp.exam1.Bag b1 = new ch03_solid.lsp.exam1.Bag();
        ch03_solid.lsp.exam1.Bag b2 = new Bag();

        b1.setPrice(50000);
        System.out.println(b1.getPrice());

        b2.setPrice(b1.getPrice());
        System.out.println(b2.getPrice());
    }
}