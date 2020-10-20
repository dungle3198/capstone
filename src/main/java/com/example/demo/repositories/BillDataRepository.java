package com.example.demo.repositories;
import com.example.demo.entities.Bill;
import com.example.demo.entities.BillData;
import com.example.demo.entities.InternetMeanMonth;
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
    List<BillData> getBillDataList(int id, String category, String biller);

    @Query ("select b from BillData b where b.user.id = ?1 and b.category = ?2 and b.biller = ?3")
    List<BillData> getBillDataByUserIdAndCategoryAndBiller(int id, String category, String biller);
}
