## 옵서버 패턴

데이터의 변경이 발생했을 경우, 상대 클래스나 객체에 의존하지 않으면서 데이터 변경을 통보하고자할 때 사용

- Observer 인터페이스: 통보를 받는 객체를 추상화한 것 →통보를 받는 객체(ConcreteObserver)들은 Observer 인터페이스를 implements한다.
- Subject 클래스: 통보를 받는 객체를 관리하는 클래스
- ConcreteObserver: ConcreteSubject에게서 변경을 통보받는 클래스
- ConcreteSubject: 변경 관리 대상이 되는 데이터가 있는 클래스.

✔️옵서버 패턴을 사용하는 상황들

- 탐색기를 여러 개 사용하는 상황에서, 한 탐색기에서 새로운 파일이 추가되거나 기존 파일이 삭제되었을 때 변경사항을 다른 탐색기들에게 알려야 함
- 차량의 연료가 소진될 때까지 주행 가능 거리를 출력하는 클래스: 연료량 클래스는 연료량에 관련된 구체적인 클래스에 직접 의존하지 않는 방식으로 설계

## 예시

입력된 성적 리스트들을 출력하는 프로그램

- ScoreRecord: 성적을 저장하는 객체
    - 리스트로 scores 가지고 있으며 점수를 추가하는 메소드,저장된 점수를 리턴하는 메소드를 가지고 있음
- DataSheetView: 저장된 성적을 출력하는 형식을 만드는 클래스
    - 메소드 displayScores(List<Integer> record, int viewCount): record 리스트를 viewCount 개수만큼 출력
    - 저장된 성적 리스트를 출력하기 때문에 새로운 점수가 추가될 때마다 새로운 데이터가 추가되었다는 것을 통보 받을 필요가 있음

성적 10, 20, 30, 40, 50을 넣어줌

```java
package code1;

import java.util.ArrayList;
import java.util.List;

// 성적 클래스 
class ScoreRecord {
	private List<Integer> scores=new ArrayList<Integer>(); // 점수를 저장하는 리스트
	private DataSheetView dataSheetView; // 목록 형태로 점수를 출력하는 클래스 

	public void setDataSheetView(DataSheetView dataSheetView) {
		this.dataSheetView=dataSheetView;
	}
	
	public void addScore(int score) { // 새로운 점수를 추가함
		scores.add(score); // 리스트에 점수 추가 
		dataSheetView.update(); // scores가 변경되었음을 통보함
		// dataSheetView는 성적의 리스트를 출력하는 역할을 함->성적이 추가될 때마다 리스트에 점수가 추가되었음을 알아야 함 
	}
	
	public List<Integer> getScoreRecord(){ // 점수 리스트를 리턴함
		return scores;
	}
}

// 성적을 출력하는 클래스
// 메소드 displayScores(List<Integer> record, int viewCount): record 리스트를 viewCount 개수만큼 출력
class DataSheetView {
	
	private ScoreRecord scoreRecord;
	private int viewCount; // 몇 개나 가질지 
	
	public DataSheetView(ScoreRecord scoreRecord, int viewCount) { // 생성자에서 초기화해줌
		this.scoreRecord=scoreRecord;
		this.viewCount=viewCount;
	}
	
	public void update() { // 점수의 변경을 통보받는 메소드
		List<Integer> record=scoreRecord.getScoreRecord(); // 점수를 조회함
		displayScores(record, viewCount); // 조회된 점수를 viewCount만큼 출력함
	}

	// record 리스트를 viewCount 개수만큼 출력하는 메소드 
	private void displayScores(List<Integer> record, int viewCount) {
		System.out.println("List of "+viewCount+"entries: ");
		
		for(int i=0;i<viewCount && i<record.size(); i++) { // i가 record와 viewCount보다 작은 동안
			System.out.println(record.get(i)+" "); 
		}
		System.out.println();
	}
}

public class Client {

	public static void main(String[] args) {
		ScoreRecord scoreRecord=new ScoreRecord();
		
		// 3개까지의 점수만 출력함
		DataSheetView dataSheetView=new DataSheetView(scoreRecord, 3);
		
		scoreRecord.setDataSheetView(dataSheetView); 
		
		// 10,20,30,40,50 점수 추가 
		for(int index=1; index<=5; index++) {
			int score=index*10;
			System.out.println("Adding "+score); 
			
			scoreRecord.addScore(score);
		}
	}
}
```

