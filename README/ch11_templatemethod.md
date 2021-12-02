# 템플릿 메서드 패턴

템플릿 메서드 패턴은 전체적인 알고리즘은 상위 클래스에서 구현하면서 다른 부분은 하위클래스에서 구현할 수 있도록 하는 디자인 패턴이다. 전체적인 알고리즘 코드를 재사용하는데 유용하다.

## 11.1 여러 회사의 모터 지원하기

엘리베이터 제어 시스템에서 모터를 구동시키는 기능

- 현태 모터를 이용하는 제어 시스템 - HyundaiMoter.move()
- move() 메소드 실행시 안전을 위해 문이 닫혀있는지 조사해야 한다.
- 엘리베이터가 이미 이동중이면 모터를 구동시킬 필요가 없다.
- Enumeration 인터페이스 설계 : 모터의 상태(정지 중, 이동 중), 문의 상태(닫힘,열림), 이동방향(위,아래)

### 구현

```java
public enum DoorStatus{
	CLOSED,
	OPENED
}

public enum MotorStatus{
	MOVING,
	STOPPED
}

public class Door{
	private DoorStatus doorStatus;

	public Door(){
		doorStatus=DoorStatus.CLOSED;
	}

	public DoorStatus getDoorStatus(){
		return doorStatus;
	}

	public void close(){
		doorStatus = DoorStatus.CLOSED;
	}

	public void open(){
		doorStatus = DoorStatus.OPEND;
	}
}

public class HyundaiMotor{
	private Door door;
	private MotorStatus motorStatus;

	public HyundaiMotor(Door door){
		this.door=door;
		motorStatus=MotorStatus.STOPPED; //초기에는 멈춘 상태
	}

	private void moveHyundaiMotor(Direction direction){
		//Hyndai Motor 구동
	}

	public MotorStatus getMotorStatus(){
		return motorStatus;
	}
	
	private void setMotorStatus(MotorStatus motorStatus){
		this.motorStatus = motorStatus;
	}

	public void move(Direction direction){
		MotorStatus motorStatus=getMotorStatus();
		if(motorStatus==MotorStatus.MOVING) //이미 이동중이면 아무 작업을 하지 않음
			return;
		
		DoorStatus doorStatus = door.getDoorStatus();
		if(doorStatus==DoorStatus.OPENd) //만약 문이 열려있으면 우선 문을 닫음
			door.closed();

		moveHyundaiMotor(direction); //모터를 주어진 방향으로 이동시킴
		setMotorStatus(MotorStatus.MOVING); //모터 상태를 이동중으로 변경함
	}
}

public class Client{
	public static void main(String[] args){
		Door door=new Door();
		HyundaiMotor hyundaiMotor=new HyundaiMotor(door);
		hyundaiMotor.move(Direcction.UP);
}
```

## 11.2 문제점

만약 현대 모터가 아닌 LG 모터를 구동시키려면?

LG 모터와 현대 모터는 완전히 같지 않으므로 새로운 LG모터를 만들어서 구현해야 한다.

하지만 LG모터와 현대모터는 많은 중복 코드를 가지므로 유지보수를 악화시킬 수 있다.

이러한 중복 코드 문제는 다른 현대,LG 말고 다른 회사의 모터를 사용할 때마다 발생한다.

2개 이상의 클래스가 유사한 기능을 제공하면서 중복된 코드가 있는 경우 상속을 이용해 코드 중복 문제를 피할 수 있다.

중복된 코드를 Motor 클래스를 만들어 관리하고, 상속받아 사용한다.

### 구현

```java
public abstract class Motor{ //현대모터와 LG모터의 공통적인 기능을 구현하는 클래스
	protected Door door;
	private MotorStatus motorStatus;

	public Motor(Door door){
		this.door=door;
		motorStatus=MotorStatus.STOPPED;
	}

	public MotorStatus getMotorStatus(){
		return motorStatus;
	}

	protected void setMotorStatus(MotorStatus motorStatus){
		this.motorStatus = motorStatus;
	}
}

public class HyundaiMotor extends Motor {//Motor를 상속받아 구현
	
	public HyundaiMotor(Door door){
		super(door);
	}

	private void moveHyundaiMotor(Direction direction){
		//Hyndai Motor 구동
	}

	
	//공통적이지 않은 부분
	public void move(Direction direction){
		MotorStatus motorStatus=getMotorStatus();
		if(motorStatus==MotorStatus.MOVING) //이미 이동중이면 아무 작업을 하지 않음
			return;
		
		DoorStatus doorStatus = door.getDoorStatus();
		if(doorStatus==DoorStatus.OPENd) //만약 문이 열려있으면 우선 문을 닫음
			door.closed();

		moveHyundaiMotor(direction); //이 부분이 LGMotor와 다른 부분
		setMotorStatus(MotorStatus.MOVING); //모터 상태를 이동중으로 변경함
	}
}

public class LGMotor extends Motor {//Motor를 상속받아 구현
	
	public LGMotor(Door door){
		super(door);
	}

	private void moveLGMotor(Direction direction){
		//LG Motor 구동
	}

	
	//공통적이지 않은 부분
	public void move(Direction direction){
		MotorStatus motorStatus=getMotorStatus();
		if(motorStatus==MotorStatus.MOVING) //이미 이동중이면 아무 작업을 하지 않음
			return;
		
		DoorStatus doorStatus = door.getDoorStatus();
		if(doorStatus==DoorStatus.OPENd) //만약 문이 열려있으면 우선 문을 닫음
			door.closed();

		moveLGMotor(direction); //이 부분이 HyundaiMotor와 다른 부분
		setMotorStatus(MotorStatus.MOVING); //모터 상태를 이동중으로 변경함
	}
}
```

