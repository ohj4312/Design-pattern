# SOLID 원칙

cf) SOLID 원칙은 객체지향 소프트웨어 설계에만 한정된 것이 아닌 절차적 프로그래밍 기법에도 적용할 수 있다.

<br>

# 단일 책임 원칙 ( SRP : Single Responsibility Principle )

말 그대로 해석하면 단 하나의 책임만을 가져야 한다는 의미이다.


<br>

# 책임의 의미

객체 지향 설계 관점에서 책임의 기본 단위는 객체를 지칭한다.

즉, **객체는 단 하나의 책임만을 가져야 한다는 의미이다.**

책임은 해야하는 것, 할 수 있는 것, 해야 하는 것을 잘 할 수 있는 것이다.

객체에 책임을 할당할 때 작업을 잘할 수 있는 객체에 할당하고, 그 객체는 책임에 수반되는 모든일을 자신만이 수행할 수 있어야 한다.

<br>

## 변경

SPR를 따르는 실효성 있는 설계가 되려면 책임을 좀 더 현실적인 개념으로 파악해야 한다.

설계 원칙을 학습하는 이유는 유연하고확장성이 있도록 시스템 구조를 설계하기 위해서이다.

좋은 설계란 기본적으로 시스템에 새로운 요구사항이나 변경이 있을 때 가능한 한 영향을 받는 부분을 줄여야 한다.

어떤 클래스가 잘 설계되었는지 판단하려면 언제 변경되어야 하는지 **"변경 이유"**를 물어보는 것이 좋다.

**책임을 많이 질수록 클래스 내부에서 서로 다른 역할을 수행하는 코드끼리 강하게 결합될 가능성이 높아진다. ( 단일 책임의 원칙을 지켜야 하는 이유 )**

<br>

## 단일 책임이 아닌 Student 클래스

```java
public class Student{
	public void getCourses(){ // 수강과목 조회 }
	public void addCourse(Course C){ // 수강과목 추가 }
	public void save(){ // 데이터베이스에 저장 }
	public Student load(){ //데이터베이스 조회 }
	public void printOnReportCard(){ //성적표 출력 }
	public void printOnAttendanceBook(){ //출석부 출력 }
}
```

문제점 : Student 클래스는 너무 많은책임을 수행해야 한다. 결합도가 높아진다.

Student 클래스 단일 책임 원칙 적용  : 수강과목 추가, 조회의 책임만 수행하도록 한다.


<br>


## 책임 분리

Student 클래스는 여러 책임을 수행하므로 Student 클래스의 도움을 필요로 하는 코드도 많다.

이 때, Student 클래스에 변경사항이 생기면 Student클래스를 사용하는 코드와 전혀 관계가 없더라도 직접 또는 간접적으로 사용하는 모든 코드를 다시 테스트해야 한다.

회귀테스트 비용을 줄이기 위한 한 방법은 시스템에 변경사항이 발생했을 때 영향 받는 부분을 적게 하는 것이다.

 CF) 회귀 테스트 : 어떤 변화가 있을 때 해당 변화가 기존 시스템의 기능에 영향을 주는지 평가하는 테스트

**책임 분리 : 한 클래스에 너무 많은 책임을 부여하지 말고 단 하나의 책임만 수행하도록해 변경 사유가 될 수 있는 것을 하나로 만든다.**

<br>

## 책임 분리 적용 예

```java
public class Student{
	public void getCourses(){ // 수강과목 조회 }
	public void addCourse(Course C){ // 수강과목 추가 }
}

public class StudentDAO{
	public void save(){ // 데이터베이스에 저장 }
	public Student load(){ //데이터베이스 조회 }
}

public class ReportCard{
	public void printOnReportCard(){ //성적표 출력 }
}

public class AttBook{
	public void printOnAttendanceBook(){ //출석부 출력 }
}
```

클래스의 책임을 적절하게 분담하도록 변경하여 변화가 생겼을때 영향을 최소화할 수 있다.

예를 들어 데이터베이스의 스키마가 변화될 때 StudentDAO 클래스, 혹은 이 클래스를 사용하는 클래스만 영향을 받는다.


<br>


## 산탄총 수술

