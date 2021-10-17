package ch02_oop.generalization.exam2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

//일반화관계
//과일바구니에 있는 과일 가격의 총합을 구하려는 코드
//새로운 과일의 종류가 추가되더라도 코드를 수정할 필요가 없다.
public class FruitBox {
    public static void main(String[] args) {
        List<Fruit> list=new LinkedList();
        list.add(new Apple());
        list.add(new Banana());
        int total=computeTotalPrice(list);


        System.out.println(total);
    }

    private static int computeTotalPrice(List<Fruit> list) {
        int total=0;
        Iterator<Fruit> itr=list.iterator();

        while(itr.hasNext()){
            Fruit curFruit= itr.next(); //과일 클래스에 어떤 과일이 들어오든 자식 클래스를 캡슐화 했다고 볼수 있다.
            //하지만 일반화관계는 한 클래스 안에 있는 속성 및 연산들의 캡슐화에 한정되지 않고 일반화 관계를 통해 클래스 자체를 캡슐화하는것으로 확장된다.
            total=total+curFruit.calculatePrice();
        }
        return total;
    }

}

class Fruit{ //새로운 과일이 추가되더라도 Fruti를 상속받도록만 하면 코드의 변화가 필요없다.


    public int calculatePrice(){
        return 0;
    }
}

class Apple extends Fruit{

    @Override
    public int calculatePrice() {
        return super.calculatePrice()+10;
    }
}

class Banana extends Fruit{

    @Override
    public int calculatePrice() {
        return super.calculatePrice()+20;
    }
}