## 예시의 문제점

### 1. 성적을 다른 형태로 출력하고 싶은 경우

입력된 성적의 최대, 최소 값만 출력하고 싶은 경우

- MinMaxView 클래스 추가: 최대, 최소 값을 계산해줌
- OCP의 원칙에 위배됨→ScoreRecord 클래스의 getScore메소드에서 리스트를 리턴하는 방식이었는데 출력 형식이 바뀌면서 MinMaxView를 호출해서 새로운 값이 들어왔으니 다시 계산하라고 해줘야 함

```java
package code9_2;
// 점수가 입력되면 최소/최대의 값만 출력하기 

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ScoreRecord { // 점수 저장하는 클래스
	
	private List<Integer> scores=new ArrayList<Integer>(); // 점수 목록 저장하는 리스트
	private MinMaxView minMaxView; // view를 객체로 가짐
	
	public void setMinMaxView(MinMaxView minMaxView) { // MinMaxView 설정
		this.minMaxView=minMaxView;
	}
	
	public void addScore(int score) { // 인자로 받은 값 점수 리스트에 더해주는 메소드
		scores.add(score);
		minMaxView.update(); // minMaxView에게 점수의 변경을 통보함
	}
	
	public List<Integer> getScoreRecord() { 
		return scores; // 점수 리턴
	}
}

**class MinMaxView** { // 최대, 최소의 값을 구하는 클래스
	private ScoreRecord scoreRecord;
	
	public MinMaxView(ScoreRecord scoreRecord) {
		this.scoreRecord=scoreRecord; // scoreRecord 생성자에서 초기화
	}
	
	public void update() { // 점수의 변경을 통보받았을 때 호출됨
		List<Integer> record=scoreRecord.getScoreRecord(); // 점수를 조회함
		displayMinMax(record); // 최소값과 최대값을 출력한다
	}

	private void displayMinMax(List<Integer> record) { // 최소값, 최대값 출력 메소드
		int min=Collections.min(record, null); // 최소값 가져옴
		int max=Collections.max(record, null); // 최대값 가져옴
		System.out.println("Min: "+min+" Max: "+max); // 출력
	}
}

public class Client {
	
	public static void main(String[] args) {
		ScoreRecord scoreRecord=new ScoreRecord();
		MinMaxView minMaxView=new MinMaxView(scoreRecord);
		
		scoreRecord.setMinMaxView(minMaxView);
		
		for (int i = 1; i <= 5; i++) {
			int score=i*10;
			System.out.println("Adding "+score); // 저장한 점수 출력
			
			scoreRecord.addScore(score); // 점수 저장하기: 10, 20, 30, 40, 50 
		}
	}

}
```

### 2. 동시 혹은 순차적으로 성적을 출력하는 경우

성적이 입력되었을 때 최대 3개 목록, 최대 5개 목록, 최소/최대의 값을 차례로 출력하는 경우 

점수가 입력되면 복수 개의 대상 클래스를 갱신하는 구조로 구현

- DataSheetView: 목록으로 출력
- MinMaxView: 최소/최대 값을 출력
- ScoreRecord: 2개의 목록을 위한 DataSheetView 객체와 MinMaxView라는 1개의 객체 필요

새로운 클래스에 성적 변경을 통보할 때마다 (ex. 평균이나 표준편차를 출력하는 클래스에게도 통보하게 될 때) ScoreRecord는 매번 변경되어야 한다.

10 20 30 40 50 ... 새로운 값이 들어올 때마다 출력해야 하는 값이 달라지기 때문에

