package ch02_oop.generalization.exam1;

//일반화관계
//과일바구니에 있는 과일 가격의 총합을 구하려는 코드
//새로운 과일의 종류가 나타날때마다 항상 코드를 수정해야 하므로 변경사항에 유연성 있게 대처하지 못한다.
public class FruitBox {
    public static void main(String[] args) {
        String[] fruits={"사과","배"};
        int n=fruits.length;
        int total=0;

        while(n-->0){
            switch (whatFruit(fruits[n])) {
                case "사과":
                    total = total + getPrice("사과");
                    break;
                case "배":
                    total = total + getPrice("배");
                    break;
                    //만약 다른 과일이 추가된다면 또다른 case문을 작성해야한다. getPrice내용도 수정해야한다.
                default:
                    break;
            }
        }
        System.out.println(total);
    }

    private static int getPrice(String s) {

        if("사과".equals(s)) return 30;
        else if("배".equals(s)) return 20;
        else if("바나나".equals(s)) return 40;
        else if("오렌지".equals(s)) return 50;
        else return 10;
    }

    private static String whatFruit(String s) {
        return s;
    }

    private static boolean hasFruit() {
        return true;
    }


}
