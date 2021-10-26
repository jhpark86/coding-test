package com.jonghyun.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SolutionRequestVo {
	private String url;
	private String type;
	private int output_unit;
}
