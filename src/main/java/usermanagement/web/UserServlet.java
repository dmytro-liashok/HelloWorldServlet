package usermanagement.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import usermanagement.dao.UserDAO;
import usermanagement.model.User;
import usermanagement.utility.JsonConverter;

/**
 * Servlet implementation class User
 */
@WebServlet(name = "userServlet", urlPatterns = {"/user/*"})
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final UserDAO userDAO;
	private final JsonConverter jsonConverter;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
		super();
		this.userDAO = new UserDAO();
		this.jsonConverter = new JsonConverter();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		ServletOutputStream out = response.getOutputStream();
		
		if (request.getPathInfo() != null) {
			var userId = retrieveUserId(request);
			var user = userDAO.selectUser(userId);
			
			var output = jsonConverter.convertToJson(user);

			out.print(output);
			return;
		}
		var users = userDAO.selectAllUsers();

		var output = jsonConverter.convertToJson(users);
		out.print(output);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		var reqBody = inputStreamToString(request.getInputStream());
		var user = jsonConverter.convertToUser(reqBody);
		userDAO.insertUser(user);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */		

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		var userId = retrieveUserId(request);
		System.out.println(userId);
		var reqBody = inputStreamToString(request.getInputStream());
		var user = jsonConverter.convertToUser(reqBody);
		userDAO.updateUser(userId, user);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		var userId = retrieveUserId(request);
		userDAO.deleteUser(userId);
	}

	private Integer retrieveUserId(HttpServletRequest req) {
		var pathInfo = req.getPathInfo();
		if (pathInfo.startsWith("/")) {
			pathInfo = pathInfo.substring(1);
		}
		return Integer.parseInt(pathInfo);
	}

	private String inputStreamToString(InputStream inputStream) {
		Scanner scanner = new Scanner(inputStream, "UTF-8");
		return scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
	}
}
