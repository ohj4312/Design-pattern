<aside>
💡 기본 기능에 추가로 여러 기능들을 동적으로 조합해 구현할 수 있는 패턴

</aside>

## 패턴 적용 전

### 상황

내비게이션 SW에서 도로를 표시하는 기능을 만들자. 

- 기본 기능 - 도로를 간단한 선으로 표시
- 추가 기능 - 도로의 차선표시

기본 기능도 함께 사용하기 위해 **상속**을 사용!

```java
class RoadDisplay {   // 기본 도로 표시 클래스
	public void draw() {
		System.out.println("기본 도로 표시");
	}
}
class RoadDisplayWithLane extends RoadDisplay  {  // 기본 도로 표시 + 차선 표시 클래스
	public void draw() {
		super.draw();  // 도로선을 그리고
		drawLane();    // 차선 표시를 하도록
	}
	public void drawLane() {
		System.out.println("차선 표시");
	}
}
public class Client {
	public static void main(String[] args) {
		RoadDisplay road = new RoadDisplay();
		road.draw();
		RoadDisplayWithLane roadWithLane = new RoadDisplayWithLane();
		roadWithLane.draw();
	}

	public void draw() {
		// TODO Auto-generated method stub
		
	}
}
```

### 문제점

1. 또 다른 도로 표시 기능을 추가로 구현해야 하는 경우 - 교통량을 표시! → 적절한 방법!
    - 기본 도로 표시 클래스를 상속받아 정의!
    
    ```java
    class RoadDisplayWithTraffic extends RoadDisplay {
    	public void draw() {
    		super.draw();
    		drawTraffic();
    	}
    	public void drawTraffic() {
    		System.out.println("교통량 표시");
    	}
    }
    ```
    
    ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/d2e6cbe9-84c4-4386-8358-da66fa649443/Untitled.png)
    
2. **여러 가지 추가 기능을 조합**해야 하는 경우 → 안됌!
    - 상속을 통해 하기 위해서는 각 기능 조합별로 하위 클래스를 구현해야 함
    - 추가기능이 n개인 경우, 조합을 통해 2^n개의 하위 클래스가 필요

### 해결책

- DisplayDecorator 추상 클래스를 생성해 추가 기능을 각각 클래스로 만들어 해당 추상클래스를 상속받아 구현하도록 함.
- DisplayDecorator로 이전의 기능 클래스를 저장할 수 있도록 함(연쇄적)
- 각 추가기능 클래스에서 draw메서드 호출 시, 가장 먼저 추가된 draw부터 차례대로 실행된다.(DisplayDecorator에서 가지고 있는 멤버변수 타입이 가장 상위 추상 클래스인 Display이므로)
- 모든 객체들의 접근이 Display 클래스를 통해 이루어짐! → 일관성 있는 방식으로 도로 정보를 표시할 수 있음(여기서는 순차적으로 draw가 실행되도록!)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/052d32bc-c31d-4fd8-be58-edd417acefd4/Untitled.png)

```java
public abstract class Display {
	public abstract void draw();
}
```

```java
public class RoadDisplay extends Display {
	public void draw() {
		System.out.println("기본 도로 표시");
	}
}
```

```java
public abstract class DisplayDecorator extends Display{
	private Display decoratedDisplay;   // 이전에 추가한 기능을 실행해주기 위해
	
	public DisplayDecorator(Display decoratedDisplay) {
		this.decoratedDisplay = decoratedDisplay;
	}
	
	public void draw() {
		decoratedDisplay.draw();   // 이전의 기능을 수행
	}
}
```

```java
public class LaneDecorator extends DisplayDecorator{
	public LaneDecorator(Display decoratedDisplay) {
		super(decoratedDisplay);
	}
	public void draw() {
		super.draw();    // DisplayDecorator의 draw 먼저 호출 - 이전의 기능을 수행하도록
		drawLane();      // 추가한 기능을 수행
	}
	public void drawLane() {
		System.out.println("차선 표시");
	}
}
```

```java
public class TrafficDecorator extends DisplayDecorator {
	public TrafficDecorator(Display decoratedDisplay) {
		super(decoratedDisplay);
	}

	public void draw() {
		super.draw();
		drawTraffic();
	}

	public void drawTraffic() {
		System.out.println("교통량 표시");
	}

}
```

