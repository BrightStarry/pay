### 支付模块
#### 模块/包
- api: 对富有/连连等支付方的主要代码逻辑
    - config: 通用的支付配置
    - fuyou/kjt/lianlian：各个支付方
        - api: 各接口的请求/响应对象
        - dto： 各接口的通用请求/响应对象
        - enum: 各支付方的异常枚举
        - xxxTemplate: 使用该类调用该支付方的接口
        - xxxConfig: 该支付方的主要配置类
    - strategy: 策略模式,封装了各支付方的可通用的支付/代付/代付查询策略类
- base: 无意义，只是为了让spring boot分模块项目可以自行修改Spring父模块的依赖版本
- common：通用模块，dto，异常类，工具类，枚举等
- dao: 数据操作模块
- service： 业务模块
- web： web模块，以及整个项目启动的相关配置
    - config： 常量/Spring cache配置/自定义配置等
    - system： SpringMVC异常处理相关类
    
    
#### 结构
- 支持的银行定义在bank表，支持的支付方定义在payer表，各支付方的各接口的支付限额在bank_quota表，各支付方的银行代码定义在payer_bank表
- caller表定义了可调用该模块接口的调用者。
- request表记录了所有的接口调用记录，包括请求和响应消息。
- 项目缓存部分使用Spring Cache redis，并定义了一些自定义参数
- dao层整合了MyBatis-Plus框架

#### 接口
- 缓存相关：见SystemController  
请求 /system/delete/cache/{cacheName} 或 /delete/cache/{cacheName}/{key}， 可删除整个个cacheName(spring cache中的缓存概念)，或 单个key
- 支付： 见PayController 
- 查询： 见QueryController



#### 整合MyBatis-Plus
- 注意点
    - Application上注解@MapperScan("com.jia.pay.dao*")，扫描mapper
    - mapper和entity都需要继承父类
    - 实体类继承extends Model<T>，用于开启AR模式（用实体类调用相关方法），该模式 感觉没意义
    
#### JsonView
- 如果在ADTO类中，定义视图AView，然后返回组合了ADTO类的通用DTO，则通用DTO中的所有字段都不会返回，则返回 "{}"
- 可以在通用DTO中，定义BaseView，然后在通用DTO中的所有字段上注解@JsonView(BaseView.class)；
- 此后，所有视图都继承BaseView即可
    
#### 注意点
- mysql中同一张表中如果包含多个timestamp字段，只有第一个字段支持更新时自动更新时间（所以需要把updateTime写在createTime之前）

#### ObjectMapper
```java
/**
 * 如果要处理更多层的嵌套，例如 A<List<B>> 可以直接 new TypeReference<ResultDTO<List<RequestDTO>>>(){}
 * 例如 objectMapper.readValue(resultJson, new TypeReference<ResultDTO<List<RequestDTO>>>(){});
 */
public class ObjectMapperUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 双层嵌套
     * 例如 ObjectA<ObjectB>
     */
    public static  JavaType doubleNestType(Class<?> parametrized, Class<?> parameterClasses) {
        return objectMapper.getTypeFactory().constructParametrizedType(parametrized, parametrized,parameterClasses);
    }
}
```

#### 其他
- sql文件在resources/sql目录下
