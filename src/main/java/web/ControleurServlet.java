package web;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import metier.ImetierCatalogue;
import metier.MetierImpl;
import metier.Produit;
@WebServlet (name = "cs",urlPatterns = {"/controleur","*.do"})
public class ControleurServlet extends HttpServlet{
	private ImetierCatalogue metier ;
	
	@Override
	public void init() throws ServletException{
		metier = new MetierImpl();
	}
	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String path=request.getServletPath();
		if (path.equals("/index.do"))
		{
			request.getRequestDispatcher("ProduitsView.jsp").forward(request,response);
		}
		else if (path.equals("/saisi.do") )
		{
			request.getRequestDispatcher("SaisiProduit.jsp").forward(request,response);
		}
		else if (path.equals("/save.do") && request.getMethod().equals("POST"))
		{
			String nom=request.getParameter("nom");
			double prix = Double.parseDouble(request.getParameter("prix"));
			Produit p = metier.addProduit(new Produit(nom,prix));
			request.setAttribute("produit", p);
			request.getRequestDispatcher("Confirmation.jsp").forward(request,response);
		}
		else if (path.equals("/supprimer.do"))
		{
			Long id= Long.parseLong(request.getParameter("id"));
			metier.deleteProduit(id);
			response.sendRedirect("/Projet_JEE/index.do");
		}
		else if (path.equals("/editer.do") )
		{
		Long id= Long.parseLong(request.getParameter("id"));
		Produit p = metier.getProduit(id);
		request.setAttribute("produit", p);
		request.getRequestDispatcher("editerProduit.jsp").forward(request,response);
		}
		else if (path.equals("/update.do") )
		{
		Long id = Long.parseLong(request.getParameter("id"));
		String nom=request.getParameter("nom");
		double prix = Double.parseDouble(request.getParameter("prix"));
		Produit p = new Produit();
		p.setIdProduit(id);
		p.setNomProduit(nom);
		p.setPrix(prix);
		metier.updateProduit(p);
		request.setAttribute("produit", p);
		request.getRequestDispatcher("Confirmation.jsp").forward(request,response);
		}
		else
		{
			response.sendError(response.SC_NOT_FOUND);
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
 
		String path=req.getServletPath();
		if(path.equals("/save.do"))
		{
			String lib=req.getParameter("nom");
			double prix=Double.parseDouble(req.getParameter("prix"));
		
			Produit p=metier.addProduit(new Produit(lib,prix));
			req.setAttribute("produit", p);
			req.getRequestDispatcher("Confirmation.jsp").forward(req, resp);
			
		}
		else if (path.equals("/search.do"))
		{		
		String mc = req.getParameter("motCle");
		ProduitModele mod = new ProduitModele();
		mod.setMotCle(mc);
		List<Produit> prods = metier.getProduitsParMotCle(mc);
		mod.setProduits(prods);
		req.setAttribute("model", mod);
		req.getRequestDispatcher("ProduitsView.jsp").forward(req, resp);
		}
		
	}	

}
