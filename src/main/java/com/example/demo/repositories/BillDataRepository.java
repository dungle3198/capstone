package com.example.demo.repositories;
import com.example.demo.entities.BillData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillDataRepository extends JpaRepository<BillData, Integer> {
    @Query ("select b from BillData b where b.user.id = ?1")
    List<BillData> getBillDataByUserId(int id);

    @Query ("select b from BillData b where b.user.id = ?1 and b.category = ?2 and b.biller = ?3 " +
                                            "and b.month != 0 and b.year != 0")
    List<BillData> getBillDataWithDate(int id, String category, String biller);

    @Query ("select b from BillData b where b.user.id = ?1 and b.category = ?2 and b.biller = ?3")
    List<BillData> getBillDataByUserIdAndCategoryAndBiller(int id, String category, String biller);

    @Query ("select b from BillData b where b.status = 1")
    List<BillData> getTrueBillData();

    @Query ("select b from BillData b where b.status = 0")
    List<BillData> getFalseBillData();
}
