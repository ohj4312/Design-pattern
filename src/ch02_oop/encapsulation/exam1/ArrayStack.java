package ch02_oop.encapsulation.exam1;

//캡슐화
//public 키워드로 외부에 모두 공개되어잇다.
// 이것은 push나 pop을 이용하지 않고 직접 배열에 값을 저장할 수 있다는 의미이다.
//이런 경우 ArrayStack과 StackClient는 강한 결합이 발생한다.
public class ArrayStack {
    public int top;
    public int[] itemArray;
    public int stackSize;

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