**한 클래스가 여러가지 책임을 가진 경우 뿐만 아니라 하나의 책임이 여러개의 클래스들로 분산되어 있는 경우도 단일 책임 원칙을 위배하는 경우이다.**

이처럼 여러개의 클래스에 책임이 분산되어 있는 경우에 " 산탄총 수술"이라는 용어를 사용한다.

산탄총 수술일 경우 어떠한 변경에 대해서 해당 변경에 책임을 갖는 클래스들을 하나하나 모두 변경하지 않으면 프로그램이 정상적으로 동작하지 않고 에러가 발생한다.

**하나의 책임이 여러개의 클래스로 분리되어 있는 예는 로깅, 보안, 트랜잭션과 같은 횡단 과점으로 분류할 수 있는 기능이 대표적이다.**

횡단 관점에 속하는 기능을 대부분 시스템 핵심 기능 안에 포함되는 부가 기능이다.

이 부가 기능에 변경 사항이 발생하면 해당 부가 기능을 실행하는 모든 핵심기능에도 변경 사항이 적용되어야 한다.

이를 해결하기 위해 부가 기능을 별개의 클래스로 분리해 책임을 담당하게 한다. 

즉, 여러 곳에 흩어진 공통 책임을 한 곳에 모으면서 **응집도를 높인다.**



<br>


## 관전지향 프로그래밍과 횡단 관심 문제

횡단 관심 문제를 해결하는 방법으로는 관심(관점) 지향 프로그래밍기법이 있다.

SW 설계는의 기본 원칙은 **응집도는 높고 결합도는 낮게하는 것이** 좋다. 

장점 : 재사용성 향상, 유지보수 용이 

<br>


# 개방-폐쇄 원칙 ( OCP : Open-Closed Principle )

기존의 코드를 변경하지 않으면서 기능을 추가할 수 있도록 설계가 되어야 한다.

앞선 예제 코드에서 도서관 대여 명무에 학생 대여 기록을 출력하는 경우 도서관 대여 명부 클래스 생성하여 이 기능을 이용하도록 할 수 있다.

그러나 이것은 개방-폐쇄 원칙을 위반한다.


<br>


## OCP를 위배하지 않는 설계

```java
public class Student{
	public void getCourses(){ // 수강과목 조회 }
	public void addCourse(Course C){ // 수강과목 추가 }
}

public class StudentDAO{
	public void save(){ // 데이터베이스에 저장 }
	public Student load(){ //데이터베이스 조회 }
}

public Interfase Printable{
	void print()
}

public class ReportCard implements Printable{
	@Override
	void print(){ //성적표 출력 }
}

public class AttBook implements Printable{
	@Override
	void print(){ //출석부 출력 }
}

public Class SomeClient implements Printable{
	@Override
	void print(//도서관 대여명부 출력);
}
```

OCP를 위반하지 않은 설계를 할 때 가장 중요한 것은 **무엇이 변하는 것인지, 무엇이 변하지 않아야 할 것인지를 구분해야 한다는 점이다. ( 클래스 = 변화의 단위 )**

변해야 하는 것은 쉽게 변할 수 있게하고 변하지 않아야 할 것은 변하는 것에 영향을 받지 않게 해야 한다.

 OCP 를 보는 또 하나의 관점은 **클래스를 변경하지 않고도(closed) 대상 클래스의 환경을 변경할 수 있는(open) 설계가 되어야 한다는 것이다.**

이는 특히 단위 테스트를 수행할 때 매우 중요하다.

단위 테스트는 빠른 시간에 자주 테스트 되어야 한다. 따라서 테스트 대상 가능이 사용하는 실제 서비스를 흉내내는 각자 객체를 만들어 테스트의 효율성을 높일 필요가 있다. ( Ex - 네트워크,DB connect )

실제 서비스에서 사용할 객체를 그대로 테스트할 때 위험이 따라는 경우가 있다.

또한 테스트 대상 기능이 특정 상태에 의존해서 동작할 수 있다는 점도 고려해야 한다.

이때 모의객체를 이용하면 위험에서 벗어날 수 있고, 특정 상태를 가상으로 만들 수 있다.

