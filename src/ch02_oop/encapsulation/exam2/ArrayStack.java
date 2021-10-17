package ch02_oop.encapsulation.exam2;

//캡슐화
//외부에서 접근하지 못하도록 private 키워드를 붙여 은닉한다.
public class ArrayStack {
    private int top;
    private int[] itemArray;
    private int stackSize;

    public ArrayStack(int stackSize){
        itemArray=new int[stackSize];
        top=-1;
        this.stackSize=stackSize;
    }

    public boolean isEmpty(){
        //스택이 비어있는지 검사
        return (top==-1);
    }

    public boolean isFull(){
        //스택이 꽉 차 있는지 검사
        return (top==this.stackSize-1);
    }

    public void push(int item){
        //스택에 아이템 추가
        if(isFull()){
            System.out.println("Inserting fail! Array Stack is full");
        }else {
            itemArray[++top] = item;
            System.out.println("Inserted Item : " + item);
        }
    }

    public int pop(){
        //스택 top에 있는 아이템 반환
        if(isEmpty()){
            System.out.println("Deleting fail! Array Stack is empty!");
            return -1;
        }else{
            return itemArray[top--];
        }
    }

    public int peek(){
        if(isEmpty()){
            System.out.println("Peeking fail! Array Stack is empty!");
            return -1;
        }else{
            return itemArray[top];
        }
    }
}
