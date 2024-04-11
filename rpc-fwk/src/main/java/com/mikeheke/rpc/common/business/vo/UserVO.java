package com.mikeheke.rpc.common.business.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserVO implements Serializable {

    private Long id;

    private String account;

    private String name;

    private String job;

}
