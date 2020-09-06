//package configuration;
//
//import com.google.common.collect.Lists;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.ParameterBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.schema.ModelRef;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Parameter;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.util.List;
//
//@Configuration
//@EnableSwagger
//public class SwaggerConfig {
//	@Value("${spring.profiles.title}")
//	private String title;
//	@Value("${spring.profiles.description}")
//	private String description;
//	@Value("${spring.profiles.version}")
//	private String version;
//
//	@Bean
//	public Docket testApi() {
//		ParameterBuilder tokenPar = new ParameterBuilder();
//		List<Parameter> pars = Lists.newLinkedList();
//		tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header")
//				.required(false).build();
//		pars.add(tokenPar.build());
//		return new Docket(DocumentationType.SWAGGER_2)
//				.apiInfo(apiInfo())
//				.select()
//				.paths(PathSelectors.any()).build().globalOperationParameters(pars);
//	}
//
//	private ApiInfo apiInfo() {
//		return new ApiInfoBuilder().title(this.title).description(this.description).version(this.version).build();
//	}
//
//
//}
