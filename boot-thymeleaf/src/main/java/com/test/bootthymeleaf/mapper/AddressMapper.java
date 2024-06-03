package com.test.bootthymeleaf.mapper;

import com.test.bootthymeleaf.dto.AddressDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressMapper {

    String time();

    int num();

    String txt();

    AddressDTO get();

    List<String> names();

    List<AddressDTO> list();
}
