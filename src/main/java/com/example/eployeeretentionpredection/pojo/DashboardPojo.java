package com.example.eployeeretentionpredection.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DashboardPojo {
    private Long empCount;
    private Long retentionCount;
    private Long jobSatisCount;
}
