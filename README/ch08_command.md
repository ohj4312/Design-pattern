## 8.1. 예제-버튼 만들기

버튼을 눌렀을 때 램프의 불이 켜지는 프로그램

- Button 클래스의 생성자를 이용해서 불을 켤 Lamp 객체를 전달
- Button 클래스의 pressed 메서드가 호출되면 생성자를 통해 전달받은 Lamp 객체의 turnOn 메서드를 호출해서 불을 켠다

```java
class Lamp { // 불을 켜는 기능 제공
	public void turnOn() { // 불 켜는 메소드 
		System.out.println("Lamp On");
	}
}

class Button { // 버튼이 눌려졌음을 인식함
	private Lamp theLamp; // Lamp 객체
	
	public Button(Lamp theLamp) { 
		this.theLamp=theLamp;
	}
	
	public void pressed() { // 버튼이 눌려졌을 때의 행동 제어하는 메소드 
		theLamp.turnOn(); // Lamp의 불 켜는 메소드 호출함 
	}
}

public class Client { // 클라이언트
	
	public static void main(String[] args) {
		Lamp lamp=new Lamp(); // Lamp 객체생성
		Button lampButton=new Button(lamp); // Button 객체 생성
		lampButton.pressed();// 주어진 버튼을 눌렀을 때 
	}
	

}
```

## 8.2. 문제점

새로운 기능(알람을 울리도록 하는 버튼)을 추가할 경우

- 버튼을 누를 때 실행할 프로그램을 선택할 수 있도록 해야 한다

```java
package code_8_2_2;

class Lamp { // 불을 켜는 기능 제공
	public void turnOn() { // 불 켜는 메소드 
		System.out.println("Lamp On");
	}
}

class Alarm { // 알람 기능 제공
	public void start() { // 알람 기능 메소드
		System.out.println("Alarming...");
	}
}

enum Mode { LAMP, ALARM };

class Button { // 버튼이 눌려졌음을 인식함
	private Lamp theLamp; // Lamp 객체
	private Alarm theAlarm; // Alarm 객체
	private Mode theMode; // 현재 상태
	
	public Button(Lamp theLamp, Alarm theAlarm) { 
		this.theLamp=theLamp;
		this.theAlarm=theAlarm;
	}
	
	public void setMode(Mode mode) { // 램프 모드 또는 알람 모드 설정
		this.theMode=mode;
	}
	
	public void pressed() { // 버튼이 눌려졌을 때의 행동 제어하는 메소드 
		
		switch(theMode) {
		case LAMP: // 램프 모드인 경우
			theLamp.turnOn(); // 램프를 켬
			break;
		case ALARM:
			theAlarm.start(); // 알람 모드면 알람을 울리도록 함
			break;
		}
	}
}

public class Client { // 클라이언트
	
	public static void main(String[] args) {
		Lamp lamp=new Lamp(); 
		Alarm alarm=new Alarm(); 
		Button button=new Button(lamp, alarm);
		
		button.setMode(Mode.LAMP); // 램프 모드 설정
		button.pressed(); // 램프 모드를 설정했기 때문에 램프가 켜짐
		
		button.setMode(Mode.ALARM); // 알람 모드 설정
		button.pressed(); // 알람 모드를 설정했기 때문에 알람이 켜짐
	}
	

}
```

위 코드의 단점

- 필요한 기능을 새로 추가할 때마다 Button 클래스의 코드를 수정해야 한다
- Button 클래스를 재사용하기 힘들다

## 8.3. 해결책 (=커맨드 패턴)

Button 클래스의 pressed() 메서드에서 구체적인 기능을 구현하는 대신, 버튼을 눌렀을 때 실행될 기능들을 Button 클래스 외부에서 제공받아 캡슐화해서 pressed() 메서드에서 호출하는 방법 사용

- Button 클래스는 램프 켜기 또는 알람 동작 등의 기능을 실행할 때 Command 인터페이스의 execute 메서드를 호출하도록 한다
- 램프 켜기, 알람 울리기 기능을 클래스로 구현→Command 인터페이스를 상속받도록 함
- execute 메서드에서 Lamp/Alarm의 램프켜기 or 알람 울리기 기능을 수행

장점

- Button의 소스코드를 변경하지 않고서도 다양한 동작을 구현할 수 있게 된다.

