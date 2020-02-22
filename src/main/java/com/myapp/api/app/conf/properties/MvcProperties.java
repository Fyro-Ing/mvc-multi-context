package com.myapp.api.app.conf.properties;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.validation.DefaultMessageCodesResolver;

public class MvcProperties {

  /**
   * Formatting strategy for message codes. For instance, `PREFIX_ERROR_CODE`.
   */
  private DefaultMessageCodesResolver.Format messageCodesResolverFormat;

  /**
   * Locale to use. By default, this locale is overridden by the "Accept-Language" header.
   */
  private Locale locale;

  /**
   * Define how the locale should be resolved.
   */
  private LocaleResolver localeResolver = LocaleResolver.ACCEPT_HEADER;

  private final Format format = new Format();

  /**
   * Whether to dispatch TRACE requests to the FrameworkServlet doService method.
   */
  private boolean dispatchTraceRequest = false;

  /**
   * Whether to dispatch OPTIONS requests to the FrameworkServlet doService method.
   */
  private boolean dispatchOptionsRequest = true;

  /**
   * Whether the content of the "default" model should be ignored during redirect scenarios.
   */
  private boolean ignoreDefaultModelOnRedirect = true;

  /**
   * Whether to publish a ServletRequestHandledEvent at the end of each request.
   */
  private boolean publishRequestHandledEvents = true;

  /**
   * Whether a "NoHandlerFoundException" should be thrown if no Handler was found to process a
   * request.
   */
  private boolean throwExceptionIfNoHandlerFound = false;
  private final Async async = new Async();

  /**
   * Whether to enable warn logging of exceptions resolved by a "HandlerExceptionResolver", except
   * for "DefaultHandlerExceptionResolver".
   */
  private boolean logResolvedException = false;

  /**
   * Path pattern used for static resources.
   */
  private String staticPathPattern = "/**";
  private final Servlet servlet = new Servlet();
  private final View view = new View();
  private final Contentnegotiation contentnegotiation = new Contentnegotiation();
  private final Pathmatch pathmatch = new Pathmatch();
  /**
   * Whether logging of (potentially sensitive) request details at DEBUG and TRACE level is
   * allowed.
   */
  private boolean logRequestDetails;

  public DefaultMessageCodesResolver.Format getMessageCodesResolverFormat() {
    return this.messageCodesResolverFormat;
  }

  public void setMessageCodesResolverFormat(
    final DefaultMessageCodesResolver.Format messageCodesResolverFormat) {
    this.messageCodesResolverFormat = messageCodesResolverFormat;
  }

  public Locale getLocale() {
    return this.locale;
  }

  public void setLocale(final Locale locale) {
    this.locale = locale;
  }

  public LocaleResolver getLocaleResolver() {
    return this.localeResolver;
  }

  public void setLocaleResolver(final LocaleResolver localeResolver) {
    this.localeResolver = localeResolver;
  }

  @Deprecated
  @DeprecatedConfigurationProperty(replacement = "spring.mvc.format.date")
  public String getDateFormat() {
    return this.format.getDate();
  }

  @Deprecated
  public void setDateFormat(final String dateFormat) {
    this.format.setDate(dateFormat);
  }

  public Format getFormat() {
    return this.format;
  }

  public boolean isIgnoreDefaultModelOnRedirect() {
    return this.ignoreDefaultModelOnRedirect;
  }

  public void setIgnoreDefaultModelOnRedirect(final boolean ignoreDefaultModelOnRedirect) {
    this.ignoreDefaultModelOnRedirect = ignoreDefaultModelOnRedirect;
  }

  public boolean isPublishRequestHandledEvents() {
    return this.publishRequestHandledEvents;
  }

  public void setPublishRequestHandledEvents(final boolean publishRequestHandledEvents) {
    this.publishRequestHandledEvents = publishRequestHandledEvents;
  }

  public boolean isThrowExceptionIfNoHandlerFound() {
    return this.throwExceptionIfNoHandlerFound;
  }

