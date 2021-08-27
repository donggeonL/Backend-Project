package com.project.nmt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {
	String name;
	String keyword;
	int quantity;
	int boughtPrice;
	int nowPrice;
	Long stockId;
	String percent;
}