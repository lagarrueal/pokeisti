package spring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.bcrypt.BCrypt;

import spring.model.Role;
import spring.model.User;
import spring.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/login")
	public String reachLoginPage() {
		return "login";
	}
	
	@GetMapping("/admin")
	public String reachAdminPage(HttpServletRequest request) {
		if (request.getSession(false).getAttribute("userRole") == Role.ADMIN) {
			//on verifie qu'il est admin
			return "admin";
		} else {
			return "redirect:403";
		}
	}

	public boolean existUser(String email) {
		return (userService.getUser(email) != null);
	}

	@PostMapping("/add")
	public String saveUser(@RequestParam("email") String email, @RequestParam("password") String password,
			Model model) {

		System.out.println(Role.values());
		if (existUser(email)) {
			//si l'utilisateur existe deja
			System.out.println("utilisateur existant");
			model.addAttribute("erreurUser", Boolean.TRUE);
		} else {
			model.addAttribute("erreurUser", Boolean.FALSE);
			User user = new User();
			String passwordHash;
			passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
			user.setEmail(email);
			user.setPassword(passwordHash);
			user.setRole(Role.USER);
			userService.save(user);
		}
		return "/login";
	}

	@PostMapping("/login")
	public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpServletRequest request, Model model) {
		if (existUser(email)) {
			//on verifie que l'utilisateur existe
			User user = new User();
			user = userService.getUser(email);
			String pw_hash = user.getPassword();
			model.addAttribute("erreurNoUser", Boolean.FALSE);
			if (BCrypt.checkpw(password, pw_hash)) {
				//on verifie le mot de passe a sa version crypté
				model.addAttribute("erreurMdp", Boolean.FALSE);
				request.getSession().setAttribute("userConnecte", true);
				request.getSession().setAttribute("idUser", email);
				request.getSession().setAttribute("userRole", user.getRole());
				return "redirect:/";
			} else {
				System.out.println("Mauvais mdp");
				model.addAttribute("erreurMdp", Boolean.TRUE);
				return "/login";
			}
		} else {
			System.out.println("Utilisateur n'existe pas");
			model.addAttribute("erreurNoUser", Boolean.TRUE);
			return "/login";
		}
	}

	@GetMapping("/users")
	public String getAllUsers(Model model, HttpServletRequest request) {
		if (request.getSession(false).getAttribute("userRole") == Role.ADMIN) {
			//on peux regarder les users si on est admins
			model.addAttribute("userList", userService.list());
			return "listUsers";
		} else {
			return "redirect:403";
		}
	}

	@GetMapping("/usersDelete/{email:.+}")
	public String delete(@PathVariable("email") String email, HttpServletRequest request) {
		if (request.getSession(false).getAttribute("userRole") == Role.ADMIN) {
			userService.delete(email);
			return "redirect:/users";
		} else {
			return "redirect:403";
		}
	}

	@GetMapping("/userEdit/{email:.+}")
	public String reachEditProduct(@PathVariable("email") String email, Model model, HttpServletRequest request) {
		if (request.getSession(false).getAttribute("userRole") == Role.ADMIN) {
			model.addAttribute("role", Role.values());
			model.addAttribute("user", userService.getUser(email));
			return ("editUser");
		} else {
			return "redirect:403";
		}
	}

	@PostMapping("/userEdit/{email:.+}/edit")
	public String editUser(@PathVariable("email") String email, @ModelAttribute("user") @Valid User user,
			BindingResult result, RedirectAttributes attr, Model model, HttpServletRequest request) {
		if (request.getSession(false).getAttribute("userRole") == Role.ADMIN) {
			if (result.hasErrors()) {
				return ("redirect:/userEdit/" + email);
			} else {
				try {
					userService.update(user);
					attr.addFlashAttribute("success", Boolean.TRUE);
				} catch (Exception e) {
					System.out.println(e);
					attr.addFlashAttribute("error", Boolean.TRUE);
				}
				return ("redirect:/users");
			}
		} else {
			return "redirect:403";
		}
	}

	@PostMapping("/addUser")
	public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult result, Model model, HttpServletRequest request) {
		if (request.getSession(false).getAttribute("userRole") == Role.ADMIN) {
			if (result.hasErrors()) {
				System.out.println("Erreurs");
				model.addAttribute("users", userService.list());
				return "register";
			}
			userService.save(user);
			return "redirect:/listUsers";
		} else {
			return "redirect:403";
		}
	}

	@GetMapping("/logout")
	public String logOut(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		//on detruit la session
		session.invalidate();
		return "redirect:/";
	}

}
