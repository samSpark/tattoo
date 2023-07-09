Tattoo用于识别设备纹身特征，如判断模拟器、设备厂商所支持的广告追踪设备符号oaid等。<br/> 

1.Tattoo.env(Context context, Tattoo.CallBack callBack)<br/>
静态方法，判断设备模拟器特征、root状态、cpu架构。CallBack回传JSONObject如下(key)：<br/>
 a: int, 0不是模拟器，1是模拟器，2不能确定<br/>
 b: boolean, true为root状态<br/>
 c: String, cpu abi信息<br/>
 
2.Tattoo.o(Context context, Tattoo.O o)<br/>
静态方法，注册广告追踪设备符号监听者，Tattoo.O回传oaid字符串，回传方法处于子线程环境，返回字符串为空则表示设备不支持获取。<br/>

Tattoo API使用时机不限，使用次数不限。<br/>

混淆规则：-keep class com.u2020.sdk.** { *; }<br/> 

Tattoo优先检测厂商接口，不支持则会自动检测存在的生效的来自MSA的oaidsdk(1.0.10版本及以上)以获取设备符。<br/>
支持厂商：华为、荣耀、小米、黑鲨、oppo、一加、真我、vivo、samsung、魅族、努比亚、中兴、<br/>
华硕、联想、酷派、酷赛、卓易、摩托罗拉、美图、360(msa2.0.0)、步步高(msa1.0.29)、酷派、酷赛、卓易<br/>

若使用msaoaidsdk1.0.26及以上版本需要在assets配置所申请证书(命名为packageName.cert.pem，证书为PEM文件中的文本内容包括首尾行、换行符，<br/>
Tattoo检测证书有效性后再根据情况获取设备符)，请注意该SDK调用问题和使用规范。<br/>

日记开关：+boolean:Tattoo.DEBUG，true：开启，日记TAG:Tattoo。<br/>
MSAOAID相关Code：<br/>
INIT_ERROR_BEGIN/INIT_INFO_RESULT_OK(获取接口是同步的) = 1008610;<br/>
INIT_ERROR_DEVICE_NOSUPPORT(不支持的设备) = 1008612;<br/>
INIT_ERROR_LOAD_CONFIGFILE(加载supplierconfig.json配置文件出错) = 1008613;<br/>
INIT_ERROR_MANUFACTURER_NOSUPPORT(不支持的设备厂商) = 1008611;<br/>
INIT_ERROR_RESULT_DELAY/INIT_INFO_RESULT_DELAY(获取接口是异步的) = 1008614;<br/>
INIT_HELPER_CALL_ERROR/INIT_ERROR_SDK_CALL_ERROR(反射调用出错) = 1008615;<br/>
INIT_ERROR_CERT_ERROR(证书未初始化或证书无效) = 1008616;<br/> 