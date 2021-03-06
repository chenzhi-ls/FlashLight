## Android FlashLight ##

![](icon.png)

### 一、前言 ###

前段时间，公司的生日趴上，笔者收到了一份走心礼物（用带着爱心的粉红少女色礼品袋，装着的“小夜灯”）。礼物到时蛮实用的，用USB数据线供电，只有一个简单的船型开关，点亮后散发出昏黄的白炽灯光芒，就是有点占地方，不久便被笔者束之高阁了。但是经过几个夜晚的陪伴，让笔者对睡前开着小夜灯产生好感，本着专业的直觉，于是第一时间到应用商店搜索一番，看下能不能找到可以满足需求手电筒应用。

几经搜索，发现常见的Android手电筒基本上都是打开闪光灯作为光源或者是通过点亮手机屏幕来实现照明效果，功能可以说是应有尽有。但是如果在夜晚使用，特别是从关灯到入睡这段时间，常见的手电筒应用要么就是闪光灯太亮，要么就是操作复杂，于是笔者便想到能不能开发一款亮度相对比较符合入睡，操作上也要绝对的简单的应用呢？于是便有了这个Android应用----------`小夜灯 FlashLight`

### 二、开发思路 ###

因为通过点亮屏幕来作为光源，于是手电筒需要保持屏幕常亮和设置最高亮度和具体的颜色值来模拟灯泡发出的光芒。

- 注意点：

	- 设置屏幕亮度，但是程序关闭或者离开手机屏幕应该保持原来的亮度
	- 保持手机屏幕常亮，在点亮屏幕的时间段内不自动锁屏

- 处理方法：	
	- 通过`WindowManager`来改变当前页面亮度，不直接修改手机系统设置
	- 通过`WakeLock`来保持屏幕常亮


### 三、最终效果 ###

![](device1.png) ![](device2.png)


### 四、其他说明 ###

**本项目中用到的内容仅用作学习研究使用，对技术的可行性做相关探索。**如果利用本文案例或者技术进行其他非法操作，带来的一切法律责任与本人无关！！！

项目中使用的Icon和部分页面效果布局借鉴其他手电筒应用，如存在侵权行为请联系本人修改。谢谢！！