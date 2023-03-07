package com.nest.bluehydrogen.runner;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
@RunWith(Cucumber.class)
@CucumberOptions(features ="src/test/resources/features/Kstream.feature",glue="com.nest.bluehydrogen.stepdef")
public class test {
}
