/**
 * 
 */
package com.macaitech.beanpay.swagger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*重要！如果你的项目引入junit测试，此处需要使用@WebAppConfiguration，
 * 如果没有使用junit使用@Configuration(很多的博客都没有注明这个问题，为此我花了非常多的时间解决问题)
 * */
@Configuration
//@WebAppConfiguration
@EnableSwagger2//重要！
@EnableWebMvc
@ComponentScan(basePackages = "com.macaitech.beanpay.action")//扫描control所在的package请修改为你control所在package
public class SwaggerConfig {
	/**
	 * 是否启用swagger 默认不启用
	 */
	private boolean swaggerShow = false;
	
    public boolean isSwaggerShow() {
		return swaggerShow;
	}
	public void setSwaggerShow(String swaggerShow) {
		try {
			this.swaggerShow = Boolean.valueOf(swaggerShow);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
        			.enable(swaggerShow)
                .select()
                .apis(RequestHandlerSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("HiPay接口文档")
                .description("HiPay接口文档")
                .version("1.0.0")
                .termsOfServiceUrl("")
                .license("")
                .licenseUrl("")
                .build();
    }
}