Motor 클래스를 HyundaiMotor 클래스와 LGMotor 클래스의 상위 클래스로 정의함으로써 원래 2개의 클래스에 있었던 Door 클래스와의 연관 관계, motorStatus 필드, getMotorStatus()와 setMotorStatus() 메서드의 중복을 피할 수 있다.

하지만 여전히 move() 메서드에서 코드 중복문제가 있다.

## 11.3 해결책

move 메서드처럼 완전히 중복되지는 않지만 부분적으로 중복되는 경우에도 상속을 활용해 코드 중복을 피할 수 있다.

move 메서드를 상위 Motor 클래스로 이동시키고 공통적이지 않은 구문을 하위클래스에서 오버라이드하는 방식으로 move 메서드의 중복을 최소화할 수 있다.

코드가 차이나는 moveHyundaiMotor()와 moveLGMotor() 메소드를 Motor 클래스의 추상메소드인 moveMotor() 메소드로 정의한 후 각 하위 클래스에서 적절하게 오버라이드 되도록한다.

### 구현

```java
public abstract class Motor{ //현대모터와 LG모터의 공통적인 기능을 구현하는 클래스(Abstract class)
	protected Door door;
	private MotorStatus motorStatus;

	public Motor(Door door){
		this.door=door;
		motorStatus=MotorStatus.STOPPED;
	}

	public MotorStatus getMotorStatus(){
		return motorStatus;
	}

	protected void setMotorStatus(MotorStatus motorStatus){
		this.motorStatus = motorStatus;
	}

	//LGMotor와 HyundaiMotor의 move 메서드에서 공통된 부분만을 가짐
	public void move(Direction direction){ //Templete method
		MotorStatus motorStatus=getMotorStatus();
		if(motorStatus==MotorStatus.MOVING) 
			return;
		
		DoorStatus doorStatus = door.getDoorStatus();
		if(doorStatus==DoorStatus.OPENd) 
			door.closed();

		moveMotor(direction); //자식 클래스에서 특수화(오버라이드) 될 예정
		setMotorStatus(MotorStatus.MOVING); 
	}
	
	protected abstract void moveMotor(Direction direction); //primitive / hook method
}

public class HyundaiMotor extends Motor { //Concrete Class : primitive(hook) 메서드 오버라이드
	
	public HyundaiMotor(Door door){
		super(door);
	}

	@Override
	protected void moveMotor(Direction direction){
		//Hyndai Motor 구동
	}
}

public class LGMotor extends Motor {//Motor를 상속받아 구현
	
	public LGMotor(Door door){
		super(door);
	}

	@Override
	protected void moveMotor(Direction direction){
		//LG Motor 구동
	}
}
```

move() 메서드의 중복코드는 Motor클래스에서, 다른 부분은 각각 오버라이드하도록 변환했다.

## 11.4 템플릿 메서드 패턴

템플릿 메서드 패턴은 전체적으로는 동일하면서 부분적으로는 다른 구문으로 구성된 메서드의 코드 중복을 최소화할 때 유용하다.

다른 관점에서 보면 동일한 기능을 상위 클래스에서 정의하면서 확장/변화가 필요한 부분만 서브클래스에서 구현할수 있도록 한다.

예제처럼 Motor 클래스의 move 메서드는 HyundaiMotor와 LGMotor에서 동일한 기능을 구현하면서 각 하위 클래스에서 구체적으로 정의할 필요가 있는 부분, movMotor 메서드 부분만 각 하위 클래스에서 오버라이드되도록 한다.

이러한 경우 Motor 클래스의 move 메서드를 템플릿 메서드라고 부르고, move 메서드에서 호출되면서 하위클래스에서 오버라이드될 필요가 있는 moveMotor 메서드를 primitive 또는 hook 메서드라고 부른다.