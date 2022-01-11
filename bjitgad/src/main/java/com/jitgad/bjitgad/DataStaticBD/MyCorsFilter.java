//
//package com.jitgad.bjitgad.DataStaticBD;
//
//import java.io.IOException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Set;
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.apache.juli.logging.Log;
//import org.apache.juli.logging.LogFactory;
//import org.apache.tomcat.util.res.StringManager;
//
///**
// *
// * @author jorge
// */
//public class MyCorsFilter {
//    private static final Log log = LogFactory.getLog(MyCorsFilter.class);
//	private static final StringManager sm = StringManager.getManager("org.apache.catalina.filters");
//	private final Collection<String> allowedOrigins;
//	private boolean anyOriginAllowed;
//	private final Collection<String> allowedHttpMethods;
//	private final Collection<String> allowedHttpHeaders;
//	private final Collection<String> exposedHeaders;
//	private boolean supportsCredentials;
//	private long preflightMaxAge;
//	private boolean decorateRequest;
//	public static final String RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
//	public static final String RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
//	public static final String RESPONSE_HEADER_ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
//	public static final String RESPONSE_HEADER_ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
//	public static final String RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
//	public static final String RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
//	public static final String REQUEST_HEADER_ORIGIN = "Origin";
//	public static final String REQUEST_HEADER_ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
//	public static final String REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
//	public static final String HTTP_REQUEST_ATTRIBUTE_PREFIX = "cors.";
//	public static final String HTTP_REQUEST_ATTRIBUTE_ORIGIN = "cors.request.origin";
//	public static final String HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST = "cors.isCorsRequest";
//	public static final String HTTP_REQUEST_ATTRIBUTE_REQUEST_TYPE = "cors.request.type";
//	public static final String HTTP_REQUEST_ATTRIBUTE_REQUEST_HEADERS = "cors.request.headers";
// 
//	public MyCorsFilter() {
//		this.allowedOrigins = new HashSet();
//		this.allowedHttpMethods = new HashSet();
//		this.allowedHttpHeaders = new HashSet();
//		this.exposedHeaders = new HashSet();
//	}
// 
//	
//
//}
