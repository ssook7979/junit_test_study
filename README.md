# 테스트 조직
- AAA: 준비(Arrange) - 실행(Act) - 단언(Assert)
	- 준비(Arrange)
		- 테스트에 적절한 상태로 시스템 설정
			- 객체 생성, API 호출 등등
	- 실행(Act)
		- 테스트 코드 실행 (보통 단일 메소드 호출)
	- 단언(Assert)
		- 코드 반환값, 객체 상태 검사
		- 다른 객체 사이의 의사소통 검사
- 메소드가 아닌 **동작** 테스트
- 테스트와 프로덕션 코드의 관계
	- 코드 분리: maven의 경우 프로덕션 코드와 테스트 코드의 폴더를 분리하고 package는 동일하게 설정하여 프로덕션 코드를 패키징할 경우 테스트 코드가 포함되지 않게 한다.
	- 테스트 코드가 프로덕션 코드와 지나치게 결합하는 것은 좋지 않음. 
		- 프로덕션 코드의 작은 변화에도 테스트가 깨질 수 있음
		- 테스트 코드가 내부 데이터(private) 및 동작까지 접근하려 하면 설계에 변형이 올 수 있음  
- 테스트 이름
	- doingSomeOperationGeneratesSomeResult
	- someResultOccursUnderSomeCondition
	- BDD 형식(given-when-then)
		- givenSomeContextWhenDoingSomeBehaviorThenSomeResultOccurs
		- whenDoingSomeBehaviorThenSomeResultOccurs (given part 제거)
- 다른 고려사항
	- 지역 변수 이름 개선하기
	- 의미 있는 상수 도입하기
	- 햄크레스트 단언 사용하기
	- 커다란 테스트를 작게 나누어 집중적인 테스트 만들기
	- 테스트 군더더기들을 도우미 메서드와 @BeforeEach 메서드로 이동하기
- Before After annotation
	- 각 테스트 전후 실행
	- 순서가 보장되지 않음
- 테스트 무시
	- @Ignore

# 좋은 테스트의 조건: FIRST
- [F]ast
- [I]solated
- [R]epeatable
- [S]elf-validating
- [T]imely

## [F]ast
- 의존성을 줄여 테스트를 빠르게 한다.
- 느린 것(DB, 외부 API 등)에 의존하는 부분을 캡슐화하여 테스트 시 완전 분리 가능하도록 redesign한다.
	- 코드를 클린 객체 지향 설계 개념과 맞출수록 단위 테스트 작성이 쉬워진다.(p.107)
## [I]solated
- 테스트 코드와 상호작용하는 코드를 최소화한다.
- 다른 단위테스트에 의존하지 않아야 한다.
	- 순서, 시간에 관계없이 독립적으로 실행할 수 있어야한다.
- SRP(객체 지향 클래스 설계의 단일 책임 원칙): 클래스는 작고 단일한 목적을 가져야 한다.
	- 클래스를 변경해야 할 이유가 하나만 있어야 한다. 
	- 테스트 메소드가 하나 이상의 이유로 깨진다면 테스트를 분할하는 것을 고려(p.109)
	
## [R]epeatable
- 실행할 때마다 결과가 같아야 한다.
- 직접 통제할 수 없는 외부 환경과 격리시켜야 한다.(API 등)(p.109)
	- 테스트 대상을 격리하고 Mock으로 대체 
	
## [S]elf-valildating
- 완전 자동화 된 단위테스트
- Infinitest: 코드 변화를 감지하여 변경된 부분만 백그라운드에서 테스트 실행
- 젠킨스, TeamCity 등 CI(Countinuous Integration) 도구를 사용하여 자동 관리 시스템을 구축할 수 있음

## [T]imely
- 테스트 작성 및 실행할 적절한 시기는?

# Right-BICEP: 무엇을 테스트할 것인가?
- [Right] 결과가 올바른가?
- [B]oundary Conditions
- [I]nverse Relationship
- [C]ross-check
- [E]rror conditions
- [P]erformance characteristics

## Right
- 기대한 결과를 산출하는지 검증할 수 있어야 한다.
- '코드가 정상적으로 작동한다면 어떻게 알 수 있는가?'에 대한 답을 할 수 있을 때까지 테스트 작성을 미룬다.
	- 혹은 일단 작성하고 명확해졌을때 개선
