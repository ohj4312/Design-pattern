package ch02_oop.ab.exam1;

//추상화
//자동차 종류마다 엔진 오일을 교환하는 방식이 다르다고 가정할때, 추상화를 적용하는 경우 코드

//구체적이 차종류 대신 이들의 추상화 개념인 자동차 추상화 클래스 구현
//구체적인 자동차 종류와 연관된 부분이 없으므로 새로운 차가 추가되더라도 변경할 필요가 없다. (다형성 원리)
abstract class Car{
    void changeEngineOil(Car c){
        c.changeEngineOil();
    }

    abstract void changeEngineOil();
}

class Audi extends Car{

    @Override
    protected void changeEngineOil() {
        System.out.println("아우디 엔진 오일");
    }
}

class Benz extends Car{

    @Override
    protected void changeEngineOil() {
        System.out.println("벤츠 엔진 오일");
    }
}

class BMW extends Car{

    @Override
    protected void changeEngineOil() {
        System.out.println("BMW 엔진 오일");
    }
}

public class Code2_2 {



    public static void main(String[] args) {
        String carName="";

        Car car=new Audi();
        car.changeEngineOil(new Audi());

        car.changeEngineOil(new Benz());
        car.changeEngineOil(new BMW());


        //새로운 차가 추가된다고 하더라도 새로운 차에 해당하는 객체를 만들고 해당 연로주입방식으로 변경해주면 된다.
        //car.changeEngineOil(new 새로운차객체());
    }

}
