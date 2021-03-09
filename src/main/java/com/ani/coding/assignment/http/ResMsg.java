package com.ani.coding.assignment.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResMsg<T> {
    private String msg;
    private String sts;
    private T payload;
}
