package metier;

import java.util.List;

public class TestMetier {
	public static void main (String[]args) {
		MetierImpl metier = new MetierImpl();
		
		Produit prod= metier.addProduit(new Produit("iphone 8 plus",2800));
		System.out.println(prod);
		
		List<Produit> prods = metier.getProduitsParMotCle("le");
		for (Produit p : prods)
			System.out.println(p.getNomProduit());
	}

}
