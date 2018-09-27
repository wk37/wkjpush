#  极光推送demo
##	包含 极光官方推送，及 小米、华为、魅族 第三方推送
	
#  集成步骤  

1. 统一管理版本号  
	在 project 的 build.gradle 中添加 ext 节点，并在其中 定义 compileSdkVersion、minSdkVersion、targetSdkVersion节点  
	
2. 依赖配置  
	在 app 的 build.gradle 中添加 jniLibs.srcDirs = ['libs'] 及 dirs project(':jpush').file('libs')，  
	然后下载并依赖 jpush 模块  
	
3. appkey、appid 配置  
	在 app 的 build.gradle defaultConfig（或 productFlavors多渠道中 渠道节点下）  
	节点中添加 manifestPlaceholders节点，并在指定名称下配置申请的key或id  
	
4. 推送通知到达配置及解析  
	app 模块中 JpushZGReceiver 及 HuaweiClickActivity 为消息到达处理类，  
	按照 TODO 提示，可解析 消息中的数据，然后设置跳转界面  
	
5. 初始化  
	拷贝 app 模块中 manifest <!--推送专用 start--> 至 <!--推送专用 end--> 到目标项目 manifest，  
	并在 Application 执行 ZgPushUtil.init(this); 进行初始化  
	
6. 推送配置  
	参照 demo 中 MainActivity 类，按照 TODO ，在合适的时候进行 推送 Alias 、Tag 的设置、更新及清除  
	
7. 魅族推送小logo替换  
	因魅族推送 通知栏logo 需要定制（若未定制，则默认为app的 桌面图标，size太大），  
	请替换 xxhdpi 中 mz_push_notification_small_icon.png 图片（图片大小自行适配）  
	
8. 其他事项  
	极光推送相关 jar包、so库、aar 均放置于 jpush 模块的 lib 文件夹，若开发时 有更新，  
	请替换相关文件，然后集成最新版（记得修改build.gradle中aar文件的版本依赖）  
	
		