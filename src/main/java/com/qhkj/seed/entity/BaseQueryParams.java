package com.qhkj.seed.entity;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

import lombok.Getter;
import lombok.Setter;

/**
 * 列表查询的基本参数结构
 */
@Getter @Setter
public class BaseQueryParams {
	@QueryParam("fuzzy")
    private String fuzzy;
	@QueryParam("limit") @DefaultValue("10")
    private int limit;
	@QueryParam("page") @DefaultValue("1")
    private int page;
    
    public BaseQueryParams() {

    }

	public BaseQueryParams(String fuzzy, int limit, int page) {
		super();
		this.fuzzy = fuzzy;
		this.limit = limit;
		this.page = page;
	}
}
