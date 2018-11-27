Web开发框架：Rose
参考：
https://github.com/XiaoMi/rose/tree/master/ebook
http://www.54chen.com/rose.html

Rose框架内部采用"匹配->执行"两阶段逻辑。Rose内部结构具有一个匹配树， 这个数据结构可以快速判断一个请求是否应该由Rose处理并进行，
 没有找到匹配的请求交给过滤器的下一个组件处理。匹配成功的请求将进入”执行“阶段。 
 执行阶段需要经过6个步骤处理：“参数解析 -〉 验证器 -〉 拦截器 -〉 控制器 -〉 视图渲染 -〉渲染后"的处理链

controllers是rose框架默认的加载controller的package name
*Controller是rose框架默认的controller层的class后缀
Path注解是rose框架提供的标识每个controller的对外访问时的基础路径
Get注解是rose框架提供的标识一个http访问是get还是post或者是其他，并且会将path与get中的字符串连接成一个url [注意path与get中的参数]

返回结果规则：
每个方法的返回值都是一个普通字符串，比如“comment”，意思是，找到web项目中“webapp/views”路径下名叫“comment”的视图文件，
比如“comment.jsp”，用这个视图文件来渲染网页结果并返回

通过rose提供类net.paoding.rose.web.var.Model来设置变量名和变量值，然后在视图文件中用“${paramName}”的方式得到变量值

rose中，controller方法的返回值有下面几种规则：
1.返回普通字符串，如上所述，最常用的做法，渲染视图文件并返回。
2.以“@”开头的字符串，比如“return "@HelloWorld";”，会将“@”后面的字符串“HelloWorld”作为结果返回；
3.以“@json:”开头的字符串，比如:
@Get("json")
public String returnJson(){
    JSONObject jo = new JSONObject();
    return "@json:"+jo.toString();
}
将会返回一个字符串（jo.toString()），并自动将“HttpServletResponse”中的“contentType”设置为“application/json”。
4.【不推荐使用】以“r:”开头的字符串，比如“return "r:/aaa";”，等效于调用“javax.servlet.http.HttpServletResponse.sendRedirect("/aaa")”，将执行301跳转。
5.【不推荐使用】以“a:”开头的字符串，比如“return "a:/bbb";”，将会携带参数再次匹配roseTree，找到controller中某个方法并执行，相当于“javax.servlet.RequestDispatcher.forward(request, response)”。


拦截器要放在controllers下(高级用法:打在rose-jar包里，参见5.1)
继承net.paoding.rose.web.ControllerInterceptorAdapter
按照实现的方法名，在controller执行前、中、后执行：
before：在controller执行前执行。
after：在controller执行中（后）执行，如果一个返回抛出了异常，则不会进来。
afterCompletion：在controller执行后执行，不论是否异常，都会进来。
isForAction：定义满足某条件的才会被拦截。


controller层：ErrorHandler支持
ErrorHandler的作用
一般来说传统的编程都会到处去try，特别是java里，try来try去的（如果你用erlang一定就知道，已经知道的可能性，怎么能叫异常？都try了还是让它崩了算了。。。）。
如果打开你的项目，每个java文件中的代码都有一堆的try，那这时候就是ErrorHandle上阵的时候了。
ErrorHanle致力于：统一捕捉和处理各种异常，可区分对待和返回；统一的出错体验。
非常类似做web开发时的500统一出错页面这样的东东。

controller层：自定义http参数支持