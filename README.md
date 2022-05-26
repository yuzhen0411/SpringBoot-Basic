# Demo

### 實現i18n國際化
1. 建立語言環境文件，編寫將在頁面上呈現的內容，並放置於resources資料夾下  

   <img src="https://github.com/yuzhen0411/SpringBoot-Basic/blob/master/Pictures/SpringBoot-Basic_ReadMe_3.PNG" width="600px"/>
2. 呈現  
<img src="https://github.com/yuzhen0411/SpringBoot-Basic/blob/master/Pictures/SpringBoot-Basic_ReadMe_1.PNG" width="300px"/>  <img src="https://github.com/yuzhen0411/SpringBoot-Basic/blob/master/Pictures/SpringBoot-Basic_ReadMe_2.PNG" width="300px"/>  
3. 切換中英文時，利用Request URL帶的參數l為en_US或zh_TW，判斷語言環境
   ```
   public class MyLocaleResolver implements LocaleResolver{
	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		String l = request.getParameter("l");
		Locale locale = Locale.getDefault();
		if(StringUtils.hasText(l)) {
			String[] split = l.split("_");
			locale = new Locale(split[0], split [1]);
		}
		return locale;
	}

	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {	
	}
   }
   ```
### 攔截器: 判斷使用者登入狀態
1. 添加處理登入的攔截器，需重寫此方法  
   攔截所有URL的請求，前提是需排除登入頁面及其他靜態資源的路徑  
   ```
   @Override
   public void addInterceptors(InterceptorRegistry registry) {
	registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").
			excludePathPatterns("/index.html", "/", "/asserts/**", "/webjars/**", "/user/login");
   }
   ```
3. 登入檢查，若session中找不到當前使用者名稱，則將請求轉發到登入頁面處理  
   ```
   public class LoginHandlerInterceptor implements HandlerInterceptor{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Object user = request.getSession().getAttribute("loginUser");
		if(user == null) {
			//not login yet, turn to login page
			request.setAttribute("msg", "請先登入");
			request.getRequestDispatcher("/index.html").forward(request,response);
			return false;
		}
		else {
			//has login
			return true;
		}
	}
   ```  
   <img src="https://github.com/yuzhen0411/SpringBoot-Basic/blob/master/Pictures/SpringBoot-Basic_ReadMe_4.PNG" width="300px"/>  

### 共用程式區塊  
1. 共用程式範例  
   ```
   <!--th:fragment: 標記共用程式區塊-->
   <footer th:fragment="copy">
      Shared Block
   </footer>
   ```  
2. 引入方式
   ```
   <!--區塊插入所屬標籤中-->
   <div th:insert="templatename :: copy"></div>
   
   <!--區塊取代所屬標籤-->
   <div th:replace="templatename :: copy"></div>
   
   <!--區塊內容插入所屬標籤中(Thymeleaf v3.x之後不建議使用)
   <div th:include="templatename :: copy"></div>
   ```  
3. 效果
   ```
   <div>
     <footer>
        Shared Block
     </footer>
   </div>
   
   <footer>
       Shared Block
   </footer>
   
   <div>
       Shared Block
   </div>
   ```  
   
### 點擊後藍字效果  
1. 在html中程式區塊，傳入變數activeUri的值
   ```
   <div th:replace="commons/bar::#sidebar(activeUri='main.html')"></div>
   ```
2. 利用共用程式區塊中，變數activeUri的值控制是否變色
   ```
   <a class="nav-link active" th:class="${activeUri=='main.html'?'nav-link active':'nav-link'}" href="#" th:href="@{/main.html}">
   ```  
3. 呈現效果  
   <img src="https://github.com/yuzhen0411/SpringBoot-Basic/blob/master/Pictures/SpringBoot-Basic_ReadMe_5.PNG" width="300px"/>  

### 獲取員工資料
1. 由Controoler發送emps請求，獲取員工資料後，將資料放在Model的emps中回傳
2. 遍歷員工資料並顯示在頁面上
   ```
   <tr th:each="emp:${emps}">
	<td th:text="${emp.id}"></td>
	<td> [[${emp.lastName}]]</td>
	<td th:text="${emp.email}"></td>
	<td th:text="${emp.gender}==0?'Female':'Male'"></td>
	<td th:text="${emp.department.departmentName}"></td>
	<td th:text="${#dates.format(emp.birth, 'yyyy-MM-dd HH:mm')}"></td>
	<td>
		<a class="btn btn-sm btn-primary" th:href="@{/emp/}+${emp.id}">編輯</a>
		<button th:attr="del_uri=@{/emp/}+${emp.id}" class="btn btn-sm btn-danger deleteBtn">刪除</button>
	</td>
   </tr>
   ```  
