package com.bypx.synthesis.dao;

import com.bypx.synthesis.bean.Outbound;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehousingInterface {

    void edit(Outbound outbound);
    void delete(String id);

    List<Outbound> query(Outbound outbound);
}