```java
package code3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 성적 클래스 
**class ScoreRecord** {
	private List<Integer> scores=new ArrayList<Integer>(); // 점수를 저장하는 리스트
	
	// 복수 개의 dataSheetView: 여러 개의 출력 형태
	private List<DataSheetView> dataSheetViews=new ArrayList<>();
	private MinMaxView minMaxView; // 1개의 MinMaxView 

	**// 성적 출력하는 버전 추가**
	public void addDataSheetView(DataSheetView dataSheetView) {
		dataSheetViews.add(dataSheetView);
	}
	
	public void setMinMaxView(MinMaxView minMaxView) { // minMaxView 설정
		this.minMaxView=minMaxView;
	}
	
	public void addScore(int score) { // 새로운 점수를 추가함
		scores.add(score); // 리스트에 점수 추가 
		
		for(DataSheetView d: dataSheetViews) { **// 각각의 출력 형태마다**
			d.update(); **// 값이 변경되었다는 것 통보** 
			// **성적의 리스트를 출력하는 역할을 함->성적이 추가될 때마다 리스트에 점수가 추가되었음을 알아야 함** 
		}
		
		minMaxView.update(); // MinMaxView에 값의 변경 통보 
	}
	
	public List<Integer> getScoreRecord(){ // 점수 리스트를 리턴함
		return scores;
	}
}

//성적을 출력하는 클래스
//메소드 displayScores(List<Integer> record, int viewCount): record 리스트를 viewCount 개수만큼 출력
class DataSheetView {
	
	private ScoreRecord scoreRecord;
	private int viewCount; // 몇 개나 가질지 
	
	public DataSheetView(ScoreRecord scoreRecord, int viewCount) {// 생성자에서 초기화해줌
		this.scoreRecord=scoreRecord;
		this.viewCount=viewCount;
	}
	
	public void update() { **// 점수의 변경을 통보받는 메소드**
		List<Integer> record=scoreRecord.getScoreRecord(); // 점수를 조회함
		displayScores(record, viewCount); // 조회된 점수를 viewCount만큼 출력함
	}

	// record 리스트를 viewCount 개수만큼 출력하는 메소드 
	private void displayScores(List<Integer> record, int viewCount) {
		System.out.println("List of "+viewCount+"entries: ");
		
		for(int i=0;i<viewCount && i<record.size(); i++) { // i가 record와 viewCount보다 작은 동안
			System.out.println(record.get(i)+" "); 
		}
		System.out.println();
	}
}

class MinMaxView {
	
	private ScoreRecord scoreRecord;
	
	public MinMaxView(ScoreRecord scoreRecord) {
		this.scoreRecord=scoreRecord;
	}
	
	public void update() { **// 점수의 변경을 통보받음**
		List<Integer> record=scoreRecord.getScoreRecord(); // 점수를 조회함
		displayMinMax(record); // 최소 값과 최대 값을 출력함
	}
	
	// 최소값, 최대값 출력함
	public void displayMinMax(List<Integer> record) {
		int min=Collections.min(record, null);
		int max=Collections.max(record, null);
		System.out.println("Min: "+min+" Max: "+max+"\n");
	}
}

public class Client {
	public static void main(String[] args) {
		ScoreRecord scoreRecord=new ScoreRecord();
		
		// 3개 목록의 DataSheetView 생성
		DataSheetView dataSheetView3=new DataSheetView(scoreRecord, 3);
		
		// 5개 목록의 DataSheetView 생성
		DataSheetView dataSheetView5=new DataSheetView(scoreRecord, 5);
		MinMaxView minMaxView=new MinMaxView(scoreRecord); 
		
		scoreRecord.addDataSheetView(dataSheetView3); // 3개 목록 DataSheetView 추가
		scoreRecord.addDataSheetView(dataSheetView5);// 5개 목록 DataSheetView 추가
		scoreRecord.setMinMaxView(minMaxView); // MinMaxView 설정
		
		// 10, 20, 30, 40, 50 추가됨
		for (int i = 1; i <= 5; i++) {
			int score=i*10;
			System.out.println("Adding: "+score);
			
			scoreRecord.addScore(score);
		}
	}
}
```

## 해결책

