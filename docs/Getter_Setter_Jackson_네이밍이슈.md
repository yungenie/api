
# [lombok][jackson] Naming convention for getters/setters in Java

# 개요
- Spring Framework로 외부 API 테스트를 하다가 특정 필드의 데이터가 null로 들어오는 이슈에 직면했습니다. 
- 처음에 오타가 있는 줄 알고 인터페이스 In/Out 정의서 토대로 변수명을 재차 확인했는데 틀리지 않았음. 
- 잘못 구현했나 싶었으나 다른 필드는 다 잘 매핑됨. 띠용
- 따로 샘플 코드 만들어서 테스트 해보고 구글링 통해서 알게된 Jackson, Lombok, Java Beans 네이밍 규약에 대해서 정리합니다.

# 이슈
- 외부 API 응답 데이터를 Response 객체에 매핑할때 특정 필드값이 null로 매핑되는 현상

```
{
  "prcssRsult": "200",
  "requestId" : "124837",
  "aBCDNo": null
}
```

- 위와 같은 데이터(샘플)을 응답 받는데 특정 필드값이 null 값이 들어오는 것을 확인.
- API 거래를 HttpUrlConnection 방식으로 구현하고 있어 응답 문자열을 로그로 출력해봤다.
- 로그상 문자열에는 값이 제대로 잘 나오는데 문자열을 객체로 역직렬화하는 과정에서 특정 필드 null로 매핑 된다.

# 원인분석

## 샘플코드작성
- 원인분석을 위해 도메인과 컨트롤러를 테스트용으로 구현했습니다.
- 다음과 같이 테스트 코드를 작성했습니다.
  - 요청값을 매핑해서, 입력한 대로 잘 매핑되어 출력하는 지 확인

### Domain

```java
    @Getter
    @ToString
    @NoArgsConstructor
    public class NamingDto {
    
        private String aBCDNo;
        private String AAaa;
        private String BBBb;
        private String CCcC;
        private String DDDD;
        private String AAAAAAa;
        private String Aa;
        private String aaA;
        private String Fab;
    }
```
### Controller
```java
    @Slf4j
    @RestController
    public class NamingController {

      @PostMapping("/v1/issue/naming/lombok")
        public ResponseEntity<NamingDto> getValue(@RequestBody NamingDto dto) {
          log.info("Request v1/issue/naming/lombok POST/ getValue");
          log.info(String.valueOf(dto));
          return ResponseEntity.ok(dto);
      }
    }
```


### 결과
(postman 결과)

{
  "aaA": "asdf",
  "cccC": null,
  "abcdno": null,
  "bbbb": null,
  "aaaaaaa": null,
  "aaaa": null,
  "dddd": null,
  "aa": null,
  "fab": null
}

- 결과를 보면 JSON Key값과 필드명도 다르고 null로 찍힘. 골때림.
- 원인분석을 하다 보니깐 메시지 컨버터의 쪽의 문제가 아닐까 해서 찾아봤다.
  - HTTP 메시지 컨버터가 무엇인가요?
    - 요청 본문에서 메시지를 읽어들이거나(@RequestBody), 응답 본문에 메시지를 작성할 때(@ResponseBody) HTTP 메시지 컨버터가 동작한다.
    - HTTP 헤더와 컨트롤러 요청 및 반환타입 정보를 조합해서 컨버터의 여러 종류 중 하나가 선택된다.
    - API는 JSON 형태로 받기 때문에 MappingJackson2HttpMessageConverter가 선택되어 Jackson 컨버터가 실행된다.   
    - 그래서 Jackson 컨버터가 네이밍 규칙에 따라 데이터를 찰떡같이 매핑 해준다.
    - Jackson과 Jackson의 네이밍 규칙을 알아보자. ✨

## 1. Jackson
- 스프링에서 JSON 데이터를 객체로 변환하거나 객체를 JSON 데이터로 변환해주는 라이브러리로 스프링부트에서는 기본적으로 제공해준다.
- Jackson 라이브러리는 ObjectMapper라는 클래스를 사용하여 데이터 변환하여 매핑을 해줍니다.
- 1. 객체를 JSON으로 직렬화를 할 때 ObjectMapper 클래스의 writeValueAsString() 메서드를 사용하여 객체를 JSON 문자열로 변환한다.
- 2. 객체의 필드 또는 속성 값을 가져와서 해당 값을 JSON 키와 매핑하여 JSON 객체를 생성하고 이를 문자열로 반환시켜줍니다.
- 위 과정에서 Key 값을 매핑하는 규칙이 존재.

