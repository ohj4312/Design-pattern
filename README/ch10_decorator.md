<aside>
๐ก ๊ธฐ๋ณธ ๊ธฐ๋ฅ์ ์ถ๊ฐ๋ก ์ฌ๋ฌ ๊ธฐ๋ฅ๋ค์ ๋์ ์ผ๋ก ์กฐํฉํด ๊ตฌํํ  ์ ์๋ ํจํด

</aside>

## ํจํด ์ ์ฉ ์ 

### ์ํฉ

๋ด๋น๊ฒ์ด์ SW์์ ๋๋ก๋ฅผ ํ์ํ๋ ๊ธฐ๋ฅ์ ๋ง๋ค์. 

- ๊ธฐ๋ณธ ๊ธฐ๋ฅ - ๋๋ก๋ฅผ ๊ฐ๋จํ ์ ์ผ๋ก ํ์
- ์ถ๊ฐ ๊ธฐ๋ฅ - ๋๋ก์ ์ฐจ์ ํ์

๊ธฐ๋ณธ ๊ธฐ๋ฅ๋ ํจ๊ป ์ฌ์ฉํ๊ธฐ ์ํด **์์**์ ์ฌ์ฉ!

```java
class RoadDisplay {   // ๊ธฐ๋ณธ ๋๋ก ํ์ ํด๋์ค
	public void draw() {
		System.out.println("๊ธฐ๋ณธ ๋๋ก ํ์");
	}
}
class RoadDisplayWithLane extends RoadDisplay  {  // ๊ธฐ๋ณธ ๋๋ก ํ์ + ์ฐจ์  ํ์ ํด๋์ค
	public void draw() {
		super.draw();  // ๋๋ก์ ์ ๊ทธ๋ฆฌ๊ณ 
		drawLane();    // ์ฐจ์  ํ์๋ฅผ ํ๋๋ก
	}
	public void drawLane() {
		System.out.println("์ฐจ์  ํ์");
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

### ๋ฌธ์ ์ 

1. ๋ ๋ค๋ฅธ ๋๋ก ํ์ ๊ธฐ๋ฅ์ ์ถ๊ฐ๋ก ๊ตฌํํด์ผ ํ๋ ๊ฒฝ์ฐ - ๊ตํต๋์ ํ์! โ ์ ์ ํ ๋ฐฉ๋ฒ!
    - ๊ธฐ๋ณธ ๋๋ก ํ์ ํด๋์ค๋ฅผ ์์๋ฐ์ ์ ์!
    
    ```java
    class RoadDisplayWithTraffic extends RoadDisplay {
    	public void draw() {
    		super.draw();
    		drawTraffic();
    	}
    	public void drawTraffic() {
    		System.out.println("๊ตํต๋ ํ์");
    	}
    }
    ```
    
    ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/d2e6cbe9-84c4-4386-8358-da66fa649443/Untitled.png)
    
2. **์ฌ๋ฌ ๊ฐ์ง ์ถ๊ฐ ๊ธฐ๋ฅ์ ์กฐํฉ**ํด์ผ ํ๋ ๊ฒฝ์ฐ โ ์๋!
    - ์์์ ํตํด ํ๊ธฐ ์ํด์๋ ๊ฐ ๊ธฐ๋ฅ ์กฐํฉ๋ณ๋ก ํ์ ํด๋์ค๋ฅผ ๊ตฌํํด์ผ ํจ
    - ์ถ๊ฐ๊ธฐ๋ฅ์ด n๊ฐ์ธ ๊ฒฝ์ฐ, ์กฐํฉ์ ํตํด 2^n๊ฐ์ ํ์ ํด๋์ค๊ฐ ํ์

### ํด๊ฒฐ์ฑ

- DisplayDecorator ์ถ์ ํด๋์ค๋ฅผ ์์ฑํด ์ถ๊ฐ ๊ธฐ๋ฅ์ ๊ฐ๊ฐ ํด๋์ค๋ก ๋ง๋ค์ด ํด๋น ์ถ์ํด๋์ค๋ฅผ ์์๋ฐ์ ๊ตฌํํ๋๋ก ํจ.
- DisplayDecorator๋ก ์ด์ ์ ๊ธฐ๋ฅ ํด๋์ค๋ฅผ ์ ์ฅํ  ์ ์๋๋ก ํจ(์ฐ์์ )
- ๊ฐ ์ถ๊ฐ๊ธฐ๋ฅ ํด๋์ค์์ draw๋ฉ์๋ ํธ์ถ ์, ๊ฐ์ฅ ๋จผ์  ์ถ๊ฐ๋ draw๋ถํฐ ์ฐจ๋ก๋๋ก ์คํ๋๋ค.(DisplayDecorator์์ ๊ฐ์ง๊ณ  ์๋ ๋ฉค๋ฒ๋ณ์ ํ์์ด ๊ฐ์ฅ ์์ ์ถ์ ํด๋์ค์ธ Display์ด๋ฏ๋ก)
- ๋ชจ๋  ๊ฐ์ฒด๋ค์ ์ ๊ทผ์ด Display ํด๋์ค๋ฅผ ํตํด ์ด๋ฃจ์ด์ง! โ ์ผ๊ด์ฑ ์๋ ๋ฐฉ์์ผ๋ก ๋๋ก ์ ๋ณด๋ฅผ ํ์ํ  ์ ์์(์ฌ๊ธฐ์๋ ์์ฐจ์ ์ผ๋ก draw๊ฐ ์คํ๋๋๋ก!)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/052d32bc-c31d-4fd8-be58-edd417acefd4/Untitled.png)

```java
public abstract class Display {
	public abstract void draw();
}
```

```java
public class RoadDisplay extends Display {
	public void draw() {
		System.out.println("๊ธฐ๋ณธ ๋๋ก ํ์");
	}
}
```

```java
public abstract class DisplayDecorator extends Display{
	private Display decoratedDisplay;   // ์ด์ ์ ์ถ๊ฐํ ๊ธฐ๋ฅ์ ์คํํด์ฃผ๊ธฐ ์ํด
	
