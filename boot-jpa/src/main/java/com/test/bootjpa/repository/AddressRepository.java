package com.test.bootjpa.repository;

import com.test.bootjpa.dto.AddressDTO;
import com.test.bootjpa.entity.Address;
import com.test.bootjpa.entity.AddressNameAgeMapping;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

//JapRepository<엔티티, 인티티 @Id 자료형>
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByName(String name);

    Optional<Address> findByAddress(String address);

    Optional<Address> getByName(String name);

    Address findFirstByAge(int age);

    List<Address> findFirstByGender(String gender);

    List<Address> findFirst3ByGender(String gender);

    List<Address> findFirst30ByGender(String gender);

    Address findTopByAge(int age);

    List<Address> findTop10ByGender(String gender);

    Optional<Address> findByNameAndGender(String name, String gender);

    List<Address> findByGenderAndAge(String gender, int age);

    List<Address> findByGenderOrAge(String gender, int age);

    List<Address> findByAgeGreaterThan(int age);

    List<Address> findByAgeLessThan(int age);

    List<Address> findByAgeLessThanEqual(int age);

    List<Address> findByAgeBetween(int min, int max);

    List<Address> findByAddressIsNull();

    List<Address> findByAddressIsNotNull();

    List<Address> findByAgeIn(List<Integer> ageList);

    List<Address> findByAgeNotIn(List<Integer> ageList);

    List<Address> findByAddressStartingWith(String address);

    List<Address> findByAddressEndingWith(String address);

    List<Address> findByAddressContains(String address);

    List<Address> findByAddressLike(String search);

    List<Address> findByAddressNotLike(String search);

    Optional<Address> findByNameEquals(String name);

    List<Address> findByGenderOrderByNameAsc(String gender);

    List<Address> findByGenderOrderByNameDesc(String gender);

    List<Address> findByGenderOrderByAgeDescNameAsc(String gender);

    List<Address> findAllByOrderBySeqDesc();

    List<Address> findByGender(String gender, Sort age);

    List<Address> findByGender(Sort age, String gender);

    List<AddressNameAgeMapping> findAllByGender(String gender);


    //JPQL
    @Query("select a.name from Address as a")
    //@Query(value = "select name from tblAddress", nativeQuery = true)
    List<String> listName();

    @Query("select a from Address as a")
    List<Address> listAll();

    @Query("select a from Address as a where a.gender = ?1")
    List<Address> listAll(String gender);

    @Query("select a from Address as a where a.age >= :age")
    List<Address> listAll(@Param(value="age") int age);

    @Query("select a from Address as a where a.gender = :#{#dto.gender} and a.address like '%' || :#{#dto.address} || '%'")
    List<Address> listAll(AddressDTO dto);

    @Query("select new com.test.bootjpa.dto.AddressDTO(a.seq, a.name, a.age, a.address, a.gender, substr(a.address, 1, 6), year(current_date) - a.age) from Address a")
    List<AddressDTO> listCustomAll();

    //Optional<Address> findByGender(String gender);

}











