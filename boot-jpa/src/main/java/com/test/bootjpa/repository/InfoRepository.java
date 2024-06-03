package com.test.bootjpa.repository;

import com.test.bootjpa.entity.Info;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfoRepository extends JpaRepository<Info, Long> {

}
