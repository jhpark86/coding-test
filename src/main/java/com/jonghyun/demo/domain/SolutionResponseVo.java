package com.jonghyun.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SolutionResponseVo {
	private String quotient;
	private String remainder;
}
