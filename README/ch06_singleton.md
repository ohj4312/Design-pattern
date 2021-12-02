# 싱글톤 패턴

객체를 하나만 생성해야 하는 경우 사용

예제: 단 하나의 프린터를 여러 명이 공유

### 싱글턴 패턴 구현법

1. 생성자를 private으로 선언→ 외부에서 사용할 수 없게 만듦
2. 인스턴스를 리턴하는 메소드 선언

```java
package week01_Singleton;

class User {
	private String name;
	
	public User(String name) {
		this.name=name;
	}
	
	// 직원이 프린터 사용
	public void print() {
		Printer printer = Printer.getPrinter(); // 객체 불러옴 
		printer.print(this.name+"가 프린트, "+printer.toString()+" 사용");
	}
	
}

class Printer {
	
	private static Printer printer=null; // 인스턴스를 저장해줄 변수
	
	private Printer() {} // 기본 생성자
	
	public static Printer getPrinter() { // Printer 객체를 리턴하기 위한 메소드
		if(printer==null) { // 아직 객체가 생성된 상태가 아니라면 // 쓰레드1
			printer=new Printer(); // 객체 생성해주고 쓰레드2->쓰레드1
		}
		
		return printer; // 리턴한다
	}
	
	// 프린트 기능
	public void print(String str) {
		System.out.println(str); 
	}
}

public class Main {
	
	private static final int USER_NUM=5;
	
	public static void main(String[] args) {
		User[] user=new User[USER_NUM];
		
		for(int i=0;i<USER_NUM;i++) {
			user[i]=new User((i+1)+"번 사용자");
			user[i].print();
		}

	}

}
```

### 문제점

- 다중스레드에서 Printer클래스를 사용할 때 인스턴스가 1개 이상 생성될 수도 있음
- 문제점이 생기는 시나리오
    1. Printer 인스턴스가 아직 생성되지 않았을 때, 스레드 1이 getPrinter 메서드의 if문을 실행해서 이미 인스턴스가 생성되었는지 확인함 
    2. 스레드 1이 생성자를 호출해서 인스턴스를 만들기 전, 스레드 2가 if문을 실행해서 printer변수가 null인지 확인한다. 현재 null이기 때문에 인스턴스를 생성하는 코드, 즉 생성자를 호출하는 코드를 실행하게 된다.
    3. 스레드 1도 스레드 2와 마찬가지로 인스턴스를 생성하는 코드를 실행하게 되면 결과적으로 Printer 클래스의 인스턴스가 2개 생성된다 

### 해결책

1. 정적 변수의 성질 이용
    - 정적 변수의 성질
        - 객체가 생성되기 전 클래스가 메모리에 로딩될 때 만들어져서 초기화가 한번만 실행됨
        - 프로그램이 시작될 때부터 종료될 때까지 없어지지 않고 메모리에 계속 상주하며 클래스에서 생성된 모든 객체에서 참조할 수 있음
    
    ```java
    class Printer {
    	
    	private static Printer printer=new Printer(); // 정적 변수
    	private int counter=0;
    	
    	private Printer() {} // 기본 생성자
    	
    	public static Printer getPrinter() { // 정적 메소드
    		return printer; // 정적 변수 리턴함
    	}
    	
    	// 프린트 기능
    	public void print(String str) {
    		counter++;
    		System.out.println(str); 
    	}
    }
    ```
    

1. 인스턴스를 만드는 메서드에 동기화하는 방법 
    
    ```java
    class Printer {
    	
    	private static Printer printer=null; // 정적 변수
    	
    	private Printer() {} // 기본 생성자
    	
    	public static Printer getPrinter() { // 정적 메소드
    		if(printer==null) {
    			printer=new Printer(); 
    		}
    		return printer; // 정적 변수 리턴함
    	}
    	
    	// 프린트 기능
    	public void print(String str) {
    		System.out.println(str); 
    	}
    }
    ```
    

### 정적 클래스

싱글턴 패턴 대신 사용 가능

싱글턴 패턴과 달리, 객체를 생성하지 않고 메서드 사용함

장점: 

- 객체를 전혀 생성하지 않고 메서드 사용 가능
- 인스턴스 메서드를 사용하는 것보다 성능이 우수함

단점

- 인터페이스를 구현해야 하는 경우 사용할 수 없음
- 인터페이스를 구현해야 하는 경우
    
    대체 구현이 필요한 경우 (ex. 테스트용으로 결과가 잘 나오는지 확인하려고 매번 출력물을 검사하는 것은 매우 번거롭기 때문에 실제 프린터를 테스트용 가짜 프린터 객체로 대체)