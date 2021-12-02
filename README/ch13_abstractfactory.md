## 13.1 엘리베이터 부품 업체 변경하기

### 요구 사항 분석 >

- 엘리베이터 구성 부품중 모터와 문은 제조업체별로 다른 모터와 문을 제공할 것이다.
- 이때,여러 제조 업체의 부품을 사용하더라도 같은 동작을 지원하는 것이 바람직하다.
- 예를 들어 건물 A에서는 LG 부품이 사용되고 건물 B에서 현대의 부품이 사용되더라도 엘리베이터 프로그램의 변경을 최소화할 필요가 있다.
- LG의 모터와  현대의 모터는 구체적인 제어방식은 다르지만 엘리베이터 입장에서는 모터를 구동해 엘리베이터를 이동시킨다는 면에서는 동일하다.

### 설계>

- 추상 클래스로 Motor를 정의하고 LGMotor와 HyundaiMotor를 하위클래스로 정의할 수 있다. (Motro 클래스와 Door 클래스는 LGMotor,HyundaiMotor 객체 / LGDoor,HyudaiDoor를 일반화하고 실제로는 객체로 생성되지 않으므로 추상클래스로 정의)
- Motor 클래스는 이동하기 전에 문을 닫아야한다. 그래서 Door 객체의 DoorStatus 메서드를 호출하려고 Motor 클래스에서 Door 클래스로의 연관관계를 정의했다.
- Motor의 핵심 기능 move 메서드
    - 이미 이동 중이라면 무시한다.
    - 문이 열려있으면 문을 닫는다.
    - **모터를 구동해서 이동시킨다.**
    - 모터의 상태를 이동중으로 설정한다.
- 이때 모터를 구동해서 이동시킨다 부분만 LGMotor와 HyudaiMotor에서 달라진다.
- **이와 같이 일반적인 흐름에서는 동일하지만 특정 부분만 다른 동작을 하는 경우에는 일반적인 기능을 상위클래스에 템플릿 메서드로 설계할 수 있다.(11장 템플릿 메서드 패턴 적용)**
    - 템플릿 메서드 패턴은 전체적으로는 동일하면서 부분적으로는 다른 구문으로 구성된 메서드의 코드 중복을 최소화할 때 유용하다.
    - 다른 관점에서 보면 동일한 기능을 상위 클래스에서 정의하면서 확장/변화가 필요한 부분만 서브클래스에서 구현할수 있도록 한다.
- Door 클래스의 경우에도 open과 close 메서드 각각에 템플릿 메서드 패턴을 적용할 수 있다. 문을 닫는 부분만 동작이 다를 수 있는 것이다 . (open () / close() )
    - 이미 문이 열려있으면 무시한다. / 이미 문이 닫혀 있으면 무시한다.
    - **문을 연다 / 문을 닫는다.**
    - 문의 상태를 열림/ 닫힘으로 설정한다.
- 마찬가지로 일반적인 흐름은 동일하게 정의하고 문을 연다/닫는다만 하위클래스에서 오버라이드할 수 있도록 설계한다.

### 템플릿 메서드 패턴을 적용한 설계 - 구현>

```java
public abstract class Door{
	private DoorStatus doorStatus;

	public Door(){
		doorStatus=DoorStatus.CLOSED;
	}

	public DoorStatus getDoorStatus(){
		return doorStatus;
	}

	public void close(){ // 템플릿 메서드
		if(doorStatus==DoorStatus.CLOSED) // 이미 문이 닫혀 있으면 아무 동작을 하지 않음
			return;

		doClose(); //실제로 문을 닫는 동작을 수행, 하위 클래스에서 오버라이드 될 것
		doorStatus=DoorStatus.CLOSED(); //문의 상태를 닫힘으로 기록함
	}
	**protected abstract void doClose();//primitive 또는 hook 메서드**

	public void open(){
		if(doorStatus == DoorStatus.OPENED) // 이미 문이 열려 있으면 아무 동작을 하지 않음
			return;
		
		doOpen(); // 실제로 문을 여는 동작을 수행함. 하위 클래스에서 오버라이드될 것임.
		doorStatus=DoorStatus.OPENED; // 문의 상태를 열림으로 기록함
	}
	**protected abstract void doOpen(); //primitive 또는 hook 메서드**
}

//-----------------------------------------------------------------------

public class LGDoor extends Door{
	protected void doClosed(){
		System.out.println("close LG Door");
	}

	protected void doOpen(){
		System.out.println("open LG Door");
	}
}

//-----------------------------------------------------------------------

public class HyundaiDoor extends Door{
	protected void dbClose(){
		System.out.println("close Hyundai Door");
	}

	protected void doOpen(){
		System.out.println("open Hyundai Door");
	}
}
```

