## 14.1 컴퓨터에 추가 장치 지원하기

### 요구사항 분석>

- 컴퓨터(Computer class) 에 포함된 구성 장치
    - 데이터를 입력받는 키보드(Keyboard 클래스)
    - 데이터를 처리하는 본체 (Body 클래스)
    - 처리결과를 출력하는 모니터 (Monitor 클래스)
- Computer 클래스와 구성 장치 사이의 관계는 합성관계로 표현할 수 있다. (전체와 부분관계)
    - Computer : 전체 클래스 , 구성 장치 클래스들 : 부분 클래스

### 구현>

```java
public class Keyboard{
	private int price;
	private int power;

	public Keyboard(int power,int price){
		this.power=power;
		this.price=price;
	}
	
	public int getPrice(){
		return price;
	}

	public int getPower(){
		return power;
	}
}

public class Body{
	private int price;
	private int power;

	public Body(int power,int price){
		this.power=power;
		this.price=price;
	}
	
	public int getPrice(){
		return price;
	}

	public int getPower(){
		return power;
	}
}

public class Monitor{
	private int price;
	private int power;

	public Monitor(int power,int price){
		this.power=power;
		this.price=price;
	}
	
	public int getPrice(){
		return price;
	}

	public int getPower(){
		return power;
	}
}
```

```java
public class Computer{
	private Body body;
	private Keyboard keyboard;
	private Monitor monitor;

	public void addBody(Body body){
		this.body=body;
	}

	public void addKeyBoard(Keyboard keyboard){
		this.keyboard=keyboard;
	}

	public void addMonitor(Monitor monitor){
		this.monitor=monitor;
	}

	public int getPrice(){
		int bodyPirce=body.getPrice();
		int keyboardPirce=keyboard.getPrice();
		int monitorPirce=monitor.getPrice();
		return bodyPirce+keyboardPirce+monitorPirce;
	}

	public int getPower(){
		int bodyPower=body.getPower();
		int keyboardPower=keyboard.getPower();
		int monitorPower=monitor.getPower();
		return bodyPower+keyboardPower+monitorPower;
	}
}
```

```java
public class Client{
	public static void main(String[] args){
		//컴퓨터 부품으로 Body,Keyboard,Monitor 객체 생성
		Body body=new Body(100,70);
		Keyboard keyboard=new Keyboard(5,2);
		Monitor monitor=new Monitor(20,30);

		//Computer 객체를 생성하고 부품 객체들을 설정함
		Computer computer=new Computer();
		computer.addBody(body);
		computer.addKeyboard(keyboard);
		computer.addMonitor(monitor);
		
		//컴퓨터의 가격과 전력 소비량을 구함
		int computerPrice=computer.getPrice();
		int computerPower=computer.getPower();
		System.out.println("compute power : " + coumputerPower + "W");
		System.out.println("computer price : " + computerPrice + "만 원");
	}
} 
```

## 14.2 문제점

**Q. 현재 Computer 클래스는 Body , Keyboard, Monitor 객체로 구성되어 있다. 만약 Computer 클래스의 부품으로 Speaker 클래스를 추가한다면? 또는 Mouse 클래스를 추가한다면?**

⇒ **기존의 Computer 클래스는 새롭게 추가되는 부품을 지원할 수 있도록 확장할 필요가 있다.**

### 확장하는 방법>

Speakr 객체를 지원할 수 있도록 기존의 Computer 클래스를 확장한다면 Speaker 개체도 다른 부품과 동일한 방식으로 Computer 클래스의 부품으로서 정의하면 된다.

Speaker 클래스를 정의하고 합성관계를 이용해 Computer 클래스의 부분으로 표현하도록 한다.

Speaker를 추가한 후 컴퓨터의 각격과 소비 전력량을 계상할 때 필요한 스피커 가격과 소비전력량을 구해야 하므로 필드와 메서드를 추가로 정의한다.

```java
public class Speaker{
	private int price;
	private int power;

	public Speaker(int power,int price){
		this.power=power;
		this.price=price;
	}
	
	public int getPrice(){
		return price;
	}

	public int getPower(){
		return power;
	}
}
```

