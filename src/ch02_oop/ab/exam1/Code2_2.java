package ch02_oop.ab.exam1;

abstract class Car{
    void changeEngineOil(Car c){
        c.changeEngineOil();
    }

    protected abstract void changeEngineOil();
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
        car.changeEngineOil(new Audi());
    }

}
