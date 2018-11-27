package com.learn.restapi.nov.NovApi.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Locale;

import com.learn.restapi.nov.NovApi.bean.HelloWorld;



@RestController
public class HelloWorldController {
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping("/hello-world")
	public String helloWorld() {
		return "Hello World!!!";
	}
	
	@GetMapping("/hello-world-bean")
	public HelloWorld helloWorldBean() {
		return new HelloWorld("Amol", new Date());
	}
	
	@GetMapping("/hello-world-pathvar/{name}")
	public HelloWorld helloWorldPathVar(@PathVariable("name") String name)  {
		return new HelloWorld(name, new Date());
	}
	
	@GetMapping("/hello-world-param/banait")
	public HelloWorld helloWorldParam(@RequestParam(name="name", required=true) String name, @RequestParam(name="adr", required=false) String adr) {
		return new HelloWorld(name+adr, new Date());
	}
	
	@GetMapping("/hello-world-internationalized")
	public String helloWorldInternationalized(@RequestHeader(name="Accept-Language", required=false) Locale locale) {
		return messageSource.getMessage("good.morning.message", null, "Good Morning", LocaleContextHolder.getLocale()).toString();
	}
		
}
