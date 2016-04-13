package com.example;

import com.earl.carnet.Application;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;

@RunWith(Cucumber.class)
@SpringApplicationConfiguration(classes = Application.class)
@CucumberOptions(features = {"src/test/java/com/example/features"},format = { "pretty", "html:target/cucumber-html-report", "json:target/cucumber-json-report.json" })
public class RunCukesTest {

}