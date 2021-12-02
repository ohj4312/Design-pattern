# 스테이트 패턴

### 상태머신 다이어그램

UML에서 상태와 상태 변화를 모델링하는 도구

상태 머신 다이어그램에서 지원하는 의사 상태>

시작,종료,히스토리,선택,교차,포크,조인,진입점,진출점

### 형광등 만들기

형광등의 행위 : 

- 형광등이 꺼져있을 때 On버튼을 누르면 켜진다
- 형광등이 켜져있을 때 Off 버튼을 누르면 꺼진다.
- 꺼져 있는 상태에서 Off 버튼을 눌러도 아무런 변화가 없다.
- 켜져 있는 상태에서 On 버튼을 눌러도 아무런 변화가 없다.

```java
public class Light{
	private static int ON=0; //형광등이 켜진 상태
	private static int OFF=1; // 형광등이 꺼진 상태
	private int state; //형광등의 현재 상태

	public Light(){
		state=OFF; //형광등 초기 상태는 꺼져 있는 상태
	}

	public void on_button_pushed(){
		if(state == ON){
			System.out.println("반응 없음");
		}else{ // 형광등이 꺼져 있을 때 On버튼을 누르면 켜진 상태로 전환됨
			System.out.println("Light ON!");
			state=ON;
		}
	}

	public void off_button_pushed(){
		if(state == OFF){
			System.out.println("반응 없음");
		}else{ // 형광등이 켜져 있을 때 Off버튼을 누르면 꺼진 상태로 전환됨
			System.out.println("Light OFF!");
			state=OFF;
		}
	}
}

public class Client{
	public static void main(String[] args){
		Light light=new Light();
		light.off();  //반응 없음
		light.on();   //Light On!
		light.off();  //Light Off!
	}
}
```

### 문제점

형광등에 새로운 상태를 추가할 때, 가령 형광등에 '취침등' 상태를 추가하려면?

형광등이 켜져 있을 때 On버튼을 누르면 취침등 상태로 변경되고, 취침등 상태일 때 On버튼을 누르면 형광등이 켜지고, Off버튼을 누르면 꺼진다.

이렇게 **요구사항이 추가될 때 코드의 확장이 용이하지 않은 문제가 발생한다.**

복잡한 조건문에 상태 변화가 숨어 있는 경우 상태 변화가 어떻게 이루어지는지 이해하기 어렵고 새로운 상태 추가에 맞춰 모든 메서드를 수정해야 한다.

### 해결책

변하는 부분을 찾아서 캡슐화하는 것이 매우 중요하다.

현재 시스템이 어떤 상태인지 상관없게 구성하고 상태 변화에도 독립적이도록 코드를 수정한다.

이를 위해서는 **상태를 클래스로 분리해 캡슐화**하도록 한다.

또한 상태에 의존적인 행위들도 상태 클래스에 같이 두어 특정 상태에 따른 행위를 구현하도록 바꾼다.

효과 : 상태에 따른 행위가 각 클래스에 국지화되어 이해하고 수정하기 쉬워진다.

```java
interface State{
	public void on_button_pushed(Light light);
	public void off_button_pushed(Light light);
}
```

```java
public class ON implements State{
	
	public void on_button_pushed(Light light){
		System.out.println("반응 없음");
	}

	public void off_button_pushed(Light light){
		System.out.println("Light off!");
		light.setState(new OFF(light));
	}
}
```

```java
public class OFF implements State{
	
	public void on_button_pushed(Light light){
		System.out.println("Light on!");
		light.setState(new ON(light));

	}

	public void off_button_pushed(Light light){
		System.out.println("반응 없음");
	}
}
```

```java
public class Light{

	private int state;

	public Light(){
		state=new OFF();
	}
	
	public void setState(State state){
		this.state=state;
	}

	public void on_button_pushed(){
		state.on_button_pushed();
	}

	public void off_button_pushed(){
		state.off_button_pushed();
	}
}
```

Light 클래스에서 구체적인 상태 클래스가 아닌 추상화된 State 인터페이스만 참조하므로 현재 어떤 상태에 있는지와 무관하게 코드를 작성할 수 있다.

CF ) 스트래티지 패턴과 구조가 매우 흡사하다. 이 예제는 스트래티지 패턴과 동일하다.

### 싱글톤 패턴 추가

상태 변화가 생길 때마다 새로운 상태 객체를 생성하므로 메모리 낭비와 성능저하를 가져올 수 있다.

대부분 상태 객체는 한 번만 생성해도 충분하다.

객체를 하나만 만들 수 있는 방법이 싱글턴 패턴을 이용하여 상태 클래스를 객체 하나만 생성한다.

```java
public class ON implements State{

	private static ON on=new ON(); //ON 클래스의 인스턴스로 초기화 된다.
	
	private ON(){}	

	public static ON getInstance(){
		return on; 
	}

	public void on_button_pushed(Light light){
		System.out.println("반응 없음");
	}

	public void off_button_pushed(Light light){
		System.out.println("Light off!");
		light.setState(OFF.getInstance());
	}
}
```

```java
public class OFF implements State{
	
	private static OFF off=new OFF(); //OFF 클래스의 인스턴스로 초기화 된다.
	
	private OFF(){}	

	public static OFF getInstance(){
		return off; 
	}

	public void on_button_pushed(Light light){
		System.out.println("Light on!");
		light.setState(ON.getInstance());

	}

	public void off_button_pushed(Light light){
		System.out.println("반응 없음");
	}
}
```

### 스테이트 패턴

**스테이트 패턴은 어떤 행위를 수행할 때 상태에 행위를 수행하도록 위임한다.**

이를 위해 스테이트 패턴에서는 시스템의 각 상태를 클래스로 분리해 표현하고, 각 클래스에서 수행하는 행위들을 메서드로 구현한다. 

그리고 이러한 상태를 외부로부터 캡슐화하기 위해 인터페이스를 만들어 시스템의 각 상태를 나타내는 클래스로 하여금 실체화하게 한다.

**스테이트 패턴은 상태에 따라 동일한 작업이 다른 방식으로 실행될 때 해당 상태가 작업을 수행하도록 위임하는 패턴이다.**