  public void setThrowExceptionIfNoHandlerFound(final boolean throwExceptionIfNoHandlerFound) {
    this.throwExceptionIfNoHandlerFound = throwExceptionIfNoHandlerFound;
  }

  public boolean isLogRequestDetails() {
    return this.logRequestDetails;
  }

  public void setLogRequestDetails(final boolean logRequestDetails) {
    this.logRequestDetails = logRequestDetails;
  }

  public boolean isLogResolvedException() {
    return this.logResolvedException;
  }

  public void setLogResolvedException(final boolean logResolvedException) {
    this.logResolvedException = logResolvedException;
  }

  public boolean isDispatchOptionsRequest() {
    return this.dispatchOptionsRequest;
  }

  public void setDispatchOptionsRequest(final boolean dispatchOptionsRequest) {
    this.dispatchOptionsRequest = dispatchOptionsRequest;
  }

  public boolean isDispatchTraceRequest() {
    return this.dispatchTraceRequest;
  }

  public void setDispatchTraceRequest(final boolean dispatchTraceRequest) {
    this.dispatchTraceRequest = dispatchTraceRequest;
  }

  public String getStaticPathPattern() {
    return this.staticPathPattern;
  }

  public void setStaticPathPattern(final String staticPathPattern) {
    this.staticPathPattern = staticPathPattern;
  }

  public Async getAsync() {
    return this.async;
  }

  public Servlet getServlet() {
    return this.servlet;
  }

  public View getView() {
    return this.view;
  }

  public Contentnegotiation getContentnegotiation() {
    return this.contentnegotiation;
  }

  public Pathmatch getPathmatch() {
    return this.pathmatch;
  }

  public static class Async {

    /**
     * Amount of time before asynchronous request handling times out. If this value is not set, the
     * default timeout of the underlying implementation is used.
     */
    private Duration requestTimeout;

    public Duration getRequestTimeout() {
      return this.requestTimeout;
    }

    public void setRequestTimeout(final Duration requestTimeout) {
      this.requestTimeout = requestTimeout;
    }

  }

  public static class Servlet {

    /**
     * Path of the dispatcher servlet.
     */
    private String path = "/";

    /**
     * Load on startup priority of the dispatcher servlet.
     */
    private int loadOnStartup = -1;

    public String getPath() {
      return this.path;
    }

    public void setPath(final String path) {
      Assert.notNull(path, "Path must not be null");
      Assert.isTrue(!path.contains("*"), "Path must not contain wildcards");
      this.path = path;
    }

    public int getLoadOnStartup() {
      return this.loadOnStartup;
    }

    public void setLoadOnStartup(final int loadOnStartup) {
      this.loadOnStartup = loadOnStartup;
    }

    public String getServletMapping() {
      if (this.path.equals("") || this.path.equals("/")) {
        return "/";
      }
      if (this.path.endsWith("/")) {
        return this.path + "*";
      }
      return this.path + "/*";
    }

    public String getPath(String path) {
      final String prefix = getServletPrefix();
      if (!path.startsWith("/")) {
        path = "/" + path;
      }
      return prefix + path;
    }

    public String getServletPrefix() {
      String result = this.path;
      final int index = result.indexOf('*');
      if (index != -1) {
        result = result.substring(0, index);
      }
      if (result.endsWith("/")) {
        result = result.substring(0, result.length() - 1);
      }
      return result;
    }

  }

  public static class View {

    /**
     * Spring MVC view prefix.
     */
    private String prefix;

    /**
     * Spring MVC view suffix.
     */
    private String suffix;

    public String getPrefix() {
      return this.prefix;
    }

    public void setPrefix(final String prefix) {
      this.prefix = prefix;
    }

    public String getSuffix() {
      return this.suffix;
    }

    public void setSuffix(final String suffix) {
      this.suffix = suffix;
    }

  }

  public enum LocaleResolver {

