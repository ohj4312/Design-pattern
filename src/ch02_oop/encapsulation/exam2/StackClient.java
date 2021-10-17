package ch02_oop.encapsulation.exam2;


public class StackClient {
    public static void main(String[] args) {
        ArrayStack st=new ArrayStack(10);
        st.push(20);
        System.out.println(st.peek());
        st.pop();
        System.out.println(st.peek());
    }
}