### 1.1 Jackson은 Getter의 이름을 기반으로 Json Key 값을 만든다.
- 필드명 대신에 Getter 이름으로 JSON key값이 설정됨.

#### Domain
```java
  public class JacksonDto {
      private String aBCDNo;
  
      public void setaBCDNo(String aBCDNo) {
          this.aBCDNo = aBCDNo;
      }
  
      public String getCheckaBCDNo(){
          return aBCDNo;
      }
  }
```
#### DomainTest
```java
  class JacksonDtoTest {
  
      private ObjectMapper objectMapper;
  
      @BeforeEach
      public void setUp(){
          this.objectMapper = new ObjectMapper();
      }
  
      @Test
      public void 잭슨_Getter() throws IOException {
          JacksonDto dto = new JacksonDto();
          dto.setaBCDNo("hello");

          //writeValueAsString(변활할 객체) : Java 객체를 JSON 형식으로 변환
          String s = this.objectMapper.writeValueAsString(dto); 
  
          System.out.println("s = " + s);
      }
  }
```

#### 결과
- {"checkaBCDNo":"hello"}
- 클래스에 선언된 필드는 aBCDNo이고, Getter메서드명은 getCheckaBCDNo()이다.
- get다음으로 이어지는 이름을 CheckaBCDNo 가져와서 Jackson의 네이밍 규칙에 의해 변형하여 매핑시킨다. 🫢😲
- 이제 Jackson 네이밍 규칙을 알아보겠습니다.

### 1.2 Jackson 네이밍 규칙
- 모든 케이스에서는 첫번째 문자 하나만 소문자로 변경합니다.
  - ex) CheckaBCDNo -> checkaBCDNo
- 예외 케이스로는 맨 앞 두 글자 모두 대문자인 경우 이어진 대문자를 모두 소문자로 변경합니다.
  - ex) AAaa -> aaaa , AAAAAAa -> aaaaaaa
- 기본적으로 JavaBeans 규약을 따르지만 다른 부분이 있음.

## 2. Java Bean
### 2.1 JavaBeans 네이밍 규칙
- JavaBeans는 메서드 이름에서 필드명을 추출할때 일정한 규칙이 존재함.
- 오라클에서 제공하는 JavaBeans API specification 공식 문서의 8.8 Capitalization of inferred names 챕터에 설명되어 있습니다.

(이미지 첨부)

- 간단히 요약하면 아래의 설명과 같습니다.
- 1. 일반적으로는 첫번째 문자 하나만 소문자로 변환하여 리턴합니다.
- 2. 예외 케이스로는 두 개 이상의 문자가 있고 첫번째 문자와 두번째 문자 모두 대문자인 경우 그대로 다시 리턴합니다.
- java.beans 패키지에 있는 Introspector 클래스의 decapitalize() 메서드를 확인해보면 실제로 어떤 로직이 들어가있는 지 알 수 있습니다.

#### java.beans 패캐지의 Introspector.java
```java
    public class Introspector {
      public static String decapitalize(String name) {
        if (name == null || name.length() == 0) {
          return name;
        }
        if (name.length() > 1 && Character.isUpperCase(name.charAt(1)) &&
                Character.isUpperCase(name.charAt(0))) {
          return name;
        }
        char[] chars = name.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
      }
  }
  
```

## 3. Jackson와 Java Beans 차이점
- 둘다 공통적으로 첫번째 문자 하나만 소문자로 변환하여 리턴한다.
- 다른점은 맨 앞 두글자(첫번째, 두번째) 모두 대문자인 경우
  - Jackson은 이어지는 모든 대문자 -> 소문자로 변경하여 반환
  - Java Beans은 원래 문자 그대로 반환

  
