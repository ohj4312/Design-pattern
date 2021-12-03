## 예제: 엘리베이터 스케줄링 방법

여러 대의 엘리베이터가 있는 경우

→엘리베이터 내부에서 버튼을 눌렀을 때에는 해당 사용자가 탄 엘리베이터를 이동시킴

→엘리베이터 외부에서 버튼을 누른 경우에는 여러 대의 엘리베이터 중 하나를 선택해서 이동시킴

스케줄링: 여러 대의 엘리베이터가 있는 경우, 엘리베이터들 중 하나를 선택하는 것

- ElevatorManager
    
    이동요청을 처리하는 클래스
    
    ```java
    package code1;
    
    import java.util.ArrayList;
    import java.util.List;
    
    public class ElevatorManager {
    	// 이동 요청을 처리하는 클래스
    
    	private List<ElevatorController> controllers; // 각 엘리베이터의 이동을 책임지는 컨트롤러
    	private ThroughputScheduler scheduler; // 엘리베이터를 스케줄링하기 위한 ThroughputScheduler 객체 가짐
    	
    	// 주어진 수만큼의 ElevatorController 생성하는 메소드
    	public ElevatorManager(int controllerCount) {
    		controllers=new ArrayList<ElevatorController>(controllerCount);
    		
    		for(int i=0;i<controllerCount;i++) {
    			ElevatorController controller=new ElevatorController(i);
    			controllers.add(controller);
    		}
    		
    		scheduler=new ThroughputScheduler(); // 엘리베이터 스케줄링 처리 객체 생성
    	}
    	
    	// 엘리베이터를 선택하고 이동시키는 메소드
    	public void requestElevator(int destination, Direction direction) {
    		// ThroughputScheduler를 이용해 엘리베이터를 선택함
    		int selectedElevator=scheduler.selectElevator(this, destination, direction);
    		
    		// 선택된 엘리베이터를 이동시킴
    		controllers.get(selectedElevator).gotoFloor(destination);
    	}
    	
    }
    ```
    
- ElevatorController
    
    ```java
    package code1;
    
    public class ElevatorController {
    	// 각 엘리베이터의 이동을 책임지는 클래스
    
    	private int id; // 엘리베이터 아이디
    	private int curFloor; // 현재 층
    	
    	public ElevatorController(int id) { // 아이디를 초기화시키는 메소드
    		this.id=id; 
    		curFloor=1; // 엘리베이터를 1층에 놓는 걸로 초기화
    	}
    
    	public void gotoFloor(int destination) {
    		System.out.println("Elevator ["+id+"] Floor: "+curFloor); // 원래 있던 층에서
    		
    		curFloor=destination; // 현재 층 갱신, 즉 주어진 목적지 층으로 엘리베이터가 이동함
    		System.out.println(" ==>"+curFloor); // 이동한 층
    	}
    }
    ```
    
- ThroughputScheduler
    
    엘리베이터 스케줄링
    
    ```java
    package code1;
    
    public class ThroughputScheduler {
    
    	public int selectElevator(ElevatorManager elevatorManager, int destination, Direction direction) {
    		return 0; // 임의로 0번째 엘리베이터를 선택함
    	}
    
    }
    ```
    
- Direction
    
    방향 의미하는 enum
    
    ```java
    package code1;
    
    public enum Direction {
    	UP, DOWN
    }
    ```
    

## 문제점

다른 스케줄링 전략을 사용해야 하는 경우

프로그램 실행 중에 스케줄링 전략을 변경해야 하는 경우

- 예시: 오전시간에는 대기 시간 최소화 전략을 사용하고 오후에는 처리량 최대화 전략을 사용해야 한다면?
    
    →스트래티지 패턴 사용함
    
    ResponseTimeScheduler: 또다른 스케줄링 전략 클래스
    
    ThroughputScheduler와 ResponseTimeScheduler를 추상화하는 ElevatorScheduler 인터페이스를 두고 사용하는 전략에 따라 ThroughputScheduler로 구체화할 것인지, ResponseTimeScheduler로 구체화할 것인지를 정함
    
    변경된 ElevatorManager 클래스
    
    ```java
    package code2;
    
    import java.util.ArrayList;
    import java.util.Calendar;
    import java.util.List;
    
    public class ElevatorManager {
    	// 이동 요청을 처리하는 클래스
    
    	private List<ElevatorController> controllers; // 각 엘리베이터의 이동을 책임지는 컨트롤러
    	
    	// 주어진 수만큼의 ElevatorController 생성하는 메소드
    	public ElevatorManager(int controllerCount) {
    		controllers=new ArrayList<ElevatorController>(controllerCount);
    		
    		for(int i=0;i<controllerCount;i++) {
    			ElevatorController controller=new ElevatorController(i+1); // 변경됨!!
    			controllers.add(controller);
    		}
    		
    	}
    	
    	// 엘리베이터를 선택하고 이동시키는 메소드
    	public void requestElevator(int destination, Direction direction) {
    		**ElevatorScheduler scheduler; // ElevatorScheduler: 새로운 인터페이스.** 
    		
    		int hour=Calendar.getInstance().get(Calendar.HOUR_OF_DAY); // 몇시인지 받아옴
    		
    		**if(hour<12) { // 오전인 경우
    			scheduler=new ResponseTimeScheduler(); // ResponseTimeScheduler 클래스 이용함
    		}
    		else {// 오후인 경우
    			scheduler=new ThroughputScheduler(); // ThroughputScheduler 이용함 
    		}**
    
    		int selectedElevator=scheduler.selectElevator(this,destination, direction);
    		controllers.get(selectedElevator).gotoFloor(destination);
    	}
    	
    }
    ```
    
    문제점: ElevatorManager 클래스는 엘리베이터 스케줄링 전략이 변경될 때마다 엘리베이터 스케줄링 전략을 지원하는 클래스를 작성해야 하고, requestElevator 메서드도 수정되어야 한다. 
    

