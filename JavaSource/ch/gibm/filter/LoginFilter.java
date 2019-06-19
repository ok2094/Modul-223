package ch.gibm.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ch.gibm.entity.User;

public class LoginFilter implements Filter {
	private static List<String> allowedURIs;

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		if (allowedURIs == null) {
			allowedURIs = new ArrayList<String>();
			allowedURIs.add(fConfig.getInitParameter("loginActionURI"));
			allowedURIs.add("/JSFApp/javax.faces.resource/main.css.xhtml");
			allowedURIs.add("/JSFApp/javax.faces.resource/theme.css.xhtml");
			allowedURIs.add("/JSFApp/javax.faces.resource/primefaces.js.xhtml");
			allowedURIs.add("/JSFApp/javax.faces.resource/primefaces.css.xhtml");

			allowedURIs.add("/JSFApp/javax.faces.resource/jquery/jquery.js.xhtml");
		}
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(); // 1
		if (session.isNew()) { // 2
			doLogin(request, response, req);
			return;
		}
		User user = (User) session.getAttribute("user"); // 3
		if (user == null && !allowedURIs.contains(req.getRequestURI())) { // 4
			System.out.println(req.getRequestURI());
			doLogin(request, response, req);
			return;
		}
		chain.doFilter(request, response); // 5
	}

	protected void doLogin(ServletRequest request, ServletResponse response, HttpServletRequest req)
			throws ServletException, IOException {

		RequestDispatcher rd = req.getRequestDispatcher("/pages/public/login.xhtml");
		rd.forward(request, response);
	}

	protected void accessDenied(ServletRequest request, ServletResponse response, HttpServletRequest req)
			throws ServletException, IOException {

		RequestDispatcher rd =

				req.getRequestDispatcher("/pages/public/accessDenied.xhtml");

		rd.forward(request, response);
	}
}
