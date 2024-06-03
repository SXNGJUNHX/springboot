package com.test.bootjpa.controller;

import com.querydsl.core.Tuple;
import com.test.bootjpa.dto.AddressDTO;
import com.test.bootjpa.entity.Address;
import com.test.bootjpa.entity.AddressNameAgeMapping;
import com.test.bootjpa.entity.Info;
import com.test.bootjpa.repository.AddressRepository;
import com.test.bootjpa.repository.CustomAddressRepository;
import com.test.bootjpa.repository.InfoRepository;
import com.test.bootjpa.repository.MemoRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AddressController {


    private final AddressRepository addressRepository;
    private final InfoRepository infoRepository;
    private final MemoRepository memoRepository;
    private final CustomAddressRepository customAddressRepository;


    @GetMapping("/m01.do")
    public String m01(Model model) {

        //1. Query Method > 정해진 키워드 + 메서드명 = JPQL > SQL
        //2. JPQL > 직접 SQL 작성 > 엔티티를 대상하는 SQL
        //3. Query DSL


        //[C]RUD
        //- 레코드 추가하기
        //- 추가할 레코드의 정보 > 엔티티 객체를 생성하기
        Address address = Address.builder()
                .name("꿀꿀이")
                .age(5)
                .address("서울특별시 강남구 역삼동 한돈 빌딩")
                .gender("m")
                .build();

        Address result = addressRepository.save(address);

        model.addAttribute("address", Address.toDTO(result));

        return "result_dto";
    }

    @GetMapping("/m02.do")
    public String m02(Model model) {

        //C[R]UD
        //- 1개의 레코드 가져오기

        //addressRepository.getById(51L)
        //addressRepository.getOne(51L)

        Optional<Address> address = addressRepository.findById(60L);

//        if (address.isPresent()) {
//            model.addAttribute("address", Address.toDTO(address.get()));
//        }

        address.ifPresent(value -> model.addAttribute("address", Address.toDTO(value)));

        return "result_dto";
    }

    @GetMapping("/m03.do")
    public String m03(Model model) {

        //CR[U]D
        //- 레코드 수정하기
        //1. 생성 후 수정
        //2. 검색 후 수정
//        Address address = Address.builder()
//                .seq(51L)
//                .name("꿀꿀이")
//                .age(5)
//                .address("서울특별시 강남구 역삼동 한돈 빌딩 8층")
//                .gender("m")
//                .build();

        Address address = addressRepository.findById(51L).get();
        address.updateAddress("서울특별수 강동구", 6);


        Address result = addressRepository.save(address);

        model.addAttribute("address", Address.toDTO(result));

        return "result_dto";
    }

    @GetMapping("/m04.do")
    public String m04(Model model) {

        //CRU[D]
        //- 레코드 삭제하기

        //Address address = addressRepository.findById(51L).get();

        Address address = Address.builder()
                .seq(52L)
                .build();

        addressRepository.delete(address);

        return "result_dto";
    }

    @GetMapping("/m05.do")
    public String m05(Model model) {

        //JPA 질의 방법
        //1. Query Method > 기본
        //  - 정해진 패턴의 메서드명 > 메서드 선언 > SQL 생성
        //  - 단순 작업(O), 복잡 작업(X)
        //2. JPQL
        //  - 복잡 작업(O)
        //3. Query DSL
        //  - 복잡 작업(O)


        //Query Method
        //1. 예약 키워드 사용
        //2. 추가 키워드 사용(예약)

        //- JPA는 메서드 이름을 가지고 분석 > 쿼리를 생성 + 실행
        //- 메서드명 = Subject Part + Predicate Part
        //          = 가져올 컬럼   + 조건

        //Select Query
        //- findBy, getBy, readBy, queryBy, searchBy, streamBy
        //- findBy(***)

        //- findBy컬럼명
        String name = "강아지";

        //Optional<Address> address = addressRepository.findByName(name);
        //Optional<Address> address = addressRepository.findByAddress("서울특별시 서초구 서초대로 77길 45 아크로비스타 101동 3호");
        //Optional<Address> address = addressRepository.findByGender("m");

        Optional<Address> address = addressRepository.getByName("병아리");

        model.addAttribute("address", Address.toDTO(address.get()));

        return "result_dto";
    }

    @GetMapping("/m06.do")
    public String m06(Model model) {

        //레코드 존재 유무 > count(*) > 0
        //boolean result = addressRepository.existsById(1L);

        long result = addressRepository.count();

        model.addAttribute("result", result);

        return "result_scalar";
    }

    @GetMapping("/m07.do")
    public String m07(Model model) {

        //First, Top
        //- 가져올 레코드의 개수를 지정한다.
        //- 키워드 뒤에 숫자를 지정한다. 생략(1)
        //- 1개면 단수 반환, N개면 복수 반환

        //Address address = addressRepository.findFirstByAge(3);

        //List<Address> addressList = addressRepository.findFirst30ByGender("m");

        //Address address = addressRepository.findTopByAge(5);

        List<Address> addressList = addressRepository.findTop10ByGender("f");

        //model.addAttribute("address", Address.toDTO(address));
        //return "result_dto";

        model.addAttribute("addressList", addressList);
        return "result_list";

    }

    @GetMapping("/m08.do")
    public String m08(Model model) {

        //And, Or

        //where name = '강아지' and gender = 'm'
        //Optional<Address> address = addressRepository.findByNameAndGender("강아지", "m");
        //address.ifPresent(value -> model.addAttribute("address", Address.toDTO(value)));

        //where gender = 'm' and age = 3
        //where gender = 'm' or age = 3
        //List<Address> addressList = addressRepository.findByGenderAndAge("m", 3);
        List<Address> addressList = addressRepository.findByGenderOrAge("m", 3);
        model.addAttribute("addressList", addressList);

        //return "result_dto";
        return "result_list";
    }

    @GetMapping("/m09.do")
    public String m09(Model model) {

        //After, Before, GreaterThan(GreaterThanEqual), LessThan(LessThanEqual), Between
        //- After, GreaterThan(GreaterThanEqual) > 크다
        //- Before, LessThan(LessThanEqual) > 작다
        //- Between > 범위
        //- After/Before > 날짜/시간 비교
        //- GreaterThan/LessThan > 수치 비교

        //List<Address> addressList = addressRepository.findByAgeGreaterThan(3);
        //List<Address> addressList = addressRepository.findByAgeLessThan(3);
        //List<Address> addressList = addressRepository.findByAgeLessThanEqual(3);

        List<Address> addressList = addressRepository.findByAgeBetween(3, 5);

        model.addAttribute("addressList", addressList);

        return "result_list";
    }

    @GetMapping("/m10.do")
    public String m10(Model model) {

        //isEmpty, isNull
        //isNotEmpty, isNotNull
        //- isNull > null 체크
        //- isEmpty > 빈문자열, 컬렉션 size(0) 등을 체크

        //List<Address> addressList = addressRepository.findByAddressIsNull();
        List<Address> addressList = addressRepository.findByAddressIsNotNull();

        model.addAttribute("addressList", addressList);

        return "result_list";
    }

    @GetMapping("/m11.do")
    public String m11(Model model) {

        //In, NotIn
        //- 열거형 조건
        //- 매개변수가 List를 요구한다.

        List<Integer> ageList = List.of(3, 5, 7);

        //List<Address> addressList = addressRepository.findByAgeIn(ageList);
        List<Address> addressList = addressRepository.findByAgeNotIn(ageList);

        model.addAttribute("addressList", addressList);

        return "result_list";
    }

    @GetMapping("/m12.do")
    public String m12(Model model) {

        //StartingWith(StartsWith), EndingWith(EndsWith), Contains

        //List<Address> addressList = addressRepository.findByAddressStartingWith("서울특별시 강남구");
        //List<Address> addressList = addressRepository.findByAddressEndingWith("층");
        //List<Address> addressList = addressRepository.findByAddressContains("역삼");

        //List<Address> addressList = addressRepository.findByAddressLike("%타워%");
        List<Address> addressList = addressRepository.findByAddressNotLike("%타워%");

        model.addAttribute("addressList", addressList);

        return "result_list";
    }

    @GetMapping("/m13.do")
    public String m13(Model model) {

        //Is, Equals
        //Optional<Address> address = addressRepository.findByName("강아지");
        //Optional<Address> address = addressRepository.findByNameIs("강아지");
        Optional<Address> address = addressRepository.findByNameEquals("강아지");

        //IgnoreCase
        //- addressRepository.findByColor("White");
        //- addressRepository.findByColorIgnoreCase("White");

        address.ifPresent(value -> model.addAttribute("address", Address.toDTO(value)));

        return "result_dto";
    }

    @GetMapping("/m14.do")
    public String m14(Model model) {

        //정렬
        //- OrderBy컬럼명Asc
        //- OrderBy컬럼명Desc

        //다중 정렬
        //- And 사용 안함
        //- OrderBy컬럼명Asc컬럼명Desc컬럼명Asc

        //List<Address> addressList = addressRepository.findByGenderOrderByNameAsc("m");
        //List<Address> addressList = addressRepository.findByGenderOrderByNameDesc("m");
        //List<Address> addressList = addressRepository.findByGenderOrderByAgeDescNameAsc("m");


        //List<Address> addressList = addressRepository.findAll();

        //List<Address> addressList = addressRepository.findAllByOrderBySeqDesc();

        //List<Address> addressList = addressRepository.findAll(Sort.by("age"));

        //List<Address> addressList = addressRepository.findAll(Sort.by(Sort.Direction.DESC, "age"));

        //List<Address> addressList = addressRepository.findAll(Sort.by("age", "name")); //다중 정렬

//        List<Address> addressList = addressRepository.findAll(Sort.by(
//                Sort.Order.desc("age"),
//                Sort.Order.desc("name")
//        ));

        //List<Address> addressList = addressRepository.findByGender("m", Sort.by("age"));

        List<Address> addressList = addressRepository.findByGender(Sort.by("age"), "f");


        model.addAttribute("addressList", addressList);

        return "result_list";
    }


    @GetMapping("/m15.do")
    public String m15(Model model) {

        //- select * from tblAddress where 조건

        //일부 컬럼 가져오기
        //- 인터페이스 사용
        //- com.test.bootjpa.entity > AddressNameAgeMapping.java(I)

        List<AddressNameAgeMapping> addressSubList = addressRepository.findAllByGender("f");

        System.out.println(addressSubList);

        model.addAttribute("addressSubList", addressSubList);

        return "result_list";
    }


    @GetMapping("/m16.do")
    public String m16(Model model) {

        //JPQL
        //- SQL 직접 생성
        //- 메서드명은 자유롭게 작성한다.
        //- DB 대상으로 하는 SQL(X)
        //- 엔티티를 대상으로 하는 SQL(O)
        //- 엔티티에 반드시 별칭을 사용해야 한다.(as 생략 가능)
        //- 컬럼명 앞에 반드시 엔티티를 표현해야 한다.(select name > select a.name)
        //- ANSI의 많은 구문을 그대로 지원한다.
        //- 많은 함수를 그대로 제공한다. > JPQL 자체 함수 일부 제공


        //List<String> names = addressRepository.listName();
        //model.addAttribute("names", names);

        //List<Address> addressList =  addressRepository.listAll(); //findAll()

        //List<Address> addressList =  addressRepository.listAll("m");

        //List<Address> addressList =  addressRepository.listAll(5);

        //AddressDTO dto = new AddressDTO();
        //dto.setGender("f"); // =
        //dto.setAddress("강남구"); // like

        //List<Address> addressList =  addressRepository.listAll(dto);
        //model.addAttribute("addressList", addressList);


        List<AddressDTO> customAddressList = addressRepository.listCustomAll();

        model.addAttribute("customAddressList", customAddressList);

        return "result_list";
    }

    @GetMapping("/m17.do")
    public String m17(Model model) {

        //페이징
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Address> addressList = addressRepository.findAll(pageRequest);


        //페이지 정보
        System.out.println(addressList.getNumber()); //0 > 페이지 번호
        System.out.println(addressList.getNumberOfElements()); //10 > 가져온 엔티티 수
        System.out.println(addressList.getTotalElements()); //50 > 총 엔티티 수
        System.out.println(addressList.getTotalPages()); //5 > 총 페이지 수
        System.out.println(addressList.getSize()); //10 > 한 페이지당 엔티티 수


        model.addAttribute("addressList", addressList);

        return "result_list";
    }

    @GetMapping("/m18.do")
    public String m18(Model model, @RequestParam(name = "page", required = false, defaultValue = "1") Integer page) {

        page = page - 1;

        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("name"));

        Page<Address> addressList = addressRepository.findAll(pageRequest);


        StringBuilder sb = new StringBuilder();

        for (int i=0; i<addressList.getTotalPages(); i++) {
            sb.append("""
                    <a href="/m18.do?page=%d">%d</a>
                    """.formatted(i + 1, i + 1));
        }

        model.addAttribute("addressList", addressList);
        model.addAttribute("sb", sb.toString());

        return "result_list";
    }

    @GetMapping("/m19.do")
    public String m19(Model model, Pageable pageable) {

        //m19.do?page=0
        //m19.do?page=1
        //m19.do?page=2

        //m19.do?page=0&size=10
        //m19.do?page=1&size=10
        //m19.do?page=2&size=10

        //m19.do?page=0&size=10&sort=name.asc
        //m19.do?page=1&size=10&sort=name.asc
        //m19.do?page=2&size=10&sort=name.asc

        Page<Address> addressList = addressRepository.findAll(pageable);

        model.addAttribute("addressList", addressList);

        return "result_list";
    }


    @GetMapping("/m20.do")
    public String m20(Model model, Pageable pageable) {

        Slice<Address> addressList = addressRepository.findAll(pageable);

        System.out.println(addressList.getNumber()); //0 > 현재 페이지
        System.out.println(addressList.getNumberOfElements()); //20 > 데이터 개수
        System.out.println(addressList.getSize()); //20 > 데이터 개수
        System.out.println(addressList.hasContent()); //true > 슬라이스에 데이터 있는지?
        System.out.println(addressList.hasNext()); //true > 다음 슬라이스가 있는지?
        System.out.println(addressList.hasPrevious()); //false > 이전 슬라이스가 있는지?
        System.out.println(addressList.nextOrLastPageable());
        System.out.println(addressList.nextPageable());
        System.out.println(addressList.previousOrFirstPageable());
        System.out.println(addressList.previousPageable());
        System.out.println(addressList.isFirst());
        System.out.println(addressList.isLast());

        StringBuilder sb = new StringBuilder();

        sb.append("""
                    <a href="/m20.do?page=%d">이전 페이지</a>
                    """.formatted(addressList.previousOrFirstPageable().getPageNumber()));

        sb.append("""
                    <a href="/m20.do?page=%d">다음 페이지</a>
                    """.formatted(addressList.nextOrLastPageable().getPageNumber()));

        model.addAttribute("addressList", addressList);
        model.addAttribute("sb", sb.toString());

        return "result_list";
    }

    @GetMapping("/m21.do")
    public String m21(Model model) {

        //Join
        //- 엔티티 관계를 미리 설정

        //1:1
        //tblAddress : tblInfo > Join

        Optional<Info> info = infoRepository.findById(1L);

        model.addAttribute("info", info.get());

        return "result_dto";
    }

    @GetMapping("/m22.do")
    public String m22(Model model) {

        //Join
        //- 엔티티 관계를 미리 설정

        //1:N
        //tblAddress : tblMemo > Join
        List<Address> joinList = addressRepository.findAll();

        model.addAttribute("joinList", joinList);

        return "result_list";
    }


    @GetMapping("/m23.do")
    public String m23(Model model) {

        //Query DSL(Domain Specific Language)
        //- JPQL 작성을 도와주는 동적 쿼리 빌더
        //- JPQL 작성을 도와주는 오픈소스 라이브러리

        //JPQL 대신 Query DSL 사용 목적
        //- 동적 쿼리 용이
        //- 문자열 > 메서드 동작

        //QClass 생성
        //- 엔티티에 대응되는 정적 클래스 > 메서드를 사용해서 해당 엔티티에 적용하는 쿼리 작성

        //Config
        //- "com.test.bootjpa.config" > "QueryDSLConfig.java"

        //파일
        //- com.test.bootjpa.repository > "CustomAddressRepository.java"(I)
        //- com.test.bootjpa.repository > "CustomAddressRepositoryImpl.java"(C)


        //전체 리스트 조회
        List<Address> addressList = customAddressRepository.findAll();

        model.addAttribute("addressList", addressList);

        return "result_list";
    }

    @GetMapping("/m24.do")
    public String m24(Model model) {

        //단일 조회(레코드 1개)
        Address address = customAddressRepository.findAddressByName("강아지");

        model.addAttribute("address", Address.toDTO(address));

        return "result_dto";
    }

    @GetMapping("/m25.do")
    public String m25(Model model) {

        //특정 컬럼 가져오기
        //- 1개
        List<String> names = customAddressRepository.findName();

        model.addAttribute("names", names);

        return "result_list";
    }

    @GetMapping("/m26.do")
    public String m26(Model model) {

        List<Tuple> customList = customAddressRepository.findNameAndAge();

        //System.out.println(">>>>>>>>>>>>>>" + customList);

        for (Tuple tuple : customList) {
            System.out.println(tuple.get(0, String.class));
            System.out.println(tuple.get(1, Integer.class));
            System.out.println();
        }

        model.addAttribute("customList", customList);

        return "result_list";
    }

    @GetMapping("/m27.do")
    public String m27(Model model) {

        //일부 컬럼 조회
        //1. Tuple 사용
        //2. DTO 사용

        List<AddressDTO> addressDTOList = customAddressRepository.findNameAndAddress();

        model.addAttribute("addressDTOList", addressDTOList);

        return "result_list";
    }

    @GetMapping("/m28.do")
    public String m28(Model model) {

        //where() 절
        List<Address> addressList = customAddressRepository.findAddressByGender("m");

        model.addAttribute("addressList", addressList);

        return "result_list";
    }


    @GetMapping("/m29.do")
    public String m29(Model model) {

        //정렬
        List<Address> addressList = customAddressRepository.findAddressOrderBy();

        model.addAttribute("addressList", addressList);

        return "result_list";
    }

    @GetMapping("/m30.do")
    public String m30(Model model, @RequestParam(defaultValue = "1", name = "page") Integer page) {

        //페이징
        //- offset : 가져올 시작 위치(0)
        //- limit  : 가져올 개수

        int offset = (page - 1) * 10;

        List<Address> addressList
                = customAddressRepository.findAddressPagenation(offset, 10);

        int count = customAddressRepository.count();

        StringBuilder sb = new StringBuilder();

        for (int i = 1; i<= (int)Math.ceil(count / 10.0); i++) {

            sb.append(String.format(" <a href='/m30.do?page=%d'>%d</a> ", i, i));

        }


        model.addAttribute("addressList", addressList);
        model.addAttribute("sb", sb.toString());

        return "result_list";
    }

    @GetMapping("/m31.do")
    public String m31(Model model) {

        //집계 함수
        // - Tuple

        Tuple addressTuple = customAddressRepository.findAddressAggregation();

        model.addAttribute("addressTuple", addressTuple);

        return "result_scalar";
    }

    @GetMapping("/m32.do")
    public String m32(Model model) {

        //group by, having
        List<Tuple> addressTuple = customAddressRepository.findAddressGroupByGender();

        model.addAttribute("addressTuple", addressTuple);

        return "result_list";
    }


    /*
    @GetMapping("/m05.do")
    public String m05(Model model) {

        return "";
    }
    */


}
