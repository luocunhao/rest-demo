package xlink.rest.demo.rest;

import java.util.HashMap;
import java.util.Map;

import cn.xlink.iot.sdk.datastruct.type.XlinkIotPublishOperation;

public class OperationMode {
	
	
	public static XlinkIotPublishOperation getOperationMode(String queryParam){
		 Map<String,XlinkIotPublishOperation> map = new HashMap<>();
		 
		 map.put("upsert", XlinkIotPublishOperation.Upsert);
		 map.put("insert", XlinkIotPublishOperation.Insert);
		 
		 for (String key: map.keySet()) {
	            if (key.equals(queryParam)){
	                return map.get(key);
	            }
	     }
		 return null;
	}
}
