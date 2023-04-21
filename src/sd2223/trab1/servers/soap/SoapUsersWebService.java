package sd2223.trab1.servers.soap;


import aula5.api.User;
import aula5.api.java.Users;
import aula5.api.soap.UsersException;
import aula5.api.soap.UsersService;
import aula5.servers.java.JavaUsers;
import jakarta.jws.WebService;

import java.util.List;
import java.util.logging.Logger;

@WebService(serviceName=UsersService.NAME, targetNamespace=UsersService.NAMESPACE, endpointInterface=UsersService.INTERFACE)
public class SoapUsersWebService extends SoapWebService<UsersException> implements UsersService {

	static Logger Log = Logger.getLogger(SoapUsersWebService.class.getName());
	
	final Users impl;
	public SoapUsersWebService() {
		super( (result)-> new UsersException( result.error().toString()));
		this.impl = new JavaUsers();
	}

	@Override
	public String createUser(User user) throws UsersException {
		return super.fromJavaResult( impl.createUser(user));
	}

	@Override
	public User getUser(String name, String pwd) throws UsersException {
		return super.fromJavaResult( impl.getUser(name, pwd));
	}


	@Override
	public void verifyPassword(String name, String pwd) throws UsersException {
		super.fromJavaResult( impl.verifyPassword(name, pwd));
	}
	
	@Override
	public void updateUser(String name, String pwd, User user) throws UsersException {
		throw new RuntimeException("Not Implemented...");
	}

	@Override
	public User deleteUser(String name, String pwd) throws UsersException {
		throw new RuntimeException("Not Implemented...");
	}

	@Override
	public List<User> searchUsers(String pattern) throws UsersException {
		throw new RuntimeException("Not Implemented...");
	}

}