엘리베이터 입장에서는 특정 제조 업체의 모터와 문을 제어하는 클래스가 필요하다.

이때 객체 생석방식에서 **팩토리 메서드 패턴**을 적용할 수 있다.

```java
public enum VendorID{LG,HYUNDAI}

public class MotorFactory{ //팩토리 메서드 패턴을 사용함
	//vendorID에 따라 LGMotor 또는 HyundaiMotor 객체를 생성함.
	public static Motor createMotor(VendorID vendorID){
		Motor motor=null;
		switch(vendorID){
			case LG:
				motor = new LGMotor();
				break;
			case HYUNDAI:
				motor= new HyundaiMotor();
				break;
		}
		return motor;
	}
}
```

```java
public class DoorFactory{ //팩토리 메서드 패턴을 사용함
	//vendorID에 따라 LGDoor 또는 HyundaiDoor 객체를 생성함.
	public static Motor createDoor(VendorID vendorID){
		Door door=null;
		switch(vendorID){
			case LG:
				door = new LGDoor();
				break;
			case HYUNDAI:
				door= new HyundaiDoor();
				break;
		}
		return motor;
	}
}
```

```java
public class client{
	public static void main(String[] args){
		Door lgDoor=DoorFactory.createDoor(VendorID.LG); //팩토리 메서드를 호출함
		Motor lgMotor=MotorFactory.createMotor(VendorID.LG); //팩토리 메서드를 호출함
		lgMotor.setDoor(lgDoor);
		
		lgDoor.open();
		lgMotor.move(Direction.UP);
	}
}
```

## 13.2 문제점

**Q1. 위 예제는 LG 부품을 사용한다. 만약 다른 제조 업체의 부품을 사용해야 한다면?**

**Q2. 새로운 제조 업체의 부품을 지원해야한다면? 예를 들어 삼성의 부품을 지원해야한다면?**

### A1. 다른 제조 업체의 부품을 사용해야 하는경우

엘리베이터 프로그램에서 현대의 부품을 사용하려면 MotorFactory와 DoorFactory 클래스를 이용해서 이미 정의된 HyundaiMotor 객체와 HyundaiDoor 객체를 생성하도록 프로그램을 수정한다.

```java
public class client{
	public static void main(String[] args){
		Door hyundaiDoor=DoorFactory.createDoor(VendorID.HYUNDAI); //팩토리 메서드를 호출함
		Motor hyundaiMotor=MotorFactory.createMotor(VendorID.HYUNDAI); //팩토리 메서드를 호출함
		hyundaiMotor.setDoor(hyundaiDoor);
		
		hyundaiDoor.open();
		hyundaiMotor.move(Direction.UP);
	}
}
```

⇒ MotorFactory와 DoorFactory 객체를 사용해서 현대 부품의 객체를 생성하도록 비교적 쉽게 코드가 수정되었다. 

그러나 이런 구조라면 엘리베이터가 모터,문, 세종류의 램프, 두종류의 센서, 스피커, 두종류의 버튼 등 총 **10개의 부품을 사용해야 한다면 각 Factory 클래스를 구현하고 이들의 Factor 객체를 각각 생성해야 한다.** 

```java
public class client{
	public static void main(String[] args){
		Door hyundaiDoor=DoorFactory.createDoor(VendorID.HYUNDAI); //팩토리 메서드를 호출함
		Motor hyundaiMotor=MotorFactory.createMotor(VendorID.HYUNDAI); //팩토리 메서드를 호출함
		hyundaiMotor.setDoor(hyundaiDoor);
		
		ArrivalSensor hyundaiArrivalSensor = ArrivalSensorFactory.createArrivalSesnor(VendorID.HYUNDAI);
		WeigthSensor hyundaiWeigthSensor = WeigthSensorFactory.createWeigthSensor(VendorID.HYUNDAI);
		ElevatorLamp hyundaiElevatorLamp = ElevatorLampFactory.createElevatorLamp(VendorID.HYUNDAI);
		FloorLamp hyundaiFloorLamp  = FloorLampFactory.createFloorLamp(VendorID.HYUNDAI);
		DirectionLamp hyundaiDirectionLamp = DirectionLampFactory.createDirectionLamp(VendorID.HYUNDAI);
		Speaker hyundaiSpeaker = SpeakerFactory.createSpeaker(VendorID.HYUNDAI);
		ElevatorButton hyundaiElevatorButton = ElevatorButtonFactory.createElevatorButton(VendorID.HYUNDAI);
		FloorButton hyundaiFloorButton  = FloorButtonFactory.createFloorButton(VendorID.HYUNDAI);
	
		hyundaiDoor.open();
		hyundaiMotor.move(Direction.UP);
	}
}
```

부품의 수가 많이지면 특정 업체별 부품을 생성하는 코드의 길이가 길어지고 복잡해질 수 있다.