## 해결책

### 팩토리 메서드 방식1

주어진 기능을 제공하는 적절한 클래스 생성 작업을 별도의 클래스/메서드로 분리

- SchedulingStrategyId
    
    어떤 객체를 생성할 것인지 나타내는 enum
    
    ```java
    public enum SchedulingStrategyId { RESPONSE_TIME, THROUGHPUT, DYNAMIC }
    ```
    
- SchedulerFactory 클래스
    
    인자로 어떤 객체를 생성해줄지 넘겨줌→그에 따라서 스케줄링 전략 객체를 생성하도록 함
    
    ```java
    package code3;
    
    import java.util.Calendar;
    
    public class SchedulerFactory {
    	
    	// 스케줄러 객체 생성해주는 메소드
    	// 인자로 어떤 객체를 생성해줄지 enum 타입으로 넘겨줌
    	public static ElevatorScheduler getScheduler(SchedulingStrategyId strategyId) {
    		
    		ElevatorScheduler scheduler=null; // 스케줄러 인터페이스 객체
    
    		switch(strategyId) {
    		case RESPONSE_TIME:
    			scheduler=new ResponseTimeScheduler.getInstance();
    			break;
    			
    		case THROUGHPUT:
    			scheduler=new ThroughputScheduler.getInstance();
    			break;
    			
    		case DYNAMIC: // 오전에는 대기 시간 최소화 전략, 오후에는 처리량 최대화 전략
    			int hour=Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    			if(hour<12) // 오전
    				scheduler=new ResponseTimeScheduler.getInstance();
    			else // 오후
    				scheduler=new ThroughputScheduler.getInstance();
    			break;
    		}
    		return scheduler;
    	}
    }
    ```
    
- ElevatorManager
    
    ```java
    package code3;
    
    import java.util.ArrayList;
    import java.util.Calendar;
    import java.util.List;
    
    public class ElevatorManager {
    	// 이동 요청을 처리하는 클래스
    
    	private List<ElevatorController> controllers; // 각 엘리베이터의 이동을 책임지는 컨트롤러
    	private SchedulingStrategyId strategyId; // 어떤 객체를 생성할 것인지 
    	
    	// 주어진 수만큼의 ElevatorController 생성하는 메소드
    	public ElevatorManager(int controllerCount, SchedulingStrategyId strategyId) {
    		controllers=new ArrayList<ElevatorController>(controllerCount);
    		
    		for(int i=0;i<controllerCount;i++) {
    			ElevatorController controller=new ElevatorController(i+1);
    			controllers.add(controller);
    		}
    		this.strategyId=strategyId; // 스케줄링 전략 설정함
    	}
    	
    	// 엘리베이터를 선택하고 이동시키는 메소드
    	public void requestElevator(int destination, Direction direction) {
    		**ElevatorScheduler scheduler=SchedulerFactory.getScheduler(strategyId)**; // 스케줄링 전략 객체 생성
    		System.out.println(scheduler);
    		int selectedElevator=scheduler.selectElevator(this, destination, direction); // 엘리베이터 선택
    		controllers.get(selectedElevator).gotoFloor(destination);// 이동시킴
    	}
    	
    }
    ```
    
