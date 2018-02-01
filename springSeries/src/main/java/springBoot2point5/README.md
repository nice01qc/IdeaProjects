## Application Event / 事件

##### spring 为Bean与Bean之间提供了消息通信

spring 事件需要遵循的流程如下：

1. 自定义事件，继承ApplicationEvent。<br/>
2. 定义事件监听器，实现ApplicationListener。
3. 使用容器发布事件。