## 4. Lombok의 Getter 생성 규칙
- 롬복은 맨 앞 글자를 대문자로 바꿔서 만들어줍니다.
- 롬복의 Getter 이름으로 Jackson 네이밍 규칙에 의해 JSON key값이 설정됨. 
- 필드명  ->   Lombok  -> Jackson
  aBCDNo  -> getABCDNo -> abcdno
  AAaa    -> getAAaa   -> aaaa
  BBBb    -> getBBBb   -> bbbb
  CCcC    -> getCCcC   -> cccC
  DDDD    -> getDDDD   -> dddd
  AAAAAAa -> getAAAAAAa-> aaaaaaa
  Aa      -> getAa     -> aa
  aaA     -> getAaA    -> aaA (해당 필드명만 JSON Key값 일치해서 매핑됨)
  Fab     -> getFab    -> fab
- 맨 처음 샘플코드의 테스트 결과와 동일합니다.
{
"aaA": "asdf",
"cccC": null,
"abcdno": null,
"bbbb": null,
"aaaaaaa": null,
"aaaa": null,
"dddd": null,
"aa": null,
"fab": null
}


# 해결방법
- 필드명을 변경할 수 없는 경우
  1. @JsonPropery 애노테이션 사용
     ```java
        @JsonProperty("aBCDNo")
        private String aBCDNo;
     ```
     - @JsonProperty 사용하기 위해 Jackson 라이브러리를 사용해야합니다.
     - 라이브러리가 없는 경우 com.fasterxml.jackson.core 패키지가 필요합니다.

  2. Getter/Setter 직접 생성 
     - 롬복 @Getter 또는 @Data 애노테이션 사용 대신 Getter와 Setter 메서드를 직접 생성합니다.
     - 저의 경우 인텔리제이가 제공하는 Getter and Setter 메소드 자동생성 기능을 사용해서 해결했습니다.
      ```java
          public void setaBCDNo(String aBCDNo) {
              this.aBCDNo = aBCDNo;
          }
          public String getaBCDNo() {
              return aBCDNo;
          }
      ```

- 필드명을 변경할 수 있는 경우 
  1. 네이밍 잘 짓기 ✨
     - 지양해야하는 필드명(컬럼명)
       - case 1) 첫번째 소문자, 두번째 대문자 : aBCDNo(Field) -> getABCDNo(롬북) -> abcdno(Jackson) 
       - case 2) 첫번째 대문자, 두번째 대문자 : BBbb(Field) -> getBBbb(롬북) -> bbbb(Jackson)
     - 지향해야하는 필드명(컬럼명)
       - 필드명의 첫번째, 두번째는 소문자인 케이스로 지향합시다.
       - 카멜 표기법 중 LCC(Lower Camel Case)를 사용하자.
         - 첫단어는 소문자로 표기하며 이후 연결되는 단어부터는 첫글자를 대문자로 표기한다.
         - (이미지 첨부)


## 해결방법으로 테스트

```java
class NamingDtoTest {

    private ObjectMapper objectMapper;

    @BeforeEach // 테스트 메서드 실행 이전에 수행됨
    public void setUp(){
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void 롬북사용() throws IOException {
        String json = "{\"aBCDNo\":\"hello world\"}";

        NamingDto result = this.objectMapper.readValue(json, NamingDto.class);
        assertThat(result.getABCDNo(), is("hello world"));
    }

    @Test
    public void Getter_Setter_직접생성() throws IOException {
        String json = "{\"aBCDNo\":\"hello world\"}";

        NamingDtoV2 result = this.objectMapper.readValue(json, NamingDtoV2.class);
        assertThat(result.getaBCDNo(), is("hello world"));
    }

    @Test
    public void 롬복_잭슨애노테이션적용() throws IOException {
        String json = "{\"aBCDNo\":\"hello world\"}";

        NamingDtoV3 result = this.objectMapper.readValue(json, NamingDtoV3.class);
        assertThat(result.getABCDNo(), is("hello world"));
    }

}
```
### 결과
(이미지 첨부)
- 위 (1),(2) 해결방법으로 테스트 코드를 작성해서 검증해봤습니다. 
- 롬복의 @Getter 사용한 객체는 테스트 실패가 옳은 테스트 입니다. (Jackson 네이밍 규약에 의해서 필드와의 매핑 안되는 게 정상)
- 1.@JsonPropery 애노테이션 사용 2. Getter/Setter 직접으로 통한 해결은 테스트 통과가 됩니다.

