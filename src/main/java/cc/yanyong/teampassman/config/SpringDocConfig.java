package cc.yanyong.teampassman.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
    /**
     * https://springdoc.org/#migrating-from-springfox
     * https://stackoverflow.com/questions/59357205/springdoc-openapi-apply-default-global-securityscheme-possible
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(info());
    }

    private Info info() {
        return new Info()
                .title("TeamPassMan团队密码管理")
                .description("API文档")
                .version("0.1");
    }

    @Bean
    public GroupedOpenApi teampassmanApi() {
        return GroupedOpenApi.builder()
                .group("teampassman-api")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("user-api")
                .pathsToMatch("/user/**")
                .build();
    }

    @Bean
    public GroupedOpenApi vaultApi() {
        return GroupedOpenApi.builder()
                .group("vault-api")
                .pathsToMatch("/vault/**")
                .build();
    }

    @Bean
    public GroupedOpenApi itemApi() {
        return GroupedOpenApi.builder()
                .group("item-api")
                .pathsToMatch("/item/**")
                .build();
    }
}
