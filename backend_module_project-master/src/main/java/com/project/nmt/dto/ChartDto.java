package com.project.nmt.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
public class ChartDto {
	private LocalDate infoDate;  //  주식 정보 날짜
	private int price;           //  당일 가격
    
}
