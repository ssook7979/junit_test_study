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
	