이러한 코드 구조가 많아진다면 코드를 수정하는 것이 더욱 쉽지 않다.

### A2. 새로운 제조 업체의 부품을 지원해야 하는 경우

SamsungMotor와 SamsungDoor 클래스를 지원해야 한다면 MotorFactory와 DoorFactory 클래스가 SamsungMotor와 SamsungDoor 클래스를 지원할 수 있도록 수정되어야 한다.

```java
public class DoorFactory{ 
	public static Motor createDoor(VendorID vendorID){
		Door door=null;
		switch(vendorID){
			case LG:
				door = new LGDoor();
				break;
			case HYUNDAI:
				door= new HyundaiDoor();
				break;
			case SAMSUNG:
				door= new SamsungDoor();
				break;
		}
		return motor;
	}
}
```

DoorFactory 클래스 뿐만 아니라 나머지 9개 부품과 연관된 Factory 클래스에서도 마찬가지로 삼성의 부품을 생성하도록 변경할 필요가 있다. 

**문제점을 요약하자면 기존의 팩토리 메서드 패턴을 이용한 객체 생성은 관련 있는 여러개의 객체를 일관성 있는 방식으로 생성하는 경우에 많은 코드 변경이 발생하게 된다는 것이다.**

## 13.3 해결책

여러 종류의 객체를 생성할때 객체들 사이의 관련성이 있는 경우라면 ( 제조사가 같다.) 각 종류별로 별도의 Factory 클래스를 사용하는 대신 관련 객체들을 일관성 있게 생성하는 Factory 클래스를 사용하는 것이 편리할 수가 있다.

부품별로 Factory 클래스를 만드는 대신 LGElevatorFactory나 HyudaiElevatorFatory 클래스와 같이 제조 업체별로 Factory 클래스를 만들 수도 있다.

LGElevatorFactory 

- createMotor 메서드를 통해 LGMotor 객체를 생성하는 기능 제공
- createDoor 메서드를 통해 LGDoor 객체를 제공하는 기능을 제공

HyundaiElevatorFactory 

- createMotor 메서드를 통해 HyundaiMotor 객체를 생성하는 기능 제공
- createDoor 메서드를 통해 HyundaiDoor 객체를 제공하는 기능을 제공

⇒**LGElevatorFactory , HyundaiElevatorFactory 는 createMotor,creatDoor 메서드를 제공한다.**

즉, 엘리베이터를 구성하는 각 부품객체를 생성하는 메서드가 공통으로 있다.

**ElevatorFactor클래스로 LGElevatorFactory , HyundaiElevatorFactory 클래스를 일반화한 상위클래스를 정의할 수 있다.**

ElevatorFactory 는 추상클래스이고 createMotro와 createDoor도 추상메서드로 정의된다.

**A1. 다른 제조 업체의 부품을 사용해야 하는경우 해결책**

```java
 //추상 부품을 생성하는 추상팩토리
public abstract class ElevatorFactory{
	public abstract Motor createMotor();
	public abstract Door createDoor();
}

 // LG 부품을 생성하는 팩토리
public class LGElevatorFactory extend ElevatorFactory{
	public Motor createMotor(){
		return new LGMotor();
	}

	public Door createDoor(){
		return new LGDoor();
	}
}

//현대 부품을 생성하는 팩토리
public class HyundaiElevatorFactory extend ElevatorFactory{
	public Motor createMotor(){
		return new HyundaiMotor();
	}

	public Door createDoor(){
		return new HyundaiDoor();
	}
}
```

제조 업체별로 Factory 클래스를 정의했으므로 제조 업체별 부품 객체를 아주 간단히 생성할 수 있다.

```java
public class client{
	public static void main(String[] args){
		ElevatorFactory factory=null'
		String vendorName=args[0];
		if(vendorName.equalsIgnoreCase("LG")) //인자에 따라 LG 또는 현대 팩토리를 생성
			factory=new LGElevatorFactory();
		else{
			factory=new HyundaiElevatorFactory();

		Door door=facotry.createDoor(); 
		Motor motor=factory.createMotor();
		motor.setDoor(door);
		
		door.open();
		motor.move(Direction.UP);
	}
}
```

Client 클래스는 인자로 주어진 업체이름에 따라 적절한 부품 객체를 생성한다.

즉, 특정 제조 업체 부품을 이용할 때 제조업체가 바뀌더라도 코드는 코드가 변경될 필요가 없다.

**A2. 새로운 제조 업체의 부품을 지원해야 하는 경우**

부품별 Factory 클래스를 활용하는 경우에서 삼성 부품의 객체를 생성하도록 각 Factory 클래스를 수정해야 했다.

그러나 이제는 부품이 아니라 제조 업체별로 Factory 클래스를 설계했으므로 삼성 부품의 객체를 생성하는 SamsungFactory 클래스만 새롭게 만들면된다.

