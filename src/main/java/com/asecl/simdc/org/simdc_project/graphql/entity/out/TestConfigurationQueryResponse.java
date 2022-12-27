package com.asecl.simdc.org.simdc_project.graphql.entity.out;

import com.asecl.simdc.org.simdc_project.db.entity.TestConfiguration;
import lombok.Data;

import java.util.List;

@Data
public class TestConfigurationQueryResponse {
    private long TotalPage;
    private int PageSize;
    private int PageNumber;
    private int TotalSize;
    private List<TestConfiguration> Datas;
}
