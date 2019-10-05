# 抽象文档模式

抽象文档模式是一种面向对象结构的设计模式。模式采用key-value形式存储对象的属性，且确保类型不相关，暴露类型相关的属性数据。模式意图为强类型语言构建高灵活性的组件管理，保证新的属性可以自由的添加到对象中，且不丢失类型安全。

## 结构

接口"Document"确保属性可以通过put方法设置，通过get方法获取，并且子document可以通过children方法访问。childern方法可以提供一个类型相关的child，且此child能够提供自身数据的map。map需要指向源数据，这样所有的修改都能体现到源对象上。实现类可以继承自多个描述不同属性的trait接口。甚至多个Document可以共享一个map。实现类唯一的限制是，除了从基础组件继承的属性，其他属性必须是无状态了。



![alt text](./etc/abstract-document.png"Abstract Document Traits and Domain")



## 伪代码

Interface Document

put(key : String, value : Object) : Object

get(key : String) : Object

childern(key : String, constructor : Map)

## 使用场景

* 需要动态的添加新属性
* 灵活的方法组织树状结构的域
* 松耦合的系统

## 理解

> 1. 所有的属性都通过Map<String,Object>存储。所以存储的时候不需要关心具体的类型是什么。
> 2. 对象可以有子对象。比如，Car有Wheel，door。wheel和door都是子对象。通过car可以获得whell和door子对象，通过子对象设置和获取子对象的属性。
> 3. 通过继承接口，实现获取类型相关的属性。Car继承并实现接口HasModel。如果想获得Car的model属性，需要调用HasModel.getModel。从而实现取出的属性类型相关。
> 4. 通过基类封装基本操作。这样不同Car或者Car和Plane之间可以共享实现。

## 参考

* [Wikipedia: Abstract Document Pattern](https://en.wikipedia.org/wiki/Abstract_Document_Pattern)
* [Martin Fowler: Dealing with properties](http://martinfowler.com/apsupp/properties.pdf)