리스트를 ㅊ

성적 통보 대상이 변경되더라도 ScoreRecord 클래스를 계속 재사용할 수 있게 해주도록 하기.

→ 공통 기능을 상위 클래스 및 인터페이스로 일반화하고 이를 활용해 ScoreRecord 구현

```java
package code4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

**interface Observer** { // 추상화된 통보 대상
	public abstract void update(); // 데이터의 변경을 통보할 때 
}

**abstract class Subject** { // 추상화된 변경 관심 대상 데이터
	private List<Observer> observers=new ArrayList<Observer>(); // 데이터의 변경을 통보할 대상들 목록 
	
	// 통보할 대상을 추가하는 메소드
	public void attach(Observer observer) {
		observers.add(observer);
	}
	
	// 통보할 대상을 제거하는 메소드
	public void detach(Observer observer) {
		observers.remove(observer);
	}
	
	public void notifyObservers() {
		for(Observer o:observers) { // 옵서버들에게 변경 통보
			o.update();
		}
	}
}

**class ScoreRecord** extends Subject { // 구체적인 변경 감시 대상 데이터
	private List<Integer> scores=new ArrayList<>();
	
	public void addScore(int score) {
		scores.add(score);
		
		// 데이터가 변경되면 Subject 클래스의 notifyObservers 메서드를 호출해서
		// 각 옵서버들에게 데이터의 변경을 통보함
		notifyObservers();
	}

	public List<Integer> getScoreRecord(){
		return scores;
	}
}

// DataSheetView는 옵서버의 기능, 즉 update 메서드를 구현함으로써 통보 대상이 됨
**class DataSheetView** implements Observer {

	private ScoreRecord scoreRecord;
	private int viewCount; // 몇 개나 가질지 
	
	public DataSheetView(ScoreRecord scoreRecord, int viewCount) { // 생성자에서 초기화해줌
		this.scoreRecord=scoreRecord;
		this.viewCount=viewCount;
	}
	
	@Override
	public void update() { // 점수의 변경을 통보받는 메소드
		List<Integer> record=scoreRecord.getScoreRecord(); // 점수를 조회함
		displayScores(record, viewCount); // 조회된 점수를 viewCount만큼 출력함
	}

	// record 리스트를 viewCount 개수만큼 출력하는 메소드 
	private void displayScores(List<Integer> record, int viewCount) {
		System.out.println("List of "+viewCount+"entries: ");
		
		for(int i=0;i<viewCount && i<record.size(); i++) { // i가 record와 viewCount보다 작은 동안
			System.out.println(record.get(i)+" "); 
		}
		System.out.println();
	}
}

**class MinMaxView** implements Observer {
	private ScoreRecord scoreRecord;
	
	public MinMaxView(ScoreRecord scoreRecord) {
		this.scoreRecord=scoreRecord;
	}
	
	public void update() {// 점수의 변경을 통보받음
		List<Integer> record=scoreRecord.getScoreRecord();
		displayMinMax(record); // 최소 값과 최대 값 출력
	}
	
	private void displayMinMax(List<Integer> record) {
		int min=Collections.min(record, null);
		int max=Collections.max(record, null);
		System.out.println("Min: "+min+" Max"+max);
	}
}

public class Client {
	public static void main(String[] args) {
		ScoreRecord scoreRecord=new ScoreRecord();
		DataSheetView dataSheetView3=new DataSheetView(scoreRecord, 3); // 3개짜리 추가
		DataSheetView dataSheetView5=new DataSheetView(scoreRecord, 5);
		MinMaxView minMaxView=new MinMaxView(scoreRecord);
		
		scoreRecord.attach(dataSheetView3); // 3개 목록 Observer로 추가함
		scoreRecord.attach(dataSheetView5); // 5개 목록 Observer로 추가함
		scoreRecord.attach(minMaxView); // minMaxView를 Observer로 추가함
		
		for(int index=1; index<= 5; index++) {
			int score=index*10;
			System.out.println("Adding "+score);
			scoreRecord.addScore(score);
		}
		
	}
}
```