```java
public class CrossingDecorator extends DisplayDecorator {
	public CrossingDecorator(Display decoratedDisplay) {
		super(decoratedDisplay);
	}

	public void draw() {
		super.draw();
		drawTraffic();
	}

	public void drawTraffic() {
		System.out.println("교차로 표시");
	}
}
```

```java
public class Client {
	public static void main(String[] args) {
		Display road = new RoadDisplay();
		road.draw();
		Display roadWithLane = new LaneDecorator(new RoadDisplay());
		roadWithLane.draw();
		Display roadWithTraffic = new TrafficDecorator(new RoadDisplay());
		roadWithTraffic.draw();
		System.out.println("---------------------------------------");
		Display roadWithCrossingLaneAndTraffic = new TrafficDecorator(new LaneDecorator(new CrossingDecorator(new RoadDisplay())));
		roadWithCrossingLaneAndTraffic.draw();
		
	}
}
```

## 데커레이터 패턴

### 특징

- 기본 기능에 추가할 수 있는 기능의 종류가 많은 경우, 각 추가 기능을 Decorator클래스로 정의한 후 필요한 Decorator 객체를 조합함으로써 추가 기능의 조합을 설계하는 방식
- 추가 기능의 수가 많을수록 효과가 큼
- 추가 기능의 조합을 동적으로 생성하는 것도 가능함

```java
public static void main(String[] args) {
	Display road2 = new RoadDisplay();
	for(String decoratorName : args) {
		if(decoratorName.equalsIgnoreCase("Lane")) {		// 대소문자 구분 X
			road = new LaneDecorator(road);
		} else if(decoratorName.equalsIgnoreCase("Traffic")) {
			road = new TrafficDecorator(road);
		} else if(decoratorName.equalsIgnoreCase("Crossing")) {
			road = new CrossingDecorator(road);
		}
	}
	road.draw();
}
```

### 컬레보레이션

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/f5e91697-d94c-4035-8f0e-29a947ef9645/Untitled.png)

- Component : 기본 기능(ConcreteComponent)과 추가기능(Decorator)의 공통 기능을 정의
    - operation(draw)
- ConcreteComponent : 기본 기능을 구현하는 클래스
- Decorator : 많은 수가 존재하는 구체적인 Decorator의 공통 기능을 제공
- ConcreteDecoratorA, ComcreteDecoratorB : Decorator의 하위 클래스로 기본 기능에 추가되는 개별적인 기능을 뜻함

### 순차 다이어그램

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/94caf02d-6156-4c00-b1c1-689ae5187841/Untitled.png)

```java
Component c = new ConcreateDecoratorB(new ConcreteDecoratorA(new ConcreteComponent()));
c.operation();
```

- 순서
    1. Client가 operation을 호출
    2. ConcreateDecoratorB의 부모 operation 호출
    3. ConcreateDecoratorA의 부모 operation 호출
    4. ConcreateDecorator의 operation 실행
    5. ConcreateDecoratorA의 addedBehavior() 실행
    6. ConcreateDecoratorB의 addedBehavior() 실행
- 동작 순서는 바꿀 수 있음!
    - 본인 행위가 먼저 실행되도록
    - 자신의 addedBehavior 메서드를 먼저 호출한 후 Component의 operation 메서드를 호출하는 방식으로 구현

### 추가

**Decorator Pattern에서 기능 제거하기**

- get으로 이전이전기능의 참조값을 반환해서 LinkedList의 node처럼 이어주면 되지 않을까?
- 질문답변 - 링크드리스트 개념 생각하면 될거래

[Removing Decorator From The Decorated Object](https://coderanch.com/t/511530/engineering/Removing-Decorator-Decorated-Object)

**Strategy Pattern VS Decorator Pattern**

- Strategy Pattern - 핵심 기능(알고리즘)을 바꿈
- Decorator Pattern - 기존 기능에 영향을 주지 않고 새로운 기능을 추가

[Strategy Pattern V/S Decorator Pattern](https://stackoverflow.com/questions/26422884/strategy-pattern-v-s-decorator-pattern)