	public DisplayDecorator(Display decoratedDisplay) {
		this.decoratedDisplay = decoratedDisplay;
	}
	
	public void draw() {
		decoratedDisplay.draw();   // ์ด์ ์ ๊ธฐ๋ฅ์ ์ํ
	}
}
```

```java
public class LaneDecorator extends DisplayDecorator{
	public LaneDecorator(Display decoratedDisplay) {
		super(decoratedDisplay);
	}
	public void draw() {
		super.draw();    // DisplayDecorator์ draw ๋จผ์  ํธ์ถ - ์ด์ ์ ๊ธฐ๋ฅ์ ์ํํ๋๋ก
		drawLane();      // ์ถ๊ฐํ ๊ธฐ๋ฅ์ ์ํ
	}
	public void drawLane() {
		System.out.println("์ฐจ์  ํ์");
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
		System.out.println("๊ตํต๋ ํ์");
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
		System.out.println("๊ต์ฐจ๋ก ํ์");
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

## ๋ฐ์ปค๋ ์ดํฐ ํจํด

### ํน์ง

- ๊ธฐ๋ณธ ๊ธฐ๋ฅ์ ์ถ๊ฐํ  ์ ์๋ ๊ธฐ๋ฅ์ ์ข๋ฅ๊ฐ ๋ง์ ๊ฒฝ์ฐ, ๊ฐ ์ถ๊ฐ ๊ธฐ๋ฅ์ Decoratorํด๋์ค๋ก ์ ์ํ ํ ํ์ํ Decorator ๊ฐ์ฒด๋ฅผ ์กฐํฉํจ์ผ๋ก์จ ์ถ๊ฐ ๊ธฐ๋ฅ์ ์กฐํฉ์ ์ค๊ณํ๋ ๋ฐฉ์
- ์ถ๊ฐ ๊ธฐ๋ฅ์ ์๊ฐ ๋ง์์๋ก ํจ๊ณผ๊ฐ ํผ
- ์ถ๊ฐ ๊ธฐ๋ฅ์ ์กฐํฉ์ ๋์ ์ผ๋ก ์์ฑํ๋ ๊ฒ๋ ๊ฐ๋ฅํจ

```java
public static void main(String[] args) {
	Display road2 = new RoadDisplay();
	for(String decoratorName : args) {
		if(decoratorName.equalsIgnoreCase("Lane")) {		// ๋์๋ฌธ์ ๊ตฌ๋ถ X
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

### ์ปฌ๋ ๋ณด๋ ์ด์

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/f5e91697-d94c-4035-8f0e-29a947ef9645/Untitled.png)

- Component : ๊ธฐ๋ณธ ๊ธฐ๋ฅ(ConcreteComponent)๊ณผ ์ถ๊ฐ๊ธฐ๋ฅ(Decorator)์ ๊ณตํต ๊ธฐ๋ฅ์ ์ ์
    - operation(draw)
- ConcreteComponent : ๊ธฐ๋ณธ ๊ธฐ๋ฅ์ ๊ตฌํํ๋ ํด๋์ค
- Decorator : ๋ง์ ์๊ฐ ์กด์ฌํ๋ ๊ตฌ์ฒด์ ์ธ Decorator์ ๊ณตํต ๊ธฐ๋ฅ์ ์ ๊ณต
- ConcreteDecoratorA, ComcreteDecoratorB : Decorator์ ํ์ ํด๋์ค๋ก ๊ธฐ๋ณธ ๊ธฐ๋ฅ์ ์ถ๊ฐ๋๋ ๊ฐ๋ณ์ ์ธ ๊ธฐ๋ฅ์ ๋ปํจ

### ์์ฐจ ๋ค์ด์ด๊ทธ๋จ

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/94caf02d-6156-4c00-b1c1-689ae5187841/Untitled.png)

```java
Component c = new ConcreateDecoratorB(new ConcreteDecoratorA(new ConcreteComponent()));
c.operation();
```

- ์์
    1. Client๊ฐ operation์ ํธ์ถ
    2. ConcreateDecoratorB์ ๋ถ๋ชจ operation ํธ์ถ
    3. ConcreateDecoratorA์ ๋ถ๋ชจ operation ํธ์ถ
    4. ConcreateDecorator์ operation ์คํ
    5. ConcreateDecoratorA์ addedBehavior() ์คํ
    6. ConcreateDecoratorB์ addedBehavior() ์คํ
- ๋์ ์์๋ ๋ฐ๊ฟ ์ ์์!
    - ๋ณธ์ธ ํ์๊ฐ ๋จผ์  ์คํ๋๋๋ก
    - ์์ ์ addedBehavior ๋ฉ์๋๋ฅผ ๋จผ์  ํธ์ถํ ํ Component์ operation ๋ฉ์๋๋ฅผ ํธ์ถํ๋ ๋ฐฉ์์ผ๋ก ๊ตฌํ

### ์ถ๊ฐ

**Decorator Pattern์์ ๊ธฐ๋ฅ ์ ๊ฑฐํ๊ธฐ**

- get์ผ๋ก ์ด์ ์ด์ ๊ธฐ๋ฅ์ ์ฐธ์กฐ๊ฐ์ ๋ฐํํด์ LinkedList์ node์ฒ๋ผ ์ด์ด์ฃผ๋ฉด ๋์ง ์์๊น?
- ์ง๋ฌธ๋ต๋ณ - ๋งํฌ๋๋ฆฌ์คํธ ๊ฐ๋ ์๊ฐํ๋ฉด ๋ ๊ฑฐ๋

[Removing Decorator From The Decorated Object](https://coderanch.com/t/511530/engineering/Removing-Decorator-Decorated-Object)

**Strategy Pattern VS Decorator Pattern**

- Strategy Pattern - ํต์ฌ ๊ธฐ๋ฅ(์๊ณ ๋ฆฌ์ฆ)์ ๋ฐ๊ฟ
- Decorator Pattern - ๊ธฐ์กด ๊ธฐ๋ฅ์ ์ํฅ์ ์ฃผ์ง ์๊ณ  ์๋ก์ด ๊ธฐ๋ฅ์ ์ถ๊ฐ

[Strategy Pattern V/S Decorator Pattern](https://stackoverflow.com/questions/26422884/strategy-pattern-v-s-decorator-pattern)