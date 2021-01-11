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

# 좋은 테스트의 조건
## FIRST 조건
- [F]ast
- [I]solated
- [R]epeatable
- [S]elf-validating
- [T]imely

### [F]ast
- 의존성을 줄여 테스트를 빠르게 한다.
- 느린 것(DB, 외부 API 등)에 의존하는 부분을 캡슐화하여 테스트 시 완전 분리 가능하도록 redesign한다.
	- 코드를 클린 객체 지향 설계 개념과 맞출수록 단위 테스트 작성이 쉬워진다.(p.107)
### [I]solated
- 테스트 코드와 상호작용하는 코드를 최소화한다.
- 다른 단위테스트에 의존하지 않아야 한다.
	- 순서, 시간에 관계없이 독립적으로 실행할 수 있어야한다.
- SRP(객체 지향 클래스 설계의 단일 책임 원칙): 클래스는 작고 단일한 목적을 가져야 한다.
	- 클래스를 변경해야 할 이유가 하나만 있어야 한다. 
	- 테스트 메소드가 하나 이상의 이유로 깨진다면 테스트를 분할하는 것을 고려(p.109)
	
### [R]epeatable
- 실행할 때마다 결과가 같아야 한다.
- 직접 통제할 수 없는 외부 환경과 격리시켜야 한다.(API 등)(p.109)
	- 테스트 대상을 격리하고 Mock으로 대체 