```java
public class Computer{
	private Body body;
	private Keyboard keyboard;
	private Monitor monitor;
	private Speaker speaker;

	public void addBody(Body body){
		this.body=body;
	}

	public void addKeyBoard(Keyboard keyboard){
		this.keyboard=keyboard;
	}

	public void addMonitor(Monitor monitor){
		this.monitor=monitor;
	}

	public void addSpeaker(Speaker speaker){ //Speaker 추가
		this.speaker=speaker;
	}

	public int getPrice(){
		int bodyPirce=body.getPrice();
		int keyboardPirce=keyboard.getPrice();
		int monitorPirce=monitor.getPrice();
		int speakerPirce=speaker.getPrice(); //speaker 가격 추가
		return bodyPirce+keyboardPirce+monitorPirce+speakerPirce;
	}

	public int getPower(){
		int bodyPower=body.getPower();
		int keyboardPower=keyboard.getPower();
		int monitorPower=monitor.getPower();
		int speakerPower=speaker.getPower(); //speaker 전력소비량 추가
		return bodyPower+keyboardPower+monitorPower+speakerPower;
	}
}
```

이러한 방식으로 Computer 클래스를 수정하여 Speaker 클래스를 지원하는 설계는 확장성이 좋지 않다.

새로운 부품을 위한 클래스를 Computer 클래스에 추가할 때마다 Computer 클래스의 코드를 수정해야 하기 때문이다.

만약 새로운 부품을 추가하려면 다음과 같이 수정해야 한다. ( 코드의 빨간 형광펜 부분)

1. 새로운 부품에 대한 참조를 필드로 추가한다.
2. 새로운 부품 객체를 설정하는 setter 메서드로 addDevice와 같은 메서드를 추가한다.
3. getPrice,getPower 등과 같이 컴퓨터의 부품을 이용하는 모든 메서드에서는 새롭게 추가된 부품 객체를 이용할 수 있도록 수정한다.

**부품을 추가할 때 Computer 클래스의 코드를 수정해야 한다. 이는 OCP 를 위반하는 방법이다.**

⇒ **Computer 클래스에 부품을 추가(확장)할 때 Computer 클래스의 코드가 변경되지 않아야 한다.**

## 14.3 해결책

문제점의 핵심은 Computer 클래스에 속한 부품의 구체적인 객체를 가리키게 되면 OCP를 위반하게 된다는 점이다. 그러므로 **구체적인 부품들을 일반화한 클래스를 정의하고 이를 Computer 클래스가 가리키게 하는 것이 올바른 설계이다.**

### 설계 변화 - 개선점>

- Computer가 가질 수 있는 부품을 일반화해 ComputerDevice 클래스로 정의한다.
- ComputerDevice 클래스는 Keyboard,Body,Monitor 등 구체적인 부품 클래스의 공통기능만 가지며 실제로 존재하는 구체적인 부품은 될 수 없는 추상클래스이다.
- Keyboard,Body,Monitor 등 구체적인 부품 클래스들은 ComoputerDevice의 하위 클래스로 정의한다.
- Computer 클래스는 복수개의 ComputerDevice 객체를 갖는다.
- Computer 클래스도 ComputerDevice 클래스의 하위클래스로 정의된다. 이를 통해 ComputerDevice 클래스를 이용하면 클라이언트 프로그램은 부품 클래스와 동일한 방식으로 Computer 클래스를 사용할 수 있다.

```java
public abstract class ComputerDevice{
	public abstract int getPrice();
	public abstract int getPower();
}

public class Keyboard extends ComputerDevice{
	private int price;
	private int power;
	
	public Keyboard(int power,int price){
		this.power=power;
		this.price=price;
	}
	
	public int getPrice(){
		return price;
	}

	public int getPower(){
		return power;
	}
}

public class Body extends ComputerDevice{
	//동일
}
public class Monitor extends ComputerDevice{
	//동일
}
```

