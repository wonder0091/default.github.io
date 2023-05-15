package com.bypx.synthesis.dao;

import com.bypx.synthesis.bean.Outbound;
import com.bypx.synthesis.bean.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutboundInterface {

    void edit(Outbound outbound);
    void delete(String id);

    List<Outbound> query(Outbound outbound);
}