```java
public class SamsungElevatorFactory extend ElevatorFactory{
	public Motor createMotor(){
		return new SamsungMotor();
	}

	public Door createDoor(){
		return new SamsungDoor();
	}
}

public class HyundaiDoor extends Door{
	protected void dbClose(){
		System.out.println("close Samsung Door");
	}

	protected void doOpen(){
		System.out.println("open Samsung Door");
	}
}

public class SamsungMotor extends Motor{
	protected void moveMotor(Direction direction){
		System.out.println("move Samsung Motor");
	}
}
```

```java
public class client{
	public static void main(String[] args){
		ElevatorFactory factory=null;
		String vendorName=args[0];
		if(vendorName.equalsIgnoreCase("LG")) 
			factory=new LGElevatorFactory();
		else if(vendorName.equalsIgnoreCase("SAMSUNG")) 
			factory=new SamsungElevatorFactory();//삼성부품을 생성하는 삼성팩토리를 이용함
		else
			factory=new HyundaiElevatorFactory();
		

		Door door=facotry.createDoor(); 
		Motor motor=factory.createMotor();
		motor.setDoor(door);
		
		door.open();
		motor.move(Direction.UP);
	}
}
```

특정 제조업체에 따라 적절한 Factory 클래스가 생성된 후에는 이 Factory 클래스를 이용해 구체적인 부품을 생성한다. 이때 제조 업체별로 Factory 클래스를 생성하는 부분은 팩토리 메서드 패턴을 적용해 생성한 것이다.

즉, 구체적인 factory 클래스를 생성하는 팩토리 메서드를 사용함으로써 제조 업체별 Factory 객체를 생성하는 방식을 캡슐화할 수 있다.

제조 업체별 Factory 객체는 각각 1개만 있으면 되므로 싱글턴 패턴으로 설계할 수 있다.

```java
public class ElevatorFactoryFactory{ //팩토리 클래스에 팩토리 메서드 패턴을 적용함 
	public static ElevatorFactory getFactory(VendorID vendorID){
		ElevatorFactory factory=null;
		switch(vendorID){
			case LG:
				factory= new LGElevatorFactory.getInstance(); //LG 팩토리 생성
				break;
			case HYUNDAI:
				factory= new HyundaiElevatorFactory.getInstance(); //Hyundai 팩토리 생성
				break;
			case SAMSUNG:
				factory= new SamsungElevatorFactory.getInstance(); //Samsung 팩토리 생성
				break;
		}
		return motor;
	}
}

//싱글턴 패턴을 적용한 LG 팩토리
public class LGElevatorFactory extend ElevatorFactory{
	private static ElevatorFactory factory;
	private LGElevatorFactory() {}
	
	public static ElevatorFactory getInstance(){
		if(factory==null)
			factory=new LGElevatorFactory();
		
		return factory;
	}

	public Motor createMotor(){
		return new LGMotor();
	}

	public Door createDoor(){
		return new LGDoor();
	}
}
```

```java
public class client{
	public static void main(String[] args){
		String vendorName=args[0];
		VendorId vendorId;
		if(vendorName.equalsIgnoreCase("LG")) 
			vendorId=new VendorId.LG;
		else if(vendorName.equalsIgnoreCase("SAMSUNG")) 
			vendorId=new VendorId.SAMSUNG;
		else
			vendorId=new VendorId.HYNDAI;
		
		ElevatorFactory factory=ElevatorFactoryFactory.getFactory(vendor.ID);

		Door door=facotry.createDoor(); 
		Motor motor=factory.createMotor();
		motor.setDoor(door);
		
		door.open();
		motor.move(Direction.UP);
	}
}
```

## 13.4 추상 팩토리 패턴

**추상 팩토리 패턴은 관련성 있는 여러 종류의 객체를 일관된 방식으로 생성하는 경우에 유용하다.**

**부품별로 Factory를 정의하는 대신 관련 객체들을 일관성 있게 생성할 수 있도록 Factory 클래스를 정의하는 것이 효과적이다**.

- AbstractFactory : 실제 팩토리 클래스의 공통 인터페이스, 각 제품의 부품을 생성하는 기능을 추상 메서드로 정의한다.
    - ElevatorFactory
- ConcreteFactory : 구체적인 팩토리 클래스로 AbstractFactory 클래스의 추상메서드를 오버라이드함으로써 구체적인 제품을 생성한다.
    - LGElevatorFactory
    - HyundnaiElevatorFactory
- AbstractProduct : 제품의 공통 인터페이스
    - Motor
    - Door
- ConcreteProduct : 구체적인 팩토리 클래스에서 생성되는 구체적인 제품
    - LGMotor
    - HyundaiMotor
    - LGDoor
    - HyndaiDoor