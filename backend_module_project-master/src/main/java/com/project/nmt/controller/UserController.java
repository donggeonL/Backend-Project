package com.project.nmt.controller;

import com.project.nmt.dto.LogInDto;
import com.project.nmt.dto.SignupForm;
import com.project.nmt.dto.UserUpdateForm;
import com.project.nmt.model.OrderLog;
import com.project.nmt.model.User;
import com.project.nmt.service.OrderLogService;
import com.project.nmt.service.UserService;
import com.project.nmt.validator.UserSignupValidator;
import com.project.nmt.validator.UserUpdateValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserController {

	private final UserService userService;
	private final UserSignupValidator userSignupValidator;
	private final UserUpdateValidator userUpdateValidator;
	private final OrderLogService orderLogService;

	@GetMapping("/signUp")
	public String getSignUp(Model model) {
		model.addAttribute("signupForm", new SignupForm());

		return "user/signup";
	}

	@PostMapping("/signUp")
	public String postSignUp(@Valid SignupForm signUpForm, Errors errors, Model model) {
		userSignupValidator.validate(signUpForm, errors);

		if (errors.hasErrors()) {
			model.addAttribute("signupForm", signUpForm);

			return "/user/signup";
		}

		userService.createNewUser(signUpForm);

		return "redirect:/";
	}

	@GetMapping("/logIn")
	public String login() {
		return "user/login";
	}

	@PostMapping("/logIn")
	public String postLogin(LogInDto logIn, HttpSession session) {
		if(!userService.checkLogIn(logIn)) {
			return "error";
		}

		User user = userService.getOneByUserId(logIn.getUserId());

		session.setAttribute("user", user);

		return "redirect:/";
	}

	@GetMapping("/logOut")
	public String logOut(HttpSession session) {
		session.removeAttribute("user");

		return "redirect:/";
	}

	@GetMapping("/user/info/{id}")
	public String getUserInfo(@PathVariable("id") Long id, Model model) {
		User user = userService.getOneById(id);

		model.addAttribute("user", user);
		model.addAttribute("userUpdateForm", new UserUpdateForm());

		return "user/info";
	}

	@GetMapping("/user/update/{id}")
	public String getUserUpdate(@PathVariable("id") Long id, Model model) {
		User user = userService.getOneById(id);

		model.addAttribute("user", user);
		model.addAttribute("userUpdateForm", new UserUpdateForm());

		return "user/update";
	}

	@PostMapping("/user/update/{id}")
	public String postUserUpdate(@PathVariable("id") Long id, @Valid UserUpdateForm form, Errors errors, Model model) {
		User user = userService.getOneById(id);
		model.addAttribute("user", user);

		userUpdateValidator.validate(form, errors);
		if(errors.hasErrors()) {
			return "user/update";
		}

		userService.updateUser(user, form);

		return "redirect:/";
	}

	@GetMapping("/user/history/{id}")
	public String getHistory(@PathVariable("id") Long id, Model model) {
		User user = userService.getOneById(id);
		List<OrderLog> orderLogs = orderLogService.getListByUser(user);

		model.addAttribute("user", user);
		model.addAttribute("orderLogList", orderLogs);

		return "stock/stock-transaction";
	}

}