```java
//Computer 클래스는 ComputerDevice의 하위클래스이면서, 복수 개의 ComputerDevice를 갖도록 설계
public class Computer extends ComputerDevice{
	//복수개의 ComputerDevice 객체를 가리킴
	private List<ComputerDevice> components=new ArrayList<>();
	
	//ComputerDevice 객체를 Computer 클래스에 추가
	public void addComponents(ComputerDevice component){
		components.add(component);
	}

	//CompmuterDevice 객체를 Computer 클래스에서 제거함
	public void removeComponent(ComputerDevice comoponent){
		components.remove(component);
	}

	//전체 가격을 포함하는 각 부품의 가격을 합산함
	public int getPrice(){
		int price=0;
		for(ComputerDevice component:conponents){
			price+=component.getPrice();
		}
		return price;
	}

	//전체 소비 전력량을 포함하는 각 부품의 소비전력량을 합산함
	public int getPower(){
		int power=0;
		for(ComputerDevice component:conponents){
			power+=component.getPower();
		}
		return power;
	}
}
```

```java
public class Client{
	public static void main(String[] args){
		//컴퓨터 부품으로 Body,Keyboard,Monitor 객체 생성
		Body body=new Body(100,70);
		Keyboard keyboard=new Keyboard(5,2);
		Monitor monitor=new Monitor(20,30);

		//Computer 객체를 생성하고 부품 객체들을 설정함
		Computer computer=new Computer();
		computer.addComponent(body); //일반화된 메소드 이용
		computer.addComponent(keyboard);
		computer.addComponent(monitor);
		
		//컴퓨터의 가격과 전력 소비량을 구함
		int computerPrice=computer.getPrice();
		int computerPower=computer.getPower();
		System.out.println("compute power : " + coumputerPower + "W");
		System.out.println("computer price : " + computerPrice + "만 원");
	}
} 
```

부품에 해당하는 별도의 메서드가 아닌 addCompoent 메서드와 같이 일반화된 이름을 사용함으로써 부품 종류에 관계없이 동일한 메서드로 부품을 추가할 수 있도록 개선되었다.

**이제 Computer 클래스는 OCP를 준수한다.**

**즉, Computer 클래스에 새로운 부품 객체를 추가해 Computer 클래스를 확장하려고 할때 구체적인 부품 대신 일반화된 부품을 이용하기 때문에 Computer 클래스의 코드는 변경할 필요가 없다.**

추가하려는 부품 클래스를 ComputerDevice 하위클래스로 구현하면 된다.

### Speaker 추가>

```java
public class Speaker extends ComputerDevice{
	private int price;
	private int power;
	
	public Speaker(int power,int price){
		this.power=power;
		this.price=price;
	}
	
	public int getPrice(){
		return price;
	}

	public int getPower(){
		return power;
	}
}
```

```java
public class Client{
	public static void main(String[] args){
		//컴퓨터 부품으로 Body,Keyboard,Monitor 객체 생성
		Body body=new Body(100,70);
		Keyboard keyboard=new Keyboard(5,2);
		Monitor monitor=new Monitor(20,30);
		Speaker speaker=new Speaker(10,10);

		//Computer 객체를 생성하고 부품 객체들을 설정함
		Computer computer=new Computer();
		computer.addComponent(body); 
		computer.addComponent(keyboard);
		computer.addComponent(monitor);
		computer.addComponent(speaker);
		
		//컴퓨터의 가격과 전력 소비량을 구함
		int computerPrice=computer.getPrice();
		int computerPower=computer.getPower();
		System.out.println("compute power : " + coumputerPower + "W");
		System.out.println("computer price : " + computerPrice + "만 원");
	}
} 
```

## 14.4 컴퍼지트 패턴

컴퍼지트 패턴은 **부분-전체의 관계**를 갖는 객체들을 정의할 때 유용하다.

**부분 객체의 추가나 삭제 등이 있어도 전체 객체의 클래스 코드를 변경하지 않으면 컴퍼지트 패턴은 유용하다.**

그리고 **클라이언트는 전체와 부분을 구분하지 않고 동일한 인터페이스를 사용할 수 있다.**

### 컴퍼지트 패턴의 컬레보레이션>

- **Component :** 구체적인 부분, 즉 Leaf 클래스와 전체에 해당하는 Composite 클래스에 공통 인터페이스를 정의한다.
    - ComputerDevice
- **Leaf** : 구체적인 부분 클래스로 Composite 객체의 부품으로 설정한다.
    - Keyboard
    - Body
    - Monitor
    - Speaker
- **Composite** : 전체 클래스로 복수 개의 Component를 갖도록 정의한다. 그러므로 복수개의 Leaf, 심지어 복수 개의 Composite 객체를 부분으로 가질 수 있다.
    - Computer