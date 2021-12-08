# 농산물 주식 모의투자

<h1> My Team </h1>
<p> 박현근(idiot2222), 장주언(jje951122), 이동건(donggeonL), 진예슬(yeseul111) </p>

<h1> 화면 구성도</h1> 

> ![image](https://user-images.githubusercontent.com/87507644/135511424-38867834-e55c-440b-bc60-715b711943f2.png)

<h1> DB 구성도 </h1>

> ![image](https://user-images.githubusercontent.com/87507644/135515522-38ea8e92-21ec-4bc8-9e45-0dc808df42f2.png)
# 클라우드 네이티브 개발자 양성과정 - 백엔드 미니 프로젝트

## 농작물 모의 투자 사이트
> 현시점의 농작물을 주식처럼 사고 파는 모의 투자 사이트  
> 기간 : 2021.07.19~2021.07.23  
> 사용한 기술 : 
>* Spring Boot  
>* Java 
>* HTML
>* OracleDB

## 기능

> ![image](https://user-images.githubusercontent.com/76868571/131085521-224ad850-890a-445f-a9da-0b306a583fa6.png)
> * 메인 페이지  
> * 로그인/회원가입 페이지
> * 농산물 상세 페이지  
>   + 당일 농산물의 가격에 대한 정보
>   + 클릭시 농산물 거래 사이트로 이동
>   + 거래 사이트에서는 매수/매도 가능
>   + 구글 차트를 이용한 날짜별 금액 차트 제공
> * 매수/매도
>   + 매수 시 해당 농산물이 주문내역에 있다면 평균가격을 구해 하나의 데이터로 관리
>   + 매도 시 수익 내역등 거래 내역을 사용자의 거래내역에 추가
> * 현재 자산 현황
>   + 현재 가지고 있는 농산물에 대한 구매한 평균 단가, 현재 금액 수익률 등의 정보를 제공
>   + 종목의 현재 금액과 현금자산의 현황을 제공
> * 실현 손익 페이지  
>   + 매도한 결과에 대하 구매 금액, 판매 금액등 실현 손익에 대한 정보 제공
> * 기사 페이지  
>   + 사이트에서 취급하는 농산물에 대한 기사를 크롤링하여 보여줌

  
## 프로젝트 결과물
> ### 메인 페이지
> ![홈 페이지](https://user-images.githubusercontent.com/76868571/130433635-bc78828b-a5a8-4a2e-b0c2-a050af20539a.JPG)
> 
> ### 로그인/회원가입 페이지
> ![로그인 페이지](https://user-images.githubusercontent.com/76868571/130433631-8f4fdab4-2d10-4726-9c26-e0cc57dc03f8.JPG)
> ![회원가입 페이지](https://user-images.githubusercontent.com/76868571/130433646-685a6e9a-d778-43db-b802-bf60564414a2.JPG)


> ### 현재 자산 페이지
> ![현재 자산현황](https://user-images.githubusercontent.com/76868571/130433643-1e69b4fa-e209-4614-8d57-810db060a469.JPG)
> 
> ### 현재 농산물 가격 페이지
> ![당일 농산물 가격](https://user-images.githubusercontent.com/76868571/130433639-fa2d0c6a-b4e1-427d-b62d-d4b5a69e49e1.JPG)


> ### 농산물 거래 페이지(차트, 매수/매도)
> ![주식 상세 페이지(매수 매도)](https://user-images.githubusercontent.com/76868571/130433642-f9dc267e-1715-420a-9af2-ee4d3fef5870.JPG)

> ### 실현손익 페이지
> ![실현수익 내역](https://user-images.githubusercontent.com/76868571/130433644-b91e2b3e-169b-4401-bf9a-903cacd7ea44.JPG)
