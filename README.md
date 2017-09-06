# BasisDependency
Basis Dependency By Gingod

- Base
- Log
- SP
- Time
- Adapter
- 自定义View
- Okhttp
- Glide

##1. BasisBase
一些基本类和常用方法封装

###a. BasisBaseActivity

####部分方法
- initData  
在加载视图前对一些常用对象, 如app, mActivity, context,mHandler, gson等进行初始化赋值
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

###b. BasisBaseService

###c. BasisBaseFragment

android.support.v4.app.Fragment

###d. BasisBaseUtils

##2. BasisLogUtils
Log日志封装

####部分参数
- LOG_LEVEL  
日志打印级别: LOG_ALL 打印所有级别日志; LOG_CLOSED 关闭日志
- LOG_TAG  
日志标识: 默认"TAG"

##3. BasisSPUtils
SharedPreferences常用方法封装

##4. BasisTimesUtils
时间工具类

- getDeviceTime  
时间格式: yyyy-MM-dd HH:mm:ss
- getDeviceTimeOfSSS  
时间格式: yyyy-MM-dd HH:mm:ss:SSS
- getDeviceTimeOfYMD  
时间格式: yyyy-MM-dd

##5. Adapter

###a. ListView 的适配器
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

###b. RecyclerView 的适配器

- 使用方法  
BasisRvAdapter  
MultiRvAdapter  
与listView适配器类似
- 可重写方法  
onItemClickListener: 条目点击事件  
onItemLongClickListener: 条目长点击事件  
isSpecific(int position): true 则该position显示为一整行  
- BasisRvHeaderAndFooterWrapper  
头布局脚布局功能适配器 
- BasisRvEmptyWrapper  
空布局适配器(数据为空时,显示设置好的空布局)  

##6. 自定义View

###a. ListViewWithMaxH  
显示所有item, 并禁止滑动

###b. GridViewWithMaxH
显示所有item, 并禁止滑动

###c. BasisListView
上拉加载下拉刷新ListView

###d. BasisRecyclerView
上拉加载下拉刷新RecyclerView, 可以兼容前面封装的RecyclerView适配器

##7. BasisOkHttpUtils

###a. postString  
上传参数未json字符串

- url  
网址
- mediaType  
默认为 application/json; charset=utf-8
- content  
上传的json字符串, 或者封装的json对象  
不能与addParams方法共用
- addParams  
键值对提交参数  
不能与content方法共用  
- build  
建立请求
- execute  
执行请求, 结果在主线程中执行

###b. postFile && postFiles
上传文件(默认图片)

- url  
网址
- mediaType  
image/* //默认上传图片
- addParams  
键值对提交参数  
- addFile  
上传文件(可上传多个)  
- build  
建立请求
- execute  
执行请求, 结果在主线程中执行  

###c. download
下载文件

- url  
网址
- fileName  
储存的文件名, 默认为当前设备时间
- build  
建立请求
- execute  
执行请求, 结果在主线程中执行  
progress: 下载进度