# 회고
  - 애초부터 필드명(컬럼명)의 네이밍 컨벤션을 잘 정의하면 이런 일을 겪지 않는다는 걸 배웠음. 
  - 외부 API 응답을 받아서 처리하는 프로세스인데 파악해보니 해당 API가 여러 외부 수행사에서도 쓰임
  - 거래하고 있는 몇 개 API에서도 포스팅 주제와 같은 이슈인 필드명이 여러개 존재한다는 것도 확인 ㅠ
  - 팀 내부적으로 해당 이슈를 공유드리면서 자체 코드 수정으로 해결을 봤다.
  - 역시 네이밍의 중요성을 느끼는 이슈였다.  

# 정리 
지금까지 정리한 내용을 요약하면 아래와 같습니다.
1. Spring의 Json Message Converter는 Jackson 라이브러리를 사용
2. Jackson 라이브러리는 Getter의 맨 앞 두글자가 대문자인 경우 이어진 대문자를 모두 소문자로 변경하여 리턴함
3. Lombok의 Getter는 필드명 맨 앞 첫번째 글자를 항상 대문자로 만듬
4. Controller 단에서 Json 데이터와 Target Entity를 매핑할 때 Lombok와 Jackson의 네이밍 규칙에 따른 필드명 불일치 사태가 일어나 key 매핑이 안됨.
5. 필드명(컬럼명)을 수정하거나, Getter/Setter 직접 생성하거나, @JsonProperty 애노테이션을 사용해서 해결하면 된다.

---

# 리서치

## 스트림
입출력(I/O)이란 컴퓨터 내부 또는 외부의 장치와 프로그램간의 데이터를 주고받는 것을 말한다.
자바에서 입출력을 수행하라면 데이터를 운반하는데 사용되는 연결통로입니다.
단반향통신만 가능하기 때문에 동시에 입력 출력을 처리할 수 없다.

## 직렬화/역직렬화
직렬화(serialization) 객체에 저장된 데이터를 스트림에 쓰기(write) 위해 연속적인(serial) 데이터로 변환
반대로 스트림으로부터 데이터를 읽어서 객체를 만드는 것이 역직렬화(deserialization)라고 한다.

## 네이밍 규칙
### Lombok Getter
- 맨 앞글자를 대문자로 변경

### JavaBeans 네이밍 규칙
- 맨 앞 두개가 전부 대문자라면 그대로 리턴하고 아니라면 맨 앞 문자 하나만 소문자로 바꿔서 리턴
- ex) AAaa -> AAaa, Fab -> fab

### Jackson
- 맨 앞 두개가 전부 대문자라면 이어진 대문자들을 모두 소문자로 변경하고 아니라면 맨 앞 문자 하나만 소문자로 바꿔서 리턴
- ex) AAaa -> aaaa, Fab -> fab

### 결과
#### Lombok
Field   -> Lombok    -> Jackson
aBCDNo  -> getABCDNo -> abcdno
AAaa    -> getAAaa   -> aaaa
BBBb    -> getBBBb   -> bbbb
CCcC    -> getCCcC   -> cccC
DDDD    -> getDDDD   -> dddd
AAAAAAa -> getAAAAAAa-> aaaaaaa
Aa      -> getAa     -> aa
aA      -> ?     -> ?
aaA     -> getAaA    -> aaA (o)
Fab     -> getFab    -> fab

##### API 결과
{
"aaA": "asdf",
"cccC": null,
"fab": null,
"dddd": null,
"aa": null,
"abcdno": null,
"aaaaaaa": null,
"aaaa": null,
"bbbb": null
}
> Json 결과에 aA 필드 없음.
> .class 파일을 열어보니 getaA() 존재하지 않음.
aBCDNo 라는 필드명을 Lombok 애노테이션을 사용해서 Getter을 만들면 getABCDNo가 되기 때문에 이슈가 발생

#### console log
namingDto(aBCDNo=null, AAaa=null, BBBb=null, CCcC=null, DDDD=null, AAAAAAa=null, Aa=null, aaA=asdf, Fab=null, aA=null)
- aA=null로 찍혀서 .class 파일을 열어보니 getaA() 존재하지 않음. 
- toString() 메서드에서 aA의 값을 getaA()가 아닌 getAa()를 찍어주고 있음.

