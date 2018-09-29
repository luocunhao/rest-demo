package xlink.rest.demo.service;

import xlink.rest.demo.datastruct.DUser;

public interface IUserService {

	DUser findById(String id)throws Exception;
	
	void saveUser(DUser user)throws Exception;
	
}