모의객체 keyword : 더미 객체, 테스트 스텁, 테스트 스파이, 가짜 객체, 목 객체


<br>


## 예제1 - 문제

```java
public class FuelTankMonitoring{
	public void checkAndWanrn(){
		
		if(checkFuelTank(){
			giveWarningSignal();
		}
	}

	private boolean checkFuelTank(){}
	private void giveWarningSignal(){}
}
```

FuelTankMonitoring 클래스는 로켓의 연료 탱그를 검사해 특정 조건에 맞지 않으면 관리자에게 경고 신호를 보내주는 기능이 있다. 연료 탱크를 검사하는 방식과 경고를 보내는 방식이 변경될 가능성이 큰 경우에 대비해 위의 코드를 수정한다.


<br>


## 예제1 - Solution

```java
public class FuelTankMonitoring{
	public void checkAndWanrn(){
		
		if(checkFuelTank(){
			giveWarningSignal();
		}
	}

	protected boolean checkFuelTank(){};
	protected void giveWarningSignal(){};
}

public class fuelTankMonitoringWith extends FuelTankMonitoring{

	protected boolean checkFuelTank(){};
	protected void giveWarningSignal(){};
}
```

checkFuelTank()와 giveWarningSignal()의 구체적 행위방식이 변하는 것이다.

따라서 새로운 행위 방식을 기존의 코드에 영향을 주지 않고 추가하기 위해 이 두 메서드를 개별 클래스에서 정의한다.

protected 접근제한자로 바꾸고, 상속관계로 변경하므로써 fuelTankMonitoringWith 에 변하는 대상 메소드들을 넣어주므로써 기존 코드에 영향을 주지않고(closed) 자식클래스의 Override 메소드를 통해 기능을 변경시킬 수 있다(open).


<br>


## 예제2

```java
import java.util.Calendar;

public class TimeReminder{
	private MP3 m;

	public void reminder(){
		Calendar cal=Calendar.getInstance();
		m=new MP3();
		int hour=cal.get(Calendar.HOUR_OF_DAY);

		if(hour>=22){
			m.playSong();
		}
	}
}
```

오후 10시가 되면 MP3를 작동시켜 음악을 연주한다. 그러나 이 코드가 제대로 작동하늕 ㅣ테스트하려면 저녁 110시까지 기다려야 한다.

OCP를 적용해 이문제를 해결하는 코드를 작성한다.


<br>


## 예제2 - Solution

```java
import java.util.Calendar;

public interface TimeProvider { //인터페이스 도입
	public void setHours(int hours);
	public int getTime();
}

public class FakeTimeProvider implements TimProvider{ // TimProvider 테스트 스텁
	private Calendar cal;
	
	public FakeTimeProvider(){
		cal=Calendar.getInstance();
	}

	public FakeTimeProvider(int hours){
		cal=Calendar.getInstance();
		setHours(hours);
	}

	public void setHours(int hours){
		cal.set(Calendar.HOUR_OF_DAY,hours); // 주어진 시간으로 시간 설정
	}

	public int getTime(){
		return cal.get(Calendar.HOUR_OF_DAY); // 현재 시간 반환
	}
}

public class TimeReminder{
	TimeProvider tProv;
	MP3 m = new MP3()';
	
	public void setTimeProvider(TimeProvider tProv){
		this.tProv = tProv; //테스트 스텁이나 실제 시간을 제공하는 인스턴스를 주입
	}
	public void reminder(){
		int hour=cal.get(Calendar.HOUR_OF_DAY);
		if(hour>=22){
			m.playSong();
		}
	}
}

public class Main{
	public static void main(String[] args){
		TimeReminder sut=new TimeReminder();
		TimeProvider tProvStub = new FakeTimeProvider();
		tProvStub.setHours(18);
		sut.setTimeProvider(tProvStub);
	}
}
```

Main을 실행하면 현재시간이 언제인지 상관없이 오후 10시가 되도록 시간을 설정해 MP3가 올바르게 호출하는지 테스트할 수 있다.

실제 시간을 사용해서 테스트하는 방법은 매우 번거롭고 시간도 오래 걸리므로 시간을 원하는 대로 설정해 이용할 수 있는 방법을 찾아야한다. 

