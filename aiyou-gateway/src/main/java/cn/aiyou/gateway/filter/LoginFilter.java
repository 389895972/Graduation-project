//package cn.aiyou.gateway.filter;
//
//import cn.aiyou.common.utils.JwtUtils;
//import cn.aiyou.gateway.config.JwtProperties;
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import com.netflix.zuul.exception.ZuulException;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.List;
//
//@Component
//@EnableConfigurationProperties({JwtProperties.class, FilterProperties.class})
//public class LoginFilter extends ZuulFilter {
//    @Override
//    public String filterType() {
//        return null;
//    }
//
//    @Override
//    public int filterOrder() {
//        return 0;
//    }
//
//    @Override
//    public boolean shouldFilter() {
//        return false;
//    }
//
//    @Override
//    public Object run() throws ZuulException {
//        return null;
//    }
//
////    @Autowired
////    private JwtProperties jwtProperties;
////
////    @Autowired
////    private FilterProperties filterProperties;
////    @Override
////    public String filterType() {
////        return "pre";
////    }
////
////    @Override
////    public int filterOrder() {
////        return 10;
////    }
////
////    @Override
////    public boolean shouldFilter() {
////        List<String> allowPaths = this.filterProperties.getAllowPaths();
////        //初始化运行上下文
////        RequestContext context= RequestContext.getCurrentContext();
////        //获取 request对象
////        HttpServletRequest request = context.getRequest();
////
////        String url= request.getRequestURL().toString();
////        for (String allowPath : allowPaths) {
////            if(StringUtils.contains(url,allowPath)){
////                return  false;
////            }
////        }
////        return true;
////    }
////
////    @Override
////    public Object run() throws ZuulException {
//////        //初始化运行上下文
//////        RequestContext context= RequestContext.getCurrentContext();
//////        //获取 request对象
//////        HttpServletRequest request = context.getRequest();
//////
//////       String token = CookieUtils.getCookieValue(request, this.jwtProperties.getCookieName());
//////
////////        if(StringUtils.isBlank(token)){
////////            context.setSendZuulResponse(false);
////////            context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
////////        }
//////        try {
//////            JwtUtils.getInfoFromToken(token,this.jwtProperties.getPublicKey());
//////        } catch (Exception e) {
//////            e.printStackTrace();
//////            context.setSendZuulResponse(false);
//////            context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
//////        }
////        return null;
////    }
//
//}
