package ch02_oop.encapsulation.exam1;

public class StackClient {
    public static void main(String[] args) {
        ArrayStack st=new ArrayStack(10);
        st.itemArray[++st.top]=20; //push를 이용하지 않고 직접 집어넣음
        System.out.println(st.itemArray[st.top]); //pop이나 peek을 사용하지 않고 직접 배열에 접근한다.

        //이런경우 ArrayStack 클래스의 스택 구현이 변경되면 StackClient 클래스도 정보를 직접 사용했기때문에 코드를 변경해야한다.
    }
}
