# java-convenience-store-precourse

## 📝  편의점 프로그램 로직
1. 편의점의 보유하고 있는 상품을 출력한다.
2. 사용자에게 구매할 상품과 수량을 입력받는다.
3. 구매할 상품에 대해서 프로모션 상품이 있는지 확인한다.
    1. 프로모션 상품이 있다면
        1. 고객이 프로모션 수량만큼 가져온 경우 (ex. 1+1인 상품에 대해서 2개 이상 가지고 온 경우)
            1. 프로모션 상품의 재고 ≥ 구매할 수량
                - 프로모션의 재고를 모두 사용한다.
            2. 구매할 수량 > 프로모션 상품의 재고
                - 일반 제품의 개수에 대해서 정가로 결제할지 여부에 대한 안내메세지를 출력한다.
                    - Yes : 프로모션의 재고를 모두 사용하고, 일반 제품을 구매한다.
                    - No : 프로모션의 재고만큼만 구매한다.
        2. 고객의 프로모션 수량만큼 가져오지 않은 경우(ex. 1+1인 상품에 대해서 1개만 가지고 온경우)
            - 혜택에 대한 안내메시지를 출력한다.
            - 혜택 받을 유무에 대해서 사용자에게 입력받는다.
                - Yes : 혜택을 받은 수량만큼 구매량을 증가시킨다.
                  (ex. 1+1 제품을 1개 구매하려했지만 2개 구매)
                - No : 구매 수량을 증가시키지 않는다.

   b. 프로모션 상품이 없다면

    - 일반 제품으로 계산한다.
4. 총 구매하는 상품과 수량을 계산한다.
5. 프로모션으로 받는 상품과 수량을 계산한다.
6. 멤버십 할인 적용 여부를 확인한다.
    1. 할인 적용을 받는다.
        1. 총구매하는 상품 중에서 프로모션 혜택을 받지 않는 상품을 구한다.
        2. 혜택을 받지 않는 상품들의 금액의 총합을 구한다.
        3. 구매금액의 30% 금액을 계산한다.
        4. min(구매금액의 30%, 8000원)를 할인받는다.
    2. 할인 적용을 받지 않는다.
        1. 할인 금액이 존재하지 않는다.
7. 영수증을 출력한다.
    1. 구매 상품 내역을 출력한다.
    2. 증정 상품 내역을 출력한다.
    3. 금액 정보를 출력한다.
8. 사용자에게 추가 구매 여부를 입력받는다.

**실행 결과 예시**
```
안녕하세요. W편의점입니다.
현재 보유하고 있는 상품입니다.

- 콜라 1,000원 10개 탄산2+1
- 콜라 1,000원 10개
- 사이다 1,000원 8개 탄산2+1
- 사이다 1,000원 7개
- 오렌지주스 1,800원 9개 MD추천상품
- 오렌지주스 1,800원 재고 없음
- 탄산수 1,200원 5개 탄산2+1
- 탄산수 1,200원 재고 없음
- 물 500원 10개
- 비타민워터 1,500원 6개
- 감자칩 1,500원 5개 반짝할인
- 감자칩 1,500원 5개
- 초코바 1,200원 5개 MD추천상품
- 초코바 1,200원 5개
- 에너지바 2,000원 5개
- 정식도시락 6,400원 8개
- 컵라면 1,700원 1개 MD추천상품
- 컵라면 1,700원 10개

구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])
[콜라-3],[에너지바-5]

멤버십 할인을 받으시겠습니까? (Y/N)
Y 

===========W 편의점=============
상품명		수량	금액
콜라		3 	3,000
에너지바 		5 	10,000
===========증	정=============
콜라		1
==============================
총구매액		8	13,000
행사할인			-1,000
멤버십할인			-3,000
내실돈			 9,000

감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)
Y

...
```
## 🚀 객체별 기능 목록
### 1. store

**Counter (메인 로직)**

- [ ] util을 활용하여 md 파일을 읽어 enum 형으로 저장한다(?)
- [ ] inputview를 통해 구매 상품과 금액을 입력받는다.
- [ ] 결제금액의 정보를 저장한다.
    - [ ] 편의점에서 총 구매 상품, 수량을 입력받는다.
    - [ ] 프로모션에서 혜택받는 상품, 수량을 입력받는다.
- [ ] 멤버십 할인 적용 유무를 입력받고, 적용한다면 membership에서 할인 금액을 입력받는다.
- [ ] outputview를 통해 영수증을 출력한다.
- [ ] conveniencestore의 재고를 조정한다.
- [ ] 추가 구매 여부를 입력받는다.

**Customer**

- [ ] 구매 상품, 구매 수량을 저장한다.

**conveniencestore**

- [ ] 판매상품과 재고 수량을 저장한다.(name, price, quantity, promotion 유무)
- [ ] Counter로부터 구매할 상품, 수량을 입력받아 구매가능한 상품인지 확인한다.
- [ ] Counter로부터 구매할 상품, 수량을 입력받아 프로모션이 있는 상품인지 확인한다.
- [ ] 각 상품의 재고 수량을 알려준다.
- [ ] 각 상품의 가격을 알려준다.
- [ ] 고객이 상품을 구매하면, 결제된 수량만큼 해당 상품의 재고에서 차감한다.

**Promotion**

- [ ] 프로모션 정보를 저장한다.(name, buy, get, start_date, end_date)
- [ ] counter로부터 구매할 상품, 수량을 입력받아 프로모션 혜택을 받을 수 있는지 여부를 확인한다.
    - 기간
        - [ ] 현재 날짜가 프로모션 기간 내가 아니라면 혜택을 받을 수 없다.
    - 개수
        - [ ] 프로모션 혜택을 받을 상품의 개수가 부족하다면 혜택을 받을 수 있음을 안내한다.
          ex) 1+1 상품을 한개만 가져왔을 경우
        - [ ] 프로모션 상품의 개수가 구매수량보다 작다면, 일부 수량에 대해 정가로 결제하게 됨을 안내한다.
        - [ ] 프로모션 상품의 재고를 우선적으로 차감하며, 재고가 부족할 경우 일반 재고를 사용한다.
- [ ] 프로모션 혜택을 받는 상품명과 수량을 알려준다.

**Membership**

- [ ] 프로모션을 적용받지 않은 상품명과 수량에 대해서 저장한다.
- [ ] 멤버십 할인 금액을 계산하여 알려준다.
    - [ ] 프로모션을 적용받지 않은 총 금액을 계산한다.
    - [ ] min(총금액의 30%, 8000원)을 리턴한다.  
  
### 2. utill

- [ ] md파일에서 값을 추출한다.

### 3. Message

**StoreMessage**

- [ ] 진행 관련 메세지 저장
- [ ] 메시지 리턴 기능 제공

**ErrorMessage**

- [ ] 에러 관련 메세지 저장
- [ ] 에러 메시지 리턴 기능 제공

### 4. validate

CustomerValidate

- [ ] 구매 상품이 한글인지 확인한다.
- [ ] 구매 하려는 상품의 개수가 양수인지 확인한다.

ConvenienceStoreValidate

- [ ] 입력받은 상품이 판매상품에 존재하는지 확인한다.
- [ ] 손님이 구매할 상품의 총 수량이 판매상품의 총 수량보다 작은지 확인한다.

### 5. view

**InputView**

- [ ] 구매할 품목, 수량을 입력받는다.
- [ ] 멤버십 할인 적용 유무를 입력받는다.
- [ ] 추가 구매 여부를 입력받는다.

**OutputView**

- 재고 출력 기능
- 영수증 출력 기능