---


# 롬북 Getter/Setter naming convension 이슈 (Interview)
- 외부 API 연계시 응답 객체의 특정 필드값만 null로 들어오는 현상이 있었습니다.
- 처음엔 변수의 오타 문제인 줄 알았습니다. 
- 디버깅을 해보니 응답 문자열에는 모든 값이 잘들어오고 
- 응답 문자열을 JSON Object 변경 후 타켓 객체에 매핑을 시킬때 특정 필드 값이 null로 들어왔습니다.
- Jackson Converter 쪽의 문제이지 않을까 싶어서 구글링과 공식문서를 통해 
- Jackson 네이밍 규칙에 의해서 변환된 key와 필드값의 불일치로 생기는 원인이라는 것을 알게 되었습니다.
- Jackson은 Getter의 메소드명으로 문자의 첫 두글자가 대문자일 경우 이어지는 대문자까지 소문자로 변경합니다. 
- 그런데, 롬복의 Getter는 필드의 맨 첫번째 문자를 무조건 대문자로 만들어줍니다.
- 그래서 aBCDNo와 같은 필드일 경우 롬복에 의해서 getABCDNo로 메서드명이 만들어지고, 다시 Jackson에 의해서 abcdno 모두 소문자로 변경이 되어 JSON key 불일치가 생기는 것을 알게 되어
- 인텔리제이에서 제공해주는 Getter/Setter 직접생성을 통해 이슈를 해결했습니다.
- 기본으로 제공해주는 Getter/Setter는 기존의 필드명을 그대로 유지하기 때문에 이슈가 있었던 필드명 해결이 가능합니다. 
  > AAaa, Bbb 같은 필드명은 @JsonProperty 애노테이션을 사용해야 해결 가능합니다.
  > Getter/Setter 직접 생성으로 해결하지 못하는 필드명 없었음.


# 롬북 Getter/Setter naming convension 이슈가 발생한 이유?
JSON 객체(문자열)을 타켓 객체에 매핑을 할 때
key값(aBCDNo필드명 동일) -> getABCDNo(롬북) -> abcdno(Jackson)
Json databind에 의해서 key값과 불일치로 null이 들어간다.

> 해결 방법은 Getter/Setter를 직접생성하면 기존의 클래스에 선언된 필드명과 동일하게 생성되서
key값(aBCDNo필드명 동일) -> getaBCDNo(직접생성) -> aBCDNo(Jackson) key값과 필드명 일치로 값이 들어가게 된다.


# 추가 이슈 및 궁금증 🚨
## @JsonProperty란? (*추가파악 필요)
@JsonProperty(name)는 JSON 속성 이름을 주석이 달린 Java 필드 이름에 매핑하도록 Jackson ObjectMapper에 지시합니다.
JsonProperty는 변수에 대한 getter 및 setter 메서드를 지정하는 데 사용됩니다
@JsonProperty는 serializer에게 object를 serialize하는 방법을 알려주는 메타 데이터입니다. 다음 용도로 사용됩니다.
변수 이름
액세스(읽기, 쓰기)
기본값
필수/선택

## key값 중복 발생 이슈
### 객체의 Setter 여부에 따라 필드의 JSON Key값이 중복될 수 있음. (Setter가 없는 경우에 중복으로 나타나는 현상 발생)
객체의 필드에
@JsonProperty 붙이고 @Setter가 없으면 Jackson 네이밍 규칙과 + @JsonProperty 변수 2개 중복값으로 내보내고,
- aBCDNo(필드명) -> getABCDNo(롬북) -> abcdno(Jackson) JSON key가 aBCDNo, abcdno 2개 생긴다.
- @Setter가 없으면 어떻게 응답값을 객체에 매핑을 하는 가? (*확실히 모름)