    /**
     * Always use the configured locale.
     */
    FIXED,

    /**
     * Use the "Accept-Language" header or the configured locale if the header is not set.
     */
    ACCEPT_HEADER

  }

  public static class Contentnegotiation {

    /**
     * Whether the path extension in the URL path should be used to determine the requested media
     * type. If enabled a request "/users.pdf" will be interpreted as a request for
     * "application/pdf" regardless of the 'Accept' header.
     */
    private boolean favorPathExtension = false;

    /**
     * Whether a request parameter ("format" by default) should be used to determine the requested
     * media type.
     */
    private boolean favorParameter = false;

    /**
     * Map file extensions to media types for content negotiation. For instance, yml to text/yaml.
     */
    private Map<String, MediaType> mediaTypes = new LinkedHashMap<>();

    /**
     * Query parameter name to use when "favor-parameter" is enabled.
     */
    private String parameterName;

    @DeprecatedConfigurationProperty(
      reason = "Use of path extensions for request mapping and for content negotiation is discouraged.")
    @Deprecated
    public boolean isFavorPathExtension() {
      return this.favorPathExtension;
    }

    @Deprecated
    public void setFavorPathExtension(final boolean favorPathExtension) {
      this.favorPathExtension = favorPathExtension;
    }

    public boolean isFavorParameter() {
      return this.favorParameter;
    }

    public void setFavorParameter(final boolean favorParameter) {
      this.favorParameter = favorParameter;
    }

    public Map<String, MediaType> getMediaTypes() {
      return this.mediaTypes;
    }

    public void setMediaTypes(final Map<String, MediaType> mediaTypes) {
      this.mediaTypes = mediaTypes;
    }

    public String getParameterName() {
      return this.parameterName;
    }

    public void setParameterName(final String parameterName) {
      this.parameterName = parameterName;
    }

  }

  public static class Pathmatch {

    /**
     * Whether to use suffix pattern match (".*") when matching patterns to requests. If enabled a
     * method mapped to "/users" also matches to "/users.*".
     */
    private boolean useSuffixPattern = false;

    /**
     * Whether suffix pattern matching should work only against extensions registered with
     * "spring.mvc.contentnegotiation.media-types.*". This is generally recommended to reduce
     * ambiguity and to avoid issues such as when a "." appears in the path for other reasons.
     */
    private boolean useRegisteredSuffixPattern = false;

    @DeprecatedConfigurationProperty(
      reason = "Use of path extensions for request mapping and for content negotiation is discouraged.")
    @Deprecated
    public boolean isUseSuffixPattern() {
      return this.useSuffixPattern;
    }

    @Deprecated
    public void setUseSuffixPattern(final boolean useSuffixPattern) {
      this.useSuffixPattern = useSuffixPattern;
    }

    @DeprecatedConfigurationProperty(
      reason = "Use of path extensions for request mapping and for content negotiation is discouraged.")
    @Deprecated
    public boolean isUseRegisteredSuffixPattern() {
      return this.useRegisteredSuffixPattern;
    }

    @Deprecated
    public void setUseRegisteredSuffixPattern(final boolean useRegisteredSuffixPattern) {
      this.useRegisteredSuffixPattern = useRegisteredSuffixPattern;
    }

  }

  public static class Format {

    /**
     * Date format to use, for example `dd/MM/yyyy`.
     */
    private String date;

    /**
     * Time format to use, for example `HH:mm:ss`.
     */
    private String time;

    /**
     * Date-time format to use, for example `yyyy-MM-dd HH:mm:ss`.
     */
    private String dateTime;

    public String getDate() {
      return this.date;
    }

    public void setDate(final String date) {
      this.date = date;
    }

    public String getTime() {
      return this.time;
    }

    public void setTime(final String time) {
      this.time = time;
    }

    public String getDateTime() {
      return this.dateTime;
    }

    public void setDateTime(final String dateTime) {
      this.dateTime = dateTime;
    }

  }

}
