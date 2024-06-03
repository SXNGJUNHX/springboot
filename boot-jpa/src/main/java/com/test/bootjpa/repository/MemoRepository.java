package com.test.bootjpa.repository;

import com.test.bootjpa.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> {


}