@JsonProperty 붙이고 @Setter가 있으면 @Setter메서드에도 @JsonProperty붙여줘서 1개로 내보낸다.

      @JsonProperty("aBCDNo")
    private String aBCDNo;

    @JsonProperty("AAaa")
    private String AAaa;

    @JsonProperty("aBCDNo")
    public void setABCDNo(String aBCDNo) {
        this.aBCDNo = aBCDNo;
    }

    @JsonProperty("AAaa")
    public void setAAaa(String AAaa) {
        this.AAaa = AAaa;
    }

    public String getABCDNo() {
        return this.aBCDNo;
    }

    public String getAAaa() {
        return this.AAaa;
    }


## 위의 내용들을 통해서 어떤걸 알고 싶은 건지?
- 문자열을 JSON 객체로 만들때는 null로 매핑 안되는 지?
  - 매핑잘된다. 
  - import com.fasterxml.jackson.databind.JsonNode; 
  - import com.fasterxml.jackson.databind.ObjectMapper;
  - 이용해서 Json 문자열을 Json 객체로 만들어서 테스트 해봄. 원본 필드 그대로 유지.
- JSON 문자열 -> 객체로 매핑할때 aCSNo와 같은 필드명은 Getter/Setter 직접생성으로 해결되는데, 
  - ACSNo 같은 필드명은 Getter/Setter 직접생성으로도 해결안됨.
  - @JsonProperty를 Setter 메서드 위에 선언해줘야한다. 그러면. 되지 않을까?
    - 테스트 결과 Requset 객체에 Setter/Getter 둘중 하나만  @JsonProperty("AAaa") 써줘도 적용된다. 
    - getAAaa()가 원래는 AAaa 필드는 -> Jackson에 의해서 aaaa로 변경하지 않고 AAaa로만 되는 걸까?

## @RequestBody로 받는 경우 @Setter 메소드가 없는데 어떻게 요청 값을 객체에 매핑을 하는 가?
- 기본 생성자에 의해서 reflection 사용해서 값을 주입.
- 기본 생성자는 일반적으로 컴파일 할때 생성된다. 근데 생성자(인자가 있는)가 존재하면 기본 생성자는 안만들어짐.
- 만약 인자가 딱 1개만 있는 생성자라면 오류 남.
- JSON 데이터를 -> 객체로 역직렬화 할 수 없다고 나옴.
```java
@Getter
@ToString
public class NamingReq {

    @JsonProperty(value = "aBCDNo") // JSON(문자열) -> 객체
    private String aBCDNo;

    @JsonProperty(value = "AAaa") //객체 -> JSON(문자열)
    private String AAaa;

    public NamingReq(String aBCDNo) {
        this.aBCDNo = aBCDNo;
    }
}
```
  > .w.s.m.s.DefaultHandlerExceptionResolver : Resolved [org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Cannot construct instance of `com.web.api.domain.NamingReq` (although at least one Creator exists): cannot deserialize from Object value (no delegate- or property-based Creator)]

## Req/Res 객체에 Setter(@Setter/Setter 직접생성) 사용을 지양하고자 할때 어떻게 처리?
- @Setter(AccessLevel.PROTECTED)를 쓰고, 추가 수정해야할 경우는 update필드명() 따로 생성해서 처리.
- Entity를 만들 때는 외부에서 쉽게 변경할 수 없게 @Setter를 사용하지않는다.
- Setter를 사용하면 의도가 불명확하고 변경하면 안되는 중요한 값임에도 불구하고 변경 가능한 값으로 착각할 수 있다. (안정성 보장이 안된다.)
- 그러면 만약 값을 업데이트 시켜줘야하는 상황일 때, Setter를 사용하지않고 어떻게 하면 될까?
  - updateaCSNo() 이렇게 메서드의 이름만을 보고도 변경 의도를 한눈에 알 수 있도록 한다.
  - 물론 @Setter의 사용이 100% 잘못되었다는 것이 아닙니다. 양방향 바인딩 시 Setter 사용이 더 용이할 수 있다.
  
## jackson-databind 어떤 매커니즘으로 동작하는 지?

## aA 필드는 getaA()메서드 존재하지도 않고 toString에서 getAa()를 찍어주고 있는 이슈

## Gson 이용할 때
Gson을 이용할 때에는 @SerializedName을 사용해야하고 jackson을 사용할때에는 @JsonProperty를 사용해야 한다.

@SerializedName("aBCDNo")
private String aBCDNo;




