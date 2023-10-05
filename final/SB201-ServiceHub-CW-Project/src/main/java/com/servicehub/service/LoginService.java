package com.servicehub.service;

import com.servicehub.exception.NotFoundException;
import com.servicehub.model.Login;

public interface LoginService {

	public Login findByUsername(String usrname) throws NotFoundException;

}