시간을 제공하는 인터페이스를 만들고 이 인터페이스에서 파생한 2개의 클래스를 만든다. 한 클래스는 진짜 시간을 제공하는 클래스이고 다른 한 클래스는 테스트에 사용할 수 있게 임의의 시간을 원하는대로 설정할 수 있는 클래스다.

이러한 설계는 **TimeRemider 클래스를 전혀 수정하지 않고 주변의 환경을 바꿀 수 있다**.


<br>


# 리스코프 치환 원칙 ( LSP : Liskov Submstitution Principle )

MIT 컴퓨터공학과 리스코프 교수가 1987년에 제안한 원칙이다.

일반화 관계에 대한 내용이며, 자식 클래스는 최소한 자신의 부모 클래스에서 가능한 행위는 수행할 수 있어야 한다는 의미이다. 

**즉, LSP는 부모 클래스와 자식 클래스 사이의 행위가 일관성이 있어야 한다는 의미이다.**

LSP를 만족하면 프로그램에서 **부모클래스의 인스턴스 대신 자식 클래스의 인스턴스로 대체해도 프로그램의 의미는 변화되지 않는다.**


<br>

## 참고 ) 일반화 관계 : is a Kind of 관계

객체 지향 관점에서 부모 클래스의 인스턴스 대신에 자식 클래스의 인스턴스를 별다른 변경 없이 그대로 사용할 수 있을 때 성립한다.


<br>

## 행위 일관성을 만족하는 방법

부모 클래스의 행위를 명확하게 정의 하기 위해 어떤 클래스의 행위를 일종의 방정식 형태로 기술하여 자식 클래스의 인스턴스가 이 방정식을 만족하는지 점검한다.

```java
public class Bag{
	private int price;

	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}
}
```

Bag 클래스의 행위 : 가격은 설정된 가격 그대로 조회된다.

```java
// 모든 Bag 객체 b와 모든 정수 값 p에 대한 방정식이라고 생각하자.
[b.setPrice(p)].getPrice() == p;
```

Bag 클래스의 행위를 손상하지 않고 일관성 있게 실행하는 클래스를 만드는 가장 직접적이고 직관적인 방법은 슈퍼 클래스에서 상속받은 메서드들이 서브 클래스에 오버라이드되지 않도록 하면 된다.

```java
public class DiscountedBag extends Bag{
	private double discountedRate=0;

	public void setDisounted(double discountedRate){
		this.discountedRate = discountedRate;
	}

	public void applyDiscount(int price){
		super.setPrice(price-(int)(discountedRate*price));
}
```

Bag을 상속받아 가격 설정, 조회하는 기능을 사용하고, 할인율을 설정해서 할인된 가격을 계산하는 기능이 추가되었다.

```java
public class MainBag{
	public static void main(String[] args){
		Bag b1=new Bag();
		Bag b2=new Bag();

		b1.setPirce(50000);
		System.out.println(b1.getPrice());

		b2.setPrice(b1.getPrice());
		System.out.println(b2.getPrice());
}
```

```java
public class MainDiscBag{
	public static void main(String[] args){
		DiscountedBag b1=new DiscountedBag();
		DiscountedBag b2=new DiscountedBag();

		b1.setPirce(50000);
		System.out.println(b1.getPrice());

		b2.setPrice(b1.getPrice());
		System.out.println(b2.getPrice());
}
```

MainBag 과 MainDiscBag 클래스의 실행 결과는 동일하다. (재정의 되지 않았기 때문)

이는 현재의 DiscountedBag 클래스와 Bag 클래스의 상속관계가 LSP를 위반하지 않는다는 것을 뜻한다.


<br>


## LSP 원칙 위배

```java
public class DiscountedBag extends Bag{
	private double discountedRate=0;

	public void setDisounted(double discountedRate){
		this.discountedRate = discountedRate;
	}

	@Override
	public void setPrice(int price){
		super.setPrice(price-(int)(discountedRate*price));
}
```

