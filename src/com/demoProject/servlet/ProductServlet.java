package com.demoProject.servlet;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.demoProject.dao.ProductDaoImpl;
import com.demoProject.pojo.Product;



@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	ProductDaoImpl ud=new ProductDaoImpl();
	public ProductServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action=request.getParameter("action");
		HttpSession session=request.getSession();
		if(action!=null && action.equals("getProduct")) {

			String category=request.getParameter("category");
			List<Product> pl=ud.getAllProduct(category);
			session.setAttribute("pl",pl);
			session.setAttribute("category","category");
			response.sendRedirect("index.jsp");
		}
		
		else if(action!=null && action.equals("delete")) {
			int pid=Integer.parseInt(request.getParameter("id"));
			boolean b=ud.deleteProduct(pid);
			if(b) {
				response.sendRedirect("ProductServlet");
			}

		}
		else if(action!=null && action.equals("edit")) {
			int pid=Integer.parseInt(request.getParameter("id"));
			Product pl=ud.getProductById(pid);
			session.setAttribute("pl", pl);
			response.sendRedirect("updateProduct.jsp");
		}
		else  {

			List<Product> pl=ud.getAllProduct();
			System.out.println(pl);
			session.setAttribute("pl",pl);
			response.sendRedirect("productList.jsp");
		}}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		Product product=new Product();
		String pname=request.getParameter("pname");
		String pdescription=request.getParameter("pdescription");
		double pprice=Double.parseDouble(request.getParameter("price"));
		String pcategory=request.getParameter("category");
		product.setPname(pname);
		product.setPdescription(pdescription);
		product.setPrice(pprice);
		product.setCategory(pcategory);

		boolean b=ud.addProduct(product);
		if(b) {
			response.sendRedirect("success.jsp");
		}else {
			response.sendRedirect("error.jsp");
		}




	}
}
