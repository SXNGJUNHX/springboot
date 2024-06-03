package com.test.bootthymeleaf;

import com.test.bootthymeleaf.mapper.AddressMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MapperTests {

    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void test() {

        System.out.println(addressMapper.time());

    }

}