## [B]oundary Conditions
- 보호절을 넣어 checked exception을 발생
- 보호절을 넣으면 군더더기가 많아지므로 제한 사항을 문서화하는 테스트를 작성한다.
- CORRECT 조건
## [I]nverse Relationship
- '덧셈 -> 뺄셈으로 검증' 과 같이 역 관계로 검증을 할 수 있는가
- 비수학적 예시) 객체로부터 추출한 list가 기대값과 같은지 검증하기 위해 기대값의 요소들을 리스트로 생성하여 검증
## [C]ross-check
- 같은 결과를 내는 검증된 로직으로 테스트
## [E]rror conditions
다음의 오류가능성 및 환경적인 제약사항을 생각해보아야 한다.(p.133)
- 메모리가 가득 찰 때
- 디스크 공간이 가득 찰 때
- 시간 dependency
- 네트워크 가용성 및 오류들
- 시스템로드
- 제한된 색상 팔레트
- 매우 높거나 낮은 비디오 해상도
## [P]erformance characteristics
성능조건은 기준에 부합하는가?
p.134 - p.136
단위 성능 측정에 관한 내용
# CORRECT: 테스트 경계조건
## [C]onformance(준수)
- 값이 기대한 양식을 준수하고 있는가?
	-가능한 경우의 수를 테스트해본다.
- 입력값이 양식을 준수하는지 검증하는 것을 가능한 적게하여 불필요한 검사를 최소화한다.
## [O]rdering
- 컬렉션과 같이 다수의 object를 결과로 가져올 때 기대했던 순서대로 반환하는가?
## [R]ange
- 기대한 범위를 벗어나지 않는가?
	- object 멤버변수의 범위: 캡슐화 하여 property가 범위를 벗어나면 exception을 발생하는지 검증
		- 필요한 값을 기본형에 저장하는 것을 지양해야 한다(primitive obsession, 기본형 중독 코드 냄새)
	- 내부로직 실행 이후 object 멤버변수의 범위: After method를 통해 검증
## [R]eference
- 범위를 넘어서는 것을 참조하고 있ㄴ느가
- 어떤 외부의존성이 있는가
- 특정 상태의 객체에 의존하고 있는가
- 필수의존성 외의 다른 조건
등 을 고려해야 한다.

### 예시
- 고객이 어떠한 동작을 하기 전에 로그인 상태인가
- stack pop()을 호출하기 전에 stack이 비어있지 않은 상태인가

### pre/postconditions
- 사전조건(preconditions)을 만족하지 않을 때 gracefully 동작하는가
	- transmission 예시에서는 Gear를 DRIVE -> PARK로 변환하는 것을 허용해서는 안된다.
		- ignoresShiftToParkWhileInDrive 참고
- 사후조건(postconditions)을 만족하는가
	- 메소드의 반환값이 기대값과 일치하는가
	- 부작용으로 발생한 값의 변화가 기대값과 일치하는가
		- allowsShiftToParkWhenNotMoving에서 breakToStop을 호출하면 속도가 0이 됨
## [E]xistence
- 값이 null, 0, 비어있는지, 네트워크가 다운되었는지 등에 대해 검증해야 한다.

## [C]ardinality
- 0-1-n 경계 조건을 고려한다.
- simplify design with zero one many(http://agileinaflash.blogspot.com/2012/06/simplify-design-with-zero-one-many.html)

## [T]ime
고려해야할 것
- 상대적 시간(시간 순서)
- 절대적 시간(측정된 시간)
- 동시성 문제들

### 예시
- 상대적 시간
	- 메소드 호출 순서
		- login - logout
		- open - read - close
	- 타임아웃
		- 수명이 짧은 자원에 대해 코드가 얼마나 기다릴 수 있는가
		- 무한대기에 빠질 수도 있음
- 절대적 시간
	- 시간에 대한 의존성으 주입하여 컨트롤 가능하도록 한다.
	- 테스트 예시 있음
- 동시성 문제들(p.163 요약)
	- 동시에 같은 객체를 다수의 스레드가 접근한다면
		- 전역 혹은 인스턴스 수준의 데이터나 메서드에 동기화 해야하나?
	- 파일 혹은 하드웨어에 외적인 접근은 어떻게 처리해야 하나?
	- 클라이언트에 동시성 요구 사항이 있다면 다수의 클라이언트 스레드를 보여주는 테스트를 작성할 필요가 있다.
# 코드 리팩토링
- 낮은 중복성과 높은 명확성을 가지는 것이 목표
- 명확성
	- 이름 짓기(코드의 의도를 전달하는 가장 좋은 수단)
# SOLID 클래스의 설계 원칙
- [S]RP
- [O]CP
- [L]SP
- [I]SP
- [D]IP
# 단위 테스트의 유지 보수 비용
- 코드 중복 제거: 도우미 메소드 사용
- 작은 코드 조각들을 단일 메소드로 추출
- 클래스를 최소화(SRP)
- private 메소드를 테스트해야할 필요성이 느껴진다면 클래스가 필요이상으로 커진 것.
	- 메소드들을 새 클래스로 옮기고 public으로 만든다.
(중요) 시스템 설계 및 코드 품질이 낮아질수록 단위 테스트의 유지 보수 비용은 증가한다. (p.195)
# 다른 설계에 관한 생각들
- 생성자에서 실제적인 작업(연산하여 저장 등)을 하는 것을 지양한다.
