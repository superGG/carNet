package com.example;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;

import com.earl.carnet.Application;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@SpringApplicationConfiguration(classes = Application.class)
@CucumberOptions(features = {"classpath:features"},format = { "pretty", "html:target/cucumber-html-report", "json:target/cucumber-json-report.json" })
public class RunCukesTest {

}