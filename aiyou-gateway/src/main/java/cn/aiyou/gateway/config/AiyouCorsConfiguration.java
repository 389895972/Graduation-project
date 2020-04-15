package cn.aiyou.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class AiyouCorsConfiguration {

     @Bean
     public CorsFilter corsFilter(){
         //初始化cors配置对象
         CorsConfiguration corsConfiguration = new CorsConfiguration();
         //允许跨域的 域名，如果要携带cookie 不能写 *， *代表所有域名都可以跨域访问
//         corsConfiguration.addAllowedOrigin("http://manage.leyou.com");
//         corsConfiguration.addAllowedOrigin("http://www.leyou.com");
         corsConfiguration.addAllowedOrigin("*");
         corsConfiguration.setAllowCredentials(true);
         corsConfiguration.addAllowedMethod("*");
         corsConfiguration.addAllowedHeader("*");

         //初始化cors配置源对象
         UrlBasedCorsConfigurationSource corsConfigurationSource=new UrlBasedCorsConfigurationSource();
         corsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
         //返回corsFilter实例，参数：cors配置源对象
             return new CorsFilter(corsConfigurationSource);
     }
}