```java
package code_8_4;

interface Command { // Command 인터페이스
	public abstract void execute(); // 기능을 수행하는 메소드 
}

class Button { // 버튼이 눌려졌음을 인식함
	
	// 앞 단계에서 Lamp와 Alarm 객체를 생성했던 것과는 다르게 Command 객체 생성
	private Command theCommand; 
	
	// 생성자에서 setCommand 호출->인자로 들어온 Command를 theCommand로 설정해줌
	public Button(Command theCommand) { 
		setCommand(theCommand);
	}
	
	// 인자로 들어온 Command를 theCommand로 설정해줌
	public void setCommand(Command newCommand) { 
		this.theCommand=newCommand;
	}
	
	public void pressed() { // 버튼이 눌려졌을 때의 행동을 제어하는 메소드
		theCommand.execute(); // 버튼이 눌리면 주어진 Command의 execute 메소드 호출
	}
}

// Lamp클래스와 Alarm 클래스는 그대로 둠
class Lamp { // 불을 켜는 기능 제공
	public void turnOn() { // 불 켜는 메소드 
		System.out.println("Lamp On");
	}
}

class Alarm { // 알람 기능 제공
	public void start() { // 알람 기능 메소드
		System.out.println("Alarming...");
	}
}

// 램프를 켜는 클래스
class LampOnCommand implements Command {
	private Lamp theLamp; // 램프 객체
	
	// 생성자: 램프 객체 초기화
	public LampOnCommand(Lamp theLamp) {
		this.theLamp=theLamp;
	}
	
	@Override
	public void execute() {
		theLamp.turnOn();// 램프를 켠다
	} 
	
}

// 알람을 울리는 클래스 
class AlarmOnCommand implements Command {
	
	private Alarm theAlarm; // 알람 객체
	
	// 생성자: 알람 객체 초기화
	public AlarmOnCommand(Alarm theAlarm) {
		this.theAlarm=theAlarm;
	}

	@Override
	public void execute() {
		theAlarm.start(); // 알람을 켠다
	}
}

public class Client { // 클라이언트
	
	public static void main(String[] args) {
		Lamp lamp=new Lamp(); // 램프 객체 생성
		Command lampOnCommand=new LampOnCommand(lamp); // 램프를 켜는 객체 생성
		
		Button button1=new Button(lampOnCommand); // 램프를 켜는 커멘드 설정
		button1.pressed(); // 버튼이 눌리면 램프 켜는 기능이 실행됨
		
		Alarm alarm=new Alarm(); // 알람 객체 생성
		Command alarmOnCommand=new AlarmOnCommand(alarm); // 알람을 켜는 객체 생성
		
		Button button2=new Button(alarmOnCommand); // 알람을 켜는 커멘드 설정
		button2.pressed(); // 버튼이 눌리면 알람을 켜는 기능이 실행됨
		
	}
}
```

추가예시: 램프를 끄는 기능을 추가하기→Button은 수정하지 않고 Lamp만 수정하고 LampOffCommand만 추가하면 됨 

## 8.4. 커맨드 패턴

이벤트가 발생했을 때 실행될 기능이 다양할 때, 이벤트를 발생시키는 클래스를 재사용하고자 할 때 유용함

커맨드 패턴에서 나타나는 역할이 수행하는 작업

- Command (ex. Command 클래스): 실행될 기능에 대한 인터페이스. 실행될 기능을 execute 메서드로 선언함
- ConcreteCommand (ex. LampOnCommand, AlarmOnCommand): 실제로 실행되는 기능을 구현. Command 인터페이스 구현함
- Invoker(ex. Button): 기능을 실행해달라고 요청하는 호출자 클래스.
- Receiver(ex. Lamp, Alarm): ConcreteCommand에서 execute 메서드를 구현할 때 필요한 클래스. Concrete Command의 기능을 실행하기 위해 사용하는 클래스.

커맨드 패턴의 실행 과정

- 클라이언트가 원하는 Command 객체 생성
    - ex. Command 객체를 생성
- Command 객체를 Invoker 객체에서 생성
    - ex. Button에서 Command 객체 생성
    - ex. Command를 상속받은 객체로 초기화됨
- Invoker 객체에서는 생성한 Command 객체의 execute 메서드를 호출
    - ex. Button에서 execute 메서드 호출
- execute 메서드는 Receiver 객체의 action 메서드를 호출함으로써 원하는 기능을 실행함
    - ex. execute는 생성된 Command의 자식 객체에 따라서 다른 메서드 호출함