앞서 정리해둔 방정식에도 어긋나며, 이제 MainBag 과 MainDiscBag 클래스의 실행 결과는 동일하지 않게 된다. 즉 , 재정의로 인해 부모 클래스의 행위와 일관되지 않으므로 LSP를 만족하지 않는다.

피터 코드의 상속 규칙에서 "서브 클래스가 슈퍼 클래스의 책임을 무시하거나 재정의하지 않고 확장만 수행한다" 는 규칙이 있다.

이것은 슈퍼 클래스의 메서드를 오버라이드하지 않는 것과 같은 의미이다.

**피터 코드의 상속 규칙을 지키는 것은 LSP를 만족시키는 하나의 방법에 해당한다**.


<br>


# 의존 역전 원칙 ( DIP : Dependency Inversion Priciple )

객체 사이에 서로 도움을 주고받으면 의존 관계가 발생한다. 의존 역전 원칙은 이러한 의존관계를 맺을 때의 가이드에 해당한다.

**DIP는 의존관계를 맺을 때 변화하기 쉬운 것 또는 자주 변화하는 것보다는 변화하기 어려운 것, 거의 변화가 없는 것에 의존하는 원칙이다.**

정책, 전략과 같은 어떤 큰흐름이나 개념 같은 추상적인 것은 변화기 어려운 것에 해당하고 구체적인 방식, 사물 등과 같은 것은 변하기 쉬운 것으로 구분하면 좋다.

객체지향 관점에서는 이와 같이 변하기 어려운 추상적인 것들을 표현하는 수단으로 추상클래스와 인터페이스가 있다.

**DIP를 만족하려면 어떤 클래스가 도움을 받을 때 구체적인 클래스보다는 인터페이스나 추상클래스와 의존 관계를 맺도록 설계해야 한다.**

DIP를 만족하는 설계는 변화에 유연한 시스템이 된다.

DIP를 만족하면 의존성 주입이라는 기술로 변화를 쉽게 수용할 수 있는 코드를 작성할 수 있다.

cf ) 의존성 주입이란 클래스 외부에서 의존되는 것을 대상 객체의 인스턴스 변수에 주입하는 기술


<br>


## 예제 - 의존성 주입

아이가 장난감을 가지고 노는 경우, 어떤 경우에는 로봇 장난감을 가지고 놀고 어떤 경우에는 자동차 장난감을 가지고 논다. 

이때 가지고 노는 장난감은 변하기 쉬운 것, 장난감을 가지고 논다는 사실은 변하기 어려운 것이다.

의존성 주입(DI)를 이용하면 대상 객체를 변경하지 않고도 외부에서 대상 객체의 외부 의존 객체를 바꿀 수 있다.

```java
public class Kid{
	private Toy toy;

	public void setToy(Toy toy){ //의존성 주입
		this.toy=toy;
	}

	public void play(){
		System.out.println(toy.toString());
	}
}
```

```java
public abstract class Toy{ //추상 클래스 ( 인터페이스로도 가능 ) 
	public abstract String toString();
}
```

```java
public class Robot extends Toy{
	@Override
	public String toString(){
		return "Robot";
	}
```

```java
public class Lego extends Toy{
	@Override
	public String toString(){
		return "Lego";
	}
}
```

```java
public class Main{
	public static void main(String[] args){
		//로봇 장난감을 가지고 노는 경우
		Toy t=new Robot();
		Kid k1=new Kid();
		k1.setToy(t);
		k1.play();
		
		//레고 장난감을 가지고 노는 경우
		Toy l=new Lego();
		Kid k2=new Kid();
		k2.setToy(l);
		k2.play();

	}
}
```

Kid, Toy, Robot등 기존의 코드에 전혀 영향을 받지 않고 외부에서 주입받는 장난감만 변경시켜서 가지고 노는 장난감을 변경시킬 수 있다.


<br>


## 예제 변형 - Kid 와 Robot 클래스가 연관관계를 갖는 경우

```java
public class Kid{
	private Robot toy;

	public void setToy(Robot toy){ 
		this.toy=toy;
	}

	public void play(){
		System.out.println(toy.toString());
	}
}

public class Main{
	public static void main(String[] args){
		Robot t=new Robot();
		Kid k=new Kid();
		k.setToy(t);
		k.play();
	}
}
```

