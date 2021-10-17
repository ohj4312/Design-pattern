package ch02_oop.ab.exam1;

//추상화
//자동차 종류마다 엔진 오일을 교환하는 방식이 다르다고 가정할때, 추상화를 적용하지 않은 경우 코드
public class Code2_1 {
    public static void main(String[] args) {
        String carName="아우디";


        switch(carName){
            case "아우디":
                System.out.println("아우디 엔진 오일");
                break;
            case "벤츠":
                System.out.println("벤츠 엔진 오일");
                break;
            case "BMW":
                System.out.println("BMW 엔진 오일");
                break;
                // 이후 다른 차가 추가된다면 해당 차에 해당하는 case문이 필요하다.
        }
    }

}
