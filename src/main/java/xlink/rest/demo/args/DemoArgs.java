package xlink.rest.demo.args;

/**
 * 启动参数
 */
public class DemoArgs {

	/**
	 * Server Id，标识api唯一节点
	 */
	public static short serverId = 1;
	/**
	 * 提供http api服务的端口
	 */
	public static int port = 8887;
	/**
	 * 配置文件地址
	 */
	public static String configPath;
	/**
	 * 日志配置地址
	 */
	public static String log4jPath;
	
	public static void load(String args[]) {
		for (int ix = 0, len = args.length; ix < len; ix ++) {
			String param = args[ix];
			String key = param.split("=")[0];
			String value = param.split("=")[1];
			switch (key.trim()) {
			case "-id":
				serverId = Short.parseShort(value);
				break;
			case "-port":
				port = Integer.parseInt(value);
				break;
			case "-config":
				configPath = value;
			case "-log4j":
				log4jPath = value;
			default:
				break;
			}
		}
	}
}
