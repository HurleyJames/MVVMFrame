# MVVMFrame

MVVMFrame 是一个基于 JetPack 构建的快速开发框架项目。其以 DataBinding + LiveData + ViewModel 框架为基础，搭配 OkHttp + Retrofit + RxJava 等 Android 常用框架，同时辅以各种原生控件自定义的 BindingAdapter，是一个让 MVVM 模式完全体现的实用性开发体系。

## Feature

* **组件化**

  对于简单的小项目，一般采用单一工程，独立开发。因为本身并不大，所以编译速度以及维护成本等都在可接受范围之内。但对于一个完整的、庞大的 App 项目来说，一个单一工程的项目很难进行多人协作开发。

  所以，使用组件化方案架构，高内聚、低耦合，代码边界清晰，每一个组件都可以拆分出来独立运行。

* **基类封装**

  既有专门针对 MVVM 模式的 BaseMvvmActivity、BaseMvvmFragment、BaseViewModel 等，又有针对简单功能界面的 BaseActivity、BaseFragment  等

* **主流框架**

  Retrofit + OkHttp + RxJava 负责网络请求和线程切换；Gson 负责格式化 JSON 数据；Rxlifecycle 负责管理 View 的生命周期；RxBinding 与 DataBinding 相结合；

* **全局操作**

  * 全局的 Activity 堆栈式管理，在程序的任何地方都可以创建、启动、结束指定的 Activity
  * LoggingInterceptor 全局拦截网络请求日志，打印 Request 和 Response，并通过 JSON、XML 等格式化显示
  * 全局 Cookie，支持 SharedPreferences 和 内存存储两种方式（以后会支持 MMKV）
  * 网络异常监听，根据不同的状态码返回不同的 Message
  * 全局异常捕获，程序发生异常时不会崩溃，会跳入异常界面来重启应用
  * 全局事件回调，提供 RxBus、Messenger 两种回调的方式