- 기타 사항
    
    동일 스케줄링 방식이면 여러 스케줄링 객체를 생성하지 않고 한번 사용한 것을 계속 쓰는 것이 더 나을 수 있음→ResponseTimeScheduler 클래스와 ThroughputScheduler 클래스에 싱글턴 패턴 적용하기
    
    ```java
    package code3;
    
    public class ResponseTimeScheduler implements ElevatorScheduler {
    
    	private static ElevatorScheduler instance;
    	private ResponseTimeScheduler() {}
    	
    	public static ElevatorScheduler getInstance() {
    		if(instance==null)
    			instance=new ResponseTimeScheduler();
    		return instance;
    	}
    	
    	@Override
    	public int selectElevator(ElevatorManager elevatorManager, int destination, Direction direction) {
    		// TODO Auto-generated method stub
    		return 1;
    	}
    
    }
    ```
    
    ```java
    package code3;
    
    public class ThroughputScheduler implements ElevatorScheduler{
    
    	private ThroughputScheduler() {}
    	private static ElevatorScheduler instance;
    	
    	public static ElevatorScheduler getInstance() {
    		if(instance==null)
    			instance=new ThroughputScheduler();
    		return instance;
    	}
    	
    	public int selectElevator(ElevatorManager elevatorManager, int destination, Direction direction) {
    		return 0; // 임의로 0번째 엘리베이터를 선택함
    	}
    
    }
    ```
    

### 팩토리 메서드 방식2 → 개선된 버전

해당 스케줄링 전략에 따라 엘리베이터를 선택하는 클래스를 ElevatorManager클래스의 하위 클래스로 정의

- ElevatorManager
    
    어떤 스케줄링 객체를 생성할 건지 나타내던 SchedulingStrategyId 삭제
    
    클래스를 abstract로 정의
    
    abstract 메소드 정의: getScheduler → 위에서 스케줄링 전략 객체 생성 코드를 대체
    
    ```java
    package code4;
    
    import java.util.ArrayList;
    import java.util.Calendar;
    import java.util.List;
    
    public abstract class ElevatorManager {
    	// 이동 요청을 처리하는 클래스
    
    	private List<ElevatorController> controllers; // 각 엘리베이터의 이동을 책임지는 컨트롤러
    	
    	// 주어진 수만큼의 ElevatorController 생성하는 메소드
    	public ElevatorManager(int controllerCount) {
    		controllers=new ArrayList<ElevatorController>(controllerCount);
    		
    		for(int i=0;i<controllerCount;i++) {
    			ElevatorController controller=new ElevatorController(i+1);
    			controllers.add(controller);
    		}
    	}
    	
    	**protected abstract ElevatorScheduler getScheduler();** 
    	
    	// 엘리베이터를 선택하고 이동시키는 메소드
    	public void requestElevator(int destination, Direction direction) {
    		ElevatorScheduler scheduler=getScheduler(); // 스케줄링 전략 객체 생성
    		System.out.println(scheduler);
    		int selectedElevator=scheduler.selectElevator(this, destination, direction); // 엘리베이터 선택
    		controllers.get(selectedElevator).gotoFloor(destination);// 이동시킴
    	}
    	
    }
    ```
    

- ElevatorManagerWithThroughputScheduling
    
    ElevatorManager 상속받아서 객체 생성해줌
    
    ```java
    package code4;
    
    public class ElevatorManagerWithThroughputScheduling extends ElevatorManager{
    
    	public ElevatorManagerWithThroughputScheduling(int controllerCount) {
    		super(controllerCount);
    		// TODO Auto-generated constructor stub
    	}
    
    	@Override
    	protected ElevatorScheduler getScheduler() {
    		**ElevatorScheduler scheduler=ThroughputScheduler.getInstance();**
    		return scheduler;
    	}
    	
    }
    ```
    

- ElevatorManagerWithResponseTimeScheduling
    
    ElevatorManager 상속받아서 객체 생성해줌
    
    ```java
    package code4;
    
    public class ElevatorManagerWithResponseTimeScheduling extends ElevatorManager {
    
    	public ElevatorManagerWithResponseTimeScheduling(int controllerCount) {
    		super(controllerCount);
    		// TODO Auto-generated constructor stub
    	}
    
    	@Override
    	protected ElevatorScheduler getScheduler() {
    		ElevatorScheduler scheduler=ResponseTimeScheduler.getInstance();
    		return null;
    	}
    	
    }
    ```
    

- ElevatorManagerWithDynamicScheduling
    
    ElevatorManager 상속받아서 객체 생성
    
    ```java
    package code4;
    
    import java.util.Calendar;
    
    import code3.ResponseTimeScheduler;
    import code3.ThroughputScheduler;
    
    public class ElevatorManagerWithDynamicScheduling extends ElevatorManager{
    
    	public ElevatorManagerWithDynamicScheduling(int controllerCount) {
    		super(controllerCount);
    	}
    
    	@Override
    	protected ElevatorScheduler getScheduler() {
    		
    		ElevatorScheduler scheduler=null;
    		
    		int hour=Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    		if(hour<12) // 오전
    			scheduler=new ResponseTimeScheduler.getInstance();
    		else // 오후
    			scheduler=new ThroughputScheduler.getInstance();
    		return scheduler;
    	}
    	
    }
    ```
