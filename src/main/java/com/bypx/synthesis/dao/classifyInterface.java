package com.bypx.synthesis.dao;

import com.bypx.synthesis.bean.Classify;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface classifyInterface {
    public List query();
    public void add(Classify classify);
    public void edit(Classify classify);
    public List show(Classify classify);
    public void delete(Classify classify);

}
