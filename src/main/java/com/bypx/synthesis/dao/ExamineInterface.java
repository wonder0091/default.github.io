package com.bypx.synthesis.dao;

import com.bypx.synthesis.bean.Outbound;
import com.bypx.synthesis.bean.Products;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ExamineInterface {

    public List<Outbound> queryEx(Outbound outbound);

    void delete(String id);
}
