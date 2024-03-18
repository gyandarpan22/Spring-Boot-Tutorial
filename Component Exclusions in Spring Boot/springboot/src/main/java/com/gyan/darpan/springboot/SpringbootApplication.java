package com.gyan.darpan.springboot;

import com.gyan.darpan.springboot.annotation.ExcludeFilterAnnotation;
import com.gyan.darpan.springboot.filter.CustomFilter;
import com.gyan.darpan.springboot.service.EmployeeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

//If you want to exclude AutoConfiguration classes
@SpringBootApplication(exclude = {
            DataSourceAutoConfiguration.class
        }
)
//Now we use FilterType=CUSTOM
//Let's Start
@ComponentScan(
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.CUSTOM,
                        classes = CustomFilter.class
                )
        }
)
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

}

/*
1.    ASSIGNABLE_TYPE filter  , excluding greeting service
//@ComponentScan(
//        excludeFilters = {
//                @ComponentScan.Filter(
//                        type = FilterType.ASSIGNABLE_TYPE,
//                        value = {
//                                GreetingService.class
//                        }
//                )
//        }
//

2. ASSIGNABLE_TYPE filter  , excluding greeting service
@ComponentScan(
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        value = {
                                EmployeeService.class
                        }
                )
        }
)

3.ANNOTATION filter  , excluding greeting service
@ComponentScan(
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ANNOTATION,
                        value = {
                                ExcludeFilterAnnotation.class
                        }
                )
        }
)

4.
//Now we use FilterType=REGEX and exclude all class which name start with Emp
//Let's Start
@ComponentScan(
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.REGEX,
                        pattern = {
                             ".*\\.Emp[^\\.]*$"
                        }
                )
        }
)

5.
//Now we use FilterType=CUSTOM
//Let's Start
@ComponentScan(
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.CUSTOM,
                        classes = CustomFilter.class
                )
        }
)




 */
