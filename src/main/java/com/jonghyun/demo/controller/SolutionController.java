package com.jonghyun.demo.controller;

import com.jonghyun.demo.domain.SolutionRequestVo;
import com.jonghyun.demo.domain.SolutionResponseVo;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class SolutionController {

	@GetMapping("/solution")
	public SolutionResponseVo solution(@Validated SolutionRequestVo solutionRequestVo){
		log.info(solutionRequestVo.toString());
		String htmlContents = "";
		StringBuffer contents = new StringBuffer();
		try{
			URL url = new URL(solutionRequestVo.getUrl());
			URLConnection con = (URLConnection) url.openConnection();
			InputStreamReader isr = new InputStreamReader(con.getInputStream(),"utf-8");

			BufferedReader buff = new BufferedReader(isr);

			while((htmlContents = buff.readLine()) !=null){
				if("htmlExcept".equals(solutionRequestVo.getType())){
					htmlContents = htmlContents.replaceAll("<[^>]*>", "");
				}
				htmlContents = htmlContents.replaceAll("[^0-9a-zA-Z\\\\s]", "");

				contents.append(htmlContents);
			}
			buff.close();
		}catch (Exception e){
			e.printStackTrace();
		}

		String[] strs = contents.toString().split("");
		List<String> nums = Arrays.stream(strs).sorted().filter(s -> s.charAt(0) >= 48 && s.charAt(0) <=57).collect(Collectors.toList());
		List<String> alps = Arrays.stream(strs).sorted(String::compareToIgnoreCase).filter(s -> s.charAt(0) >= 65 && s.charAt(0) <=122).collect(Collectors.toList());

		List<String> strResult = new ArrayList<String>();
		int size = nums.size() > alps.size() ? nums.size() : alps.size();
		for(int i=0; i < size; i++){
			if(alps.size() > i){
				strResult.add(alps.get(i));
			}
			if(nums.size() > i){
				strResult.add(nums.get(i));
			}
		}

		String result = strResult.stream().map(s -> String.valueOf(s)).collect(Collectors.joining());

		String quotient = result.substring(0,result.length()/solutionRequestVo.getOutput_unit() * solutionRequestVo.getOutput_unit() -1);
		String remainder = result.substring(result.length() - result.length()%solutionRequestVo.getOutput_unit() -1 , result.length());
		return SolutionResponseVo.builder().quotient(quotient).remainder(remainder).build();
	}
}
