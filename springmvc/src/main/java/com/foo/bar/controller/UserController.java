package com.foo.bar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foo.bar.domain.Role;
import com.foo.bar.domain.User;
import com.foo.bar.dto.UserListDto;
import com.foo.bar.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService service;

	@RequestMapping
	public String getUserPage() {
		return "users";
	}

	@RequestMapping(value = "/records")
	public @ResponseBody
	UserListDto getUsers() {
		UserListDto userListDto = new UserListDto();
		userListDto.setUsers(service.readAll());
		return userListDto;
	}

	@RequestMapping(value = "/get")
	public @ResponseBody
	User get(@RequestBody User user) {
		return service.read(user);
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	User create(@RequestParam String userName, @RequestParam String password,
			@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam Integer role) {
		Role newRole = new Role();
		newRole.setRole(role);

		User newUser = new User();
		newUser.setUserName(userName);
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setPassword(password);
		newUser.setRole(newRole);

		return service.create(newUser);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	User update(@RequestParam String username, @RequestParam String firstName,
			@RequestParam String lastName, @RequestParam Integer role) {

		Role existingRole = new Role();
		existingRole.setRole(role);

		User existingUser = new User();
		existingUser.setUserName(username);
		existingUser.setFirstName(firstName);
		existingUser.setLastName(lastName);
		existingUser.setRole(existingRole);

		return service.update(existingUser);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Boolean delete(@RequestParam String username) {

		User existingUser = new User();
		existingUser.setUserName(username);

		return service.delete(existingUser);
	}

}