3. 呈現  
   <img src="https://github.com/yuzhen0411/SpringBoot-Basic/blob/master/Pictures/SpringBoot-Basic_ReadMe_6.PNG" width="900px"/>  

### 新增員工  
1. 新增畫面  
   <img src="https://github.com/yuzhen0411/SpringBoot-Basic/blob/master/Pictures/SpringBoot-Basic_ReadMe_7.PNG" width="900px"/>  
2. 列出可選的部門選項  
   ```
   <option th:selected="${emp!=null}?${dept.id==emp.department.id}" 
           th:value="${dept.id}"                       <!--提交的是部門id-->
           th:each="dept:${depts}"                     <!--遍歷所有部門，每個對象對應一個option-->
           th:text="${dept.departmentName}">1</option> <!--在頁面上顯示部門名稱  -->
   ```  
   <img src="https://github.com/yuzhen0411/SpringBoot-Basic/blob/master/Pictures/SpringBoot-Basic_ReadMe_8.PNG" width="300px"/>  
3. Form提交的name屬性對應javabean對象屬性，保存後跳轉到員工清單頁面
   ```
   @PostMapping("/emp")
   public String addEmp(Employee employee) {
	employeeDao.save(employee);
	return"redirect:/emps";
   }
   ```  

### 編輯員工
1. 每個編輯頁，超連接後綴皆為員工編號
   ```
   <a class="btn btn-sm btn-primary" th:href="@{/emp/}+${emp.id}">編輯</a>
   ```  
2. 若為員工編輯頁面，則需顯示該員工資訊；    
   <img src="https://github.com/yuzhen0411/SpringBoot-Basic/blob/master/Pictures/SpringBoot-Basic_ReadMe_7.PNG" width="900px"/>  
   若為員工新增頁面，則不需帶入任何資訊  
   <img src="https://github.com/yuzhen0411/SpringBoot-Basic/blob/master/Pictures/SpringBoot-Basic_ReadMe_9.PNG" width="900px"/>  
3. lastName、email、birth、gender：使用三元運算判斷，若emp非空，則顯示該員工資訊於編輯頁面上  
   範例  
   ```
   <div class="form-group">
	<label>LastName</label>
	<input name="lastName" type="text" class="form-control" placeholder="Zhou" th:value="${emp!=null}?${emp.lastName}">
   </div>
   ```  
4. department：使用三元運算判斷，顯示與當前員工部門id相同的部門id  
    ```
    <div class="form-group">
	<label>department</label>
	<select class="form-control" name="department.id">
	    <option th:selected="${emp!=null}?${dept.id==emp.department.id}" 
	    		th:value="${dept.id}" th:each="dept:${depts}" th:text="${dept.departmentName}">1</option>
	</select>
    </div>
   ``` 
5. 儲存編輯後員工資料  
   表單提交挾帶該員工id，當id非空，則重新put到map中保存  
   ```
   private static Map<Integer, Employee> employees = null;
	
   static{
		employees = new HashMap<Integer, Employee>();

		employees.put(201, new Employee(201, "Huang", "benson@gmail.com", 1, new Department(101, "A101"),"1990-03-11"));
		employees.put(202, new Employee(202, "Lee", "iron@gmail.com", 1, new Department(102, "B102"),"1985-06-12"));
		employees.put(203, new Employee(203, "Su", "gina@gmail.com", 0, new Department(103, "A103"),"1970-07-30"));
		employees.put(204, new Employee(204, "Chen", "helen@gmail.com", 0, new Department(104, "A102"),"1973-10-05"));
		employees.put(205, new Employee(205, "Chian", "kai@gmail.com", 1, new Department(105, "B101"),"1982-08-23"));
	}
   public void save(Employee employee){
	if(employee.getId() == null){
		employee.setId(initId++);
	}

	employee.setDepartment(departmentDao.getDepartment(employee.getDepartment().getId()));
	employees.put(employee.getId(), employee);
   }
   ```
 
 ### 刪除員工  
1. 透過delete方式傳過來的emp/id請求，刪除當前員工資料
   ```
   @DeleteMapping("/emp/{id}")
   public String deleteEmployee(@PathVariable("id") Integer id) {
	employeeDao.delete(id);
	return "redirect:/emps";
   }
   ```  
2. 當deleteBtn此事件被觸發，則表單改成del_uri代表的位址，提交後方能刪除該員工
   ```
   <script>
	$(".deleteBtn").click(function(){
		$("#deleteEmpForm").attr("action",$(this).attr("del_uri")).submit();
		return false;
	})
   </script>
   ```    
   ```
   <button th:attr="del_uri=@{/emp/}+${emp.id}" class="btn btn-sm btn-danger deleteBtn">刪除</button>
   ```  
