### 一、靜態資源  
1. /webjars/** 訪問靜態資源路徑  
   所有 /webjars/** ，都去 classpath:/META-INF/resources/webjars/ 訪問資源  
   ```
		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			if (!this.resourceProperties.isAddMappings()) {
				logger.debug("Default resource handling disabled");
				return;
			}
			addResourceHandler(registry, "/webjars/**", "classpath:/META-INF/resources/webjars/");
			addResourceHandler(registry, this.mvcProperties.getStaticPathPattern(), (registration) -> {
				registration.addResourceLocations(this.resourceProperties.getStaticLocations());
				if (this.servletContext != null) {
					ServletContextResource resource = new ServletContextResource(this.servletContext, SERVLET_LOCATION);
					registration.addResourceLocations(resource);
				}
			});
		}
   ```
    
   webjars：以jar的方式引入靜態資源  
   詳細資訊可參考 http://www.webjars.org/
   
2. 靜態資源目錄  
   以下4個目錄皆為靜態資源儲存位置，這4個目錄的根目錄皆為classpath  
   "/"：專案根目錄 
   
   * classpath:/META‐INF/resources/ 
   * classpath:/resources/
   * classpath:/static/
   * classpath:/public/      

3. 歡迎頁  
   專案啟動後，即前往localhost:8080，會至所有靜態資源目錄尋找index.html，此時index.html為預設歡迎頁  
   
4. 應用圖示  
   所有網頁標籤的左上角會顯示的應用圖示， 都從靜態資源目錄裡找
### 二、配置檔  
SpringBoot配置檔默認為application.properties或application.yml。此處以application.properties為例：  
1. 格式  
以key=value呈現  
2. 讀取順序  
SpringBoot啟動後會依序至下列位置讀取所有配置

- file:./config/ 
- file:./ 
- classpath:/config/ 
- classpath:/  
3. 配置項目  
可參考 https://docs.spring.io/spring-boot/docs/1.5.9.RELEASE/reference/htmlsingle/#common-application-properties  

### 三、Thymeleaf  
1. 引入starter依賴套件
   ```
   <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-thymeleaf</artifactId>
   </dependency>
   ```  
2. .html標籤要加入 ```xmlns:th="http://www.thymeleaf.org"```    
3. 相關配置
   ```
   @ConfigurationProperties(prefix = "spring.thymeleaf")
   public class ThymeleafProperties {

	private static final Charset DEFAULT_ENCODING = StandardCharsets.UTF_8;

	public static final String DEFAULT_PREFIX = "classpath:/templates/"; //至此位置，找到.html檔案

	public static final String DEFAULT_SUFFIX = ".html";
   ```  
4. 語法參考  
   https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.pdf  
5. Thymeleaf 以Html作為模板，可以在瀏覽器檢視頁面的靜態效果，也可於標籤增加屬性，透過後端渲染後，將資料回傳至頁面，替換掉原先的靜態內容。  
   範例:  
   ```
   <div th:text="${hello_thym}">hello_html</div>
   ```  
   ```
   @RequestMapping("/success")
   public String success(Map<String, Object> map) {
	map.put("hello_thym","hello_thymeleaf");
	return "success";
   }
   ```
   結果:  
   <img src="https://github.com/yuzhen0411/SpringBoot-Basic/blob/master/Pictures/SpringBoot-Basic_LearningNotes_1.PNG" width="600px"/>  

### 四、專案預設路徑
於properties配置檔中加入`server.servlet.context-path=/test`  
設置完畢後，須於瀏覽器網址列輸入`localhost:8080/test`方能正常導向預設URL首頁，原先的`localhost:8080`則不再起作用  

### 五、例外處理
在.html上配置Exception資訊，希望能顯示在瀏覽器頁面上  
```
<h1>status:[[${status}]]</h1>
<h2>timestamp:[[${timestamp}]]</h2>
<h2>exception:[[${exception}]]</h2>
<h2>message:[[${message}]]</h2>
```  

卻發現exception和message無法正常顯示在頁面上  
<img src="https://github.com/yuzhen0411/SpringBoot-Basic/blob/master/Pictures/SpringBoot-Basic_LearningNotes_2.PNG" width="600px"/>  

原因是以下兩項預設值為false和never  
```
server.error.include-exception=false
server.error.include-message=never
```  

於是將這兩項分別修改為  
```
server.error.include-exception=true
server.error.include-message=always
```  

頁面即可正常顯示Exception資訊  
<img src="https://github.com/yuzhen0411/SpringBoot-Basic/blob/master/Pictures/SpringBoot-Basic_LearningNotes_3.PNG" width="600px"/>  
