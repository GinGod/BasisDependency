# BasisDependency
Basis Dependency By Gingod

## How to use it?

      allprojects {
          repositories {
              ...
              maven { url 'https://jitpack.io' }
          }
      }
      
      dependencies {
          ...
          implementation 'com.github.GinGod:BasisDependency:v1.1.6'
      }

## Notes

### 相关博客
- [Android 自带日期(DatePicker)和时间(TimePicker)选择器使用][1]
- [Android 中动画的实现][2]

 [1]:https://blog.csdn.net/decetwen1989/article/details/79974047  "DatePicker和TimePicker"
 [2]:https://blog.csdn.net/decetwen1989/article/details/80182324  "动画"
 
### 后续版本计划修改类容
- 未定

### v1.1.6 更新 (2021-3-2)
- 优化, 删除一些无用代码

### v1.1.5 更新 (2021-3-1)
- 更新到androidx
- 删除recyclerview相关内容
- 删除Okhttp相关内容
- 删除Glide等图片操作相关内容

### v1.1.4 更新 (2018-5-25) (v1.1.4及其以下版本已废弃, 建议使用v1.1.5及其以上版本)
- BasisTimesUtils 中 DatePickerDialog 和 TimePickerDialog 的使用
- BasisAnimUtils 简单动画的实现(建议参照工具类自定义需要的动画)
- BasisFileUtils 添加关闭流通用方法close(...)
- Okhttp 使用太过繁琐, 已封装简便方法, 原使用逻辑仍可使用
- 自定义标题initTitle()方法的左返回允许任意布局, 以前强转为ImageView, 当前为View


## Contents

### a. Base
- BasisBaseActivity
- BasisBaseService
- BasisBaseFragment
- BasisBaseUtils


### b. Utils
- Log
- SP
- Time
- file
- version
- dialog
- popupwindow

### c. Adapter
- ListView和GridView 的适配器

### d. 自定义View
- ListViewWithMaxH
- GridViewWithMaxH
- BasisListView

## 1. BasisBase
一些基本类和常用方法封装

### a. BasisBaseActivity

#### 部分方法
- initData  
在加载视图前对一些常用对象, 如app, mActivity, context, mHandler等进行初始化赋值
- ondestory  
取消注册封装的广播
- initTitle  
标题栏封装, 建议重写
- parseInt 和 parseIntWithE  
parseInt 转换异常时返回 -1; parseIntWithE 需要手动处理异常
- findTextView 等  
已经自动添加点击监听
- showStr  
str为空时返回 ""; 便于展示

### b. BasisBaseService

### c. BasisBaseFragment

### d. BasisBaseUtils

## 2. Utils
常用工具类

### a. BasisLogUtils
Log日志封装

#### 部分参数
- LOG_LEVEL  
日志打印级别: LOG_ALL 打印所有级别日志; LOG_CLOSED 关闭日志
- LOG_TAG  
日志标识: 默认"TAG"

### b. BasisSPUtils
SharedPreferences常用方法封装

### c. BasisTimesUtils
时间工具类

- getDeviceTime  
时间格式: yyyy-MM-dd HH:mm:ss
- getDeviceTimeOfSSS  
时间格式: yyyy-MM-dd HH:mm:ss:SSS
- getDeviceTimeOfYMD  
时间格式: yyyy-MM-dd

### d. BasisFileUtils

### e. BasisVersionUtils
版本工具类

- getVersionName  
版本名 2.5.2
- getVersionCode  
版本号 252
- getDeviceInfo  
设备信息, 包含设备型号, Android版本和API

### f. BasisDialogUtils || BasisPBLoadingUtils || BasisProgressDialogUtils || BasisSelfDialogUtils
使用参见 DialogActivity

- BasisSelfDialogUtils --可自定义显示布局, 处理逻辑

### e. BasisSelfPopWinUtils
使用参见 DialogActivity

- BasisSelfPopWinUtils --可自定义显示布局, 处理逻辑

## 3. Adapter

### a. ListView和GridView 的适配器
- BasisLvGvAdapter --单类型item

        listview.setAdapter(new BasisLvGvAdapter<数据类型>(上下文, item布局id, 数据集 datas) {
                    @Override
                    protected void initView(BasisLvGvViewHolder basisViewHolder, 数据类型 data, int position) {
                        basisViewHolder.setTvText(textviewID, "内容");
                        basisViewHolder.setTvTextListener(textviewID, "内容", 监听);
                    }
                });

- MultiLvAdapter --多类型item

            //适配器
            public class MultiLvAdapter extends BasisLvGvMultiAdapter<数据类型> {
                public MultiLvAdapter(Context context, 数据集 datas) {
                    super(context, datas);
                }
            
                @Override
                public void addItemViewDelegate() {
                    addItemViewDelegate(new Type1Delagate());//添加一种类型的item
                    addItemViewDelegate(new Type2Delagate());
                    addItemViewDelegate(new Type3Delagate());
                }
            }
            
            //一种类型的item
            public class Type1Delagate implements BasisLvGvItemViewDelegate<数据类型> {
                @Override
                public int getItemViewLayoutId() {
                    return item布局id;
                }
            
                @Override
                public boolean isForViewType(数据类型 data, int position) {
                    return data.status == 1; //true 则当前position采用这种类型item
                }
            
                @Override
                public void initView(BasisLvGvViewHolder holder, 数据类型 data, int position) {
                    basisViewHolder.setTvText(textviewID, "内容");
                    basisViewHolder.setTvTextListener(textviewID, "内容", 监听);
                }
            }
        }

## 4. 自定义View

### a. ListViewWithMaxH  
显示所有item, 并禁止滑动

### b. GridViewWithMaxH
显示所有item, 并禁止滑动

### c. BasisListView
上拉加载下拉刷新ListView