**Lego로 장난감을 바꾸고 싶다면?**

```java
public class Kid{
	private Lego toy;//변경

	public void setToy(Lego toy){ //변경
		this.toy=toy;
	}

	public void play(){
		System.out.println(toy.toString());
	}
}

public class Main{
	public static void main(String[] args){
		Lego t=new Lego(); // 변경
		Kid k=new Kid();
		k.setToy(t);
		k.play();
	}
}
```

위 코드처럼 Lego를 받기 위해서 Kid 클래스 내부 로직 자체에 수정이 일어나게 된다.

혹은 Kid가 Robot, Lego를 포함한 다른 장난감들을 사용할 수 있도록, 각각의 장난감에 해당하는 필드와 setToy메소드를 오버로딩해야 한다.

즉, 장난감을 바꿀 때마다 코드를 계속 바꾸어야 하기 때문에 **DIP 위반이 OCP 위반을 초래한다.**


<br>


# 인터페이스 분리 원칙 ( ISP : Interface Segregation Principle )

클라이언트 관점에서 클라이언트 자신이 이용하지 않는 기능에는 영향을 받지 않아야 한다.

## 예제 - 프린터, 팩스, 복사기


<br>


## ISP 원칙 적용 전>

프린터, 팩스 복사기 기능이 모두 포함된 복합기

```java
public class 복합기{
	public void copy(){ //복사 } 
	public void fax(){ //팩스 }
	public void print(){ //프린터 }
}
```

클라이언트가 이 모든 기능을 동시에 사용하는 경우는 거의 없다.

따라서 프린터 기능만 이용하는 클라이언트가 팩스 기능의 변경으로 인해 발생하는 문제의 영향을 받지 않도록 해야 한다.

**클라이언트와 무관하게 발생한 변화로 클라이언트 자신이 영향을 받지 않으려면 범용의 인터페이스보다는 클라이언트에 특화된 인터페이스를 사용해야 한다.**

**즉, ISP는 인터페이스를 클라이언트에 특화되도록 분리시키라는 설계 원칙이다.**


<br>


## ISP 원칙 적용 후>

복합기를 사용하는 객체들마다 자신이 관심을 갖는 메서드들만 있는 인터페이스를 제공받도록 설계한다. 인터페이스가 일종의 방화벽 역할을 수행해 클라이언트는 자시니 사용하지 않는 메서드에 생긴 변화로 인한 영향을 받지 않게 된다.

```java
public class 복합기 implements 복사기,팩스,프린터{
	public void copy(){ //복사 } 
	public void fax(){ //팩스 }
	public void print(){ //프린터 }
}
```

```java
public interface 복사기{
	public void copy();
}
```

```java
public interface 팩스{
	public void fax();
}
```

```java
public interface 프린터{
	public void print();
}
```

프린터 클라이언트, 복사 클라이언트, 팩스 클라이언트는 이제 자신이 관심을 갖는 메서드만 있는 인터페이스의 영향만 받는다.


<br>


## SRP ( 단일 책임 원칙 ) 와 ISP ( 인터페이스 분리 원칙 ) 사이의 관계

어떤 클래스가 단일 책임을 수행하지 않고 여러 책임을 수행하게 되면 방대한 메서드를 가진 비대한 클래스가 될 가능성이 커지며, 당연히 비대한 인터페이스가 제공될 것이다.

이렇게 **비대한 클래스를 SRP에 따라 단일 책임을 갖는 여러 클래스들로 분할하고 각자의 인터페이스를 제공한다면 ISP도 만족할 수 있다.**

그러나 **SRP를 만족하더라도 ISP를 반드시 만족한다고는 할 수 없다.**

예를 들어 게시판의 글쓰기, 읽기, 수정, 삭제를 위한 메서드가 제공되는 클래스가 있다고 할 때, 클라이언트에 따라서 게시판의 일부 기능만 사용할 수도 있다.

이런 겨우 게시판 클래스는 게시판에 관련된 책임을 수행하므로 SRP를 만족하지만 클래스의 모든 메서드가 들어있는 인터페이스가 클라이언트에 상관없이 사용된다면 ISP에 위배된다.
